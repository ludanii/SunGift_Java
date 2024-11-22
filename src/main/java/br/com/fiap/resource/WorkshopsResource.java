package br.com.fiap.resource;

import br.com.fiap.bo.WorkshopsBO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.WorkshopsDAO;
import br.com.fiap.dto.Workshops;
import br.com.fiap.exceptions.DAOException;
import br.com.fiap.exceptions.ValidacaoException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.Getter;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.List;

@Path("workshop")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class WorkshopsResource {

    private WorkshopsDAO workshopsDAO;
    private ModelMapper modelMapper;
    private WorkshopsBO workshopsBO = new WorkshopsBO();

    public WorkshopsResource() {
        this.workshopsDAO = new WorkshopsDAO(ConnectionFactory.abrirConexao());
        modelMapper = new ModelMapper();
    }

    @Getter
    public static class MensagemErro {
        private String tipo;
        private String mensagem;

        public MensagemErro(String tipo, String mensagem) {
            this.tipo = tipo;
            this.mensagem = mensagem;
        }

    }

    @POST
    public Response cadastrar(Workshops workshops, @Context UriInfo uriInfo){
        try {
            workshops.definirStatus();
            workshopsBO.validarWorkshops(workshops);
            workshopsDAO.inserir(workshops);
            UriBuilder uri = uriInfo.getBaseUriBuilder();
            uri.path(String.valueOf(workshops.getID_WORKSHOPS()));
            return Response.created(uri.build()).entity(workshops).build();
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
    @Path("{ID_WORKSHOPS}")
    public Workshops buscar(@PathParam("ID_WORKSHOPS") int ID_WORKSHOPS) throws SQLException {
        return workshopsDAO.buscarPorId(ID_WORKSHOPS);
    }

    @GET
    public List<Workshops> listar() {
        return workshopsDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_WORKSHOPS}")
    public Response atualizar(Workshops workshops, @PathParam("ID_WORKSHOPS") int ID_WORKSHOPS) {
        try {
            workshops.setID_WORKSHOPS(ID_WORKSHOPS);
            workshops.definirStatus();
            workshopsBO.validarWorkshops(workshops);
            workshopsDAO.alterar(workshops);
            return Response.ok().entity(modelMapper.map(workshops, Workshops.class)).build();
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
    @Path("{ID_WORKSHOPS}")
    public void deletar(@PathParam("ID_WORKSHOPS") int ID_WORKSHOPS) {
        workshopsDAO.deletar(ID_WORKSHOPS);
    }
}
