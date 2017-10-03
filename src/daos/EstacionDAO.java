package daos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.*;

public class EstacionDAO implements InterfazEstacionDAO {

	static EMF emf = new EMF();
	static EntityManager em = emf.getEMF();

	
	public List<Estacion> listar(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Estacion");
			@SuppressWarnings("unchecked")
			List<Estacion> listado = (List<Estacion>) q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<Estacion>();
	}
	
	public List<Estacion> listarHabilitadas(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select e from Estacion e join e.estado es where es.class = 'Operativa' and e.cantLugaresLibres > 0 ");
			@SuppressWarnings("unchecked")
			List<Estacion> listado = (List<Estacion>) q.getResultList();
			return listado;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<Estacion>();
	}
	
	public List<Estacion> listarHabilitadasMapa(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select e from Estacion e join e.estado es where es.class = 'Operativa' and (e.cantLugaresLibres > 0 or e.cantBicicletas > 0)");
			@SuppressWarnings("unchecked")
			List<Estacion> habilitados = (List<Estacion>) q.getResultList();
			return habilitados;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return new ArrayList<Estacion>();
	}
	
	public Estacion buscarPorNombre(String nombre){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Estacion p where nombre = :nombre");
			q.setParameter("nombre", nombre);
			List<Estacion> result = (List<Estacion>) q.getResultList();
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
	
	public void altaEstacion(Estacion estacion){
		Estacion e = this.buscarPorNombre(estacion.getNombre());
		if (e != null)
			return;
		
		FactoryDAO FDAO = new FactoryDAO(); 
		EstadoDAO eDao = FDAO.getEstadoDAO();
		Estado es = eDao.buscarPorDescripcion(estacion.getEstado().getDescripcion());
		if(es == null){
			return; 
		}
		
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			estacion.cambiarEstado(es);
			em.persist(estacion);
			etx.commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			if (etx != null) {
				etx.rollback();
			}
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
	}
	
	public void altaEstacion(Estacion estacion, Integer cantBicis){
		Estacion e = this.buscarPorNombre(estacion.getNombre());
		if (e != null)
			return;
		FactoryDAO FDAO = new FactoryDAO(); 
		EstadoDAO eDao = FDAO.getEstadoDAO();
		Estado es = eDao.buscarPorDescripcion(estacion.getEstado().getDescripcion());
		if ( es == null){
			return; 
		}
		if(!em.isOpen())
			em = emf.getEMF();		
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			estacion.cambiarEstado(es);
			estacion.setCantBicicletas(cantBicis);
			em.persist(estacion);
			etx.commit();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
	}
	
	public void modificarEstacion(Estacion estacion, String nombre){
		Estacion e = this.buscarPorNombre(nombre);
		if (e != null){
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				e.setCantBicicletas(estacion.getCantBicicletas());
				e.setCantLugaresLibres(estacion.getCantLugaresLibres());
				e.setCodPostal(estacion.getCodPostal());
				e.setNombre(estacion.getNombre());
				e.setUbicacion(estacion.getUbicacion());
				EstadoDAO eDao = (new FactoryDAO()).getEstadoDAO();
				Estado es = eDao.buscarPorDescripcion(estacion.getEstado().getDescripcion());
				if ( es!= null){
					e.cambiarEstado(es);
				}
				if(!em.isOpen())
					em = emf.getEMF();
				etx.begin();
				em.merge(e);
				etx.commit();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				if (etx != null) {
					etx.rollback();
				}
			}
			finally{
				FactoryDAO.cerrarConexion(em);
			}
		}
	}
	
	public void cambiarEstadoEstacion(String nombre, Estado es){
		Estacion e = this.buscarPorNombre(nombre);
		if (e != null){
			EstadoDAO eDao = (new FactoryDAO()).getEstadoDAO();
			es = eDao.buscarPorDescripcion(es.getDescripcion());
			if ( es!= null){
				if(!em.isOpen())
					em = emf.getEMF();
				EntityTransaction etx = em.getTransaction();
				try{
					etx.begin();
					e.cambiarEstado(es);
					em.merge(e);
					etx.commit();
				}
				catch (Exception ex) {
					ex.printStackTrace();
					if (etx != null) {
						etx.rollback();
					}
				}
				finally{
					FactoryDAO.cerrarConexion(em);
				}
			}
			
		}
	}
	
	public void cerrarEstacion(String nombre){
		Estacion e = this.buscarPorNombre(nombre);
		if (e == null)
			return;
		EstadoDAO eDao = (new FactoryDAO()).getEstadoDAO();
		eDao.altaEstado(new Cerrada());
		cambiarEstadoEstacion(nombre, eDao.buscarPorDescripcion("Cerrada"));
	}
	
	public List<Bicicleta> bicicletasDisponibles(String nombre){
		Estacion e = this.buscarPorNombre(nombre);
		if (e != null){	
			if(!em.isOpen())
				em = emf.getEMF();
			try{
				Query q = em.createQuery("select bi.id from Activo a join a.bicicleta bi");
				List<Object[]> ids = q.getResultList();
				Query q2 = em.createQuery("select b from Bicicleta b join b.estadoActual e join b.ubicacionActual u where u.nombre = :nombre and e.class = 'Apta para uso'");
				q2.setParameter("nombre", nombre);
				List<Bicicleta> listBici = q2.getResultList();
				List<Bicicleta> listBici2 = new ArrayList<Bicicleta>();
				for(Bicicleta bici : listBici){
					if(ids.contains((Object)bici.getId())){
						continue;
					}
					else{
						listBici2.add(bici);
					}		
				}
				return listBici2;
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			finally{
				FactoryDAO.cerrarConexion(em);
			}
		}
		return new ArrayList<Bicicleta>();
	}
	
	public void vaciarTabla(){
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
			em.createNativeQuery("TRUNCATE Estacion").executeUpdate();
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

	public void sacarBicicleta(String nombre){
		Estacion e = this.buscarPorNombre(nombre);
		if (e != null){
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				etx.begin();
				e.setCantBicicletas(e.getCantBicicletas()-1);
				e.setCantLugaresLibres(e.getCantLugaresLibres()+1);
				em.merge(e);
				etx.commit();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				if (etx != null) {
					etx.rollback();
				}
			}
			finally{
				FactoryDAO.cerrarConexion(em);
			}
		}
	}
	
	public void guardarBicicleta(String nombre){
		Estacion e = this.buscarPorNombre(nombre);
		if (e != null){
			if(!em.isOpen())
				em = emf.getEMF();
			EntityTransaction etx = em.getTransaction();
			try{
				etx.begin();
				e.setCantBicicletas(e.getCantBicicletas()+1);
				e.setCantLugaresLibres(e.getCantLugaresLibres()-1);
				em.merge(e);
				etx.commit();
			}
			catch (Exception ex) {
				ex.printStackTrace();
				if (etx != null) {
					etx.rollback();
				}
			}
			finally{
				FactoryDAO.cerrarConexion(em);
			}
		}
	}
}
