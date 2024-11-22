package br.com.fiap.dao;

import br.com.fiap.dto.Condominios;
import br.com.fiap.dto.Doacoes;
import br.com.fiap.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;

public class DoacaoDAO {
    private Connection con;

    public DoacaoDAO(Connection con) {
        this.con = con;
    }

    public Connection getCon() {
        return con;
    }

    public int obterDescontoTotalCondominio(int ID_CONDOMINIO) {
        String sql = "SELECT COALESCE(SUM(NR_DESCONTOS), 0) AS TOTAL_DESCONTO " +
                "FROM SUNGIFT_TB_DOACOES WHERE ID_CONDOMINIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_CONDOMINIO);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("TOTAL_DESCONTO");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao obter o desconto total do condomínio.", e);
        }
        return 0;
    }


    public String inserir(Doacoes doacoes){
        String sql = "insert into SUNGIFT_TB_DOACOES (ID_CONDOMINIO, ID_MATERIAIS, NR_QUANTIDADE, NR_DESCONTOS, DT_DOACAO) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, doacoes.getID_CONDOMINIO());
            ps.setInt(2, doacoes.getID_MATERIAIS());
            ps.setInt(3, doacoes.getNR_QUANTIDADE());
            ps.setInt(4, doacoes.getNR_DESCONTOS());
            ps.setDate(5, Date.valueOf(doacoes.getDT_DOACAO()));
            if (ps.executeUpdate() > 0) {
                return "\nInserido com sucesso";
            } else {
                throw new SQLException("Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir doacao no banco de dados.", e);
        }
    }


    public void alterar(Doacoes doacoes) throws SQLException {
        String sql = "UPDATE SUNGIFT_TB_DOACOES SET ID_CONDOMINIO = ?, ID_MATERIAIS = ?, NR_QUANTIDADE = ?, NR_DESCONTOS = ?, DT_DOACAO = ? " +
                "WHERE ID_DOACOES = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, doacoes.getID_CONDOMINIO());
            ps.setInt(2, doacoes.getID_MATERIAIS());
            ps.setInt(3, doacoes.getNR_QUANTIDADE());
            ps.setInt(4, doacoes.getNR_DESCONTOS());
            ps.setDate(5, Date.valueOf(doacoes.getDT_DOACAO()));
            ps.setInt(6, doacoes.getID_DOACOES());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Nenhum doacao encontrado com o ID: " + doacoes.getID_DOACOES());
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar doacao no banco de dados.", e);
        }
    }

    public String deletar(int ID_DOACOES) {
        String sql = "delete from SUNGIFT_TB_DOACOES where ID_DOACOES = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_DOACOES);
            if (ps.executeUpdate() > 0) {
                return "\nDeletado com sucesso";
            } else {
                return "\nErro ao deletar";
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar no banco de dados.", e);
        }
    }

    public ArrayList<Doacoes> ListarTodos() {
        String sql = "select * from SUNGIFT_TB_DOACOES order by ID_DOACOES";
        ArrayList<Doacoes> listaDoacoes = new ArrayList<Doacoes>();
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Doacoes doacoes = new Doacoes();
                    doacoes.setID_DOACOES(rs.getInt("ID_DOACOES"));
                    doacoes.setID_CONDOMINIO(rs.getInt("ID_CONDOMINIO"));
                    doacoes.setID_MATERIAIS(rs.getInt("ID_MATERIAIS"));
                    doacoes.setNR_QUANTIDADE(rs.getInt("NR_QUANTIDADE"));
                    doacoes.setNR_DESCONTOS(rs.getInt("NR_DESCONTOS"));
                    doacoes.setDT_DOACAO(rs.getDate("DT_DOACAO").toLocalDate());
                    listaDoacoes.add(doacoes);
                }
                return listaDoacoes;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar doacao no banco de dados.", e);
        }

    }

    public ArrayList<Doacoes> buscarPorCondominio(int ID_CONDOMINIO) throws SQLException {
        String sql = "select * from SUNGIFT_TB_DOACOES where ID_CONDOMINIO = ?";
        try (PreparedStatement ps = getCon().prepareStatement(sql)) {
            ps.setInt(1, ID_CONDOMINIO);
            ArrayList<Doacoes> listaDoacoes = new ArrayList<Doacoes>();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs != null) {
                    while (rs.next()) {
                        Doacoes doacoes = new Doacoes();
                        doacoes.setID_DOACOES(rs.getInt("ID_DOACOES"));
                        doacoes.setID_CONDOMINIO(rs.getInt("ID_CONDOMINIO"));
                        doacoes.setID_MATERIAIS(rs.getInt("ID_MATERIAIS"));
                        doacoes.setNR_QUANTIDADE(rs.getInt("NR_QUANTIDADE"));
                        doacoes.setNR_DESCONTOS(rs.getInt("NR_DESCONTOS"));
                        doacoes.setDT_DOACAO(rs.getDate("DT_DOACAO").toLocalDate());
                        listaDoacoes.add(doacoes);
                    }
                    return listaDoacoes;
                } else {
                    return null;
                }
            }
        }catch (SQLException e) {
            throw new DAOException("Erro ao buscar doacao no banco de dados.", e);
        }
    }
}
