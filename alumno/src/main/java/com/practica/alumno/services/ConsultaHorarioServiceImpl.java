package com.practica.alumno.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practica.alumno.model.ParametrosSP;
import com.practica.alumno.repositories.SPRepository;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

@Service
@Transactional
public class ConsultaHorarioServiceImpl implements ConsultaHorarioService {
	
	// Inyeccion de dependencias del Repositorio JPA
	@Autowired
	private SPRepository spRepository;
	
	public JSONArray obtenerProfesores(RequestEntity<Object> request) throws Exception{
		
        // Invocar SP
		String nombreSP = "spr_obtenerProfesores";
        List<ParametrosSP> lstParametrosSP = new ArrayList<ParametrosSP>();
        StringBuilder JSON = spRepository.getQuerySP(nombreSP, lstParametrosSP);
        
        // Parseador JSON (String to JSONObject)
        JSONParser parser = new JSONParser();
        JSONArray query = (JSONArray) parser.parse(JSON.toString());
    	
		return query;
	}

}