package com.energiza.EnergizaWeb.modelos.pessoa;

public class RedeSocial {
    private int id;
    private int idPessoaFJ;
    private String plataforma;
    private String telefone;
    private String url;

    public RedeSocial() {}

    // Construtor personalizado (para facilitar a criação)
    public RedeSocial(String plataforma, String telefone, String url) {
        this.plataforma = plataforma;
        this.telefone = telefone;
        this.url = url;
    }
    
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

	public String getPlataforma() {
		return plataforma;
	}

	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
