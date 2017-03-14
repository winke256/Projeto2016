package Model.DAO;

import Model.ControleTemperatura;
import Enum.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ControleTemperaturaDAOImpl extends DAOImpl<ControleTemperatura> {

    public ControleTemperaturaDAOImpl(){
        super(Database.Databases.ProjetoIntegrado);
    }

    public ControleTemperatura getControleTemperaturaPorID(long id) throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblControleTemperatura WHERE ID = ?")){
            st.setLong(1, id);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    return parseToDTO(rs);
                }
            }
        }
        throw new Exception("Erro ao recuperar ControleTemperatura");
    }


    public ControleTemperatura saveUpdate(ControleTemperatura controleTemperatura) throws Exception {
        if(controleTemperatura == null) throw new Exception("ControleTemperatura não pode ser nulo");
        return controleTemperatura.getId() <= 0 ? save(controleTemperatura) : update(controleTemperatura);
    }


    public ControleTemperatura delete(ControleTemperatura controleTemperatura) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("DELETE FROM tblControleTemperatura WHERE ID = ?")){
            st.setLong(1, controleTemperatura.getId());
            st.executeUpdate();
            return controleTemperatura;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }


    public ControleTemperatura save(ControleTemperatura controleTemperatura) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("INSERT INTO tblControleTemperatura (ID_EMPRESA, Status) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)){
            st.setLong(1, controleTemperatura.getEmpresa().getID());
            st.setString(2, controleTemperatura.getStatus());
            st.executeUpdate();
            try(ResultSet rs = st.getGeneratedKeys()){
                while(rs.next()){
                    if(rs.getLong(1) > 0){
                        controleTemperatura.setId(rs.getLong(1));
                        return controleTemperatura;
                    }
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        throw new Exception("Erro ao inserir ControleTemperatura");
    }


    public ControleTemperatura update(ControleTemperatura controleTemperatura) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("UPDATE tblControleTemperatura SET ID_EMPRESA = ?, Status = ? WHERE ID = ?")){
            st.setLong(1, controleTemperatura.getEmpresa().getID());
            st.setString(2, controleTemperatura.getStatus());
            st.setLong(3, controleTemperatura.getId());
            st.executeUpdate();
            return controleTemperatura;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }


    public ControleTemperatura parseToDTO(ResultSet resultset) throws Exception {
        ControleTemperatura c = new ControleTemperatura();
        c.setId(resultset.getLong("ID"));
        c.setEmpresa(new EmpresaDAOImpl().getEmpresaPorID(resultset.getLong("ID_EMPRESA")));
        c.setStatus(resultset.getString("Status"));
        return c;
    }
}
