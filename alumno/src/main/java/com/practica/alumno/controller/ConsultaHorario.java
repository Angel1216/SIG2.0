package com.practica.alumno.controller;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.practica.alumno.services.ConsultaHorarioService;

/**
*
* @author Nombre del desrrollador
*
*/
@RestController
public class ConsultaHorario {

	// Log para la traza
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaHorario.class);
	
	@Autowired
	private ConsultaHorarioService service;
	
	
	/**
	 * Servicio para alta de registro de profesor
	 * 
     * @param Request
     * 
     * @return Response
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/obtenerProfesores", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Object>> obtenerProfesores(RequestEntity<Object> request){
		LOGGER.info("EndPoint-obtenerProfesores-alumno");
		JSONArray resultado = null;
		Map<String,Object> map = new HashMap<String,Object>();
		
		try{
			resultado = service.obtenerProfesores(request);
		} catch(Exception ex){
			LOGGER.error("Error al consumir el servicio (obtenerProfesores): " + ex.getMessage());
			map.put("Error", ex.getMessage());
			return new ResponseEntity<Collection<Object>>((map.values()), HttpStatus.BAD_REQUEST);
		}	

		return new ResponseEntity<Collection<Object>>((resultado), HttpStatus.OK);
	}
	
	
	
	/**
	 * Servicio para retornar Horario
     * @param 
     * 
     * @return 
     * @throws 
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/obtenerHorario", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Object> obtenerHorario(RequestEntity<Object> request) {
		
		LOGGER.info("EndPoint obtenerHorario");
		
		// Variables
		Map<String,Object> mapBody = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		
		mapBody = (Map<String,Object>) request.getBody();
		String idHorario = mapBody.get("idHorario").toString();
		System.out.println("idHorario");
		System.out.println(idHorario);
		ClassPathResource pdfFile = null;
		long contentLengthPDF = 0;
		InputStream inputStream = null; 
		
		try{
			pdfFile = new ClassPathResource(idHorario);
			contentLengthPDF = pdfFile.contentLength();
			inputStream = pdfFile.getInputStream();
		} catch(Exception ex){
			LOGGER.error("Error al retornar el archivo: " + ex.getMessage());
			map.put("Error", ex.getMessage());
			return new ResponseEntity<Object>((map.values()), HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok()
							 .contentLength(contentLengthPDF)
							 .contentType(MediaType.parseMediaType("application/octet-stream"))
							 .body(new InputStreamResource(inputStream));
	}
		
	

}
