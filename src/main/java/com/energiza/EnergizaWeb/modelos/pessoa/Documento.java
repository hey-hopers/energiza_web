package com.energiza.EnergizaWeb.modelos.pessoa;

public class Documento {
    private int id;
    private int idPessoaFJ;
    private String tipoDocumento; // CPF, RG, CNPJ
    private String numero;
    private byte[] imagemDocumento;

    public Documento() {}

    // Construtor personalizado (para facilitar a criação)
    public Documento(String tipoDocumento, String numero) {
        this.tipoDocumento = tipoDocumento;
        this.numero = numero;
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

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public byte[] getImagemDocumento() {
		return imagemDocumento;
	}

	public void setImagemDocumento(byte[] imagemDocumento) {
		this.imagemDocumento = imagemDocumento;
	}
    
}
