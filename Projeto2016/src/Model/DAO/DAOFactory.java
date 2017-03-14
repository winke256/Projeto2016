package Model.DAO;

public class DAOFactory {
	
	public static EmpresaDAO initEmpresaDAO(){
		return new EmpresaDAOImpl();
	}

}
