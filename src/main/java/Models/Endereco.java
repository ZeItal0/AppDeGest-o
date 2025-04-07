package Models;

public class Endereco {
    private int id;
    private String cidade;
    private String rua;
    private String bairro;
    private String cep;

    public Endereco(){

    }

    public Endereco(int id, String cidade, String rua, String bairro, String cep) {
        this.id = id;
        this.cidade = cidade;
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
