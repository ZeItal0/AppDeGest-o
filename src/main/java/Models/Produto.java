package Models;

import java.time.LocalDate;

public class Produto {
    private int id;
    private double preco;
    private double preco_De_venda;
    private String nomeProduto;
    private String detalhes;
    private Categoria categoria;
    private Fornecedor fornecedor;
    private Estoque estoque;
    private LocalDate dataDeCadastro;

    public Produto() {

    }

    public Produto(int id, double preco, double preco_De_venda, String nomeProduto, String detalhes, Categoria categoria,Fornecedor fornecedor,Estoque estoque,LocalDate dataDeCadastro) {
        this.id = id;
        this.preco = preco;
        this.preco_De_venda = preco_De_venda;
        this.nomeProduto = nomeProduto;
        this.detalhes = detalhes;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.estoque = estoque;
        this.dataDeCadastro = dataDeCadastro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getPreco_De_venda() {
        return preco_De_venda;
    }

    public void setPreco_De_venda(double preco_De_venda) {
        this.preco_De_venda = preco_De_venda;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public LocalDate getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(LocalDate dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }
}
