package Model;

public class Empresa {
    private Long ID;
    private Usuario usuario;
    private String CNPJ;
    private String RazaoSocial;
    private String HorarioInicioArCondicionado;
    private String HorarioTerminoArCondicionado;
    private int TemperaturaMaxima;
    private String HorarioInicioFuncionamento;
    private String HorarioTerminoFuncionamento;

    public Long getID() {
        return ID == null ? 0 : ID;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public String getHorarioInicioArCondicionado() {
        return HorarioInicioArCondicionado;
    }

    public String getHorarioTerminoArCondicionado() {
        return HorarioTerminoArCondicionado;
    }

    public int getTemperaturaMaxima() {
        return TemperaturaMaxima;
    }

    public String getHorarioInicioFuncionamento() {
        return HorarioInicioFuncionamento;
    }

    public String getHorarioTerminoFuncionamento() {
        return HorarioTerminoFuncionamento;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public void setRazaoSocial(String razaoSocial) {
        RazaoSocial = razaoSocial;
    }

    public void setHorarioInicioArCondicionado(String horarioInicioArCondicionado) {
        HorarioInicioArCondicionado = horarioInicioArCondicionado;
    }

    public void setHorarioTerminoArCondicionado(String horarioTerminoArCondicionado) {
        HorarioTerminoArCondicionado = horarioTerminoArCondicionado;
    }

    public void setTemperaturaMaxima(int temperaturaMaxima) {
        TemperaturaMaxima = temperaturaMaxima;
    }

    public void setHorarioInicioFuncionamento(String horarioInicioFuncionamento) {
        HorarioInicioFuncionamento = horarioInicioFuncionamento;
    }

    public void setHorarioTerminoFuncionamento(String horarioTerminoFuncionamento) {
        HorarioTerminoFuncionamento = horarioTerminoFuncionamento;
    }
}
