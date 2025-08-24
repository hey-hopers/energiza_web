package com.energiza.EnergizaWeb.servlets.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.*;

@WebServlet("/servlet/cadastroUsuario")
public class cadastroUsuario extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String contextPath = request.getContextPath();
    	
    	request.setCharacterEncoding("UTF-8");
    	
        // Obtém os parâmetros do formulário
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String confirmaSenha = request.getParameter("confirmaSenha");
        String termos = request.getParameter("termos");
        
        // Validações básicas
        if (senha.equals(confirmaSenha) && termos != null) {
        	
        	Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNome(nome);
            usuario.setSenha(senha);
            
            UsuarioDAO dao = new UsuarioDAO();
            if (dao.incluirUsuario(usuario)) {
            	response.sendRedirect(contextPath + "/menuPrincipal.html");
            }       
            
        } else {
            response.getWriter().println("Erro no cadastro. Verifique os campos.");
        }
    }
}
