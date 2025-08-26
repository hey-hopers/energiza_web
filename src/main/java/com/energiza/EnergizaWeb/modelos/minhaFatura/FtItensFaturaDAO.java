package com.energiza.EnergizaWeb.modelos.minhaFatura;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.energiza.EnergizaWeb.utils.Conexao;

public class FtItensFaturaDAO {
    
    // Construtor padrão
    public FtItensFaturaDAO() {
    }
    
    // Inserir novo item de fatura
    public int incluirItemFatura(FtItensFatura item) {
        String sql = "INSERT INTO FT_ITENS_FATURA (ITEM, UNIDADE, QUANTIDADE, VALOR, ID_FATURA) VALUES (?, ?, ?, ?, ?)";
        Connection con = Conexao.conectar();
        int idItem = -1;
        
        try {
            PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, item.getItem());
            stm.setString(2, item.getUnidade());
            stm.setBigDecimal(3, item.getQuantidade());
            stm.setBigDecimal(4, item.getValor());
            stm.setInt(5, item.getIdFatura());
            
            int linhasAfetadas = stm.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    idItem = rs.getInt(1);
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro ao incluir item de fatura: " + ex.getMessage());
        }
        
        return idItem;
    }
    
    // Buscar item por ID
    public FtItensFatura getItemById(int idItem) {
        FtItensFatura item = null;
        String sql = "SELECT * FROM FT_ITENS_FATURA WHERE ID_FT_ITENS_FATURA = ?";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            
            stm.setInt(1, idItem);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                item = new FtItensFatura();
                item.setIdFtItensFatura(rs.getInt("ID_FT_ITENS_FATURA"));
                item.setItem(rs.getString("ITEM"));
                item.setUnidade(rs.getString("UNIDADE"));
                item.setQuantidade(rs.getBigDecimal("QUANTIDADE"));
                item.setValor(rs.getBigDecimal("VALOR"));
                item.setIdFatura(rs.getInt("ID_FATURA"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar item de fatura: " + e.getMessage());
        }
        
        return item;
    }
    
    // Buscar itens por ID da fatura
    public List<FtItensFatura> getItensByFatura(int idFatura) {
        List<FtItensFatura> itens = new ArrayList<>();
        String sql = "SELECT * FROM FT_ITENS_FATURA WHERE ID_FATURA = ? ORDER BY ID_FT_ITENS_FATURA";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            
            stm.setInt(1, idFatura);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                FtItensFatura item = new FtItensFatura();
                item.setIdFtItensFatura(rs.getInt("ID_FT_ITENS_FATURA"));
                item.setItem(rs.getString("ITEM"));
                item.setUnidade(rs.getString("UNIDADE"));
                item.setQuantidade(rs.getBigDecimal("QUANTIDADE"));
                item.setValor(rs.getBigDecimal("VALOR"));
                item.setIdFatura(rs.getInt("ID_FATURA"));
                itens.add(item);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens da fatura: " + e.getMessage());
        }
        
        return itens;
    }
    
    // Atualizar item
    public boolean atualizarItemFatura(FtItensFatura item) {
        String sql = "UPDATE FT_ITENS_FATURA SET ITEM = ?, UNIDADE = ?, QUANTIDADE = ?, VALOR = ? WHERE ID_FT_ITENS_FATURA = ?";
        Connection con = Conexao.conectar();
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, item.getItem());
            stm.setString(2, item.getUnidade());
            stm.setBigDecimal(3, item.getQuantidade());
            stm.setBigDecimal(4, item.getValor());
            stm.setInt(5, item.getIdFtItensFatura());
            
            int linhasAfetadas = stm.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar item de fatura: " + ex.getMessage());
            return false;
        }
    }
    
    // Excluir item
    public boolean excluirItemFatura(int idItem) {
        String sql = "DELETE FROM FT_ITENS_FATURA WHERE ID_FT_ITENS_FATURA = ?";
        Connection con = Conexao.conectar();
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idItem);
            
            int linhasAfetadas = stm.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir item de fatura: " + ex.getMessage());
            return false;
        }
    }
    
    // Excluir todos os itens de uma fatura
    public boolean excluirItensByFatura(int idFatura) {
        String sql = "DELETE FROM FT_ITENS_FATURA WHERE ID_FATURA = ?";
        Connection con = Conexao.conectar();
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idFatura);
            
            int linhasAfetadas = stm.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir itens da fatura: " + ex.getMessage());
            return false;
        }
    }
}
