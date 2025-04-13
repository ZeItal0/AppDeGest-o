package Models;

import java.time.LocalDate;

public class Despesas {
    private int id;
    private String nomeDadispesa;
    private LocalDate data;
    private Double valor;
    private String observacoes;
    private String Status;
    private FormaDepagamento formaDepagamento;

    public Despesas (){

    }

    public Despesas(int id, String nomeDadispesa, LocalDate data, Double valor, String observacoes, String status, FormaDepagamento formaDepagamento) {
        this.id = id;
        this.nomeDadispesa = nomeDadispesa;
        this.data = data;
        this.valor = valor;
        this.observacoes = observacoes;
        Status = status;
        this.formaDepagamento = formaDepagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeDadispesa() {
        return nomeDadispesa;
    }

    public void setNomeDadispesa(String nomeDadispesa) {
        this.nomeDadispesa = nomeDadispesa;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public FormaDepagamento getFormaDepagamento() {
        return formaDepagamento;
    }

    public void setFormaDepagamento(FormaDepagamento formaDepagamento) {
        this.formaDepagamento = formaDepagamento;
    }
}
