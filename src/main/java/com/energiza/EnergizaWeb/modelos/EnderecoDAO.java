package com.energiza.EnergizaWeb.modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.energiza.EnergizaWeb.utils.Conexao;

public class EnderecoDAO {

	public Endereco getEndereco(int idEndereco) {
        
		Endereco ed = new Endereco();
		
        try {
        	
	        Connection con = Conexao.conectar();
	
	        PreparedStatement stm = con.prepareStatement("SELECT * FROM ENDERECOS WHERE ID_ENDERECO = ?");	
        	
        	stm.setInt(1, idEndereco);           
        	ResultSet rs = stm.executeQuery(); 
        	
            while (rs.next()) {
            	ed.setId(rs.getInt("ID_ENDERECO"));
            	ed.setCep(rs.getString("CEP"));
            	ed.setEndereco(rs.getString("ENDERECO"));
            	ed.setNumero(rs.getString("NUMERO"));
            	ed.setComplemento(rs.getString("COMPLEMENTO"));
            	ed.setBairro(rs.getString("BAIRRO"));
            	ed.setCidade(rs.getString("CIDADE"));
            	ed.setEstado(rs.getString("ESTADO"));
            	ed.setPais(rs.getString("PAIS"));
            }
            
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ed;
    }
}
