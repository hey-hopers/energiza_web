package com.energiza.EnergizaWeb.modelos.acesso;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.UsuarioDAO;

public class SessionManager {
    
    private static final SessionManager instance = new SessionManager();
    private UserSessionDAO sessionDAO = new UserSessionDAO();
    
    private SessionManager() {}
    
    public static SessionManager getInstance() {
        return instance;
    }
    
    public void createUserSession(HttpServletRequest request, Usuario user) {
        String sessionId = UUID.randomUUID().toString();
        
        // Criar objeto de sessão
        UserSession session = new UserSession();
        session.setSessionId(sessionId);
        session.setUserId(user.getId());
        session.setLoginTime(LocalDateTime.now());
        session.setLastActivity(LocalDateTime.now());
        session.setIpAddress(request.getRemoteAddr());
        session.setActive(true);
        
        // Salvar no banco de dados
        sessionDAO.createSession(session);
        
        // Armazenar na sessão HTTP
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute("SESSION_ID", sessionId);
        httpSession.setAttribute("CURRENT_USER", user);
    }
    
    public Usuario getCurrentUser(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            return (Usuario) httpSession.getAttribute("CURRENT_USER");
        }
        return null;
    }
    
    public void setCurrentUser(HttpServletRequest request, Usuario usuario) {
        request.getSession().setAttribute("CURRENT_USER", usuario);
    }
    
    public void updateActivity(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            String sessionId = (String) httpSession.getAttribute("SESSION_ID");
            if (sessionId != null) {
                sessionDAO.updateLastActivity(sessionId, LocalDateTime.now());
            }
        }
    }
    
    public void invalidateSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            String sessionId = (String) httpSession.getAttribute("SESSION_ID");
            if (sessionId != null) {
                sessionDAO.deactivateSession(sessionId);
            }
            httpSession.invalidate();
        }
    }
}
