package com.energiza.EnergizaWeb.modelos.minhaFatura;

/**
 * ========================================
 * DAO PARA OPERAÇÕES COM FATURAS
 * ========================================
 * 
 * Esta classe implementa o padrão Data Access Object (DAO) para
 * operações de banco de dados relacionadas às faturas de energia.
 * 
 * Funcionalidades:
 * - Inserção de novas faturas
 * - Busca por ID
 * - Listagem de todas as faturas
 * - Busca por unidade de consumo
 * - Atualização de faturas existentes
 * - Exclusão de faturas
 * 
 * @author Energiza Web
 * @version 1.0
 */

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.energiza.EnergizaWeb.utils.Conexao;

public class FaturaDAO {
    
    // ========================================
    // CONSTRUTOR
    // ========================================
    /**
     * Construtor padrão da classe
     */
    public FaturaDAO() {
    }
    
    // ========================================
    // OPERAÇÕES DE INSERÇÃO
    // ========================================
    /**
     * Insere uma nova fatura no banco de dados
     * @param fatura Objeto Fatura com dados a serem inseridos
     * @return ID da fatura inserida ou -1 em caso de erro
     */
    public int incluirFatura(Fatura fatura) {
        String sql = "INSERT INTO Fatura (REFERENCIA, VENCIMENTO, ID_UNIDADE_CONSUMO, VALOR_TOTAL) VALUES (?, ?, ?, ?)";
        Connection con = Conexao.conectar();
        int idFatura = -1;
        
        try {
            PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, fatura.getReferencia());
            stm.setDate(2, new Date(fatura.getVencimento().getTime()));
            stm.setInt(3, fatura.getIdUnidadeConsumo());
            stm.setBigDecimal(4, fatura.getValorTotal());
            
            int linhasAfetadas = stm.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    idFatura = rs.getInt(1);
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro ao incluir fatura: " + ex.getMessage());
        }
        
        return idFatura;
    }
    
    // ========================================
    // OPERAÇÕES DE BUSCA
    // ========================================
    /**
     * Busca uma fatura específica por seu ID
     * @param idFatura ID único da fatura a ser buscada
     * @return Objeto Fatura encontrado ou null se não encontrado
     */
    public Fatura getFaturaById(int idFatura) {
        Fatura fatura = null;
        String sql = "SELECT * FROM Fatura WHERE ID_FATURA = ?";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            
            stm.setInt(1, idFatura);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                fatura = new Fatura();
                fatura.setIdFatura(rs.getInt("ID_FATURA"));
                fatura.setReferencia(rs.getString("REFERENCIA"));
                fatura.setVencimento(rs.getDate("VENCIMENTO"));
                fatura.setIdUnidadeConsumo(rs.getInt("ID_UNIDADE_CONSUMO"));
                fatura.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar fatura: " + e.getMessage());
        }
        
        return fatura;
    }
    
    /**
     * Busca todas as faturas de uma unidade de consumo específica
     * @param idUnidadeConsumo ID da unidade de consumo
     * @return Lista de faturas ordenadas por vencimento (mais recente primeiro)
     */
    public List<Fatura> getFaturasByUnidadeConsumo(int idUnidadeConsumo) {
        List<Fatura> faturas = new ArrayList<>();
        String sql = "SELECT * FROM Fatura WHERE ID_UNIDADE_CONSUMO = ? ORDER BY VENCIMENTO DESC";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            
            stm.setInt(1, idUnidadeConsumo);
            ResultSet rs = stm.executeQuery();
            
            while (rs.next()) {
                Fatura fatura = new Fatura();
                fatura.setIdFatura(rs.getInt("ID_FATURA"));
                fatura.setReferencia(rs.getString("REFERENCIA"));
                fatura.setVencimento(rs.getDate("VENCIMENTO"));
                fatura.setIdUnidadeConsumo(rs.getInt("ID_UNIDADE_CONSUMO"));
                fatura.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));
                faturas.add(fatura);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar faturas: " + e.getMessage());
        }
        
        return faturas;
    }
    
    // ========================================
    // OPERAÇÕES DE ATUALIZAÇÃO
    // ========================================
    /**
     * Atualiza uma fatura existente no banco de dados
     * @param fatura Objeto Fatura com dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizarFatura(Fatura fatura) {
        String sql = "UPDATE Fatura SET REFERENCIA = ?, VENCIMENTO = ?, ID_UNIDADE_CONSUMO = ?, VALOR_TOTAL = ? WHERE ID_FATURA = ?";
        Connection con = Conexao.conectar();
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, fatura.getReferencia());
            stm.setDate(2, new Date(fatura.getVencimento().getTime()));
            stm.setInt(3, fatura.getIdUnidadeConsumo());
            stm.setBigDecimal(4, fatura.getValorTotal());
            stm.setInt(5, fatura.getIdFatura());
            
            int linhasAfetadas = stm.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar fatura: " + ex.getMessage());
            return false;
        }
    }
    
    // ========================================
    // OPERAÇÕES DE EXCLUSÃO
    // ========================================
    /**
     * Remove uma fatura do banco de dados
     * @param idFatura ID da fatura a ser excluída
     * @return true se excluída com sucesso, false caso contrário
     */
    public boolean excluirFatura(int idFatura) {
        String sql = "DELETE FROM Fatura WHERE ID_FATURA = ?";
        Connection con = Conexao.conectar();
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idFatura);
            
            int linhasAfetadas = stm.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir fatura: " + ex.getMessage());
            return false;
        }
    }
    
    // ========================================
    // OPERAÇÕES DE LISTAGEM
    // ========================================
    /**
     * Lista todas as faturas do banco de dados
     * @return Lista de todas as faturas ordenadas por vencimento (mais recente primeiro)
     */
    public List<Fatura> listarTodasFaturas() {
        List<Fatura> faturas = new ArrayList<>();
        String sql = "SELECT * FROM Fatura ORDER BY VENCIMENTO DESC";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stm = con.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            
            while (rs.next()) {
                Fatura fatura = new Fatura();
                fatura.setIdFatura(rs.getInt("ID_FATURA"));
                fatura.setReferencia(rs.getString("REFERENCIA"));
                fatura.setVencimento(rs.getDate("VENCIMENTO"));
                fatura.setIdUnidadeConsumo(rs.getInt("ID_UNIDADE_CONSUMO"));
                fatura.setValorTotal(rs.getBigDecimal("VALOR_TOTAL"));
                faturas.add(fatura);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar faturas: " + e.getMessage());
        }
        
        return faturas;
    }
}
