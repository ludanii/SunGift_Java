package br.com.fiap.dao;

import br.com.fiap.dto.Materiais;
import br.com.fiap.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MateriaisDAO {
    private Connection con;

    public MateriaisDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(Materiais materiais){
        String sql = "insert into SUNGIFT_TB_MATERIAIS (TP_MATERIAIS) " +
                "VALUES (?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, materiais.getTP_MATERIAIS());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                throw new SQLException("Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir material no banco de dados.", e);
        }
    }


    public void alterar(Materiais materiais) throws SQLException {
        String sql = "UPDATE SUNGIFT_TB_MATERIAIS SET TP_MATERIAIS = ? WHERE ID_MATERIAIS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, materiais.getTP_MATERIAIS());
            ps.setInt(2, materiais.getID_MATERIAIS());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Nenhum material encontrado com o ID: " + materiais.getID_MATERIAIS());
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao alterar material no banco de dados.", e);
        }
    }

    public String deletar(int ID_MATERIAIS) {
        String sql = "delete from SUNGIFT_TB_MATERIAIS where ID_MATERIAIS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_MATERIAIS);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar no banco de dados.", e);
        }
    }

    public ArrayList<Materiais> ListarTodos() {
        String sql = "select * from SUNGIFT_TB_MATERIAIS order by ID_MATERIAIS";
        ArrayList<Materiais> listaMateriais = new ArrayList<Materiais>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Materiais materiais = new Materiais();
                    materiais.setID_MATERIAIS(rs.getInt("ID_MATERIAIS"));
                    materiais.setTP_MATERIAIS(rs.getString("TP_MATERIAIS"));
                    listaMateriais.add(materiais);
                }
                return listaMateriais;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar material no banco de dados.", e);
        }

    }

    public Materiais buscarPorId(int ID_MATERIAIS) {
        String sql = "select * from SUNGIFT_TB_MATERIAIS where ID_MATERIAIS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_MATERIAIS);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Materiais materiais = new Materiais();
                    materiais.setID_MATERIAIS(rs.getInt("ID_MATERIAIS"));
                    materiais.setTP_MATERIAIS(rs.getString("TP_MATERIAIS"));
                    return materiais;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar material no banco de dados.", e);
        }
    }
}
