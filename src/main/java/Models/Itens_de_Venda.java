package Models;

public class Itens_de_Venda {
    private int id;
    private int quantidade_itens;
    private Vendas vendas;
    private Produto produto;

    public Itens_de_Venda (){

    }

    public Itens_de_Venda(int id, int quantidade_itens, Vendas vendas, Produto produto) {
        this.id = id;
        this.quantidade_itens = quantidade_itens;
        this.vendas = vendas;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade_itens() {
        return quantidade_itens;
    }

    public void setQuantidade_itens(int quantidade_itens) {
        this.quantidade_itens = quantidade_itens;
    }

    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
