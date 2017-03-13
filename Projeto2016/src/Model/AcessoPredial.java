package Model;

import java.util.Date;

public class AcessoPredial {

    private long ID;
    private Empresa empresa;
    private String Identificacao;
    private Date dataEntrada;
    private Date dataSaida;

    public long getID() {
        return ID;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public String getIdentificacao() {
        return Identificacao;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setIdentificacao(String identificacao) {
        Identificacao = identificacao;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }
}
