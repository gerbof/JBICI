package daos;

import java.util.List;
import model.*;


public interface InterfazAlquilerDAO {

	public List<Activo> listarActivo();
	public List<Historico> listarHistorico();
	
	public void altaActivo(Activo activo);
	public void altaHistorico(long id, Estacion estDevolucion);
	public void borrarActivo(long id);
	public void borrarActivo(Alquiler alquiler); 
	
	public List<Historico> devolverHistoricoDePersona(String username);
	public List<Historico> devolverHistoricoDeBicicleta(long idBicicleta);
	public List<Historico> devolverHistoricoDeEstacionRetiro(String nombreEstacion);
	public List<Historico> devolverHistoricoDeEstacionDevolucion(String nombreEstacion);
	
	public List<Activo> devolverActivoDePersona(String username);
	public List<Activo> devolverActivoDeBicicleta(long idBicicleta);
	public List<Activo> devolverActivoDeEstacionRetiro(String nombreEstacion);

	public Activo devolverActivo(long id);
	public Historico devolverHistorico(long id);
	
	public void vaciarTabla();
}
