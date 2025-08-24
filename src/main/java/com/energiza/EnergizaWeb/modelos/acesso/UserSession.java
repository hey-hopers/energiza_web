package com.energiza.EnergizaWeb.modelos.acesso;

import java.time.LocalDateTime;

public class UserSession {
	
    private String sessionId;
    private int userId;
    private LocalDateTime loginTime;
    private LocalDateTime lastActivity;
    private String ipAddress;
    private boolean active;
	
    
    // Getters e setters
    public String getSessionId() {
		return sessionId;
	}
    
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public LocalDateTime getLoginTime() {
		return loginTime;
	}
	
	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}
	
	public LocalDateTime getLastActivity() {
		return lastActivity;
	}
	
	public void setLastActivity(LocalDateTime lastActivity) {
		this.lastActivity = lastActivity;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
}