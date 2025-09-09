package com.energiza.EnergizaWeb.modelos.unidade_consumo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.energiza.EnergizaWeb.modelos.pessoa.PessoaFJ;
import com.energiza.EnergizaWeb.utils.Conexao;

public class UnidadeConsumoDAO {

	public List<UnidadeConsumo> getUnidadesConsumoByUsuario(int idUsuario) {
        List<UnidadeConsumo> lista = new ArrayList<>();
        
        try {
        	
	        Connection con = Conexao.conectar();
	        
	        String sql = "SELECT UC.ID_UNIDADE_CONSUMO, UC.UC_CODIGO, FJ.APELIDO "
			        		+ "FROM UNIDADE_CONSUMO UC "
			        		+ "INNER JOIN PESSOAS_FJ FJ ON FJ.ID_PESSOAS_FJ = UC.ID_PESSOA_FJ_PROPRIETARIO "
			        		+ "INNER JOIN USUARIO US ON US.ID = FJ.ID_USUARIO "		        		
			        		+ "WHERE US.ID = ?"; 
	
	        PreparedStatement stm = con.prepareStatement(sql);	
        	
        	stm.setInt(1, idUsuario);           
        	ResultSet rs = stm.executeQuery(); 

            while (rs.next()) {
            	UnidadeConsumo uc = new UnidadeConsumo();
            	uc.setId(rs.getInt("ID_UNIDADE_CONSUMO"));
            	uc.setUcCodigo(rs.getString("UC_CODIGO"));
            	uc.setNomePessoa(rs.getString("APELIDO"));
                lista.add(uc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
	
	public boolean inserirUnidadeConsumo(UnidadeConsumo uc, PessoaFJ pessoa) throws SQLException {
        
        try {

        	ResultSet rs;
        	Connection con = Conexao.conectar();
	        
        	PreparedStatement stm = con.prepareStatement("INSERT INTO UC_ENDERECO (CEP, ENDERECO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, PAIS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
	        		Statement.RETURN_GENERATED_KEYS);
	        
	        stm.setString(1, uc.getCep());
	        stm.setString(2, uc.getEndereco());
	        stm.setString(3, uc.getNumero());
	        stm.setString(4, uc.getComplemento());
	        stm.setString(5, uc.getBairro());
	        stm.setString(6, uc.getCidade());
	        stm.setString(7, uc.getEstado());
	        stm.setString(8, uc.getPais());
	        
	        stm.executeUpdate();  
	        
	        rs = stm.getGeneratedKeys();
        	int id_endereco = -1; 

        	if (rs.next()) {
        		id_endereco = rs.getInt(1);
        	}     	
	        rs.close();
	        
	        stm = con.prepareStatement("INSERT INTO OPERADOR_ENERGETICO (ID_PESSOAS_FJ_OPERADOR) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
	        
	        stm.setInt(1, pessoa.getId());
	        
        	stm.executeUpdate();

        	rs = stm.getGeneratedKeys();
        	int id_operador = -1; 

        	if (rs.next()) {
        		id_operador = rs.getInt(1);
        	}     	
	        rs.close();
	        
        	stm = con.prepareStatement("INSERT INTO UNIDADE_CONSUMO (UC_CODIGO, MEDIDOR, ID_OPERADOR_ENERGETICO, EH_GERADORA, ETAPA, ID_PESSOA_FJ_PROPRIETARIO, ID_UC_ENDERECO, ID_UC_DISTRIBUIDORA) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	        
	        stm.setString(1, uc.getUcCodigo());
	        stm.setString(2, uc.getMedidor());
	        stm.setInt(3, id_operador);
	        stm.setBoolean(4, uc.isEhGeradora());
	        stm.setString(5, uc.getEtapa());
	        stm.setInt(6, uc.getUcProprietario());
	        stm.setInt(7, id_endereco);
	        stm.setInt(8, 1);
	        
        	stm.executeUpdate();

        	stm.close();
	        con.close();
	        
	        return true;
            
	    } catch (SQLException ex) {
	        System.out.println("Erro ao atualizar usuário: " + ex.getMessage());
	        return false;
	    }
  
    }
	
	public boolean atualizarUnidadeConsumo(UnidadeConsumo uc)  throws SQLException {

	    try {
	    	
	    	PreparedStatement stm;
	    	Connection con = Conexao.conectar();
	    	
	    	stm = con.prepareStatement("UPDATE UC_ENDERECO SET CEP = ?, ENDERECO = ?, NUMERO = ?, COMPLEMENTO = ?, BAIRRO = ?, CIDADE = ?, ESTADO = ?, PAIS = ? WHERE ID_UC_ENDERECO = ?");
	        
	        stm.setString(1, uc.getCep());
	        stm.setString(2, uc.getEndereco());
	        stm.setString(3, uc.getNumero());
	        stm.setString(4, uc.getComplemento());
	        stm.setString(5, uc.getBairro());
	        stm.setString(6, uc.getCidade());
	        stm.setString(7, uc.getEstado());
	        stm.setString(8, uc.getPais());
	        
	        stm.setInt(9, uc.getUcEndereco());
	        stm.executeUpdate();
	        
	        stm = con.prepareStatement("UPDATE UNIDADE_CONSUMO SET UC_CODIGO = ?, MEDIDOR = ?, EH_GERADORA = ?, ETAPA = ?, ID_PESSOA_FJ_PROPRIETARIO = ? WHERE ID_UNIDADE_CONSUMO = ?");
	        
	        stm.setString(1, uc.getUcCodigo());
	        stm.setString(2, uc.getMedidor());
	        stm.setBoolean(3, uc.isEhGeradora());
	        stm.setString(4, uc.getEtapa());
	        stm.setInt(5, uc.getUcProprietario());
	        
	        stm.setInt(7, uc.getId());
	        stm.executeUpdate();
	        
	        con.close();
	        return true;

	    } catch (SQLException ex) {
	        System.out.println("Erro ao atualizar usuário: " + ex.getMessage());
	        return false;
	    }
	}

	public boolean excluirUnidadeConsumo(int idUnidade) {
        try {
            Connection con = Conexao.conectar();
            
            // Primeiro, buscar o ID do endereço para excluir depois
            String sqlGetEndereco = "SELECT ID_UC_ENDERECO FROM UNIDADE_CONSUMO WHERE ID_UNIDADE_CONSUMO = ?";
            PreparedStatement stmGetEndereco = con.prepareStatement(sqlGetEndereco);
            stmGetEndereco.setInt(1, idUnidade);
            ResultSet rs = stmGetEndereco.executeQuery();
            
            int idEndereco = -1;
            if (rs.next()) {
                idEndereco = rs.getInt("ID_UC_ENDERECO");
            }
            rs.close();
            stmGetEndereco.close();
            
            if (idEndereco == -1) {
                // Unidade não encontrada
                con.close();
                return false;
            }
            
            // Excluir a unidade de consumo
            String sqlDeleteUnidade = "DELETE FROM UNIDADE_CONSUMO WHERE ID_UNIDADE_CONSUMO = ?";
            PreparedStatement stmDeleteUnidade = con.prepareStatement(sqlDeleteUnidade);
            stmDeleteUnidade.setInt(1, idUnidade);
            int rowsAffected = stmDeleteUnidade.executeUpdate();
            stmDeleteUnidade.close();
            
            if (rowsAffected > 0) {
                // Se a unidade foi excluída, excluir também o endereço
                String sqlDeleteEndereco = "DELETE FROM UC_ENDERECO WHERE ID_UC_ENDERECO = ?";
                PreparedStatement stmDeleteEndereco = con.prepareStatement(sqlDeleteEndereco);
                stmDeleteEndereco.setInt(1, idEndereco);
                stmDeleteEndereco.executeUpdate();
                stmDeleteEndereco.close();
                
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
