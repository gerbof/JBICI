package daos;

import java.util.List;

import model.Bicicleta;
import model.Estacion;
import model.Estado;


public interface InterfazEstacionDAO {
	public List<Estacion> listar();
	public Estacion buscarPorNombre(String nombre);
	public List<Estacion> listarHabilitadasMapa();
	public List<Estacion> listarHabilitadas();
	public void altaEstacion(Estacion estacion);
	public void modificarEstacion(Estacion estacion, String nombre); 
	public void cambiarEstadoEstacion(String nombre, Estado e);
	public List<Bicicleta> bicicletasDisponibles(String nombre);
	public void cerrarEstacion(String nombre);
	public void vaciarTabla();
}
