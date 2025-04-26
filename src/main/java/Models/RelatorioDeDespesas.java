package Models;

public class RelatorioDeDespesas {
    private double despesasMes;
    private double valorInvestido;
    private String mes;

    public RelatorioDeDespesas(String mes, double valorInvestido, double despesasMes) {
        this.mes = mes;
        this.valorInvestido = valorInvestido;
        this.despesasMes = despesasMes;
    }

    public double getDespesasMes() {
        return despesasMes;
    }

    public double getValorInvestido() {
        return valorInvestido;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
