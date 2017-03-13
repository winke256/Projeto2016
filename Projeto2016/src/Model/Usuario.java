package Model;

public class Usuario {

    private Long id;
    private String Nome;
    private byte[] Senha;
    private String CPF;
    private String Telefone;
    private String TipoUsuario;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return Nome;
    }

    public byte[] getSenha() {
        return Senha;
    }

    public String getCPF() {
        return CPF;
    }

    public String getTelefone() {
        return Telefone;
    }

    public String getTipoUsuario() {
        return TipoUsuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public void setSenha(byte[] senha) {
        Senha = senha;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public void setTipoUsuario(String tipoUsuario) {
        TipoUsuario = tipoUsuario;
    }
}
