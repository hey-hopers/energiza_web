package com.energiza.EnergizaWeb.servlets.minhaConta;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.UsuarioDAO;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;

@WebServlet("/servlet/minhaConta/*")
public class MinhaConta extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;

    public MinhaConta() {
        super();
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        Usuario usuarioLogado = (Usuario) request.getSession(false).getAttribute("CURRENT_USER");
        
        Usuario usuario = usuarioDAO.getUsuarioById(usuarioLogado.getId());       
        
        if (usuario == null) {
            out.print("{\"success\": false, \"message\": \"Usuário não logado\"}");
            return;
        }
        
        String json = String.format(
            "{" +
            "\"success\": true," +
            "\"usuario\": {" +
            "\"nome\": \"%s\"," +
            "\"email\": \"%s\"," +
            "\"whatsapp\": \"%s\"," +
            "\"telefone\": \"%s\"," +
            "\"dataNascimento\": \"%s\"," +
            "\"dataCadastro\": \"%s\"" +
            "}" +
            "}",
            escapeJson(usuario.getNome()),
            escapeJson(usuario.getEmail()),
            escapeJson(usuario.getWhatsapp()),
            escapeJson(usuario.getTelefone()),
            usuario.getDataNascimento() != null ? usuario.getDataNascimento().toString() : "",
            usuario.getDataCadastro() != null ? usuario.getDataCadastro().toString() : ""
        );
        out.print(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            salvarUsuario(request, response);
        } else {
            enviarRespostaErro(response, "Operação não suportada");
        }
    }

    private void salvarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();

        try {
        	
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String whatsapp = request.getParameter("whatsapp");
            String telefone = request.getParameter("telefone");
            String dataNascimentoStr = request.getParameter("data_nascimento");
            
            Usuario usuario = SessionManager.getInstance().getCurrentUser(request);   
            
            if (usuario == null) {
                out.print("{\"success\": false, \"message\": \"Usuário não logado\"}");
                return;   
            }
            
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setWhatsapp(whatsapp);
            usuario.setTelefone(telefone);

            if (dataNascimentoStr != null && !dataNascimentoStr.isEmpty()) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsed = format.parse(dataNascimentoStr);
                    usuario.setDataNascimento(new Date(parsed.getTime()));
                } catch (ParseException e) {
                    enviarRespostaErro(response, "Formato de data inválido");
                    return;
                }
            }

        	if (usuarioDAO.atualizarUsuario(usuario)) {
                out.print("{\"success\": true, \"message\": \"Usuário editado com sucesso!\"}");
            } else {
                enviarRespostaErro(response, "Erro ao editar usuário");
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            enviarRespostaErro(response, "Erro interno: " + e.getMessage());
        }
    }

    private void enviarRespostaErro(HttpServletResponse response, String mensagem)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String json = String.format("{\"success\": false, \"message\": \"%s\"}", escapeJson(mensagem));
        out.print(json);
    }

    private String escapeJson(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
