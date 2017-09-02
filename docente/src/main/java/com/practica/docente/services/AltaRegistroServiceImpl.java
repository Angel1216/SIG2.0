package com.practica.docente.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

//import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import com.practica.docente.model.ParametrosSP;
import com.practica.docente.utilities.DataType;
import com.practica.docente.repositories.SPRepository;

@Service
@Transactional
public class AltaRegistroServiceImpl implements AltaRegistroService{

	// Inyeccion de dependencias del Repositorio JPA
	@Autowired
	private SPRepository spRepository;
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> altaRegistro(RequestEntity<Object> request) throws Exception{

		// Instanciar repositorio y catalogo de tipo de datos
		DataType dataType = new DataType();
		
		// Variables y Constantes
		Map<String,Object> mapBody = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
        final int EXITOSO = 0;
        
        // Recuperar Body 
		mapBody = (Map<String,Object>) request.getBody();
		
        // Nombre del stored procedure
        String nombreSP = "spi_altaRegistro";
        		
        // Parametros SP
        List<ParametrosSP> lstParametrosSP = new ArrayList<ParametrosSP>();
        ParametrosSP pNombre = new ParametrosSP(dataType.PARAMETRO_String,"nombre",mapBody.get("nombre").toString());
        ParametrosSP pPaterno = new ParametrosSP(dataType.PARAMETRO_String,"paterno",mapBody.get("paterno").toString());
        ParametrosSP pMaterno = new ParametrosSP(dataType.PARAMETRO_String,"materno",mapBody.get("materno").toString());
        ParametrosSP pGrupo = new ParametrosSP(dataType.PARAMETRO_String,"grupo",mapBody.get("grupo").toString());
        lstParametrosSP.add(pNombre);
        lstParametrosSP.add(pPaterno);
        lstParametrosSP.add(pMaterno);
        lstParametrosSP.add(pGrupo);

        // Invocar SP para insert, update y delete
        Vector<Object> response = (Vector<Object>)spRepository.getCRUDSP(nombreSP, lstParametrosSP);
        
        if (((int)response.get(0))==EXITOSO){
    		map.put("message", "Inserción exitosa");
        	
        } else{
        	map.put("error", response.get(1).toString());
        }
        
        
		return map;
	}
	
}
