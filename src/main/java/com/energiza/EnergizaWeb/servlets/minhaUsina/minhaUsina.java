package com.energiza.EnergizaWeb.servlets.minhaUsina;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;
import com.energiza.EnergizaWeb.modelos.usina.Usina;
import com.energiza.EnergizaWeb.modelos.usina.UsinaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet/minhaUsina/*")
public class minhaUsina extends HttpServlet {
    private UsinaDAO usinaDAO;

    @Override
    public void init() throws ServletException {
        usinaDAO = new UsinaDAO();
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

        List<Usina> usinas = usinaDAO.getUsinasByUsuario(usuario.getId());
        StringBuilder json = new StringBuilder();
        json.append("{\"success\": true, \"usinas\": [");

        for (int i = 0; i < usinas.size(); i++) {
            Usina u = usinas.get(i);
            json.append(String.format(
                "{\"id\": %d, \"identificacao\": \"%s\", \"porcentagem\": %.2f, \"idUnidadeConsumo\": %d}",
                u.getId(), escapeJson(u.getIdentificacao()), u.getPorcentagem(), u.getIdUnidadeConsumo()
            ));
            if (i < usinas.size() - 1) json.append(",");
        }

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

        try {
            String idStr = request.getParameter("id");
            String identificacao = request.getParameter("identificacao");
            String porcentagemStr = request.getParameter("porcentagem");

            Usina usina = new Usina();
            usina.setIdentificacao(identificacao);
            usina.setPorcentagem(Double.parseDouble(porcentagemStr));

            if (idStr != null && !idStr.isEmpty()) {
                usina.setId(Integer.parseInt(idStr));
                boolean atualizado = usinaDAO.atualizar(usina);
                if (atualizado) {
                    response.getWriter().print("{\"success\": true, \"message\": \"Usina atualizada com sucesso\"}");
                } else {
                    response.getWriter().print("{\"success\": false, \"message\": \"Falha ao atualizar usina\"}");
                }
            } else {
                boolean inserido = usinaDAO.inserir(usina, usuario.getId());
                if (inserido) {
                    response.getWriter().print("{\"success\": true, \"message\": \"Usina criada com sucesso\"}");
                } else {
                    response.getWriter().print("{\"success\": false, \"message\": \"Falha ao criar usina\"}");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("{\"success\": false, \"message\": \"Erro interno\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (idStr == null || idStr.isEmpty()) {
            response.getWriter().print("{\"success\": false, \"message\": \"ID não informado\"}");
            return;
        }

        int id = Integer.parseInt(idStr);
        boolean deletado = usinaDAO.excluir(id);

        if (deletado) {
            response.getWriter().print("{\"success\": true, \"message\": \"Usina excluída com sucesso\"}");
        } else {
            response.getWriter().print("{\"success\": false, \"message\": \"Erro ao excluir usina\"}");
        }
    }

    private String escapeJson(String valor) {
        return valor == null ? "" : valor.replace("\"", "\\\"");
    }
}
