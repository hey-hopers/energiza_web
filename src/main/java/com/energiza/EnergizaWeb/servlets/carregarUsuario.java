package com.energiza.EnergizaWeb.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.UsuarioDAO;


@WebServlet("/mostrarUsuario")
public class carregarUsuario extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int usuarioId = 1;
        
        UsuarioDAO UsuarioDAO = new UsuarioDAO();
        Usuario usuario = UsuarioDAO.getUsuarioById(usuarioId);
        
        // Enviar os dados para o JSP
        request.setAttribute("usuario", usuario);
        RequestDispatcher dispatcher = request.getRequestDispatcher("minhaConta.html");
        dispatcher.forward(request, response);
    }
}



