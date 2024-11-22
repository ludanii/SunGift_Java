package br.com.fiap.resource;

import br.com.fiap.bo.DoacaoBO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.DoacaoDAO;
import br.com.fiap.dto.Doacoes;
import br.com.fiap.exceptions.DAOException;
import br.com.fiap.exceptions.ValidacaoException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Path("doacao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class DoacoesResource {

    private DoacaoDAO doacaoDAO;
    private ModelMapper modelMapper;
    private DoacaoBO doacaoBO = new DoacaoBO();

    public DoacoesResource() {
        this.doacaoDAO = new DoacaoDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }

    public static class MensagemErro {
        private String tipo;
        private String mensagem;

        public MensagemErro(String tipo, String mensagem) {
            this.tipo = tipo;
            this.mensagem = mensagem;
        }

        public String getTipo() {
            return tipo;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    @POST
    public Response cadastrar(Doacoes doacoes, @Context UriInfo uriInfo) {
        try {
            doacoes.setDT_DOACAO(LocalDate.now());
            int descontoAtual = doacaoDAO.obterDescontoTotalCondominio(doacoes.getID_CONDOMINIO());
            doacoes.setNR_DESCONTOS(doacaoBO.calcularDesconto(doacoes, descontoAtual));
            doacaoDAO.inserir(doacoes);
            UriBuilder uri = uriInfo.getBaseUriBuilder();
            uri.path(String.valueOf(doacoes.getID_DOACOES()));
            return Response.created(uri.build()).entity(doacoes).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new MensagemErro("Erro de validação", e.getMessage()))
                    .build();
        } catch (DAOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensagemErro("Erro no banco de dados", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensagemErro("Erro inesperado", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("{ID_CONDOMINIO}")
    public List<Doacoes> buscar(@PathParam("ID_CONDOMINIO") int ID_CONDOMINIO) throws SQLException {
        return doacaoDAO.buscarPorCondominio(ID_CONDOMINIO);
    }

    @GET
    public List<Doacoes> listar() {
        return doacaoDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_DOACOES}")
    public Response atualizar(Doacoes doacoes, @PathParam("ID_DOACOES") int ID_DOACOES) {
        try {
            doacoes.setID_DOACOES(ID_DOACOES);
            doacoes.setDT_DOACAO(LocalDate.now());
            int descontoAtual = doacaoDAO.obterDescontoTotalCondominio(doacoes.getID_CONDOMINIO());
            doacoes.setNR_DESCONTOS(doacaoBO.calcularDesconto(doacoes, descontoAtual));
            doacaoDAO.alterar(doacoes);
            return Response.ok().entity(modelMapper.map(doacoes, Doacoes.class)).build();
        } catch (ValidacaoException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new MensagemErro("Erro de validação", e.getMessage()))
                    .build();
        } catch (DAOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensagemErro("Erro no banco de dados", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensagemErro("Erro inesperado", e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("{ID_DOACOES}")
    public void deletar(@PathParam("ID_DOACOES") int ID_DOACOES){
        doacaoDAO.deletar(ID_DOACOES);
    }
}
