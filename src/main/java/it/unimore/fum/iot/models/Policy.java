package it.unimore.fum.iot.models;

public class Policy {
    private String nome;
    private int tempoRosso;
    private int tempoVerde;
    private int tempoGiallo;

    public Policy(String nome, int tempoRosso, int tempoVerde, int tempoGiallo) {
        this.nome = nome;
        this.tempoRosso = tempoRosso;
        this.tempoVerde = tempoVerde;
        this.tempoGiallo = tempoGiallo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTempoRosso() {
        return tempoRosso;
    }

    public void setTempoRosso(int tempoRosso) {
        this.tempoRosso = tempoRosso;
    }

    public int getTempoVerde() {
        return tempoVerde;
    }

    public void setTempoVerde(int tempoVerde) {
        this.tempoVerde = tempoVerde;
    }

    public int getTempoGiallo() {
        return tempoGiallo;
    }

    public void setTempoGiallo(int tempoGiallo) {
        this.tempoGiallo = tempoGiallo;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "nome='" + nome + '\'' +
                ", tempoRosso=" + tempoRosso +
                ", tempoVerde=" + tempoVerde +
                ", tempoGiallo=" + tempoGiallo +
                '}'+'\n';
    }
}
