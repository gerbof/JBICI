package daos;

import java.util.List;
import model.Persona;

public interface InterfazPersonaDAO {

	public List <Persona> listar();
	public Persona buscarPorDni(String dni);
	public void altaPersona(Persona persona);
	public void modificarPersonaConDni(Persona persona, String dni); 
	public void borrarPersona(String dni);
	public void borrarPersona(Persona p);
	public void vaciarTabla();
}
