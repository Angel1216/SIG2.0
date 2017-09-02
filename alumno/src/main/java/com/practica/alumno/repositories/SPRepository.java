package com.practica.alumno.repositories;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
import com.practica.alumno.model.ParametrosSP;
import com.practica.alumno.utilities.DataType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class SPRepository
  extends DataType
{
  @PersistenceContext
  private EntityManager entityManager;
  @Value("${spring.datasource.driver-class-name}")
  private String driverClass;
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;
  SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss.SS");
  
  public StringBuilder getQuerySP(String nameSP, List<ParametrosSP> parametros)
  {
    StoredProcedureQuery storedProcedure = null;
    try
    {
      storedProcedure = createStoredProcedure(nameSP, parametros);
      System.out.println(String.valueOf("Inicio Respuesta BD(getQuerySP): \t" + this.time.format(new Date())));
      
      storedProcedure.execute();
      System.out.println(String.valueOf("Fin Respuesta BD(getQuerySP): \t\t" + this.time.format(new Date())));
    }
    catch (Exception ex)
    {
      ex.getMessage();
    }
    return convertJSON((ArrayList<?>)storedProcedure.getResultList());
  }
  
  public Vector<Object> getCRUDSP(String nameSP, List<ParametrosSP> parametros)
  {
    Vector<Object> response = new Vector<Object>();
    boolean banTypeConnection_JDBC = false;
    for (ParametrosSP aux : parametros) {
      if (aux.getTipoDato() == 10) {
        banTypeConnection_JDBC = true;
      }
    }
    try
    {
      if (banTypeConnection_JDBC == true)
      {
        response = createSQLDataTable(nameSP, parametros);
      }
      else
      {
        StoredProcedureQuery storedProcedure = null;
        Integer result = null;
        String message = null;
        
        storedProcedure = createStoredProcedure(nameSP, parametros);
        
        storedProcedure.registerStoredProcedureParameter("result", Integer.class, ParameterMode.OUT);
        storedProcedure.registerStoredProcedureParameter("message", String.class, ParameterMode.OUT);
        
        storedProcedure.execute();
        
        result = (Integer)storedProcedure.getOutputParameterValue("result");
        message = (String)storedProcedure.getOutputParameterValue("message");
        
        response.add(result);
        response.add(message);
      }
    }
    catch (Exception ex)
    {
      response.add(Integer.valueOf(-1));
      response.add(ex.getMessage().toString());
    }
    return response;
  }
  
  private StoredProcedureQuery createStoredProcedure(String nameSP, List<ParametrosSP> parametros)
  {
    StoredProcedureQuery storedProcedure = null;
    try
    {
      storedProcedure = this.entityManager.createStoredProcedureQuery(nameSP);
      
      int totalParametros = parametros.size();
      for (int i = 0; i < totalParametros; i++) {
        switch (((ParametrosSP)parametros.get(i)).getTipoDato())
        {
        case 1: 
          this.parametroInteger = ((Integer)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), Integer.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroInteger);
          break;
        case 2: 
          this.parametroFloat = ((Float)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), Float.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroFloat);
          break;
        case 3: 
          this.parametroString = ((String)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), String.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroString);
          break;
        case 4: 
          this.parametroByte = ((Byte)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), Byte.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroByte);
          break;
        case 5: 
          this.parametroShort = ((Short)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), Short.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroShort);
          break;
        case 6: 
          this.parametroLong = ((Long)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), Long.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroLong);
          break;
        case 7: 
          this.parametroDouble = ((Double)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), Double.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroDouble);
          break;
        case 8: 
          this.parametroBoolean = ((Boolean)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), Boolean.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroBoolean);
          break;
        case 9: 
          this.parametroCharacter = ((Character)((ParametrosSP)parametros.get(i)).getParametro());
          storedProcedure.registerStoredProcedureParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), Character.class, ParameterMode.IN);
          storedProcedure.setParameter(((ParametrosSP)parametros.get(i)).getNombreParametro(), this.parametroCharacter);
        }
      }
    }
    catch (Exception ex)
    {
      ex.getMessage();
    }
    return storedProcedure;
  }
  
  @SuppressWarnings("unchecked")
  private Vector<Object> createSQLDataTable(String nameSP, List<ParametrosSP> parametros)
  {
    Vector<Object> response = new Vector<Object>();
    try
    {
      int totalParametros = parametros.size();
      for (int i = 0; i < totalParametros; i++) {
        switch (((ParametrosSP)parametros.get(i)).getTipoDato())
        {
        case 10: 
          DriverManagerDataSource source = new DriverManagerDataSource();
          source.setDriverClassName(this.driverClass);
          source.setUrl(this.url);
          source.setUsername(this.username);
          source.setPassword(this.password);
          
          NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(source);
          
          Map<String, Object> mpDatos = (Map<String, Object>)((ParametrosSP)parametros.get(i)).getParametro();
          
          MapSqlParameterSource mpSource = new MapSqlParameterSource();
          mpSource.addValue(((ParametrosSP)parametros.get(i)).getNombreParametro(), mpDatos.get("dataTable"));
          namedParameterJdbcTemplate.update(mpDatos.get("sql").toString(), mpSource);
        }
      }
    }
    catch (Exception ex)
    {
      ex.getMessage();
    }
    return response;
  }
  
  private StringBuilder convertJSON(ArrayList<?> lstConsulta)
  {
    StringBuilder json = new StringBuilder();
    json.append("[");
    
    int totalregistros = lstConsulta.size();
    System.out.println(String.valueOf("Total Registros para generar JSON: \t" + totalregistros));
    System.out.println(String.valueOf("Inicio --> Genera JSON: \t\t" + this.time.format(new Date())));
    for (int i = 0; i < totalregistros; i++)
    {
      json.append(lstConsulta.get(i));
      if (i + 1 != totalregistros) {
        json.append(",");
      }
    }
    json.append("]");
    System.out.println(String.valueOf("Fin --> Genera JSON: \t\t\t" + this.time.format(new Date())));
    return json;
  }
}
