package Models;

public class RelatorioDoMes {
    private String mes;
    private double total;
    private int quantidade;

    public RelatorioDoMes(String mes, double total, int quantidade) {
        this.mes = mes;
        this.total = total;
        this.quantidade = quantidade;
    }

    public String getMes() {
        return mes;
    }

    public double getTotal() {
        return total;
    }

    public int getQuantidade() {
        return quantidade;
    }


}
