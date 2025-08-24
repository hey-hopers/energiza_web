package com.energiza.EnergizaWeb.modelos.acesso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.energiza.EnergizaWeb.utils.Conexao;

public class UserSessionDAO {
    
    public void createSession(UserSession session) {
        try (Connection con = Conexao.conectar();
             PreparedStatement pstmt = con.prepareStatement(
                 "INSERT INTO user_sessions (session_id, user_id, login_time, last_activity, ip_address, is_active) " +
                 "VALUES (?, ?, ?, ?, ?, ?)")) {
            
            pstmt.setString(1, session.getSessionId());
            pstmt.setInt(2, session.getUserId());
            pstmt.setTimestamp(3, Timestamp.valueOf(session.getLoginTime()));
            pstmt.setTimestamp(4, Timestamp.valueOf(session.getLastActivity()));
            pstmt.setString(5, session.getIpAddress());
            pstmt.setBoolean(6, session.isActive());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateLastActivity(String sessionId, LocalDateTime lastActivity) {
        try (Connection con = Conexao.conectar();
             PreparedStatement pstmt = con.prepareStatement(
                 "UPDATE user_sessions SET last_activity = ? WHERE session_id = ?")) {
            
            pstmt.setTimestamp(1, Timestamp.valueOf(lastActivity));
            pstmt.setString(2, sessionId);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deactivateSession(String sessionId) {
        try (Connection con = Conexao.conectar();
             PreparedStatement pstmt = con.prepareStatement(
                 "UPDATE user_sessions SET is_active = 0 WHERE session_id = ?")) {
            
            pstmt.setString(1, sessionId);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}