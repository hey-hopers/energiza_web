package com.energiza.EnergizaWeb.modelos;

import java.util.Date;

public class Usuario {

	private int id;
	private int id_pessoa;
	private String whatsapp;
	private String telefone;
    private Date dataNascimento;
    private Date dataCadastro;
    private String nome;
    private String senhausuario;
    private String email;
    private boolean situacao;
    
    // 🔹 Construtor Padrão
    public Usuario() {
    }
    
    // 🔹 Construtor principal
    public Usuario(int id, int id_pessoa, String nome, String email, String whatsapp, String telefone, Date dataNascimento, Date dataCadastro, String senhausuario, boolean situacao) {
        this.id = id;
        this.id = id_pessoa;
        this.nome = nome;
        this.email = email;
        this.whatsapp = whatsapp;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = dataCadastro;
        this.senhausuario = senhausuario;
        this.situacao = situacao;
    }

    // 🔹 Construtor sem senha e situação (útil para algumas situações)
    public Usuario(int id, int id_pessoa, String nome, String email, String whatsapp, String telefone, Date dataNascimento, Date dataCadastro) {
        this(id, id_pessoa, nome, email, whatsapp, telefone, dataNascimento, dataCadastro, "", true); // Senha vazia e situação ativa por padrão
    }

    @Override
    public String toString() {
        return "Usuario{" + 
               "Nome='" + nome + '\'' + 
               ", Email='" + email + '\'' + 
               ", WhatsApp='" + whatsapp + '\'' + 
               ", Telefone='" + telefone + '\'' + 
               ", DataNascimento=" + dataNascimento + 
               ", DataCadastro=" + dataCadastro + 
               ", Situacao=" + situacao + 
               '}';
    }

    // 🔹 Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdPessoa() {
        return id_pessoa;
    }
    
    public void setIdPessoa(int id_pessoa) {
        this.id_pessoa = id_pessoa;
    }
    
    public String getWhatsapp() {
        return whatsapp;
    }
    
    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senhausuario;
    }

    public void setSenha(String senhausuario) {
        this.senhausuario = senhausuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getDataNascimento() {
        return dataNascimento;
    }
    
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public Date getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }
    
}

/*

        

*/
