package Model.DAO;

import Enum.*;
import java.sql.*;

public abstract class DAOImpl<T> implements DAO<T> {

    private final String CONECTION_IP   = "localhost";
    private final String CONECTION_USER = "root";
    private final String CONECTIO_PASS  = "123456";
    private String database;

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://"+CONECTION_IP+"/"+database+"", CONECTION_USER, CONECTIO_PASS);
    }

    public DAOImpl(Database.Databases database){
        this.database = Database.getDatabaseName(database);
    }

    public abstract T saveUpdate(T t) throws Exception;
    public abstract T delete(T t) throws Exception;
    public abstract T save(T t) throws Exception;
    public abstract T update(T t) throws Exception;
    public abstract T parseToDTO(ResultSet resultset) throws Exception;


}
