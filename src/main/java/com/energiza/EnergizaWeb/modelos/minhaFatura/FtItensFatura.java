package com.energiza.EnergizaWeb.modelos.minhaFatura;

import java.math.BigDecimal;

public class FtItensFatura {
    
    private int idFtItensFatura;
    private String item;
    private String unidade;
    private BigDecimal quantidade;
    private BigDecimal valor;
    private int idFatura;
    
    // Construtor padrão
    public FtItensFatura() {
    }
    
    // Construtor principal
    public FtItensFatura(int idFtItensFatura, String item, String unidade, BigDecimal quantidade, BigDecimal valor, int idFatura) {
        this.idFtItensFatura = idFtItensFatura;
        this.item = item;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.valor = valor;
        this.idFatura = idFatura;
    }
    
    // Construtor sem ID (para inserção)
    public FtItensFatura(String item, String unidade, BigDecimal quantidade, BigDecimal valor, int idFatura) {
        this.item = item;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.valor = valor;
        this.idFatura = idFatura;
    }
    
    // Getters e Setters
    public int getIdFtItensFatura() {
        return idFtItensFatura;
    }
    
    public void setIdFtItensFatura(int idFtItensFatura) {
        this.idFtItensFatura = idFtItensFatura;
    }
    
    public String getItem() {
        return item;
    }
    
    public void setItem(String item) {
        this.item = item;
    }
    
    public String getUnidade() {
        return unidade;
    }
    
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    
    public BigDecimal getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
    
    public BigDecimal getValor() {
        return valor;
    }
    
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    public int getIdFatura() {
        return idFatura;
    }
    
    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }
    
    @Override
    public String toString() {
        return "FtItensFatura{" +
                "idFtItensFatura=" + idFtItensFatura +
                ", item='" + item + '\'' +
                ", unidade='" + unidade + '\'' +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                ", idFatura=" + idFatura +
                '}';
    }
}
