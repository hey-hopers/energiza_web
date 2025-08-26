package com.energiza.EnergizaWeb.modelos.minhaFatura;

import java.math.BigDecimal;
import java.util.Date;

public class FtLeitura {
    
    private int idFtLeitura;
    private Date dataLeituraAnterior;
    private Date dataLeituraAtual;
    private Date dataLeituraProxima;
    private int diasLidos;
    private String medidor;
    private BigDecimal leituraAnterior;
    private BigDecimal leituraAtual;
    private BigDecimal totalApurado;
    private int idFatura;
    private String servico;
    
    // Construtor padrão
    public FtLeitura() {
    }
    
    // Construtor principal
    public FtLeitura(int idFtLeitura, Date dataLeituraAnterior, Date dataLeituraAtual, Date dataLeituraProxima, 
                     int diasLidos, String medidor, BigDecimal leituraAnterior, BigDecimal leituraAtual, 
                     BigDecimal totalApurado, int idFatura, String servico) {
        this.idFtLeitura = idFtLeitura;
        this.dataLeituraAnterior = dataLeituraAnterior;
        this.dataLeituraAtual = dataLeituraAtual;
        this.dataLeituraProxima = dataLeituraProxima;
        this.diasLidos = diasLidos;
        this.medidor = medidor;
        this.leituraAnterior = leituraAnterior;
        this.leituraAtual = leituraAtual;
        this.totalApurado = totalApurado;
        this.idFatura = idFatura;
        this.servico = servico;
    }
    
    // Construtor sem ID (para inserção)
    public FtLeitura(Date dataLeituraAnterior, Date dataLeituraAtual, Date dataLeituraProxima, 
                     int diasLidos, String medidor, BigDecimal leituraAnterior, BigDecimal leituraAtual, 
                     BigDecimal totalApurado, int idFatura, String servico) {
        this.dataLeituraAnterior = dataLeituraAnterior;
        this.dataLeituraAtual = dataLeituraAtual;
        this.dataLeituraProxima = dataLeituraProxima;
        this.diasLidos = diasLidos;
        this.medidor = medidor;
        this.leituraAnterior = leituraAnterior;
        this.leituraAtual = leituraAtual;
        this.totalApurado = totalApurado;
        this.idFatura = idFatura;
        this.servico = servico;
    }
    
    // Getters e Setters
    public int getIdFtLeitura() {
        return idFtLeitura;
    }
    
    public void setIdFtLeitura(int idFtLeitura) {
        this.idFtLeitura = idFtLeitura;
    }
    
    public Date getDataLeituraAnterior() {
        return dataLeituraAnterior;
    }
    
    public void setDataLeituraAnterior(Date dataLeituraAnterior) {
        this.dataLeituraAnterior = dataLeituraAnterior;
    }
    
    public Date getDataLeituraAtual() {
        return dataLeituraAtual;
    }
    
    public void setDataLeituraAtual(Date dataLeituraAtual) {
        this.dataLeituraAtual = dataLeituraAtual;
    }
    
    public Date getDataLeituraProxima() {
        return dataLeituraProxima;
    }
    
    public void setDataLeituraProxima(Date dataLeituraProxima) {
        this.dataLeituraProxima = dataLeituraProxima;
    }
    
    public int getDiasLidos() {
        return diasLidos;
    }
    
    public void setDiasLidos(int diasLidos) {
        this.diasLidos = diasLidos;
    }
    
    public String getMedidor() {
        return medidor;
    }
    
    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }
    
    public BigDecimal getLeituraAnterior() {
        return leituraAnterior;
    }
    
    public void setLeituraAnterior(BigDecimal leituraAnterior) {
        this.leituraAnterior = leituraAnterior;
    }
    
    public BigDecimal getLeituraAtual() {
        return leituraAtual;
    }
    
    public void setLeituraAtual(BigDecimal leituraAtual) {
        this.leituraAtual = leituraAtual;
    }
    
    public BigDecimal getTotalApurado() {
        return totalApurado;
    }
    
    public void setTotalApurado(BigDecimal totalApurado) {
        this.totalApurado = totalApurado;
    }
    
    public int getIdFatura() {
        return idFatura;
    }
    
    public void setIdFatura(int idFatura) {
        this.idFatura = idFatura;
    }
    
    public String getServico() {
        return servico;
    }
    
    public void setServico(String servico) {
        this.servico = servico;
    }
    
    @Override
    public String toString() {
        return "FtLeitura{" +
                "idFtLeitura=" + idFtLeitura +
                ", dataLeituraAnterior=" + dataLeituraAnterior +
                ", dataLeituraAtual=" + dataLeituraAtual +
                ", dataLeituraProxima=" + dataLeituraProxima +
                ", diasLidos=" + diasLidos +
                ", medidor='" + medidor + '\'' +
                ", leituraAnterior=" + leituraAnterior +
                ", leituraAtual=" + leituraAtual +
                ", totalApurado=" + totalApurado +
                ", idFatura=" + idFatura +
                ", servico='" + servico + '\'' +
                '}';
    }
}
