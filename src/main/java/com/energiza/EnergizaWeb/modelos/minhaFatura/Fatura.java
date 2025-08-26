package com.energiza.EnergizaWeb.modelos.minhaFatura;

/**
 * ========================================
 * MODELO DE FATURA DE ENERGIA ELÉTRICA
 * ========================================
 * 
 * Esta classe representa uma fatura de energia elétrica no sistema.
 * Contém todos os dados básicos necessários para identificação
 * e processamento de faturas.
 * 
 * Campos principais:
 * - ID único da fatura
 * - Referência (mês/ano)
 * - Data de vencimento
 * - Unidade de consumo associada
 * - Valor total da fatura
 * 
 * @author Energiza Web
 * @version 1.0
 */

import java.math.BigDecimal;
import java.util.Date;

public class Fatura {
    // ========================================
    // ATRIBUTOS DA FATURA
    // ========================================
    /** Identificador único da fatura no banco de dados */
    private int idFatura;
    /** Referência da fatura (ex: mês/ano) */
    private String referencia;
    /** Data de vencimento da fatura */
    private Date vencimento;
    /** ID da unidade de consumo associada */
    private int idUnidadeConsumo;
    /** Valor total da fatura */
    private BigDecimal valorTotal;
    
    // ========================================
    // CONSTRUTORES
    // ========================================
    /**
     * Construtor padrão sem parâmetros
     * Utilizado para criação de objetos vazios
     */
    public Fatura() {
    }
    
    /**
     * Construtor completo com todos os parâmetros incluindo ID
     * Utilizado para objetos recuperados do banco de dados
     * @param idFatura ID único da fatura
     * @param referencia Referência da fatura (mês/ano)
     * @param vencimento Data de vencimento
     * @param idUnidadeConsumo ID da unidade de consumo
     * @param valorTotal Valor total da fatura
     */
    public Fatura(int idFatura, String referencia, Date vencimento, int idUnidadeConsumo, BigDecimal valorTotal) {
        this.idFatura = idFatura;
        this.referencia = referencia;
        this.vencimento = vencimento;
        this.idUnidadeConsumo = idUnidadeConsumo;
        this.valorTotal = valorTotal;
    }
    
    // Construtor sem ID (para inserção)
    /**
     * Construtor com parâmetros para criação de faturas
     * @param referencia Referência da fatura (mês/ano)
     * @param vencimento Data de vencimento
     * @param idUnidadeConsumo ID da unidade de consumo
     * @param valorTotal Valor total da fatura
     */
    public Fatura(String referencia, Date vencimento, int idUnidadeConsumo, BigDecimal valorTotal) {
        this.referencia = referencia;
        this.vencimento = vencimento;
        this.idUnidadeConsumo = idUnidadeConsumo;
        this.valorTotal = valorTotal;
    }
    
    // ========================================
    // MÉTODOS GETTERS E SETTERS
    // ========================================
    /**
     * Obtém o ID único da fatura
     * @return ID da fatura
     */
    public int getIdFatura() {
        return idFatura;
    }
    
    /**
     * Define o ID único da fatura
     * @param idFatura ID da fatura
     */
    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }
    
    /**
     * Obtém a referência da fatura
     * @return Referência (mês/ano)
     */
    public String getReferencia() {
        return referencia;
    }
    
    /**
     * Define a referência da fatura
     * @param referencia Referência (mês/ano)
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    /**
     * Obtém a data de vencimento
     * @return Data de vencimento
     */
    public Date getVencimento() {
        return vencimento;
    }
    
    /**
     * Define a data de vencimento
     * @param vencimento Data de vencimento
     */
    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }
    
    /**
     * Obtém o ID da unidade de consumo
     * @return ID da unidade de consumo
     */
    public int getIdUnidadeConsumo() {
        return idUnidadeConsumo;
    }
    
    /**
     * Define o ID da unidade de consumo
     * @param idUnidadeConsumo ID da unidade de consumo
     */
    public void setIdUnidadeConsumo(int idUnidadeConsumo) {
        this.idUnidadeConsumo = idUnidadeConsumo;
    }
    
    /**
     * Obtém o valor total da fatura
     * @return Valor total
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    /**
     * Define o valor total da fatura
     * @param valorTotal Valor total
     */
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    /**
     * Retorna representação em string da fatura
     * Útil para debug e logging
     * @return String formatada com dados da fatura
     */
    @Override
    public String toString() {
        return "Fatura{" +
                "idFatura=" + idFatura +
                ", referencia='" + referencia + '\'' +
                ", vencimento=" + vencimento +
                ", idUnidadeConsumo=" + idUnidadeConsumo +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
