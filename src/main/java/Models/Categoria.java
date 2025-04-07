package Models;

public class Categoria {
    private int id;
    private String tipo_produto;

    public Categoria(){

    }

    public Categoria(int id, String tipo_produto) {
        this.id = id;
        this.tipo_produto = tipo_produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_produto() {
        return tipo_produto;
    }

    public void setTipo_produto(String tipo_produto) {
        this.tipo_produto = tipo_produto;
    }

}
