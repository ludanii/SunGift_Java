package br.com.fiap.resource;

import br.com.fiap.bo.ProdutosBO;
import br.com.fiap.dao.ConnectionFactory;
import br.com.fiap.dao.ProdutosDAO;
import br.com.fiap.dto.Produtos;
import br.com.fiap.exceptions.DAOException;
import br.com.fiap.exceptions.ValidacaoException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.util.List;

@Path("produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ProdutosResorce {

    private ProdutosDAO produtosDAO;
    private ModelMapper modelMapper;
    private ProdutosBO produtosBO = new ProdutosBO();

    public ProdutosResorce() {
        this.produtosDAO = new ProdutosDAO(ConnectionFactory.abrirConexao());
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
    public Response cadastrar(Produtos produtos, @Context UriInfo uriInfo){
        try {
            produtosBO.validarProdutos(produtos);
            produtosDAO.inserir(produtos);
            UriBuilder uri = uriInfo.getBaseUriBuilder();
            uri.path(String.valueOf(produtos.getID_PRODUTOS()));
            return Response.created(uri.build()).entity(produtos).build();
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
    @Path("{ID_PRODUTOS}")
    public Produtos buscar(@PathParam("ID_PRODUTOS") int ID_PRODUTOS) {
        return produtosDAO.buscarPorId(ID_PRODUTOS);
    }

    @GET
    public List<Produtos> listar() {
        return produtosDAO.ListarTodos();
    }

    @PUT
    @Path("{ID_PRODUTOS}")
    public Response atualizar(Produtos produtos, @PathParam("ID_PRODUTOS") int ID_PRODUTOS) {
        try {
            produtos.setID_PRODUTOS(ID_PRODUTOS);
            produtosBO.validarProdutos(produtos);
            produtosDAO.alterar(produtos);
            return Response.ok().entity(modelMapper.map(produtos, Produtos.class)).build();
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
    @Path("{ID_PRODUTOS}")
    public void deletar(@PathParam("ID_PRODUTOS") int ID_PRODUTOS) {
        produtosDAO.deletar(ID_PRODUTOS);
    }
}
