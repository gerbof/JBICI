package aspectos;

import java.lang.reflect.Method;

import daos.*;

public aspect Aspecto{
	
   static FactoryDAO fDao = new FactoryDAO();
	
   //POINTCUTS	
    
   public pointcut altaObjeto(Object objeto):execution( void daos.*.alta*(..)) && args(objeto, ..); 
   public pointcut modificarObjeto(Object objeto):execution( void daos.*.modificar*(..)) && args(objeto, ..); 
   public pointcut borrarObjeto(Object objeto):execution( void daos.*.borrar*(..)) && args(objeto);   
   public pointcut inhabilitarObjeto(Object objeto):execution( void daos.*.inhabilitar*(..)) && args(objeto,..);    
  
   //ADVICES 
   
  after(Object objeto) : altaObjeto(objeto){
	  try{
	  Method metodo = objeto.getClass().getMethod("getId");
		  try{
			  long id = (long) metodo.invoke(objeto);
			  String nombreClase = objeto.getClass().toString().replace("class model.", "");
			  fDao.getAuditoriaDAO().guardarMovimiento("alta", nombreClase, id);
		  }
		  catch(Exception e){
		  }
	  }
	  catch(Exception e){
	  }
   }

	after(Object objeto) : modificarObjeto(objeto){
	  try{
	      Method metodo = objeto.getClass().getMethod("getId");
		  try{
			  long id = (long) metodo.invoke(objeto);
			  String nombreClase = objeto.getClass().toString().replace("class model.", "");
			  fDao.getAuditoriaDAO().guardarMovimiento("modificacion", nombreClase, id);
		  }
		  catch(Exception e){
		  }
	  }
	  catch(Exception e){
	  }
   }
   	
   after(Object objeto) : borrarObjeto(objeto){
	 try{
		 Method metodo = objeto.getClass().getMethod("getId");
		 try{
			 long id = (long) metodo.invoke(objeto);
			 String nombreClase = objeto.getClass().toString().replace("class model.", "");
			 fDao.getAuditoriaDAO().guardarMovimiento("borrado", nombreClase, id);
		 }
		 catch(Exception e){
		 }
	 }
	 catch(Exception e){
	 }
   }
   
   after(Object objeto) : inhabilitarObjeto(objeto){
     try{
		 Method metodo = objeto.getClass().getMethod("getId");
		 try{
			 long id = (long) metodo.invoke(objeto);
			 String nombreClase = objeto.getClass().toString().replace("class model.", "");
			 fDao.getAuditoriaDAO().guardarMovimiento("inhabilitacion", nombreClase, id);
		 }
		 catch(Exception e){
		 }
	 }
	 catch(Exception e){
	 }
   }
}




