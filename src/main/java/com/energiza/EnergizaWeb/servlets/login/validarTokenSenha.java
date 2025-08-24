package com.energiza.EnergizaWeb.servlets.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.UsuarioDAO;

@WebServlet("/servlet/validarTokenSenha")
public class validarTokenSenha extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UsuarioDAO dao = new UsuarioDAO(); 
        
        if (dao.validarToken(request.getParameter("token"))) {
        	response.sendRedirect("recuperarSenha.jsp?mostrarSenha=true");
            
        } else {
            request.setAttribute("erro", "Token inválido ou expirado.");
            request.getRequestDispatcher("recuperarSenha.jsp").forward(request, response);
            
        }
    }
}
