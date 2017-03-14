package Model.DAO;

public interface DAO<T> {

    T saveUpdate(T t) throws Exception;

    T delete(T t) throws Exception;

    T save(T t) throws Exception;

    T update(T t) throws Exception;
}
