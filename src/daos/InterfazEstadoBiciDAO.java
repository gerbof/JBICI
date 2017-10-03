package daos;

import java.util.List;
import model.EstadoBici;

public interface InterfazEstadoBiciDAO {
	public List <EstadoBici> listar();
	public void altaEstadoBici(EstadoBici e);
	public EstadoBici buscarEstado(long id);
	public EstadoBici buscarPorDescripcion(String desc);
	public void vaciarTabla();
	public void borrarEstadoBici(EstadoBici e);
}
