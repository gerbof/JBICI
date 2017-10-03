package daos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.*;

public class AlquilerDAO implements InterfazAlquilerDAO {

	static EMF emf = new EMF();
	static EntityManager em;
	static BicicletaDAO bDao;
	static EstacionDAO estDao;
	static PerfilesDAO pDao;
	static FactoryDAO fDao;
	
	public AlquilerDAO() {
		fDao = new FactoryDAO();
		bDao = fDao.getBicicletaDAO();
		estDao = fDao.getEstacionDAO();
		pDao = fDao.getPerfilesDAO();
		em = emf.getEMF();
	}
	
	public List<Activo> listarActivo(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Activo a order by a.fechaHoraRetiro");
			@SuppressWarnings("unchecked")
			List<Activo> listado = (List<Activo>) q.getResultList();
			return listado;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public List<Historico> listarHistorico(){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Historico h order by h.fechaHoraDevolucion");
			@SuppressWarnings("unchecked")
			List<Historico> listado = (List<Historico>) q.getResultList();
			return listado;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public void altaActivo(Activo activo){
		Estacion esta = estDao.buscarPorNombre(activo.getEstacionRetiro().getNombre());
		if ((esta == null) || (esta.getEstado().getDescripcion()!="Operativa") || (esta.getCantBicicletas() == 0)){
			return;
		}
		
		List<Bicicleta> bicisDisponibles = estDao.bicicletasDisponibles(esta.getNombre());
		if(bicisDisponibles.isEmpty()){
			return;
		}
		long idBicicleta = bicisDisponibles.get(0).getId(); 
		
		Bicicleta bici = bDao.buscar(idBicicleta);
		if ((bici == null) && (bici.getEstadoActual().getDescripcion()!="Apta para uso")){
			return;
		}
		
		PerfilDeUsuario p = pDao.buscarPorUsername(activo.getPerfil().getUsername());
		if ((p == null) && (p.getActivo())){
			return;
		}

		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			activo.setFechaHoraRetiro(new Date());
			activo.setBicicleta(bici);
			activo.setEstacionRetiro(esta);
			activo.setPerfil(p);
			em.persist(activo);
			etx.commit();
			estDao.sacarBicicleta(esta.getNombre());
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

	public void altaHistorico(long id, Estacion estDevolucion){
		Alquiler act = this.devolverActivo(id); 
		if (act == null){
			return;
		}
		Estacion estaD = estDao.buscarPorNombre(estDevolucion.getNombre());
		if ((estaD == null) || (estaD.getEstado().getDescripcion()!="Operativa") || (estaD.getCantLugaresLibres() == 0)){
			return;
		}

		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			Historico h = new Historico(act.getFechaHoraRetiro(), new Date(), act.getEstacionRetiro(), estaD, act.getBicicleta(), act.getPerfil());
			h.setEstacionDevolucion(estaD);
			h.setFechaHoraDevolucion(new Date());
			em.persist(h);
			etx.commit();
			this.borrarActivo(act);
			estDao.guardarBicicleta(estaD.getNombre());
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
	
	public void borrarActivo(long id){
		Alquiler act = this.devolverActivo(id); 
		if (act == null){
			return;
		}

		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			try{
				Query q = em.createQuery("DELETE Activo a where a.id=:id");
				q.setParameter("id", id);
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
	
	public void borrarActivo(Alquiler act){
		if (act == null){
			return;
		}

		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			try{
				Query q = em.createQuery("DELETE Activo a where a.id=:id");
				q.setParameter("id", act.getId());
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
	
	public List<Historico> devolverHistoricoDePersona(String username){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select h from Historico h join h.perfil p where p.username=:username order by h.fechaHoraDevolucion desc");
			q.setParameter("username", username);
			@SuppressWarnings("unchecked")
			List<Historico> historico = (List<Historico>) q.getResultList();
			return historico;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public List<Historico> devolverHistoricoDeBicicleta(long idBicicleta){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select h from Historico h join h.bicicleta b where b.id=:idBicicleta order by h.fechaHoraDevolucion desc");
			q.setParameter("idBicicleta", idBicicleta);
			@SuppressWarnings("unchecked")
			List<Historico> historico = (List<Historico>) q.getResultList();
			return historico;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public List<Historico> devolverHistoricoDeEstacionRetiro(String nombreEstacion){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select h from Historico h join h.estacionRetiro e where e.nombre = :nombreEstacion order by h.fechaHoraDevolucion desc");
			q.setParameter("nombreEstacion", nombreEstacion);
			@SuppressWarnings("unchecked")
			List<Historico> historico = (List<Historico>) q.getResultList();
			return historico;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public List<Historico> devolverHistoricoDeEstacionDevolucion(String nombreEstacion){	
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select h from Historico h join h.estacionDevolucion e where e.nombre = :nombreEstacion order by h.fechaHoraDevolucion desc");
			q.setParameter("nombreEstacion", nombreEstacion);
			@SuppressWarnings("unchecked")
			List<Historico> historico = (List<Historico>) q.getResultList();
			return historico;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public List<Activo> devolverActivoDePersona(String username){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select a from Activo a join a.perfil p where p.username=:username order by fechaHoraRetiro desc");
			q.setParameter("username", username);
			@SuppressWarnings("unchecked")
			List<Activo> activo = (List<Activo>) q.getResultList();
			return activo;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public List<Activo> devolverActivoDeBicicleta(long idBicicleta){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select a from Activo a join a.bicicleta b where b.id=:id order by fechaHoraRetiro desc");
			q.setParameter("id", idBicicleta);
			@SuppressWarnings("unchecked")
			List<Activo> activo = (List<Activo>) q.getResultList();
			return activo;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public List<Activo> devolverActivoDeEstacionRetiro(String nombreEstacion){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("select a from Activo a join a.estacionRetiro esta where esta.nombre=:nombre order by fechaHoraRetiro desc");
			q.setParameter("nombre", nombreEstacion);
			@SuppressWarnings("unchecked")
			List<Activo> activo = (List<Activo>) q.getResultList();
			return activo;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}

	public Activo devolverActivo(long id){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			//em = (new EMF()).getEMF();
			Query q = em.createQuery("from Activo a where a.id=:id");
			q.setParameter("id", id);
			List<Activo> result = (List<Activo>) q.getResultList();
			if (!result.isEmpty()){
				return result.get(0);
			}
			return null;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public Historico devolverHistorico(long id){
		if(!em.isOpen())
			em = emf.getEMF();
		try{
			Query q = em.createQuery("from Historico h where h.id=:id");
			q.setParameter("id", id);
			List<Historico> result = (List<Historico>) q.getResultList();
			if (!result.isEmpty()){
				return result.get(0);
			}
			return null;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally{
			FactoryDAO.cerrarConexion(em);
		}
		return null;
	}
	
	public void vaciarTabla(){
		if(!em.isOpen())
			em = emf.getEMF();
		EntityTransaction etx = em.getTransaction();
		try{
			etx.begin();
			em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
			em.createNativeQuery("TRUNCATE Alquiler").executeUpdate();
			em.createNativeQuery("TRUNCATE Historico").executeUpdate();
			em.createNativeQuery("TRUNCATE Activo").executeUpdate();
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
