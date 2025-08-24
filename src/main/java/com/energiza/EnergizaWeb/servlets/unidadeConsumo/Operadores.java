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

//        List<PessoaFJ> pessoas = pessoaDAO.getOperadores();
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"operadores\": [");

//        for (int i = 0; i < pessoas.size(); i++) {
//        	PessoaFJ fj = pessoas.get(i);
//            json.append(String.format(
//                "{\"id\": %d, \"nome\": \"%s}",
//                fj.getId(), escapeJson(fj.getNome())));
//            if (i < pessoas.size() - 1) json.append(",");
//        }

        json.append("]}");
        out.print(json.toString());
    }
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario usuario = SessionManager.getInstance().getCurrentUser(request);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (usuario == null) {
            response.getWriter().print("{\"success\": false, \"message\": \"Usuário não logado\"}");
            return;
        }

        System.out.println("Olaaa");
//        try {
//        	
//            String idStr = request.getParameter("id");
//            String identificacao = request.getParameter("identificacao");
//            String porcentagemStr = request.getParameter("porcentagem");
//
//            Usina usina = new Usina();
//            usina.setIdentificacao(identificacao);
//            usina.setPorcentagem(Double.parseDouble(porcentagemStr));
//
//            if (idStr != null && !idStr.isEmpty()) {
//                usina.setId(Integer.parseInt(idStr));
//                boolean atualizado = usinaDAO.atualizar(usina);
//                if (atualizado) {
//                    response.getWriter().print("{\"success\": true, \"message\": \"Usina atualizada com sucesso\"}");
//                } else {
//                    response.getWriter().print("{\"success\": false, \"message\": \"Falha ao atualizar usina\"}");
//                }
//            } else {
//                boolean inserido = usinaDAO.inserir(usina, usuario.getId());
//                if (inserido) {
//                    response.getWriter().print("{\"success\": true, \"message\": \"Usina criada com sucesso\"}");
//                } else {
//                    response.getWriter().print("{\"success\": false, \"message\": \"Falha ao criar usina\"}");
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().print("{\"success\": false, \"message\": \"Erro interno\"}");
//        }
    }
	
	private String escapeJson(String valor) {
        return valor == null ? "" : valor.replace("\"", "\\\"");
    }
}
