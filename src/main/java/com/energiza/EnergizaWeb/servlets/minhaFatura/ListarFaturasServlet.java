package com.energiza.EnergizaWeb.servlets.minhaFatura;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;
import com.energiza.EnergizaWeb.modelos.minhaFatura.Fatura;
import com.energiza.EnergizaWeb.modelos.minhaFatura.FaturaDAO;
import com.energiza.EnergizaWeb.modelos.Usuario;

@WebServlet("/servlet/listarFaturas/*")
public class ListarFaturasServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private FaturaDAO faturaDAO;
    
    public ListarFaturasServlet() {
        super();
        faturaDAO = new FaturaDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        try {
            // Verificar se o usuário está logado
            Usuario usuario = SessionManager.getInstance().getCurrentUser(request);
            if (usuario == null) {
                out.print("{\"success\": false, \"message\": \"Usuário não logado\"}");
                return;
            }
            
            // Obter parâmetros de filtro
            String unidadeConsumoStr = request.getParameter("unidadeConsumo");
            
            List<Fatura> faturas;
            
            if (unidadeConsumoStr != null && !unidadeConsumoStr.trim().isEmpty()) {
                int unidadeConsumo = Integer.parseInt(unidadeConsumoStr);
                faturas = faturaDAO.getFaturasByUnidadeConsumo(unidadeConsumo);
            } else {
                faturas = faturaDAO.listarTodasFaturas();
            }
            
            // Construir JSON manualmente seguindo o padrão do MinhaConta
            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{\"success\": true, \"faturas\": [");
            
            if (faturas != null && !faturas.isEmpty()) {
                for (int i = 0; i < faturas.size(); i++) {
                    Fatura fatura = faturas.get(i);
                    jsonBuilder.append("{");
                    jsonBuilder.append("\"idFatura\": ").append(fatura.getIdFatura()).append(",");
                    jsonBuilder.append("\"referencia\": \"").append(escapeJson(fatura.getReferencia())).append("\",");
                    jsonBuilder.append("\"vencimento\": \"").append(fatura.getVencimento() != null ? fatura.getVencimento().toString() : "").append("\",");
                    jsonBuilder.append("\"idUnidadeConsumo\": ").append(fatura.getIdUnidadeConsumo()).append(",");
                    jsonBuilder.append("\"valorTotal\": ").append(fatura.getValorTotal() != null ? fatura.getValorTotal() : "0");
                    jsonBuilder.append("}");
                    
                    if (i < faturas.size() - 1) {
                        jsonBuilder.append(",");
                    }
                }
            }
            
            jsonBuilder.append("]}");
            out.print(jsonBuilder.toString());
            
        } catch (NumberFormatException e) {
            out.print("{\"success\": false, \"message\": \"ID da unidade de consumo inválido\"}");
        } catch (Exception e) {
            System.out.println("Erro ao listar faturas: " + e.getMessage());
            out.print("{\"success\": false, \"message\": \"Erro interno do servidor\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Método POST não é suportado");
    }
    
    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
