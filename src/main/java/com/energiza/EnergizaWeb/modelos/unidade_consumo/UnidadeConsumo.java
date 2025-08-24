package com.energiza.EnergizaWeb.modelos.unidade_consumo;

public class UnidadeConsumo {
	
	private int id;
    private String ucCodigo;
    private String medidor;
    private boolean ehGeradora;
    private String etapa;
    private String cep;
    private String endereco;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String nomePessoa;
    private int ucEndereco;
    private int ucProprietario;
    

    public UnidadeConsumo() {   	
    }
    
 // 🔹 Construtor principal
    public UnidadeConsumo(int id, String ucCodigo, String medidor, boolean ehGeradora, String etapa, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String estado, String pais, String nomePessoa, int ucEndereco, int ucProprietario) {
        this.id = id;
        this.ucCodigo = ucCodigo;
        this.medidor = medidor;
        this.ehGeradora = ehGeradora;
        this.etapa = etapa;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
        this.nomePessoa = nomePessoa;
        this.ucEndereco = ucEndereco;
        this.ucProprietario = ucProprietario;
    }
    
    @Override
    public String toString() {
        return "UnidadeConsumo{" + 
        	   "ID='" + id + '\'' + 
               "Codigo='" + ucCodigo + '\'' + 
               "Medidor='" + medidor + '\'' +
               ", Geradora='" + ehGeradora + '\'' + 
               ", Etapa='" + etapa + '\'' + 
               ", CEP='" + cep + '\'' + 
               ", Endereço='" + endereco + '\'' + 
               ", Número='" + numero + '\'' + 
               ", Complemento='" + complemento + '\'' + 
               ", Bairro='" + bairro + '\'' + 
               ", Cidade='" + cidade + '\'' + 
               ", Estado='" + estado + '\'' + 
               ", País='" + pais + '\'' + 
               ", Pessoa='" + nomePessoa + '\'' + 
               ", ucEndereço='" + ucEndereco + '\'' + 
               ", ucProprietario='" + ucProprietario + '\'' +
               '}';
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}
	
	public String getUcCodigo() {
		return ucCodigo;
	}

	public void setUcCodigo(String ucCodigo) {
		this.ucCodigo = ucCodigo;
	}
	
	public String getMedidor() {
		return medidor;
	}

	public void setMedidor(String medidor) {
		this.medidor = medidor;
	}

	public boolean isEhGeradora() {
		return ehGeradora;
	}

	public void setEhGeradora(boolean ehGeradora) {
		this.ehGeradora = ehGeradora;
	}

	public String getEtapa() {
		return etapa;
	}

	public void setEtapa(String etapa) {
		this.etapa = etapa;
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
	  
	public int getUcEndereco() {
		return ucEndereco;
	}

	public void setUcEndereco(int ucEndereco) {
		this.ucEndereco = ucEndereco;
	}
	
	public int getUcProprietario() {
		return ucProprietario;
	}

	public void setUcProprietario(int ucProprietario) {
		this.ucProprietario = ucProprietario;
	}
}
