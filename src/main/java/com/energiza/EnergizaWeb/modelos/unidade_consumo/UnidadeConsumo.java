package com.energiza.EnergizaWeb.modelos.unidade_consumo;

public class UnidadeConsumo {
	
	private int id;
	private int id_operador;
	private int id_distribuidora;
	private int id_proprietario;
    private int id_endereco;
    private String ucCodigo;
    private String medidor;
    private int ehGeradora;
    

    public UnidadeConsumo() {   	
    }
    

    public UnidadeConsumo(int id, int id_operador, int id_distribuidora, int id_proprietario,  int id_endereco, String ucCodigo, String medidor, int ehGeradora) {
        this.id = id;
        this.id = id_operador;
        this.id = id_distribuidora;
        this.id = id_proprietario;
        this.id_endereco = id_endereco;
        this.ucCodigo = ucCodigo;
        this.medidor = medidor;
        this.ehGeradora = ehGeradora;
    }
    
    @Override
    public String toString() {
        return "UnidadeConsumo{" + 
        	   "ID='" + id + '\'' + 
        	   "ID Operador='" + id_operador + '\'' + 
        	   "ID Distribuidora='" + id_distribuidora + '\'' + 
        	   "ID Propiretario='" + id_proprietario + '\'' + 
        	   "ID Endereco='" + id_endereco + '\'' + 
               "Codigo='" + ucCodigo + '\'' + 
               "Medidor='" + medidor + '\'' +
               ", Geradora='" + ehGeradora + '\'' + 
               '}';
    }

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
	
	public int getIdDistribuidora() {
		return id_distribuidora;
	}

	public void setIdDistribuidora(int id_distribuidora) {
		this.id_distribuidora = id_distribuidora;
	}
	
	public int getIdProprietario() {
		return id_proprietario;
	}

	public void setIdProprietario(int id_proprietario) {
		this.id_proprietario = id_proprietario;
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

	public int getEhGeradora() {
		return ehGeradora;
	}

	public void setEhGeradora(int ehGeradora) {
		this.ehGeradora = ehGeradora;
	}
	  
	public int getIdEndereco() {
		return id_endereco;
	}

	public void setIdEndereco(int id_endereco) {
		this.id_endereco = id_endereco;
	}
	
}
