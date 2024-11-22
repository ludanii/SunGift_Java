package br.com.fiap.resource;

import br.com.fiap.bo.CondominioBO;
import br.com.fiap.dao.CondominioDAO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dto.Condominios;
import br.com.fiap.exceptions.DAOException;
import br.com.fiap.exceptions.ValidacaoException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("condominios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class CondominiosResource {

    private CondominioDAO condominioDAO;
    private ModelMapper modelMapper;
    private CondominioBO condominioBO = new CondominioBO();

    public CondominiosResource() {
        this.condominioDAO = new CondominioDAO(ConnectionFactory.abrirConexao());
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
    public Response cadastrar(Condominios condominios, @Context UriInfo uriInfo){
        try {
            condominios.cepFormatado();
            condominios.formatandoLetras();
        condominios.logradouroFormatado();
        condominioBO.validarRegistro(condominios);
        condominioDAO.inserir(condominios);
        UriBuilder uri = uriInfo.getBaseUriBuilder();
        uri.path(String.valueOf(condominios.getID_CONDOMINIO()));
        return Response.created(uri.build()).entity(condominios).build();
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
    @Path("{NM_EMAIL}")
    public Condominios buscar(@PathParam("NM_EMAIL") String NM_EMAIL) throws SQLException {
        return condominioDAO.buscarPorEmail(NM_EMAIL);
    }

    @GET
    public List<Condominios> listar() {
        return condominioDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_CONDOMINIO}")
    public Response atualizar(Condominios condominios, @PathParam("ID_CONDOMINIO") int ID_CONDOMINIO) {
        try {
            condominios.setID_CONDOMINIO(ID_CONDOMINIO);
            condominios.cepFormatado();
            condominios.formatandoLetras();
            condominios.logradouroFormatado();
            condominioBO.validarRegistro(condominios);
            condominioDAO.alterar(condominios);
            return Response.ok().entity(modelMapper.map(condominios, Condominios.class)).build();
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
    @Path("{ID_CONDOMINIO}")
    public void deletar(@PathParam("ID_CONDOMINIO") int ID_CONDOMINIO) {
        condominioDAO.deletar(ID_CONDOMINIO);
    }
}
