package com.energiza.EnergizaWeb.servlets.meuNegocio;

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
import com.energiza.EnergizaWeb.modelos.meuNegocio.meuNegocio;
import com.energiza.EnergizaWeb.modelos.meuNegocio.meuNegocioDAO;

@SuppressWarnings("serial")
@WebServlet("/servlet/operadores/*")
public class OperadoresServlet  extends HttpServlet {
	private meuNegocioDAO negocioDAO;
	
	@Override
    public void init() throws ServletException {
		negocioDAO = new meuNegocioDAO();
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

        List<meuNegocio> pessoas = negocioDAO.getOperadores(usuario.getId());
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"operadores\": [");

        for (int i = 0; i < pessoas.size(); i++) {
        	meuNegocio negocio = pessoas.get(i);
            json.append(String.format(
                "{\"id\": %d, \"nome\": \"%s\"}",
                negocio.getId(), escapeJson(negocio.getNome())));
            if (i < pessoas.size() - 1) json.append(",");
        }

        json.append("]}");
        out.print(json.toString());
    }
	
	private String escapeJson(String valor) {
        return valor == null ? "" : valor.replace("\"", "\\\"");
    }
}
