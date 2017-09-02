package com.practica.docente.services;

import org.springframework.http.RequestEntity;
import java.util.Map;

public interface AltaRegistroService {
	
	// Metodo para la alta de registro
	public Map<String,Object> altaRegistro(RequestEntity<Object> request) throws Exception;

}
