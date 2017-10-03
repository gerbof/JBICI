package daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.PerfilDeUsuario;
import model.Persona;

public class PersonaDAO implements InterfazPersonaDAO {
	
	static EMF emf = new EMF();
	static EntityManager em = emf.getEMF();
	
	public PersonaDAO() {
	}
	
	public List<Persona> listar(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Persona");
			@SuppressWarnings("unchecked")
			List<Persona> listado = (List<Persona>) q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<Persona>();
	}
	
	public Persona buscarPorDni(String dni){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Persona p where dni = :dni");
			q.setParameter("dni", dni);
			List<Persona> result = (List<Persona>) q.getResultList();
			if (!result.isEmpty()){
				return result.get(0);
			}
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public void altaPersona(Persona p){
		Persona per = this.buscarPorDni(p.getDni());
		if (per != null)
			return;
		
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			em.persist(p);
			etx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			if (etx != null) {
				etx.rollback();
			}
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
	}
	
	public void modificarPersonaConDni(Persona persona, String dni){
		Persona p = this.buscarPorDni(dni);
		if (p != null){
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				etx.begin();
				p.setApellido(persona.getApellido());
				p.setDni(persona.getDni());
				p.setDomicilio(persona.getDomicilio());
				p.setEmail(persona.getEmail());
				p.setFechaNacimiento(persona.getFechaNacimiento());
				p.setNombres(persona.getNombres());
				p.setSexo(persona.getSexo());
				em.merge(p);
				etx.commit();
			}
			catch (Exception e) {
				e.printStackTrace();
				if (etx != null) {
					etx.rollback();
				}
			}
			finally{
				FactoryDAO.cerrarConexion(em);
			}
		}
	}
	
	public void borrarPersona(String dni){
		Persona p = this.buscarPorDni(dni);
		if (p != null){
			List<PerfilDeUsuario> perfiles = new FactoryDAO().getPerfilesDAO().devolverPerfilesPersona(dni);
			if (!perfiles.isEmpty())
				return;
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				etx.begin();
				em.remove(p);
				etx.commit();
			}
			catch (Exception e) {
				e.printStackTrace();
				if (etx != null) {
					etx.rollback();
				}
			}
			finally{
				FactoryDAO.cerrarConexion(em);
			}
		}
	}
	
	public void borrarPersona(Persona p){
		if (p != null){
			List<PerfilDeUsuario> perfiles = new FactoryDAO().getPerfilesDAO().devolverPerfilesPersona(p.getDni());
			if (!perfiles.isEmpty())
				return;
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				etx.begin();
				em.remove(em.contains(p) ? p : em.merge(p));
				etx.commit();
			}
			catch (Exception e) {
				e.printStackTrace();
				if (etx != null) {
					etx.rollback();
				}
			}
			finally{
				FactoryDAO.cerrarConexion(em);
			}
		}
	}
	
	public void vaciarTabla(){
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
			em.createNativeQuery("TRUNCATE Persona").executeUpdate();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
			etx.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			if (etx != null) {
				etx.rollback();
			}
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
	}
}
