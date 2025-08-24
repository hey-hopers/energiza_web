package com.energiza.EnergizaWeb.modelos.pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.energiza.EnergizaWeb.modelos.Usuario;
import com.energiza.EnergizaWeb.modelos.usina.Usina;
import com.energiza.EnergizaWeb.servlets.unidadeConsumo.Operadores;
import com.energiza.EnergizaWeb.utils.Conexao;

public class PessoaFJDAO {
	
	// 🔹 Construtor Padrão
    public PessoaFJDAO() {
    }
    
    public PessoaFJ getPessoaFJByIdUser(int usuarioID) {
		
    	PessoaFJ pessoa = null;
        
        try {
        		
        	Connection con = Conexao.conectar();
        	String sql = "SELECT FJ.ID_PESSOAS_FJ, FJ.APELIDO, DOC.NUMERO, LOC.CEP, LOC.ENDERECO, LOC.NUMERO, LOC.COMPLEMENTO, LOC.BAIRRO, LOC.CIDADE, LOC.ESTADO, LOC.PAIS "
					+ "FROM PESSOAS_FJ FJ "
        			+ "INNER JOIN USUARIO US ON US.ID_PESSOA = FJ.ID_PESSOAS_FJ "
        			+ "INNER JOIN DOCUMENTOS DOC ON DOC.ID_PESSOAS_FJ = FJ.ID_PESSOAS_FJ "
        			+ "INNER JOIN LOCALIZACAO LOC ON LOC.ID_PESSOAS_FJ = FJ.ID_PESSOAS_FJ "
        			+ "WHERE US.ID = ?";
        	
        	PreparedStatement stm = con.prepareStatement(sql);
            
        	stm.setInt(1, usuarioID);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	pessoa = new PessoaFJ();
            	pessoa.setId(rs.getInt("ID_PESSOAS_FJ"));           	
            	pessoa.setNome(rs.getString("APELIDO"));
            	pessoa.setDocumento(rs.getString("NUMERO"));
            	pessoa.setCep(rs.getString("CEP"));
            	pessoa.setEndereco(rs.getString("ENDERECO"));
            	pessoa.setNumero(rs.getString("NUMERO"));
            	pessoa.setComplemento(rs.getString("COMPLEMENTO"));
            	pessoa.setBairro(rs.getString("BAIRRO"));
            	pessoa.setCidade(rs.getString("CIDADE"));
            	pessoa.setEstado(rs.getString("ESTADO"));
            	pessoa.setPais(rs.getString("PAIS"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pessoa;
    }

    public boolean getPessoaCadastrada(int pessoaID) {
        
        try {
        	
        	PessoaFJ pessoa = null;
        	
        	Connection con = Conexao.conectar();
        	
        	String sql = "SELECT * FROM PESSOAS_FJ FJ INNER JOIN USUARIO US ON US.ID_PESSOA = FJ.ID_PESSOAS_FJ WHERE US.ID = ?";
        	
        	PreparedStatement stm = con.prepareStatement(sql);
            
        	stm.setInt(1, pessoaID);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

	public PessoaFJ getPessoa(int usuarioID) {	
        
		PessoaFJ pessoa = null;
		
        try {
        	
        	Connection con = Conexao.conectar();
    		
    		
        		
        	String sql = "SELECT FJ.ID_PESSOAS_FJ, FJ.APELIDO, DOC.NUMERO, LOC.CEP, LOC.ENDERECO, LOC.NUMERO, LOC.COMPLEMENTO, LOC.BAIRRO, LOC.CIDADE, LOC.ESTADO, LOC.PAIS "
        					+ "FROM PESSOAS_FJ FJ "
		        			+ "INNER JOIN USUARIO US ON US.ID_PESSOA = FJ.ID_PESSOAS_FJ "
		        			+ "INNER JOIN DOCUMENTOS DOC ON DOC.ID_PESSOAS_FJ = FJ.ID_PESSOAS_FJ "
		        			+ "INNER JOIN LOCALIZACAO LOC ON LOC.ID_PESSOAS_FJ = FJ.ID_PESSOAS_FJ "
		        			+ "WHERE US.ID = ?";
        	
        	PreparedStatement stm = con.prepareStatement(sql);
            
        	System.out.println("ID Select: "+ usuarioID);
        	
        	stm.setInt(1, usuarioID);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	pessoa = new PessoaFJ();
            	pessoa.setId(rs.getInt("ID_PESSOAS_FJ"));           	
            	pessoa.setNome(rs.getString("APELIDO"));
            	pessoa.setDocumento(rs.getString("NUMERO"));
            	pessoa.setCep(rs.getString("CEP"));
            	pessoa.setEndereco(rs.getString("ENDERECO"));
            	pessoa.setNumero(rs.getString("NUMERO"));
            	pessoa.setComplemento(rs.getString("COMPLEMENTO"));
            	pessoa.setBairro(rs.getString("BAIRRO"));
            	pessoa.setCidade(rs.getString("CIDADE"));
            	pessoa.setEstado(rs.getString("ESTADO"));
            	pessoa.setPais(rs.getString("PAIS"));
            	
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println(pessoa);
        return pessoa;
    }
	
    public boolean inserirPessoaFJ(PessoaFJ pessoa, Usuario usuario) throws SQLException {
        
        try {

        	Connection con = Conexao.conectar();
        	
        	PreparedStatement stm = con.prepareStatement("INSERT INTO PESSOAS_FJ (APELIDO) VALUES (?)", 
        			Statement.RETURN_GENERATED_KEYS);
	        
	        stm.setString(1, pessoa.getNome());
        	stm.executeUpdate();

        	ResultSet rs = stm.getGeneratedKeys();
        	int id_pessoa = -1;

        	if (rs.next()) {
        	    id_pessoa = rs.getInt(1);
        	}

        	rs.close();
        	stm.close();
	        
	        stm = con.prepareStatement("INSERT INTO DOCUMENTOS (NUMERO, ID_PESSOAS_FJ) VALUES (?, ?)");
	        
	        stm.setString(1, pessoa.getDocumento());	        
	        stm.setInt(2, id_pessoa);
	        
	        stm.executeUpdate();
	        
	        stm = con.prepareStatement("INSERT INTO LOCALIZACAO (CEP, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, PAIS, ID_PESSOAS_FJ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
	        
	        stm.setString(1, pessoa.getCep());
	        stm.setString(2, pessoa.getEndereco());
	        stm.setString(3, pessoa.getNumero());
	        stm.setString(4, pessoa.getComplemento());
	        stm.setString(5, pessoa.getBairro());
	        stm.setString(6, pessoa.getCidade());
	        stm.setString(7, pessoa.getEstado());
	        stm.setString(8, pessoa.getPais());	        
	        stm.setInt(9, id_pessoa);
	        
	        stm.executeUpdate();  
	        
	        stm = con.prepareStatement("UPDATE USUARIO SET ID_PESSOA = ? where ID = ?");
	        	        
	        stm.setInt(1, id_pessoa);
	        stm.setInt(2, usuario.getId());
	        
	        stm.executeUpdate();
	        
	        con.close();
	        return true;
            
	    } catch (SQLException ex) {
	        System.out.println("Erro ao atualizar usuário: " + ex.getMessage());
	        return false;
	    }
  
    }
    
    public boolean atualizarPessoaFJ(PessoaFJ pessoa)  throws SQLException {

	    try {
	    	
	    	Connection con = Conexao.conectar();
	    	
	    	PreparedStatement stm = con.prepareStatement("UPDATE PESSOAS_FJ SET APELIDO = ? WHERE ID_PESSOAS_FJ = ?");
	        
	        stm.setString(1, pessoa.getNome());	        
	        stm.setInt(2, pessoa.getId());
	        
	        stm.executeUpdate();
	        
	        stm = con.prepareStatement("UPDATE DOCUMENTOS SET NUMERO = ? WHERE ID_PESSOAS_FJ = ?");
	        
	        stm.setString(1, pessoa.getDocumento());	        
	        stm.setInt(2, pessoa.getId());
	        
	        stm.executeUpdate();
	        
	        stm = con.prepareStatement("UPDATE LOCALIZACAO SET CEP = ?, ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, ESTADO = ?, PAIS = ? WHERE ID_PESSOAS_FJ = ?");
	        
	        stm.setString(1, pessoa.getCep());
	        stm.setString(2, pessoa.getEndereco());
	        stm.setString(3, pessoa.getNumero());
	        stm.setString(4, pessoa.getComplemento());
	        stm.setString(5, pessoa.getBairro());
	        stm.setString(6, pessoa.getCidade());
	        stm.setString(7, pessoa.getEstado());
	        stm.setString(8, pessoa.getPais());
	        
	        stm.setInt(9, pessoa.getId());
	        stm.executeUpdate();
	        
	        con.close();
	        return true;

	    } catch (SQLException ex) {
	        System.out.println("Erro ao atualizar usuário: " + ex.getMessage());
	        return false;
	    }
	}
    
}

