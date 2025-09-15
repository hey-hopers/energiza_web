package com.energiza.EnergizaWeb.servlets.unidadeConsumo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.Distribuidora;
import com.energiza.EnergizaWeb.modelos.unidade_consumo.DistribuidoraDAO;

@SuppressWarnings("serial")
@WebServlet("/servlet/distribuidoras/*")
public class DistribuidoraServlet extends HttpServlet {
	private DistribuidoraDAO distribuidoraDAO;
	
	@Override
    public void init() throws ServletException {
		distribuidoraDAO = new DistribuidoraDAO();
    }
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = SessionManager.getInstance().getCurrentUser(request);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (usuario == null) {
            out.print("{\"success\": false, \"message\": \"Usuário não logado\"}");
            return;
        }

        List<Distribuidora> distribuidoras = distribuidoraDAO.getDistribuidoras();
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"distribuidoras\": [");

        for (int i = 0; i < distribuidoras.size(); i++) {
        	Distribuidora dist = distribuidoras.get(i);
            json.append(String.format(
                "{\"id\": %d, \"nome\": \"%s\"}",
                dist.getId(), escapeJson(dist.getNome())));
            if (i < distribuidoras.size() - 1) json.append(",");
        }

        json.append("]}");
        out.print(json.toString());
    }
	
	private String escapeJson(String valor) {
        return valor == null ? "" : valor.replace("\"", "\\\"");
    }

}