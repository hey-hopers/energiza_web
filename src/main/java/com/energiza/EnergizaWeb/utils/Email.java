package com.energiza.EnergizaWeb.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class Email {
    
    public boolean EnviarEmail(String email, String assunto, String corpo) {
        
    	// Configurações do servidor SMTP
        String username = "hoperstrab@gmail.com";
        String password = "lfmiibfxwaqyxeoi";

        // Propriedades do SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587"); // Porta SMTP
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
    	// Autenticação e envio do e-mail
        Session session = Session.getInstance(props, new Authenticator() {
        	
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
                
            }
            
        });     	
                   
        try {
        	
        	 // Criando a mensagem
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); 
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Destinatário
            message.setSubject(assunto);
            message.setContent(corpo, "text/html; charset=utf-8");

            // Enviando o e-mail
            Transport.send(message);

            System.out.println("E-mail enviado com sucesso!");
            
        } catch (MessagingException e) {
            e.printStackTrace();
            
        } 
        
        return true;
        
    }
    
}
