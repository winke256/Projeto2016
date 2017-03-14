package Model.DAO;

import java.util.List;
import Model.Empresa;

public interface EmpresaDAO{
	
	List<Empresa> getEmpresas() throws Exception;

    Empresa getEmpresaPorID(long id) throws Exception;

    public Empresa getEmpresaPorIDUsuario(Long id) throws Exception;
	
}
