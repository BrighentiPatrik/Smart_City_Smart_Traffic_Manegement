package it.unimore.fum.iot.models;

public class Via {

    private static int count=0;

    private String id;
    private String nome;

    public Via(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Via(String nome) {
        this.id="SP0"+this.count;
        this.nome = nome;
        count++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Via{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}'+'\n';
    }
}
