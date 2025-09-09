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
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJ;
import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJDAO;

@WebServlet("/servlet/operadores/*")
public class Operadores  extends HttpServlet {
	private PessoaFJDAO pessoaDAO;
	
	@Override
    public void init() throws ServletException {
        pessoaDAO = new PessoaFJDAO();
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

        List<PessoaFJ> pessoas = pessoaDAO.getPessoaFJByUsuario(usuario.getId());
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"operadores\": [");

        for (int i = 0; i < pessoas.size(); i++) {
        	PessoaFJ fj = pessoas.get(i);
            json.append(String.format(
                "{\"id\": %d, \"apelido\": \"%s\"}",
                fj.getId(), escapeJson(fj.getApelido())));
            if (i < pessoas.size() - 1) json.append(",");
        }

        json.append("]}");
        out.print(json.toString());
    }
	
	private String escapeJson(String valor) {
        return valor == null ? "" : valor.replace("\"", "\\\"");
    }
}
