package daos;

import java.util.List;

import model.*;

public interface InterfazPerfilesDAO {

	public List<PerfilDeUsuario> listar();
	public List<PerfilDeAdministrador> listarAdmin();
	public List<PerfilDeUsuario> listarUser();
	public PerfilDeUsuario buscarPorUsername(String username);
	public void altaPerfil(PerfilDeUsuario perfil);
	public void modificarPerfilConUsername(PerfilDeUsuario perfil, String username); 
	public void borrarPerfil(String username);
	public void borrarPerfil(PerfilDeUsuario p);
	public void vaciarTabla();
	public List<PerfilDeUsuario> devolverPerfilesPersona(String dni);
	public void borrarPerfilesPersona(String dni);
	public void borrarPerfilesPersona(Persona pers);
	public void inhabilitarUsuario(String username, boolean activo);
	public void inhabilitarUsuario(PerfilDeUsuario p, boolean activo);
	public String devolverTipo(String username);
}
