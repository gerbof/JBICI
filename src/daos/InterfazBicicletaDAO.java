package daos;

import java.util.List;

import model.*;


public interface InterfazBicicletaDAO {
	public List<Bicicleta> listar();
	public Bicicleta buscar(long id);
	public void altaBicicleta(Bicicleta bicicleta);
	public void modificarBicicleta(Bicicleta Bicicleta, long id); 
	public void cambiarEstadoBicicleta(long id, EstadoBici e);
	public void BiciEnDesuso(long id);
	public void vaciarTabla();
}
