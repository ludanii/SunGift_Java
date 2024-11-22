package br.com.fiap.resource;

import br.com.fiap.bo.MateriaisBO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.MateriaisDAO;
import br.com.fiap.dto.Materiais;
import br.com.fiap.exceptions.DAOException;
import br.com.fiap.exceptions.ValidacaoException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.util.List;

@Path("materiais")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class MateriaisResorce {

    private MateriaisDAO materiaisDAO;
    private ModelMapper modelMapper;
    private MateriaisBO materiaisBO = new MateriaisBO();

    public MateriaisResorce() {
        this.materiaisDAO = new MateriaisDAO(ConnectionFactory.abrirConexao());
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
    public Response cadastrar(Materiais materiais, @Context UriInfo uriInfo){
        try {
            materiaisBO.validarMateriais(materiais);
            materiaisDAO.inserir(materiais);
            UriBuilder uri = uriInfo.getBaseUriBuilder();
            uri.path(String.valueOf(materiais.getID_MATERIAIS()));
            return Response.created(uri.build()).entity(materiais).build();
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
    @Path("{ID_MATERIAIS}")
    public Materiais buscar(@PathParam("ID_MATERIAIS") int ID_MATERIAIS) {
        return materiaisDAO.buscarPorId(ID_MATERIAIS);
    }

    @GET
    public List<Materiais> listar() {
        return materiaisDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_MATERIAIS}")
    public Response atualizar(Materiais materiais, @PathParam("ID_MATERIAIS") int ID_MATERIAIS) {
        try {
            materiais.setID_MATERIAIS(ID_MATERIAIS);
            materiaisBO.validarMateriais(materiais);
            materiaisDAO.alterar(materiais);
            return Response.ok().entity(modelMapper.map(materiais, Materiais.class)).build();
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
    @Path("{ID_MATERIAIS}")
    public void deletar(@PathParam("ID_MATERIAIS") int ID_MATERIAIS) {
        materiaisDAO.deletar(ID_MATERIAIS);
    }
}
