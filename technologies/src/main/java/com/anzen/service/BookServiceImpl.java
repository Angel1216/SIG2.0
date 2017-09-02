package com.anzen.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;

import com.anzen.dao.BookCrudRepository;
import com.anzen.dao.BookOwnJPARepository;
import com.anzen.dao.SPRepository;
import com.anzen.utilities.DataType;
import com.anzen.dao.BookOwnHibernateRepository;
import com.anzen.bean.Libro;
import com.anzen.bean.ParametrosSP;
import com.anzen.bean.Book;
import com.anzen.bean.ParametrosSPOld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.anzen.dao.BookMyBatisMapper;


@Service
@Transactional
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookCrudRepository bookRepository;
	
	@Autowired
	private BookOwnJPARepository bookOwnJPARepository; 
	
	@Autowired
	private BookOwnHibernateRepository bookOwnHibernateRepository;
	
	@Autowired
	private BookMyBatisMapper bookMyBatisMapper;
	
	@Autowired
	private SPRepository spRepository;
	
	// CrudRepository
	public Book findOne(long id) {
		return bookRepository.findOne(id);
	}

	public List<Book> findAll() {
		return (List<Book>) bookRepository.findAll();
	}


	// BookOwnJPARepository
	public Book getById(long id) {
		return bookOwnJPARepository.getById(id);
	}
	
	public List<Book> getAll() {
		return bookOwnJPARepository.getAll();
	}

	@Override
	public String getCallSP() {
		return bookOwnJPARepository.getCallSP();
	}
	
	@Override
	public List<Libro> getQueryBookSP(int id) {
		
		// Nombre SP
		String nombreSP = "spQueryBook";
		
		// Parametros SP
		List<ParametrosSPOld> lstParametrosSP = new ArrayList<ParametrosSPOld>();
		ParametrosSPOld pId = new ParametrosSPOld(1,"id",id);
		lstParametrosSP.add(pId);
		
		// Invocar SP
		@SuppressWarnings("unchecked")
		List<Libro> lstBook = (List<Libro>)bookOwnJPARepository.getQuerySP(nombreSP, lstParametrosSP);
		return lstBook;
	}

	@Override
	public String getCreateBookSP(int id, String autor, String name) {
		
		// Nombre SP
		String nombreSP = "spInsertBook";
		
		// Parametros SP
		List<ParametrosSPOld> lstParametrosSP = new ArrayList<ParametrosSPOld>();
		ParametrosSPOld pId = new ParametrosSPOld(1,"id",id);
		ParametrosSPOld pAuthor = new ParametrosSPOld(3,"author",autor);
		ParametrosSPOld pName = new ParametrosSPOld(3,"name",name);
		lstParametrosSP.add(pId);
		lstParametrosSP.add(pAuthor);
		lstParametrosSP.add(pName);
		
		// Invocar SP
		@SuppressWarnings("unchecked")
		Vector<Object> response = (Vector<Object>)bookOwnJPARepository.getCrUpDeSP(nombreSP, lstParametrosSP);
		
		// Regresar respuesta
		if((Integer)response.get(0)==0){
			return "Exitoso";
		} else {
			return (String)response.get(1);
		}
				
		
	}
	
	
	// BookOwnHibernateRepository
	@Override
	public List<Book> getAllBooks() {
		return bookOwnHibernateRepository.getAllBooks();
	}

	@Override
	public List<Book> getBook(long id) {
		return bookOwnHibernateRepository.getBook(id);
	}

	
	// BookMyBatisMapper
	@Override
	public Book findBookById(long id) {
		return bookMyBatisMapper.findBookById(id);
	}

	@Override
	public List<Book> findAllBooks() {
		return bookMyBatisMapper.findAllBooks();
	}

	@Override
	public String getCallSPMyBatis(int param1, int param2, String texto) {
		
		Map<String, Object> params = new HashMap<String, Object>();
	    params.put("param1", param1);
	    params.put("param2", param2);
	    
		bookMyBatisMapper.getCallSPMyBatis(params);
		
		return "La suma es: " + params.get("suma").toString();
	}

	
	
	
	
	
	
	// Testing
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