package Models;

import java.time.LocalDate;
import java.util.List;

public class Vendas {
    private int id;
    private LocalDate data_De_venda;
    private Fornecedor fornecedor;
    private FormaDepagamento formaDepagamento;
    private StatusDeVenda statusDeVenda;
    private List<Itens_de_Venda> itens;

    public Vendas (){

    }

    public Vendas(int id, LocalDate data_De_venda, Fornecedor fornecedor, FormaDepagamento formaDepagamento, StatusDeVenda statusDeVenda) {
        this.id = id;
        this.data_De_venda = data_De_venda;
        this.fornecedor = fornecedor;
        this.formaDepagamento = formaDepagamento;
        this.statusDeVenda = statusDeVenda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData_De_venda() {
        return data_De_venda;
    }

    public void setData_De_venda(LocalDate data_De_venda) {
        this.data_De_venda = data_De_venda;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public FormaDepagamento getFormaDepagamento() {
        return formaDepagamento;
    }

    public void setFormaDepagamento(FormaDepagamento formaDepagamento) {
        this.formaDepagamento = formaDepagamento;
    }

    public StatusDeVenda getStatusDeVenda() {
        return statusDeVenda;
    }

    public void setStatusDeVenda(StatusDeVenda statusDeVenda) {
        this.statusDeVenda = statusDeVenda;
    }

    public void setItens(List<Itens_de_Venda> itens) {
        this.itens = itens;
    }

    public Double TotalVenda(){
        Double total = 0.0;
        for (Itens_de_Venda item: itens){
            total += item.getValor_unitario();
        }
        return total;
    }
}
