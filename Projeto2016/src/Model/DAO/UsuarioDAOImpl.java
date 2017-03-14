package Model.DAO;

import Model.Usuario;
import Enum.*;
import Util.CryptRSA;
import Util.DataFormatter;
import Util.Parser;
import Util.TextFile;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UsuarioDAOImpl extends DAOImpl<Usuario> {

    public UsuarioDAOImpl(){
        super(Database.Databases.ProjetoIntegrado);
    }

    public Usuario getUsuarioPorID(Long id) throws Exception{
        List<Usuario> usuarios = parseToDTO();
        for(Usuario u : usuarios){
            if(u.getId().equals(id)){
                return u;
            }
        }
        throw new Exception("Erro ao recuperar usuario");
    }

    public List<Usuario> getUsuariosPorNome(String nome){
        List<Usuario> aux = new ArrayList<>();
        Usuario usuario;
        for(Usuario u : parseToDTO()){
            if(u.getNome().equals(nome)){
                aux.add(u);
            }
        }
        return aux;
    }

    public List<Usuario> getUsuarios() throws Exception{
        List<Usuario> list = new ArrayList<>();
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblUsuario")){
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    list.add(parseToDTO(rs));
                }
                return list;
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public boolean checkUsuarioValido(Usuario usuario) throws Exception{
        List<Usuario> usuarios = this.getUsuariosPorNome(usuario.getNome());
        for(Usuario u : usuarios){
            if(Arrays.equals(u.getSenha(), usuario.getSenha())){
                return true;
            }
        }
        return false;
    }

    public Usuario saveUpdate(Usuario usuario) throws Exception{
        if(usuario == null) {
            throw new Exception("Usuario não pode ser nulo!");
        }

        return usuario.getId() <= 0 ? save(usuario) : update(usuario);
    }

    public Usuario delete(Usuario usuario) throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("DELETE FROM tblUsuario WHERE ID = ?")){
            st.setLong(1, usuario.getId());
            st.executeUpdate();
            return usuario;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public Usuario save(Usuario usuario) throws Exception {
        try {
            TextFile textFile = new TextFile("usuarios.txt");
            textFile.write(Parser.parse(generateID(), new String()), false);
            textFile.write(usuario.getNome(), false);
            textFile.write(usuario.getCPF(), false);
            textFile.write(usuario.getTelefone(), false);
            textFile.write(DataFormatter.byteToHexString(new CryptRSA().geraCifra(usuario.getSenha())), false);
            textFile.write(usuario.getTipoUsuario(), false);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return usuario;
    }

    public void saveAllUsers(List<Usuario> usuarios){
        try {
            if(new File("usuarios.txt").exists()) new File("usuarios.txt").delete();
            for(Usuario usuario : usuarios){
                TextFile textFile = new TextFile("usuarios.txt");
                textFile.write(Parser.parse(usuario.getId(), new String()), false);
                textFile.write(usuario.getNome(), false);
                textFile.write(usuario.getCPF(), false);
                textFile.write(usuario.getTelefone(), false);
                textFile.write(DataFormatter.byteToHexString(new CryptRSA().geraCifra(usuario.getSenha())), false);
                textFile.write(usuario.getTipoUsuario(), false);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public Usuario update(Usuario usuario) throws Exception {
        if(usuario == null) throw new Exception("Usuario não pode ser nulo.");
        try(PreparedStatement st = this.getConnection().prepareStatement("UPDATE tblUsuario SET Nome = ?, Senha = ?, CPF = ?, Telefone = ?, TipoUsuario = ? WHERE ID = ?")){
            st.setString(1, usuario.getNome());
            st.setBytes(2, new CryptRSA().geraCifra(usuario.getSenha()));
            st.setString(3, usuario.getCPF());
            st.setString(4, usuario.getTelefone());
            st.setLong(6, usuario.getId());
            st.executeUpdate();
            return usuario;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public Usuario parseToDTO(ResultSet resultset) throws Exception {
        Usuario u = new Usuario();
        u.setId(resultset.getLong("ID"));
        u.setNome(resultset.getString("Nome"));
        u.setCPF(resultset.getString("CPF"));
        u.setSenha(resultset.getBytes("Senha"));
        u.setTelefone(resultset.getString("Telefone"));
        return u;
    }

    public List<Usuario> parseToDTO(){
        List<String> usuarios = new TextFile("usuarios.txt").getFileLines();
        List<Usuario> aux = new ArrayList<>();
        Usuario u;
        try {
            for (int i = 0, size = usuarios.size(); i < size; i = i + 6) {
                u = new Usuario();
                u.setId(Parser.parse(usuarios.get(i), 0L));
                u.setNome(usuarios.get(i+1));
                u.setCPF(usuarios.get(i + 2));
                u.setTelefone(usuarios.get(i + 3));
                u.setSenha(new CryptRSA().geraDecifra(DataFormatter.HexStringtoByte(usuarios.get(i + 4))));
                u.setTipoUsuario(usuarios.get(i+5));
                aux.add(u);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return aux;
    }

    public Long generateID(){
        return (long)(Math.random()*99999999+1);
    }
}
