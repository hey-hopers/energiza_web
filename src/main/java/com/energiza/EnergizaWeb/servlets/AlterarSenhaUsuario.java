package com.energiza.EnergizaWeb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.UsuarioDAO;

@WebServlet("/servlet/AlterarSenhaUsuario")
public class AlterarSenhaUsuario extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        UsuarioDAO u = new UsuarioDAO(); 
        
        if(request.getParameter("senha") == request.getParameter("confirma-senha")){
        	
        	if (u.alterarSenhaUsuario(request.getParameter("email"), request.getParameter("senha"))) {
            	request.setAttribute("OK", "Senha alterada.");
                response.sendRedirect("login.jsp");
                
            } else {
                request.setAttribute("erro", "Senha inválida.");
                request.getRequestDispatcher("recuperarSenha.jsp").forward(request, response);
                
            }
        	
        } else {
        	request.setAttribute("erro", "As senhas não coincidem, favor digitar a mesma senha.");
            request.getRequestDispatcher("recuperarSenha.jsp").forward(request, response);
            
        }
        	
        
    }
}

