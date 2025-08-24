package com.energiza.EnergizaWeb.servlets.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.UsuarioDAO;
import com.energiza.EnergizaWeb.modelos.acesso.SessionManager;

@WebServlet("/servlet/login")
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private UsuarioDAO userDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    	String contextPath = request.getContextPath();
    	
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (email == null || senha == null || email.isEmpty() || senha.isEmpty()) {
        	response.sendRedirect(contextPath + "index.html?erro=2");
            return;
        }

        try {
            Usuario user = userDAO.podeLogar(email, senha);

            if (user != null) {
                SessionManager.getInstance().createUserSession(request, user);

                response.sendRedirect(contextPath + "/menuPrincipal.html");


            } else {
            	response.sendRedirect(contextPath + "/index.html?erro=1");
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao processar login.");
        }
    }
}
