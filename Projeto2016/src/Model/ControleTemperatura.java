package Model;

public class ControleTemperatura {

    private long id;
    private Empresa empresa;
    private String status;

    public long getId() {
        return id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
