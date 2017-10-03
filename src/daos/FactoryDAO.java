package daos;

import javax.persistence.EntityManager;

public class FactoryDAO {
		
	public EstacionDAO getEstacionDAO(){
		return new EstacionDAO();
	}
	
	public PersonaDAO getPersonaDAO(){
		return new PersonaDAO();
	}
	
	public PerfilesDAO getPerfilesDAO(){
		return new PerfilesDAO();
	}
	
	public EstadoDAO getEstadoDAO(){
		return new EstadoDAO();
	}

	public EstadoBiciDAO getEstadoBiciDAO(){
		return new EstadoBiciDAO();
	}
	
	public BicicletaDAO getBicicletaDAO(){
		return new BicicletaDAO();
	}
	
	public HistorialDAO getHistorialDAO(){
		return new HistorialDAO();
	}
	
	public AlquilerDAO getAlquilerDAO(){
		return new AlquilerDAO();
	}
	
	public AuditoriaDAO getAuditoriaDAO(){
		return new AuditoriaDAO();
	}
	
	public void vaciarTablas(){	
		getPerfilesDAO().vaciarTabla();
		getPersonaDAO().vaciarTabla();
		getHistorialDAO().vaciarTabla();
		getEstacionDAO().vaciarTabla();
		getEstadoDAO().vaciarTabla();
		getBicicletaDAO().vaciarTabla();
		getEstadoBiciDAO().vaciarTabla();		
		getAlquilerDAO().vaciarTabla();
		getAuditoriaDAO().vaciarTabla();
	}
	
	public static boolean cerrarConexion(EntityManager em){
		try{
			if(em.isOpen())
				em.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
