package br.com.fiap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection abrirConexao() {
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";
            final String USER = "rm555292";
            final String PASS = "070905";
            con = DriverManager.getConnection(url,USER, PASS);
            System.out.print("Conexao aberta.");
        } catch (ClassNotFoundException e) {
            System.out.printf("Erro: a classe de conecao nao foi encontrada!\n" +
                    e.getMessage());
        } catch (SQLException e) {
            System.out.printf("Erro de SQL: " +
                    e.getMessage());
        } catch (Exception e) {
            System.out.printf("Erro: " +
                    e.getMessage());
        }
        return con;
    }

    public static void fecharConexao(Connection con) {
        try {
            con.close();
            System.out.print("Conexao fechada.");
        } catch (SQLException e) {
            System.out.printf("Erro de SQL: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    public static void commit(Connection con) {
        try {
            if (con != null) {
                con.commit();
                System.out.println("Transação confirmada com sucesso.");
            }
        } catch (SQLException e) {
            System.out.printf("Erro de SQL ao realizar commit: " + e.getMessage());
        }
    }
    public static void rollback(Connection con) {
        try {
            if (con != null) {
                con.rollback();
                System.out.println("Transação revertida.");
            }
        } catch (SQLException e) {
            System.out.printf("Erro de SQL ao realizar rollback: " + e.getMessage());
        }
    }

}
