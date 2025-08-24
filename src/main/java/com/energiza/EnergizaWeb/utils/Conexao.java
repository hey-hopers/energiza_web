package com.energiza.EnergizaWeb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
	public static Connection conectar() {
        Connection con = null;
        String url = "jdbc:sqlserver://localhost:1433;databaseName=projetoenergiza;encrypt=false";
        String user = "sa";
        String password = "Ener2025@";

        try {
            // Carrega o driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Estabelece a conexão
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException ex) {
            System.out.println("Erro: Driver JDBC não encontrado.");
            ex.printStackTrace(); // Exibe detalhes do erro no console

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar com o banco de dados.");
            System.out.println("Mensagem: " + ex.getMessage());
            System.out.println("Código de erro: " + ex.getErrorCode());
            System.out.println("Estado SQL: " + ex.getSQLState());
            ex.printStackTrace(); // Exibe o stack trace completo no console

        } catch (Exception ex) {
            System.out.println("Ocorreu um erro inesperado.");
            ex.printStackTrace(); // Exibe qualquer outro erro inesperado
        }

        return con;
    }
}
