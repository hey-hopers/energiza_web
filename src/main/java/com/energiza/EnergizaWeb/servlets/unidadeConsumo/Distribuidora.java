package com.energiza.EnergizaWeb.servlets.unidadeConsumo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.utils.Conexao;

@WebServlet("/distribuidoras")
public class Distribuidora extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try (Connection con = Conexao.conectar();
            PreparedStatement stmt = con.prepareStatement("SELECT ID_UC_DISTRIBUIDORA, NOME FROM UC_DISTRIBUIDORA");
            ResultSet rs = stmt.executeQuery()) {

            StringBuilder jsonBuilder = new StringBuilder("[");
            boolean first = true;

            while (rs.next()) {
                if (!first) {
                    jsonBuilder.append(",");
                }
                first = false;

                jsonBuilder.append("{");
                jsonBuilder.append("\"id\":" + rs.getInt("ID_UC_DISTRIBUIDORA") + ",");
                jsonBuilder.append("\"nome\":\"" + escapeJsonString(rs.getString("NOME")) + "\"");
                jsonBuilder.append("}");
            }

            jsonBuilder.append("]");
            out.print(jsonBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"erro\": \"Erro ao buscar distribuidoras.\"}");
        }
    }

    // Método auxiliar para escapar caracteres especiais em strings JSON
    private String escapeJsonString(String input) {
        if (input == null) {
            return "";
        }
        
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}