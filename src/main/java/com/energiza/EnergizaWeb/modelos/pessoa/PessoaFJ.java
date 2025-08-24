package com.energiza.EnergizaWeb.modelos.pessoa;

public class PessoaFJ {
    
	private int id;
    private String nome;
    private String documento;
    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;

    public PessoaFJ() {   	
    }

    // 🔹 Construtor principal
    public PessoaFJ(int id, String nome, String documento, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }
    
    @Override
    public String toString() {
        return "PessoaFJ{" + 
               "Nome='" + nome + '\'' + 
               "ID='" + id + '\'' + 
               ", Documento='" + documento + '\'' + 
               ", CEP='" + cep + '\'' + 
               ", Endereço='" + endereco + '\'' + 
               ", Número='" + numero + '\'' + 
               ", Complemento='" + complemento + '\'' + 
               ", Bairro='" + bairro + '\'' + 
               ", Cidade='" + cidade + '\'' + 
               ", Estado='" + estado + '\'' + 
               ", País='" + pais + '\'' + 
               '}';
    }

    
    // 🔹 Getters e Setters
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

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}
    
   
}
