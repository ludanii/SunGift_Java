package br.com.fiap.dao;

import br.com.fiap.dto.Condominios;
import br.com.fiap.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class CondominioDAO {
    private Connection con;

    public CondominioDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(Condominios condominios){
        String sql = "insert into SUNGIFT_TB_CONDOMINIO (NM_CONDOMINIO, NR_CEP, NM_BAIRRO, NM_ESTADO, NM_CIDADE, DS_COMPLEMENTO, DS_SENHA, NM_LOGRADOURO, NM_EMAIL) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, condominios.getNM_CONDOMINIO());
            ps.setString(2, condominios.getNR_CEP());
            ps.setString(3, condominios.getNM_BAIRRO());
            ps.setString(4, condominios.getNM_ESTADO());
            ps.setString(5, condominios.getNM_CIDADE());
            ps.setInt(6, condominios.getDS_COMPLEMENTO());
            ps.setString(7, condominios.getDS_SENHA());
            ps.setString(8, condominios.getNM_LOGRADOURO());
            ps.setString(9, condominios.getNM_EMAIL());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                throw new SQLException("Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir condomínio no banco de dados.", e);
        }
    }


    public void alterar(Condominios condominios) throws SQLException {
        String sql = "UPDATE SUNGIFT_TB_CONDOMINIO SET NM_CONDOMINIO = ?, NR_CEP = ?, NM_BAIRRO = ?, NM_ESTADO = ?, NM_CIDADE = ?, DS_COMPLEMENTO = ?, DS_SENHA = ?, " +
                "NM_LOGRADOURO = ?, NM_EMAIL = ? WHERE ID_CONDOMINIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, condominios.getNM_CONDOMINIO());
            ps.setString(2, condominios.getNR_CEP());
            ps.setString(3, condominios.getNM_BAIRRO());
            ps.setString(4, condominios.getNM_ESTADO());
            ps.setString(5, condominios.getNM_CIDADE());
            ps.setInt(6, condominios.getDS_COMPLEMENTO());
            ps.setString(7, condominios.getDS_SENHA());
            ps.setString(8, condominios.getNM_LOGRADOURO());
            ps.setString(9, condominios.getNM_EMAIL());
            ps.setInt(10, condominios.getID_CONDOMINIO());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Nenhum usuário encontrado com o ID: " + condominios.getID_CONDOMINIO());
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao alterar condomínio no banco de dados.", e);
        }
    }

    public String deletar(int ID_CONDOMINIO) {
        String sql = "delete from SUNGIFT_TB_CONDOMINIO where ID_CONDOMINIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_CONDOMINIO);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar no banco de dados.", e);
        }
    }

    public ArrayList<Condominios> ListarTodos() {
        String sql = "select * from SUNGIFT_TB_CONDOMINIO order by ID_CONDOMINIO";
        ArrayList<Condominios> listaCondominio = new ArrayList<Condominios>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Condominios condominio = new Condominios();
                    condominio.setID_CONDOMINIO(rs.getInt("ID_CONDOMINIO"));
                    condominio.setNM_CONDOMINIO(rs.getString("NM_CONDOMINIO"));
                    condominio.setNR_CEP(rs.getString("NR_CEP"));
                    condominio.setNM_BAIRRO(rs.getString("NM_BAIRRO"));
                    condominio.setNM_ESTADO(rs.getString("NM_ESTADO"));
                    condominio.setNM_CIDADE(rs.getString("NM_CIDADE"));
                    condominio.setDS_COMPLEMENTO(rs.getInt("DS_COMPLEMENTO"));
                    condominio.setDS_SENHA(rs.getString("DS_SENHA"));
                    condominio.setNM_LOGRADOURO(rs.getString("NM_LOGRADOURO"));
                    condominio.setNM_EMAIL(rs.getString("NM_EMAIL"));
                    listaCondominio.add(condominio);
                }
                return listaCondominio;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar condomínio no banco de dados.", e);
        }

    }

    public Condominios buscarPorEmail(String NM_EMAIL) throws SQLException {
        String sql = "select * from SUNGIFT_TB_CONDOMINIO where NM_EMAIL = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, NM_EMAIL);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Condominios condominio = new Condominios();
                    condominio.setNM_EMAIL(rs.getString("NM_EMAIL"));
                    condominio.setID_CONDOMINIO(rs.getInt("ID_CONDOMINIO"));
                    condominio.setNM_CONDOMINIO(rs.getString("NM_CONDOMINIO"));
                    condominio.setNR_CEP(rs.getString("NR_CEP"));
                    condominio.setNM_BAIRRO(rs.getString("NM_BAIRRO"));
                    condominio.setNM_ESTADO(rs.getString("NM_ESTADO"));
                    condominio.setNM_CIDADE(rs.getString("NM_CIDADE"));
                    condominio.setDS_COMPLEMENTO(rs.getInt("DS_COMPLEMENTO"));
                    condominio.setDS_SENHA(rs.getString("DS_SENHA"));
                    condominio.setNM_LOGRADOURO(rs.getString("NM_LOGRADOURO"));
                    return condominio;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar condomínio no banco de dados.", e);
        }
    }
}
