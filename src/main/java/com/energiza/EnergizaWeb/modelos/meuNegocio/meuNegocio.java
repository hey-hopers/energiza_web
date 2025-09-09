package com.energiza.EnergizaWeb.modelos.meuNegocio;

public class meuNegocio {
    
	private int id;
	private int id_operador;
	private String nome;
    private String apelido;
    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String nomeDocumento;
    private String numeroDocumento;
    private String email;
    private String telefone;

    public meuNegocio() {   	
    }

    // 🔹 Construtor principal
    public meuNegocio(int id, int id_operador, String nome, String apelido, String nomeDocumento, String numeroDocumento, String email, String telefone, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais) {
        this.id = id;
		this.id_operador = id_operador;
		this.nome = nome;
        this.apelido = apelido;
        this.nomeDocumento = nomeDocumento;
        this.numeroDocumento = numeroDocumento;
        this.email = email;
        this.telefone = telefone;
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
			   "ID='" + id + '\'' + 
			   "ID_OPERADOR='" + id_operador + '\'' +
			   "Nome='" + nome + '\'' +
               "Apelido='" + apelido + '\'' +   
			   "Nome do Documento='" + nomeDocumento + '\'' +
               "Numero do Documento='" + numeroDocumento + '\'' + 
			   "Email='" + email + '\'' +
               "Telefone='" + telefone + '\'' + 
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

	public int getIdOperador() {
		return id_operador;
	}

	public void setIdOperador(int id_operador) {
		this.id_operador = id_operador;
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

	public String getNomeDocumento() {
		return nomeDocumento;
	}

	public void setNomeDocumento(String nomeDocumento) {
		this.nomeDocumento = nomeDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
