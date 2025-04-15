package Models;

public class Itens_de_Venda {
    private int id;
    private int quantidade_itens;
    private Double valor_unitario;
    private Vendas vendas;
    private Produto produto;

    public Itens_de_Venda (){

    }

    public Itens_de_Venda(int id, int quantidade_itens, Double valor_unitario, Vendas vendas, Produto produto) {
        this.id = id;
        this.quantidade_itens = quantidade_itens;
        this.valor_unitario = valor_unitario;
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

    public Double getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(Double valor_unitario) {
        this.valor_unitario = valor_unitario;
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
