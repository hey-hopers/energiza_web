package com.energiza.EnergizaWeb.modelos.unidade_consumo;

public class Distribuidora {
	
	private int id;
	private String nome;

	
	public Distribuidora() {   	
    }
	
	public Distribuidora(int id, String nome) {
        this.id = id;
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Distribuidora [id=" + id + ", nome=" + nome + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
