package Model.DAO;

import Enum.*;
import Model.Empresa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAOImpl extends DAOImpl<Empresa> implements EmpresaDAO {

    public EmpresaDAOImpl(){
        super(Database.Databases.ProjetoIntegrado);
    }

    public List<Empresa> getEmpresas() throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblEmpresa")){
            List<Empresa> list = new ArrayList<>();
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    list.add(parseToDTO(rs));
                }
            }
            return list;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public Empresa getEmpresaPorID(long id) throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblEmpresa WHERE ID = ?")){
            st.setLong(1, id);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    return parseToDTO(rs);
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        throw new Exception("Erro ao recuperar empresa");
    }

    public Empresa getEmpresaPorIDUsuario(Long id) throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblEmpresa WHERE ID_USUARIO = ?")){
            st.setLong(1, id);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    return parseToDTO(rs);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        throw new Exception("Este usuario não esta vinculado a nenhuma empresa.");
    }

    public Empresa saveUpdate(Empresa empresa) throws Exception {
        if(empresa == null) throw new Exception("Empresa não pode ser nula");
        return empresa.getID() <= 0 ? save(empresa) : update(empresa);
    }

    public Empresa delete(Empresa empresa) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("DELETE FROM tblEmpresa WHERE ID = ?")){
            st.setLong(1, empresa.getID());
            st.executeUpdate();
            return empresa;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public Empresa save(Empresa empresa) throws Exception {

        try(PreparedStatement st = this.getConnection().prepareStatement("INSERT INTO tblEmpresa (ID_USUARIO, CNPJ, RazaoSocial, HorarioInicioArCondicionado, HorarioTerminoArCondicionado," +
                                                                                                   "TemperaturaMaxima, HorarioInicioFuncionamento, HorarioTerminoFuncionamento) " +
                                                                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            st.setLong(1, empresa.getUsuario().getId());
            st.setString(2, empresa.getCNPJ());
            st.setString(3, empresa.getRazaoSocial());
            st.setString(4, empresa.getHorarioInicioArCondicionado());
            st.setString(5, empresa.getHorarioTerminoArCondicionado());
            st.setInt(6, empresa.getTemperaturaMaxima());
            st.setString(7, empresa.getHorarioInicioFuncionamento());
            st.setString(8, empresa.getHorarioTerminoFuncionamento());
            st.executeUpdate();
            try(ResultSet rs = st.getGeneratedKeys()){
                while(rs.next()){
                    if(rs.getLong(1) > 0){
                        empresa.setID(rs.getLong(1));
                        return empresa;
                    }
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        throw new Exception("Erro ao inserir empresa");
    }

    public Empresa update(Empresa empresa) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("UPDATE tblEmpresa SET ID_USUARIO = ?, CNPJ = ?, RazaoSocial = ?, HorarioInicioArCondicionado = ?, "+
                                                                          "HorarioTerminoArCondicionado = ?, TemperaturaMaxima = ?, HorarioInicioFuncionamento = ?,"+
                                                                          "HorarioTerminoFuncionamento = ? WHERE ID = ?")){
            st.setLong(1, empresa.getUsuario().getId());
            st.setString(2, empresa.getCNPJ());
            st.setString(3, empresa.getRazaoSocial());
            st.setString(4, empresa.getHorarioInicioArCondicionado());
            st.setString(5, empresa.getHorarioTerminoArCondicionado());
            st.setInt(6, empresa.getTemperaturaMaxima());
            st.setString(7, empresa.getHorarioInicioFuncionamento());
            st.setString(8, empresa.getHorarioTerminoFuncionamento());
            st.setLong(9, empresa.getID());
            st.executeUpdate();
            return empresa;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

    public Empresa parseToDTO(ResultSet resultset) throws Exception {
        Empresa e = new Empresa();
        e.setID(resultset.getLong("ID"));
        e.setUsuario(new UsuarioDAOImpl().getUsuarioPorID(resultset.getLong("ID_USUARIO")));
        e.setCNPJ(resultset.getString("CNPJ"));
        e.setRazaoSocial(resultset.getString("RazaoSocial"));
        e.setHorarioInicioArCondicionado(resultset.getString("HorarioInicioArCondicionado"));
        e.setHorarioTerminoArCondicionado(resultset.getString("HorarioTerminoArCondicionado"));
        e.setTemperaturaMaxima(resultset.getInt("TemperaturaMaxima"));
        e.setHorarioInicioFuncionamento(resultset.getString("HorarioInicioFuncionamento"));
        e.setHorarioTerminoFuncionamento(resultset.getString("HorarioTerminoFuncionamento"));
        return e;
    }
}
