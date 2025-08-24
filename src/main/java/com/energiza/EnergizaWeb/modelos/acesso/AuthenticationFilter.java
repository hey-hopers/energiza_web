package com.energiza.EnergizaWeb.modelos.acesso;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.energiza.EnergizaWeb.modelos.Usuario;

public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String uri = httpRequest.getRequestURI();
        
        // Verificar se é uma página que requer autenticação
        if (requiresAuthentication(uri)) {
            Usuario currentUser = SessionManager.getInstance().getCurrentUser(httpRequest);
            
            if (currentUser == null) {
                // Redirecionar para página de login
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.html");
                return;
            }
            
            // Atualizar timestamp da última atividade
            SessionManager.getInstance().updateActivity(httpRequest);
        }
        
        chain.doFilter(request, response);
    }
    
    private boolean requiresAuthentication(String uri) {
        // Lógica para determinar quais páginas são protegidas
        return !uri.endsWith("/index.html") && !uri.contains("/servlet/") && !uri.contains("/public/");
    }
    
    @Override
    public void destroy() {}
}