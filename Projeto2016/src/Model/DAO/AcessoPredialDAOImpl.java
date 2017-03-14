package Model.DAO;

import Model.AcessoPredial;
import Enum.*;
import Util.DataFormatter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AcessoPredialDAOImpl extends DAOImpl<AcessoPredial>{

    public AcessoPredialDAOImpl(){
        super(Database.Databases.ProjetoIntegrado);
    }


    public AcessoPredial getAcessoPredialPorID(long id) throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblAcessoPredial WHERE ID = ?")){
            st.setLong(1, id);
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    return parseToDTO(rs);
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        throw new Exception("Erro ao obter AcessoPredial");
    }

    public List<AcessoPredial> getAcessosPredial() throws Exception{
        try(PreparedStatement st = this.getConnection().prepareStatement("SELECT * FROM tblAcessoPredial ORDER BY DataEntrada DESC")){
            List<AcessoPredial> list = new ArrayList<>();
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

    public List<AcessoPredial> getAcessoPredialPorDataEmpresa(String data, Long idEmpresa){
        List list = new ArrayList<>();
        boolean isData = false;
        if(data.replace("/", "").trim().length() > 0){
            isData = true;
        }
        String query = isData && idEmpresa > 0 ? "SELECT * FROM tblAcessoPredial WHERE (DATE_FORMAT(DataEntrada, '%d/%m/%Y') = '"+data+"' OR DATE_FORMAT(DataSaida, '%d/%m/%Y') = '"+data+"') AND ID_EMPRESA = "+idEmpresa+" ORDER BY DataSaida DESC":
                       isData && idEmpresa <= 0 ? "SELECT * FROM tblAcessoPredial WHERE (DATE_FORMAT(DataEntrada, '%d/%m/%Y') = '"+data+"' OR DATE_FORMAT(DataSaida, '%d/%m/%Y') = '"+data+"') ORDER BY DataSaida DESC":
                       (data == null || !isData) && idEmpresa > 0 ? "SELECT * FROM tblAcessoPredial WHERE ID_EMPRESA = "+idEmpresa+" ORDER BY DataEntrada DESC":
                        "SELECT * FROM tblAcessoPredial ORDER BY DataEntrada DESC";
        try(PreparedStatement st = this.getConnection().prepareStatement(query)){
            try(ResultSet rs = st.executeQuery()){
                while(rs.next()){
                    list.add(parseToDTO(rs));
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    public AcessoPredial saveUpdate(AcessoPredial acessoPredial) throws Exception {
        if(acessoPredial == null) throw new Exception("AcessoPredial nao pode ser nulo");
        return acessoPredial.getID() <= 0 ? save(acessoPredial) : update(acessoPredial);
    }


    public AcessoPredial delete(AcessoPredial acessoPredial) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("DELETE FROM tblAcessoPredial WHERE ID = ?")){
            st.setLong(1, acessoPredial.getID());
            st.executeUpdate();
            return acessoPredial;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }


    public AcessoPredial save(AcessoPredial acessoPredial) throws Exception {

        try(PreparedStatement st = this.getConnection().prepareStatement("INSERT INTO tblAcessoPredial (ID_EMPRESA, Identificacao, DataEntrada, DataSaida) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            st.setLong(1, acessoPredial.getEmpresa().getID());
            st.setString(2, acessoPredial.getIdentificacao());
            st.setString(3, acessoPredial.getDataEntrada() != null ? DataFormatter.DateToString(acessoPredial.getDataEntrada(), null) : null);
            st.setString(4, acessoPredial.getDataSaida() != null ? DataFormatter.DateToString(acessoPredial.getDataSaida(), null) : null);
            st.executeUpdate();
            try(ResultSet rs = st.getGeneratedKeys()){
                while(rs.next()){
                    if(rs.getLong(1) > 0){
                        acessoPredial.setID(rs.getLong(1));
                        return acessoPredial;
                    }
                }
            }
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
        throw new Exception("Erro ao inserir AcessoPredial");
    }


    public AcessoPredial update(AcessoPredial acessoPredial) throws Exception {
        try(PreparedStatement st = this.getConnection().prepareStatement("UPDATE tblAcessoPredial SET ID_EMPRESA = ?, Identificacao = ?, DataEntrada = ?, DataSaida = ? WHERE ID = ?")){
            st.setLong(1, acessoPredial.getEmpresa().getID());
            st.setString(2, acessoPredial.getIdentificacao());
            st.setString(3, acessoPredial.getDataEntrada() != null ? DataFormatter.DateToString(acessoPredial.getDataEntrada(), null) : null);
            st.setString(4, acessoPredial.getDataSaida() != null ? DataFormatter.DateToString(acessoPredial.getDataSaida(), null) : null);
            st.executeUpdate();
            return acessoPredial;
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }


    public AcessoPredial parseToDTO(ResultSet resultset) throws Exception {
        AcessoPredial a = new AcessoPredial();
        a.setID(resultset.getLong("ID"));
        a.setEmpresa(new EmpresaDAOImpl().getEmpresaPorID(resultset.getLong("ID_EMPRESA")));
        a.setIdentificacao(resultset.getString("Identificacao"));
        a.setDataEntrada(resultset.getDate("DataEntrada"));
        a.setDataSaida(resultset.getDate("DataSaida"));
        return a;
    }
}
