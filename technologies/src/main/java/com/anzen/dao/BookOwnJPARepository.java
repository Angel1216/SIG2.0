package com.anzen.dao;

import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import com.anzen.bean.Book;
import com.anzen.bean.ParametrosSPOld;

import java.util.List;
import java.util.Vector;

import javax.persistence.StoredProcedureQuery;
import javax.persistence.ParameterMode;

@Repository
@Transactional
public class BookOwnJPARepository {
	
	// An EntityManager will be automatically injected from entityManagerFactory
	// setup on DatabaseConfig class.
	@PersistenceContext
	private EntityManager entityManager;
	
	
	  /**
	   * Return all the users stored in the database.
	   */
	  @SuppressWarnings("unchecked")
	  public List<Book> getAll() {
	    return entityManager.createQuery("from Book").getResultList();
	  }

	  /**
	   * Return the user having the passed id.
	   */
	  public Book getById(long id) {
	    return entityManager.find(Book.class, id);
	  }
	  
	  
	  
	  /**
	   * Invoke a Stored Procedure (Return Parameter: String)
	   */
	  public String getCallSP(){
		  
		  // Variable
		  String suma = "";
		  try{
			  // Create Call Stored Procedure
			  //entityManager.getTransaction().begin();
			  StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("sumaXY");
			  
			  // Set Parameters
			  storedProcedure.registerStoredProcedureParameter("x", Integer.class, ParameterMode.IN);
			  storedProcedure.registerStoredProcedureParameter("y", Integer.class, ParameterMode.IN);
			  storedProcedure.registerStoredProcedureParameter("suma", String.class, ParameterMode.OUT);
			  
			  storedProcedure.setParameter("x", 13);
			  storedProcedure.setParameter("y", 15);
			  
			  // execute SP
		      storedProcedure.execute();
		        
		      // get result
		      suma = (String)storedProcedure.getOutputParameterValue("suma");
		        System.out.println("La suma es: " + suma);
			  
		      //entityManager.getTransaction().commit();
		      //entityManager.close();
		        
			  
		  } catch(Exception ex){
			  System.out.println(ex.getMessage());
		  }
		  
		  return "La suma es: " + suma;
	  }
	  
	  
	  
	  /**
	   * Invoke a Stored Procedure (Consultas)
	   * 
	   * Tipos de Datos de PARAMETROS
	   * 1.- Enteros
	   * 2.- Decimales
	   * 3.- Texto
	   */
	  public Object getQuerySP(String nameSP, List<ParametrosSPOld> parametros){
		  
		  // Variable
		  StoredProcedureQuery storedProcedure = null;
		  Integer parametroEntero = 0;
		  Float parametroDecimal = 0F;
		  String parametroTexto = "";
		  
		  // Constantes
		  final int ENTEROS = 1, DECIMALES=2, TEXTO=3;
		  
		  try{
			  // Se invoca el SP
			  storedProcedure = entityManager.createStoredProcedureQuery(nameSP);
			  
			  // Se declaran y configuran los parametros
			  int totalParametros = parametros.size();
			  
			  // Crear parametros
			  for(int i=0; i<totalParametros; i++){
				  
				  switch(parametros.get(i).getTipoDato()){
				  
			  		case ENTEROS:
			  			parametroEntero = (Integer) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Integer.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroEntero);
			  			break;
			  		
			  		case DECIMALES:
			  			parametroDecimal = (Float) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Float.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroDecimal);
			  			break;
			  			
			  		case TEXTO:
			  			parametroTexto = (String) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), String.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroTexto);
			  			break;
			  			
			  			default:
			  				break;
				  }
				  
			  }
			  
			  // execute SP
			  storedProcedure.execute();
			  
			  
		  } catch(Exception ex){
			  System.out.println(ex.getMessage());
		  }
		  
		  return storedProcedure.getResultList();
	  }
	  
	  
	  
	  
	  /**
	   * Invoke a Stored Procedure (Insercion, actualizacion y eliminacion de datos)
	   * Tipos de Datos de PARAMETROS
	   * 1.- Enteros
	   * 2.- Decimales
	   * 3.- Texto
	   */
	  public Object getCrUpDeSP(String nameSP, List<ParametrosSPOld> parametros){
		
		  // Variable
		  StoredProcedureQuery storedProcedure = null;
		  Integer parametroEntero = 0;
		  Float parametroDecimal = 0F;
		  String parametroTexto = "";
		  Integer reslt = null;
		  String message = null;
		  Vector<Object> response = new Vector<Object>();
		  
		  // Constantes
		  final int ENTEROS = 1, DECIMALES=2, TEXTO=3;
		  
		  try{
			  // Se invoca el SP
			  storedProcedure = entityManager.createStoredProcedureQuery(nameSP);
			  
			  // Se declaran y configuran los parametros
			  int totalParametros = parametros.size();
			  
			  // Crear parametros de Entrada
			  for(int i=0; i<totalParametros; i++){
				  
				  switch(parametros.get(i).getTipoDato()){
				  
			  		case ENTEROS:
			  			parametroEntero = (Integer) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Integer.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroEntero);
			  			break;
			  		
			  		case DECIMALES:
			  			parametroDecimal = (Float) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), Float.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroDecimal);
			  			break;
			  			
			  		case TEXTO:
			  			parametroTexto = (String) parametros.get(i).getParametro();
			  			storedProcedure.registerStoredProcedureParameter(parametros.get(i).getNombreParametro(), String.class, ParameterMode.IN);
			  			storedProcedure.setParameter(parametros.get(i).getNombreParametro(), parametroTexto);
			  			break;
			  			
			  			default:
			  				break;
				  }
				  
			  }
			  
			  // Parametros de salida
			  storedProcedure.registerStoredProcedureParameter("reslt", Integer.class, ParameterMode.OUT);
			  storedProcedure.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
			  
			  // execute SP
			  storedProcedure.execute();
			  
		      // get the result
			  reslt = (Integer)storedProcedure.getOutputParameterValue("reslt");
			  message = (String)storedProcedure.getOutputParameterValue("message");
			  
			  response.add(reslt);
			  response.add(message);
			  
			  
			  
		  } catch(Exception ex){
			  System.out.println(ex.getMessage());
		  }
		  
		  return response;
	  }
}
