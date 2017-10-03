package daos;

import java.util.List;

import model.*;


public interface InterfazHistorialDAO {
	public List<Historial> listar();
	public List<Historial> listarParaBicicleta(long idBicicleta);
	public Historial buscarUltimo(long idBicicleta);
	public void altaHistorial(Historial historial);
	public void vaciarTabla();
}
