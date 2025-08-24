package com.energiza.EnergizaWeb.modelos;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import com.energiza.EnergizaWeb.utils.Conexao;
import com.energiza.EnergizaWeb.utils.UserSenha;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UsuarioDAO {
	
	// 🔹 Construtor Padrão
    public UsuarioDAO() {
    }
    
	public Usuario getUsuarioById(int usuarioID) {
		
        Usuario usuario = null;
        
        try {
        		
        	Connection con = Conexao.conectar();
        	String sql = "SELECT * FROM usuario WHERE id = ?";
        	
        	PreparedStatement stm = con.prepareStatement(sql);
            
        	stm.setInt(1, usuarioID);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setWhatsapp(rs.getString("whatsapp"));
                usuario.setTelefone(rs.getString("telefone"));
                usuario.setDataNascimento(rs.getDate("data_nascimento"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuario;
    }
	
	public boolean incluirUsuario(Usuario usuario) {
	    String sql = "INSERT INTO usuario (nome, email, senha_hash) VALUES (?, ?, ?)";
	    Connection con = Conexao.conectar();

	    try {
	        PreparedStatement stm = con.prepareStatement(sql);
	        stm.setString(1, usuario.getNome());
	        stm.setString(2, usuario.getEmail());

	        System.out.println(UserSenha.hashSenha(usuario.getSenha()));

	        stm.setString(3, UserSenha.hashSenha(usuario.getSenha()));
	        stm.execute();

	    } catch (SQLException | NoSuchAlgorithmException ex) {
	        System.out.println("Erro: " + ex.getMessage());
	        return false;
	    }

	    return true;
	}
	
	public boolean atualizarUsuario(Usuario usuario) {
	    String sql = "UPDATE usuario SET nome = ?, email = ?, whatsapp = ?, telefone = ?, data_nascimento = ? WHERE id = ?";
	    Connection con = Conexao.conectar();

	    try {
	        PreparedStatement stm = con.prepareStatement(sql);
	        stm.setString(1, usuario.getNome());
	        stm.setString(2, usuario.getEmail());
	        stm.setString(3, usuario.getWhatsapp());
	        stm.setString(4, usuario.getTelefone());
	        
	        if (usuario.getDataNascimento() != null) {
	            stm.setDate(5, (Date) usuario.getDataNascimento());
	        } else {
	            stm.setNull(5, java.sql.Types.DATE);
	        }
	        
	        stm.setInt(6, usuario.getId());
	        
	        int linhasAfetadas = stm.executeUpdate();
	        return linhasAfetadas > 0;

	    } catch (SQLException ex) {
	        System.out.println("Erro ao atualizar usuário: " + ex.getMessage());
	        return false;
	    }
	}
	
    public boolean alterarSenhaUsuario(String pEmail, String pSenha) {
    	
        String sql = "UPDATE usuario ";
        sql += " set senha_hash =?, ";
        sql += " where email= ? ";
        
        Connection con = Conexao.conectar();
        
        try {
        	
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, UserSenha.hashSenha(pSenha));
            stm.setString(2, pEmail);  
            stm.execute();
            
        } catch (SQLException | NoSuchAlgorithmException ex) {	
            System.out.println("Erro:" + ex.getMessage());
            return false;
            
        }
        
        return true;
    }
    
    public boolean consultarUsuario(String pEmail) {
       
        String sql = "select * from usuario where email = ?";

        Connection con = Conexao.conectar();
        
        try {
        	
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, pEmail);
            
            ResultSet rs = stm.executeQuery(); 
            if (!rs.next()) {
            	return false;
            	
            } 
            
        } catch (SQLException ex) {
            System.out.println("Erro:" + ex.getMessage());
            
        }  
        
        return true;
        
    }
    
    public boolean validarCadastroPessoa(String pEmail) {
    	
        String sql = "select * from usuario where email = ? and id_pessoa is not null";
        
        Connection con = Conexao.conectar();
        
        try {
        	
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, pEmail); 


            ResultSet rs = stm.executeQuery(); 
            if (!rs.next()) {
            	System.out.println("");
            	return false;
            	
            } 
            
            
        } catch (SQLException ex) {	
            System.out.println("Erro:" + ex.getMessage());
            return false;
            
        }
        
        return true;
    }

	public boolean incluirToken(String pToken, String pEmail) {

        LocalDateTime DataHoraExpiracao = LocalDateTime.now().plusMinutes(15);
        
        String sql = "insert into senha_Token (token,email,expiracao) VALUES (?,?,?)";

        Connection con = Conexao.conectar();
        
        try {
        	
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, pToken);
            stm.setString(2, pEmail);           
            stm.setTimestamp(3, Timestamp.valueOf(DataHoraExpiracao));
            stm.execute();
            
        } catch (SQLException ex) {
            System.out.println("Erro:" + ex.getMessage());
            return false;
            
        }  
        
        return true;
        
    }

    public boolean validarToken(String pToken) {
        
        String sql = "select expiracao from senha_Token where token = ?";

        Connection con = Conexao.conectar();
        
        try {
        	
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, pToken);
            
            ResultSet rs = stm.executeQuery(); 
            if (rs.next()) { 
                LocalDateTime expiracao = rs.getTimestamp(1).toLocalDateTime();
                
            	if (!LocalDateTime.now().isBefore(expiracao)) {
                    return false;                   
                }         	
            	
            } else {
            	return false;
            	
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro:" + ex.getMessage());
            
        } 
        
        return true;
        
    }
    
    //realiza consulta para validar usuario e senha pelo BD para iniciar uma sessao
    public Usuario podeLogar(String pEmail, String pSenha) throws NoSuchAlgorithmException {
    	Usuario user = null;
    	
        String sql = "select * from usuario where email = ? and senha_hash = ?";
        
        try (Connection con = Conexao.conectar();
        	PreparedStatement stm = con.prepareStatement(sql)) {
        	
            stm.setString(1, pEmail);         
            stm.setString(2, UserSenha.hashSenha(pSenha));
            
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
            	user = new Usuario();
                user.setId(rs.getInt("ID"));
                user.setNome(rs.getString("NOME"));
                user.setEmail(rs.getString("EMAIL"));
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro: " + ex.getMessage());
            
        }     
        
        return user;
    }
}



