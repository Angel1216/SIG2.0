package com.practica.docente.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collection;
import com.practica.docente.services.AltaRegistroService;
import org.springframework.http.HttpStatus;


/**
*
* @author Nombre del desrrollador
*
*/

@RestController
public class AltaRegistro {

	// Log para la traza
	private static final Logger LOGGER = LoggerFactory.getLogger(AltaRegistro.class);

	// Implementacion del servicio de AltaRegistro
	@Autowired
	private AltaRegistroService service;

	
	
	/**
	 * Servicio para alta de registro de profesor
	 * 
     * @param Request
     * 
     * @return Response
     */
	@RequestMapping(value = "/altaRegistro", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Object>> altaRegistro(RequestEntity<Object> request) {
		
		LOGGER.info("EndPoint-altaRegistro-docente");

		Map<String, Object> resultado = null;
		
		try{
			resultado = service.altaRegistro(request);
		} catch(Exception ex){
			LOGGER.error("Error al consumir el servicio (altaRegistro): " + ex.getMessage());
			return new ResponseEntity<Collection<Object>>(HttpStatus.FAILED_DEPENDENCY);
		}
		
		return new ResponseEntity<Collection<Object>>(resultado.values(), HttpStatus.OK);
	}
	
}