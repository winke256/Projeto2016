package Model;

public class Conjunto {

    private long id;
    private Empresa empresa;
    private String nomeConjunto;
    private int numeroConjunto;

    public long getId() {
        return id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getNomeConjunto() {
        return nomeConjunto;
    }

    public int getNumeroConjunto() {
        return numeroConjunto;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setNomeConjunto(String nomeConjunto) {
        this.nomeConjunto = nomeConjunto;
    }

    public void setNumeroConjunto(int numeroConjunto) {
        this.numeroConjunto = numeroConjunto;
    }
}
