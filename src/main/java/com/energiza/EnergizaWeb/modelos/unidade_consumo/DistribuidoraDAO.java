package com.energiza.EnergizaWeb.modelos.unidade_consumo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.energiza.EnergizaWeb.utils.Conexao;

public class DistribuidoraDAO {

	// 🔹 Construtor Padrão
    public DistribuidoraDAO() {
    }
    
    public List<Distribuidora> getDistribuidoras() {
    	
        List<Distribuidora> lista = new ArrayList<>();
        
        try {
        		
			String sql = "SELECT * FROM UC_DISTRIBUIDORA"; 

			Connection con = Conexao.conectar();
			
			PreparedStatement stmt = con.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Distribuidora d = new Distribuidora();
				d.setId(rs.getInt("ID_UC_DISTRIBUIDORA"));
				d.setNome(rs.getString("NOME"));
				lista.add(d);
			}

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public Distribuidora getDistribuidora(int idDistribuidora) {
    	
        Distribuidora dist = new Distribuidora();
        
        try {
			Connection con = Conexao.conectar();
			
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM UC_DISTRIBUIDORA WHERE ID_UC_DISTRIBUIDORA = ?");
			
			stmt.setInt(1, idDistribuidora);   
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				dist.setId(rs.getInt("ID_UC_DISTRIBUIDORA"));
				dist.setNome(rs.getString("NOME"));
			}

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dist;
    }
}
