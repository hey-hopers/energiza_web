package com.energiza.EnergizaWeb.modelos.usina;

import java.sql.*;
import java.util.*;

import com.energiza.EnergizaWeb.utils.Conexao;

public class UsinaDAO {

    public boolean inserir(Usina usina, int idUsuario) {
        String sql = "INSERT INTO UC_USINAS (IDENTIFICACAO, PERC_PERDA_MES, ID_UNIDADE_CONSUMO) VALUES (?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, usina.getIdentificacao());
            stmt.setDouble(2, usina.getPorcentagem());
            stmt.setInt(3, usina.getIdUnidadeConsumo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(Usina usina) {
        String sql = "UPDATE UC_USINAS SET IDENTIFICACAO=?, PERC_PERDA_MES=?, ID_UNIDADE_CONSUMO=? WHERE ID_UC_USINAS=?";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, usina.getIdentificacao());
            stmt.setDouble(2, usina.getPorcentagem());
            stmt.setInt(3, usina.getIdUnidadeConsumo());
            stmt.setInt(4, usina.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Usina> getUsinasByUsuario(int idUsuario) {
    	
        List<Usina> lista = new ArrayList<>();
        
        try {
        		
        String sql = "SELECT * FROM UC_USINAS "
        		+ "INNER JOIN UNIDADE_CONSUMO ON UNIDADE_CONSUMO.ID_UNIDADE_CONSUMO = UC_USINAS.ID_UNIDADE_CONSUMO "
        		+ "INNER JOIN USUARIO ON UNIDADE_CONSUMO.ID_PESSOA_FJ_PROPRIETARIO = USUARIO.ID_PESSOA "
        		+ "WHERE USUARIO.ID = ?"; 

        Connection con = Conexao.conectar();
        
        PreparedStatement stmt = con.prepareStatement(sql);
        
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Usina u = new Usina();
            u.setId(rs.getInt("ID_UC_USINAS"));
            u.setIdentificacao(rs.getString("IDENTIFICACAO"));
            u.setPorcentagem(rs.getDouble("PERC_PERDA_MES"));
            u.setIdUnidadeConsumo(rs.getInt("ID_UNIDADE_CONSUMO"));
            lista.add(u);
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM UC_USINAS WHERE ID_UC_USINAS=?";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
