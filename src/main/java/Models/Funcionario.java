package Models;

public class Funcionario {
    private int id;
    private String cargo;

    public Funcionario(){

    }

    public Funcionario(int id, String cargo) {
        this.id = id;
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
