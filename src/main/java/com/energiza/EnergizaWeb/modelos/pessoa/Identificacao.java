package com.energiza.EnergizaWeb.modelos.pessoa;

public class Identificacao {
    private int id;
    private int idPessoaFJ;
    private String nome;
    private String apelido;
    private byte[] imagem;

    public Identificacao() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdPessoaFJ() {
		return idPessoaFJ;
	}

	public void setIdPessoaFJ(int idPessoaFJ) {
		this.idPessoaFJ = idPessoaFJ;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

}
