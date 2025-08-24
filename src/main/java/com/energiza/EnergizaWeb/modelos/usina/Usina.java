 package com.energiza.EnergizaWeb.modelos.usina;

public class Usina {
	
    private int id;
    private String identificacao;
    private double porcentagem;
    private int idUnidadeConsumo;
    
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIdentificacao() {
		return identificacao;
	}
	
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
	
	public double getPorcentagem() {
		return porcentagem;
	}
	
	public void setPorcentagem(double porcentagem) {
		this.porcentagem = porcentagem;
	}
	
	public int getIdUnidadeConsumo() {
		return idUnidadeConsumo;
	}
	
	public void setIdUnidadeConsumo(int idUnidadeConsumo) {
		this.idUnidadeConsumo = idUnidadeConsumo;
	}

    
}
