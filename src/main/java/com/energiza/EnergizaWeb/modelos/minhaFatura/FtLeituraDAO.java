package com.energiza.EnergizaWeb.modelos.minhaFatura;

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

public class FtLeituraDAO {
    
    // Construtor padrão
    public FtLeituraDAO() {
    }
    
    // Inserir nova leitura
    public int incluirLeitura(FtLeitura leitura) {
        String sql = "INSERT INTO FT_LEITURA (DATA_LEITURA_ANTERIOR, DATA_LEITURA_ATUAL, DATA_LEITURA_PROXIMA, " +
                     "DIAS_LIDOS, MEDIDOR, LEITURA_ANTERIOR, LEITURA_ATUAL, TOTAL_APURADO, ID_FATURA, SERVICO) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection con = Conexao.conectar();
        int idLeitura = -1;
        
        try {
            PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setDate(1, leitura.getDataLeituraAnterior() != null ? new Date(leitura.getDataLeituraAnterior().getTime()) : null);
            stm.setDate(2, leitura.getDataLeituraAtual() != null ? new Date(leitura.getDataLeituraAtual().getTime()) : null);
            stm.setDate(3, leitura.getDataLeituraProxima() != null ? new Date(leitura.getDataLeituraProxima().getTime()) : null);
            stm.setInt(4, leitura.getDiasLidos());
            stm.setString(5, leitura.getMedidor());
            stm.setBigDecimal(6, leitura.getLeituraAnterior());
            stm.setBigDecimal(7, leitura.getLeituraAtual());
            stm.setBigDecimal(8, leitura.getTotalApurado());
            stm.setInt(9, leitura.getIdFatura());
            stm.setString(10, leitura.getServico());
            
            int linhasAfetadas = stm.executeUpdate();
            
            if (linhasAfetadas > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    idLeitura = rs.getInt(1);
                }
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro ao incluir leitura: " + ex.getMessage());
        }
        
        return idLeitura;
    }
    
    // Buscar leitura por ID
    public FtLeitura getLeituraById(int idLeitura) {
        FtLeitura leitura = null;
        String sql = "SELECT * FROM FT_LEITURA WHERE ID_FT_LEITURA = ?";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            
            stm.setInt(1, idLeitura);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                leitura = new FtLeitura();
                leitura.setIdFtLeitura(rs.getInt("ID_FT_LEITURA"));
                leitura.setDataLeituraAnterior(rs.getDate("DATA_LEITURA_ANTERIOR"));
                leitura.setDataLeituraAtual(rs.getDate("DATA_LEITURA_ATUAL"));
                leitura.setDataLeituraProxima(rs.getDate("DATA_LEITURA_PROXIMA"));
                leitura.setDiasLidos(rs.getInt("DIAS_LIDOS"));
                leitura.setMedidor(rs.getString("MEDIDOR"));
                leitura.setLeituraAnterior(rs.getBigDecimal("LEITURA_ANTERIOR"));
                leitura.setLeituraAtual(rs.getBigDecimal("LEITURA_ATUAL"));
                leitura.setTotalApurado(rs.getBigDecimal("TOTAL_APURADO"));
                leitura.setIdFatura(rs.getInt("ID_FATURA"));
                leitura.setServico(rs.getString("SERVICO"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar leitura: " + e.getMessage());
        }
        
        return leitura;
    }
    
    // Buscar leitura por ID da fatura
    public FtLeitura getLeituraByFatura(int idFatura) {
        FtLeitura leitura = null;
        String sql = "SELECT * FROM FT_LEITURA WHERE ID_FATURA = ?";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stm = con.prepareStatement(sql)) {
            
            stm.setInt(1, idFatura);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                leitura = new FtLeitura();
                leitura.setIdFtLeitura(rs.getInt("ID_FT_LEITURA"));
                leitura.setDataLeituraAnterior(rs.getDate("DATA_LEITURA_ANTERIOR"));
                leitura.setDataLeituraAtual(rs.getDate("DATA_LEITURA_ATUAL"));
                leitura.setDataLeituraProxima(rs.getDate("DATA_LEITURA_PROXIMA"));
                leitura.setDiasLidos(rs.getInt("DIAS_LIDOS"));
                leitura.setMedidor(rs.getString("MEDIDOR"));
                leitura.setLeituraAnterior(rs.getBigDecimal("LEITURA_ANTERIOR"));
                leitura.setLeituraAtual(rs.getBigDecimal("LEITURA_ATUAL"));
                leitura.setTotalApurado(rs.getBigDecimal("TOTAL_APURADO"));
                leitura.setIdFatura(rs.getInt("ID_FATURA"));
                leitura.setServico(rs.getString("SERVICO"));
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar leitura da fatura: " + e.getMessage());
        }
        
        return leitura;
    }
    
    // Atualizar leitura
    public boolean atualizarLeitura(FtLeitura leitura) {
        String sql = "UPDATE FT_LEITURA SET DATA_LEITURA_ANTERIOR = ?, DATA_LEITURA_ATUAL = ?, DATA_LEITURA_PROXIMA = ?, " +
                     "DIAS_LIDOS = ?, MEDIDOR = ?, LEITURA_ANTERIOR = ?, LEITURA_ATUAL = ?, TOTAL_APURADO = ?, " +
                     "SERVICO = ? WHERE ID_FT_LEITURA = ?";
        Connection con = Conexao.conectar();
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setDate(1, leitura.getDataLeituraAnterior() != null ? new Date(leitura.getDataLeituraAnterior().getTime()) : null);
            stm.setDate(2, leitura.getDataLeituraAtual() != null ? new Date(leitura.getDataLeituraAtual().getTime()) : null);
            stm.setDate(3, leitura.getDataLeituraProxima() != null ? new Date(leitura.getDataLeituraProxima().getTime()) : null);
            stm.setInt(4, leitura.getDiasLidos());
            stm.setString(5, leitura.getMedidor());
            stm.setBigDecimal(6, leitura.getLeituraAnterior());
            stm.setBigDecimal(7, leitura.getLeituraAtual());
            stm.setBigDecimal(8, leitura.getTotalApurado());
            stm.setString(9, leitura.getServico());
            stm.setInt(10, leitura.getIdFtLeitura());
            
            int linhasAfetadas = stm.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar leitura: " + ex.getMessage());
            return false;
        }
    }
    
    // Excluir leitura
    public boolean excluirLeitura(int idLeitura) {
        String sql = "DELETE FROM FT_LEITURA WHERE ID_FT_LEITURA = ?";
        Connection con = Conexao.conectar();
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idLeitura);
            
            int linhasAfetadas = stm.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir leitura: " + ex.getMessage());
            return false;
        }
    }
    
    // Excluir leitura por ID da fatura
    public boolean excluirLeituraByFatura(int idFatura) {
        String sql = "DELETE FROM FT_LEITURA WHERE ID_FATURA = ?";
        Connection con = Conexao.conectar();
        
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idFatura);
            
            int linhasAfetadas = stm.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir leitura da fatura: " + ex.getMessage());
            return false;
        }
    }
}
