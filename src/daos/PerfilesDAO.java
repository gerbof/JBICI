package daos;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.*;

public class PerfilesDAO implements InterfazPerfilesDAO {
	
	static EMF emf = new EMF();
	static EntityManager em = emf.getEMF();
	
	public List<PerfilDeUsuario> listar(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from PerfilDeUsuario");
			@SuppressWarnings("unchecked")
			List<PerfilDeUsuario> listado = (List<PerfilDeUsuario>) q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<PerfilDeUsuario>();
	}
	
	public List<PerfilDeAdministrador> listarAdmin(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from PerfilDeUsuario where tipo = 'Administrador'");
			@SuppressWarnings("unchecked")
			List<PerfilDeAdministrador> listado = (List<PerfilDeAdministrador>) q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<PerfilDeAdministrador>();
	}
	
	public List<PerfilDeUsuario> listarUser(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from PerfilDeUsuario where tipo = 'Usuario'");
			@SuppressWarnings("unchecked")
			List<PerfilDeUsuario> listado = (List<PerfilDeUsuario>) q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<PerfilDeUsuario>();
	}
	
	public PerfilDeUsuario buscarPorUsername(String username){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from PerfilDeUsuario where username = :user");
			q.setParameter("user", username);
			List<PerfilDeUsuario> result = (List<PerfilDeUsuario>) q.getResultList();
			
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
	
	public String devolverTipo(String username){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createNativeQuery("select tipo from perfiles where username = :user");
			q.setParameter("user", username);
			List<String> result = (List<String>) q.getResultList();
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
	
	public void altaPerfil(PerfilDeUsuario perfil){
		PerfilDeUsuario p = this.buscarPorUsername(perfil.getUsername());
		if (p != null)
			return;
		
		new PersonaDAO().altaPersona(perfil.getPersona());
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			em.persist(perfil);
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
	
	public void modificarPerfilConUsername(PerfilDeUsuario p, String username){
		PerfilDeUsuario per = this.buscarPorUsername(username);
		if (per != null){
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				per.setPassword(p.getPassword());
				per.setPersona(p.getPersona());
				per.setUsername(p.getUsername());
				per.setActivo(p.getActivo());
				new PersonaDAO().altaPersona(per.getPersona());
				if(!em.isOpen())
					em = emf.getEMF();
				etx.begin();
				em.merge(per);
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
	
	public void borrarPerfil(String username){
		PerfilDeUsuario p = this.buscarPorUsername(username);
		if (p != null){
			AlquilerDAO aDao = new FactoryDAO().getAlquilerDAO();
			List<Activo> activos =  aDao.devolverActivoDePersona(username);
			List<Historico> historico = aDao.devolverHistoricoDePersona(username);
			if ((!activos.isEmpty())&&(!historico.isEmpty()))
				return;
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				etx.begin();
				//em.remove(p);
				try{
					Query q = em.createQuery("DELETE PerfilDeUsuario p where p.username=:id");
					q.setParameter("id", username);
					q.executeUpdate();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
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
	
	public void borrarPerfil(PerfilDeUsuario p){
		if (p != null){
			AlquilerDAO aDao = new FactoryDAO().getAlquilerDAO();
			List<Activo> activos =  aDao.devolverActivoDePersona(p.getUsername());
			List<Historico> historico = aDao.devolverHistoricoDePersona(p.getUsername());
			if ((!activos.isEmpty())&&(!historico.isEmpty()))
				return;
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				etx.begin();
				//em.remove(p);
				try{
					Query q = em.createQuery("DELETE PerfilDeUsuario p where p.username=:id");
					q.setParameter("id", p.getUsername());
					q.executeUpdate();
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
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
			em.createNativeQuery("TRUNCATE perfiles").executeUpdate();
			em.createNativeQuery("TRUNCATE persona").executeUpdate();
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
	
	public List<PerfilDeUsuario> devolverPerfilesPersona(String dni){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select per from PerfilDeUsuario per join per.persona p where p.dni = :dni");
			q.setParameter("dni", dni);
			@SuppressWarnings("unchecked")
			List<PerfilDeUsuario> perfiles = (List<PerfilDeUsuario>) q.getResultList();
			return perfiles;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<PerfilDeUsuario>();
	}
	
	public void borrarPerfilesPersona(String dni){
		List<PerfilDeUsuario> p = this.devolverPerfilesPersona(dni);
		AlquilerDAO aDao = new FactoryDAO().getAlquilerDAO();
		for(PerfilDeUsuario per: p){
			List<Activo> activos =  aDao.devolverActivoDePersona(per.getUsername());
			List<Historico> historico = aDao.devolverHistoricoDePersona(per.getUsername());
			if ((!activos.isEmpty())&&(!historico.isEmpty()))
				break;
			this.borrarPerfil(per.getUsername());
		}
	}
	
	public void borrarPerfilesPersona(Persona pers){
		List<PerfilDeUsuario> p = this.devolverPerfilesPersona(pers.getDni());
		AlquilerDAO aDao = new FactoryDAO().getAlquilerDAO();
		for(PerfilDeUsuario per: p){
			List<Activo> activos =  aDao.devolverActivoDePersona(per.getUsername());
			List<Historico> historico = aDao.devolverHistoricoDePersona(per.getUsername());
			if ((!activos.isEmpty())&&(!historico.isEmpty()))
				break;
			this.borrarPerfil(per.getUsername());
		}
	}
	
	public void inhabilitarUsuario(String username, boolean activo){
		PerfilDeUsuario p = this.buscarPorUsername(username);
		if (p != null){
			p.setActivo(activo);
			this.modificarPerfilConUsername(p, username);
		}
	}
	
	public void inhabilitarUsuario(PerfilDeUsuario p, boolean activo){
		if (p != null){
			p.setActivo(activo);
			this.modificarPerfilConUsername(p, p.getUsername());
		}
	}
}
