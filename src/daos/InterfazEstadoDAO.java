package daos;
import java.util.List;

import model.Estado;


public interface InterfazEstadoDAO {

	public List <Estado> listar();
	public void altaEstado(Estado e);
	public Estado buscarEstado(long id);
	public Estado buscarPorDescripcion(String desc);
	public void vaciarTabla();
}
