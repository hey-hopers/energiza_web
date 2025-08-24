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
			        		+ "INNER JOIN USUARIO US ON US.ID_PESSOA = UC.ID_PESSOA_FJ_PROPRIETARIO "
			        		+ "INNER JOIN PESSOAS_FJ FJ ON FJ.ID_PESSOAS_FJ = UC.ID_PESSOA_FJ_PROPRIETARIO "
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
	
	public UnidadeConsumo getUnidadeConsumo(int usuarioID) {	
        
		UnidadeConsumo uc = null;
		
        try {
        	
        	Connection con = Conexao.conectar();
	
        	String sql = "SELECT UC.ID_UNIDADE_CONSUMO, UC.UC_CODIGO, UC.ETAPA, UC.EH_GERADORA, UC.MEDIDOR, EN.CEP, EN.ENDERECO, EN.NUMERO, EN.COMPLEMENTO, EN.BAIRRO, EN.CIDADE, EN.ESTADO, EN.PAIS, UC.ID_UC_DISTRIBUIDORA, UC.ID_PESSOA_FJ_PROPRIETARIO, UC.ID_UC_ENDERECO "
			        			+ "	FROM UNIDADE_CONSUMO UC "
			        			+ "	INNER JOIN USUARIO US ON US.ID_PESSOA = UC.ID_PESSOA_FJ_PROPRIETARIO "
			        			+ "	INNER JOIN UC_ENDERECO EN ON EN.ID_UC_ENDERECO = UC.ID_UC_ENDERECO "
			        			+ "	WHERE US.ID = ?";
        	
        	PreparedStatement stm = con.prepareStatement(sql);
        	
        	stm.setInt(1, usuarioID);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	uc = new UnidadeConsumo();
            	uc.setId(rs.getInt("ID_UNIDADE_CONSUMO")); 
            	uc.setUcCodigo(rs.getString("UC_CODIGO"));
            	uc.setEtapa(rs.getString("ETAPA"));
            	uc.setEhGeradora(rs.getBoolean("EH_GERADORA"));
            	uc.setMedidor(rs.getString("MEDIDOR"));
            	uc.setCep(rs.getString("CEP"));
            	uc.setEndereco(rs.getString("ENDERECO"));
            	uc.setNumero(rs.getString("NUMERO"));
            	uc.setComplemento(rs.getString("COMPLEMENTO"));
            	uc.setBairro(rs.getString("BAIRRO"));
            	uc.setCidade(rs.getString("CIDADE"));
            	uc.setEstado(rs.getString("ESTADO"));
            	uc.setPais(rs.getString("PAIS"));
            	uc.setUcEndereco(rs.getInt("ID_UC_ENDERECO"));
            	uc.setUcProprietario(rs.getInt("ID_PESSOA_FJ_PROPRIETARIO"));
            	
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return uc;
    }
	
	public boolean getUnidadeConsumoCadastrada(int usuarioID) {
        
        try {
        	
        	Connection con = Conexao.conectar();
        	
        	String sql = "SELECT * FROM UNIDADE_CONSUMO UC INNER JOIN USUARIO US ON US.ID_PESSOA = UC.ID_PESSOA_FJ_PROPRIETARIO WHERE US.ID = ?";
        	
        	PreparedStatement stm = con.prepareStatement(sql);
            
        	stm.setInt(1, usuarioID);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
            	return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
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

}
