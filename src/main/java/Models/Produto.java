package Models;

public class Produto {
    private int id;
    private double preco;
    private String nomeProduto;
    private String detalhes;
    private Categoria categoria;

    public Produto() {

    }

    public Produto(int id, double preco, String nomeProduto, String detalhes, Categoria categoria) {
        this.id = id;
        this.preco = preco;
        this.nomeProduto = nomeProduto;
        this.detalhes = detalhes;
        this.categoria = categoria;
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
}
