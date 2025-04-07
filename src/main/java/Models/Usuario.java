package Models;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nome;
    private String senha;
    private String user;
    private String telefone;
    private LocalDate DataDeNascimento;
    private LocalDate DataDeRegistro;
    private Endereco endereco;

    public Usuario(){

    }

    public Usuario(int id, String nome, String senha, String user, String telefone, LocalDate dataDeNascimento, LocalDate dataDeRegistro,Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.user = user;
        this.telefone = telefone;
        DataDeNascimento = dataDeNascimento;
        DataDeRegistro = dataDeRegistro;
        this.endereco = endereco;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataDeNascimento() {
        return DataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        DataDeNascimento = dataDeNascimento;
    }

    public LocalDate getDataDeRegistro() {
        return DataDeRegistro;
    }

    public void setDataDeRegistro(LocalDate dataDeRegistro) {
        DataDeRegistro = dataDeRegistro;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
