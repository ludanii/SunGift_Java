package br.com.fiap.dao;

import br.com.fiap.dto.Condominios;
import br.com.fiap.dto.Workshops;
import br.com.fiap.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class WorkshopsDAO {
    private Connection con;

    public WorkshopsDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public String inserir(Workshops workshops){
        String sql = "insert into SUNGIFT_TB_WORKSHOPS (NM_WORKSHOP, DS_WORKSHOP, NM_LOGRADOURO, TP_WORKSHOPS, DT_WORKSHOP, ST_WORKSHOP) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, workshops.getNM_WORKSHOP());
            ps.setString(2, workshops.getDS_WORKSHOP());
            ps.setString(3, workshops.getNM_LOGRADOURO());
            ps.setString(4, workshops.getTP_WORKSHOPS());
            ps.setDate(5, Date.valueOf(workshops.getDT_WORKSHOP()));
            ps.setString(6, workshops.getST_WORKSHOP());
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                throw new SQLException("Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir workshop no banco de dados.", e);
        }
    }


    public void alterar(Workshops workshops) throws SQLException {
        String sql = "UPDATE SUNGIFT_TB_WORKSHOPS SET NM_WORKSHOP = ?, DS_WORKSHOP = ?, NM_LOGRADOURO = ?, TP_WORKSHOPS = ?, DT_WORKSHOP = ?, ST_WORKSHOP = ? WHERE ID_WORKSHOPS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setString(1, workshops.getNM_WORKSHOP());
            ps.setString(2, workshops.getDS_WORKSHOP());
            ps.setString(3, workshops.getNM_LOGRADOURO());
            ps.setString(4, workshops.getTP_WORKSHOPS());
            ps.setDate(5, Date.valueOf(workshops.getDT_WORKSHOP()));
            ps.setString(6, workshops.getST_WORKSHOP());
            ps.setInt(7, workshops.getID_WORKSHOPS());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Nenhum workshop encontrado com o ID: " + workshops.getID_WORKSHOPS());
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao alterar workshop no banco de dados.", e);
        }
    }

    public String deletar(int ID_WORKSHOPS) {
        String sql = "delete from SUNGIFT_TB_WORKSHOPS where ID_WORKSHOPS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_WORKSHOPS);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar no banco de dados.", e);
        }
    }

    public ArrayList<Workshops> ListarTodos() {
        String sql = "select * from SUNGIFT_TB_WORKSHOPS order by ID_WORKSHOPS";
        ArrayList<Workshops> listaWorkshops = new ArrayList<Workshops>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Workshops workshops = new Workshops();
                    workshops.setNM_WORKSHOP(rs.getString("NM_WORKSHOP"));
                    workshops.setDS_WORKSHOP(rs.getString("DS_WORKSHOP"));
                    workshops.setNM_LOGRADOURO(rs.getString("NM_LOGRADOURO"));
                    workshops.setTP_WORKSHOPS(rs.getString("TP_WORKSHOPS"));
                    workshops.setDT_WORKSHOP(rs.getDate("DT_WORKSHOP").toLocalDate());
                    workshops.setST_WORKSHOP(rs.getString("ST_WORKSHOP"));
                    workshops.setID_WORKSHOPS(rs.getInt("ID_WORKSHOPS"));
                    listaWorkshops.add(workshops);
                }
                return listaWorkshops;
            } else {
                return null;
            }

        }catch (SQLException e) {
            throw new DAOException("Erro ao buscar workshop no banco de dados.", e);
        }

    }

    public Workshops buscarPorId(int ID_WORKSHOPS) throws SQLException {
        String sql = "select * from SUNGIFT_TB_WORKSHOPS where ID_WORKSHOPS = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_WORKSHOPS);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Workshops workshops = new Workshops();
                    workshops.setNM_WORKSHOP(rs.getString("NM_WORKSHOP"));
                    workshops.setDS_WORKSHOP(rs.getString("DS_WORKSHOP"));
                    workshops.setNM_LOGRADOURO(rs.getString("NM_LOGRADOURO"));
                    workshops.setTP_WORKSHOPS(rs.getString("TP_WORKSHOPS"));
                    workshops.setDT_WORKSHOP(rs.getDate("DT_WORKSHOP").toLocalDate());
                    workshops.setST_WORKSHOP(rs.getString("ST_WORKSHOP"));
                    workshops.setID_WORKSHOPS(rs.getInt("ID_WORKSHOPS"));
                    return workshops;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar workshop no banco de dados.", e);
        }
    }
}
