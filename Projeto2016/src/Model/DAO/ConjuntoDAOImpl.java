package Model.DAO;

import Model.Conjunto;
import Enum.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConjuntoDAOImpl extends DAOImpl<Conjunto>{

    public ConjuntoDAOImpl(){
        super(Database.Databases.ProjetoIntegrado);
    }

    public List<Conjunto> getConjuntos() throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblConjunto")){
            List<Conjunto> list = new ArrayList<>();
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

    public Conjunto getConjuntoPorID(long id) throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblConjunto WHERE ID = ?")){
            st.setLong(1, id);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    return parseToDTO(rs);
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        throw new Exception("Erro ao obter conjunto");
    }


    public Conjunto saveUpdate(Conjunto conjunto) throws Exception {
        if(conjunto == null) throw new Exception("Conjunto não pode ser nulo");
        return conjunto.getId() <= 0 ? save(conjunto) : update(conjunto);
    }


    public Conjunto delete(Conjunto conjunto) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("DELETE FROM tblConjunto WHERE ID = ?")){
            st.setLong(1, conjunto.getId());
            st.executeUpdate();
            return conjunto;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }


    public Conjunto save(Conjunto conjunto) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("INSERT INTO tblConjunto (ID_EMPRESA, NomeConjunto, NumeroConjunto) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            st.setLong(1, conjunto.getEmpresa().getID());
            st.setString(2, conjunto.getNomeConjunto());
            st.setInt(3, conjunto.getNumeroConjunto());
            st.executeUpdate();
            try(ResultSet rs = st.getGeneratedKeys()){
                while(rs.next()){
                    if(rs.getLong(1) > 0){
                        conjunto.setId(rs.getLong(1));
                        return conjunto;
                    }
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        throw new Exception("Erro ao inserir conjunto");
    }


    public Conjunto update(Conjunto conjunto) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("UPDATE tblConjunto SET ID_EMPRESA = ?, NomeConjunto = ?, NumeroConjunto = ? WHERE ID = ?")){
            st.setLong(1, conjunto.getEmpresa().getID());
            st.setString(2, conjunto.getNomeConjunto());
            st.setInt(3, conjunto.getNumeroConjunto());
            st.setLong(4, conjunto.getId());
            st.executeUpdate();
            return conjunto;
        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }


    public Conjunto parseToDTO(ResultSet resultset) throws Exception {
        Conjunto c = new Conjunto();
        c.setId(resultset.getLong("ID"));
        c.setEmpresa(new EmpresaDAOImpl().getEmpresaPorID(resultset.getLong("ID_EMPRESA")));
        c.setNomeConjunto(resultset.getString("NomeConjunto"));
        c.setNumeroConjunto(resultset.getInt("NumeroConjunto"));
        return c;
    }
}
