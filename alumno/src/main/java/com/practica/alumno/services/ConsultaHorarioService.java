package com.practica.alumno.services;

import org.json.simple.JSONArray;
import org.springframework.http.RequestEntity;

public interface ConsultaHorarioService {
	
	public JSONArray obtenerProfesores(RequestEntity<Object> request) throws Exception;

}
