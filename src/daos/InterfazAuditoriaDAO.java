package daos;

import java.util.List;
import model.AuditoriaModel;

public interface InterfazAuditoriaDAO {
	public void guardarMovimiento(String operacion, String clase, long idEntidad);	
	public void vaciarTabla();
	public List<AuditoriaModel> cantidadOperaciones(String entidad);
	public void eliminarNulos();
}
