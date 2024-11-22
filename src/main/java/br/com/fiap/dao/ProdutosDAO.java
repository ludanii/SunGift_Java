package br.com.fiap.dao;

import br.com.fiap.dto.Condominios;
import br.com.fiap.dto.Produtos;
import br.com.fiap.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {
    private Connection con;

    public ProdutosDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(Produtos produtos){
        String sql = "insert into SUNGIFT_TB_PRODUTOS (NM_PRODUTO, DS_PRODUTO, NR_QUANTIDADE, NR_PRECO) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, produtos.getNM_PRODUTO());
            ps.setString(2, produtos.getDS_PRODUTO());
            ps.setInt(3, produtos.getNR_QUANTIDADE());
            ps.setDouble(4, produtos.getNR_PRECO());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                throw new SQLException("Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir produto no banco de dados.", e);
        }
    }


    public void alterar(Produtos produtos) throws SQLException {
        String sql = "UPDATE SUNGIFT_TB_PRODUTOS SET NM_PRODUTO = ?, DS_PRODUTO = ?, NR_QUANTIDADE = ?, NR_PRECO = ? WHERE ID_PRODUTOS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, produtos.getNM_PRODUTO());
            ps.setString(2, produtos.getDS_PRODUTO());
            ps.setInt(3, produtos.getNR_QUANTIDADE());
            ps.setDouble(4, produtos.getNR_PRECO());
            ps.setInt(5, produtos.getID_PRODUTOS());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Nenhum produto encontrado com o ID: " + produtos.getID_PRODUTOS());
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao alterar produto no banco de dados.", e);
        }
    }

    public String deletar(int ID_PRODUTOS) {
        String sql = "delete from SUNGIFT_TB_PRODUTOS where ID_PRODUTOS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_PRODUTOS);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar no banco de dados.", e);
        }
    }

    public ArrayList<Produtos> ListarTodos() {
        String sql = "select * from SUNGIFT_TB_PRODUTOS order by ID_PRODUTOS";
        ArrayList<Produtos> listaProdutos = new ArrayList<Produtos>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Produtos produtos = new Produtos();
                    produtos.setID_PRODUTOS(rs.getInt("ID_PRODUTOS"));
                    produtos.setNM_PRODUTO(rs.getString("NM_PRODUTO"));
                    produtos.setDS_PRODUTO(rs.getString("DS_PRODUTO"));
                    produtos.setNR_QUANTIDADE(rs.getInt("NR_QUANTIDADE"));
                    produtos.setNR_PRECO(rs.getDouble("NR_PRECO"));
                    listaProdutos.add(produtos);
                }
                return listaProdutos;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar produto no banco de dados.", e);
        }

    }

    public Produtos buscarPorId(int ID_PRODUTOS) {
        String sql = "select * from SUNGIFT_TB_PRODUTOS where ID_PRODUTOS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_PRODUTOS);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produtos produtos = new Produtos();
                    produtos.setID_PRODUTOS(rs.getInt("ID_PRODUTOS"));
                    produtos.setNM_PRODUTO(rs.getString("NM_PRODUTO"));
                    produtos.setDS_PRODUTO(rs.getString("DS_PRODUTO"));
                    produtos.setNR_QUANTIDADE(rs.getInt("NR_QUANTIDADE"));
                    produtos.setNR_PRECO(rs.getDouble("NR_PRECO"));
                    return produtos;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar produto no banco de dados.", e);
        }
    }
}
