package Models;

public class RelatorioDoMes {
    private String mes;
    private double total;

    public RelatorioDoMes(double total, String mes) {
        this.total = total;
        this.mes = mes;
    }
    public String getMes(){
        return mes;
    }
    public double getTotal(){
        return total;
    }
}
