package com.energiza.EnergizaWeb.servlets.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import com.energiza.EnergizaWeb.modelos.*;
import com.energiza.EnergizaWeb.utils.Email;

@WebServlet("/servlet/recuperarSenha")
public class recuperarSenha extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String contextPath = request.getContextPath();
        String email = request.getParameter("email");
        
        if (email == null || email.isEmpty()) {
        	response.sendRedirect(contextPath + "/public/recuperarSenha.html?erro=3");
            return;
        }
        
        UsuarioDAO dao = new UsuarioDAO();
        
        Usuario u = new Usuario();
        
        if (dao.consultarUsuario(email)) {
            // Simula envio de instruções por e-mail       	
        	Email e = new Email();
        	
        	String token = generateRecoveryToken();
        	
        	u.setEmail(email);
        	
        	dao.incluirToken(token, email);
        	
        	String corpo = """
                    <!DOCTYPE html>
                    <html>
                    <body style="font-family: Arial, sans-serif;">
                        <h2>Redefinição de Senha</h2>
                        <p>Olá,</p>
                        <p>Você solicitou a redefinição de senha. Utilize o token abaixo:</p>
                        <p style="font-size: 22px; font-weight: bold; text-align: center; background: #f8f9fa; padding: 10px; border-radius: 5px; border: 1px solid #ddd;">
                        """ + token + """
                        </p>
                        <p>Se você não solicitou essa alteração, ignore este e-mail.</p>
                        <p>Atenciosamente,</p>
                        <p><strong>Energiza</strong></p>
                    </body>
                    </html>
                """;
        	
        	String assunto = "Energiza - Token para Redefinição de Senha";
        	
        	if(e.EnviarEmail(email, assunto, corpo)) {
        		response.sendRedirect(contextPath + "/public/recuperarSenha.jsp?mostrarToken=true");
        		
        	} else {
        		// Não foi possivel realizar o envio do e-mail.
        		response.sendRedirect(contextPath + "/public/recuperarSenha.html?erro=2");
                
        	}
 
            
        } else {     
        	// Não encontrado!.
        	response.sendRedirect(contextPath + "/public/recuperarSenha.html?erro=1");
            
        }
    }
    
    public static String generateRecoveryToken() {
        return UUID.randomUUID().toString();
    }

}
