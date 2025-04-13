package Models;

public class FormaDepagamento {
    private int id;
    private String tipoDepagamento;

    public FormaDepagamento (){

    }

    public FormaDepagamento(int id,String tipoDepagamento) {
        this.id = id;
        this.tipoDepagamento = tipoDepagamento;
    }

    public String getTipoDepagamento() {
        return tipoDepagamento;
    }

    public void setTipoDepagamento(String tipoDepagamento) {
        this.tipoDepagamento = tipoDepagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
