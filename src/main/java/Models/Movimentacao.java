package Models;

import java.time.LocalDate;

public class Movimentacao {

    private int id;
    private String tipo_movimentacao;
    private int quantidade;
    private LocalDate data_movimentacao;
    private Produto produto;

    public Movimentacao (){

    }

    public Movimentacao(int id, String tipo_movimentacao, int quantidade, LocalDate data_movimentacao, Produto produto) {
        this.id = id;
        this.tipo_movimentacao = tipo_movimentacao;
        this.quantidade = quantidade;
        this.data_movimentacao = data_movimentacao;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_movimentacao() {
        return tipo_movimentacao;
    }

    public void setTipo_movimentacao(String tipo_movimentacao) {
        this.tipo_movimentacao = tipo_movimentacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData_movimentacao() {
        return data_movimentacao;
    }

    public void setData_movimentacao(LocalDate data_movimentacao) {
        this.data_movimentacao = data_movimentacao;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
