package com.anzen.controller;

import org.springframework.web.bind.annotation.RestController;
import com.anzen.bean.Book;
import com.anzen.bean.Libro;
import com.anzen.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;


@RestController
public class BooksController {

	// Inyeccion de dependencias del servicio
	@Autowired
	private BookService bookService;

	
	// Metodos
	
	// CrudRepository
	@RequestMapping(value = "/CrudRepository/{id}")
	public Book getBook(@PathVariable int id) {
		Book book = bookService.findOne(id);
		return book;
	}
	
	@RequestMapping(value = "/CrudRepository/")
	public List<Book> getBooks() {
		return bookService.findAll();
	}
	
	
	
	// BookOwnJPARepository
	@RequestMapping(value = "/JPA/manual/{id}")
	public Book getById(@PathVariable int id) {
		Book book = bookService.getById(id);
		return book;
	}
	
	@RequestMapping(value = "/JPA/manual/")
	public List<Book> getAll() {
		return bookService.getAll();
	}
	
	@RequestMapping(value = "/JPA/manual/SP")
	public String getSP() {
		return bookService.getCallSP();
	}
	
	@RequestMapping(value = "/JPA/manual/SP/Query/{id}")
	public List<Libro> getQueryBookSP(@PathVariable int id) {
		return bookService.getQueryBookSP(id);
	}
	
	@RequestMapping(value = "/JPA/manual/SP/Insert/{id}/{autor}/{name}")
	public String getCreateBookSP(@PathVariable int id, @PathVariable String autor, @PathVariable String name) {
		return bookService.getCreateBookSP(id, autor, name);
	}
	
	
	
	// BookOwnHibernateRepository
	@RequestMapping(value = "/Hibernate/manual/")
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}
	
	@RequestMapping(value = "/Hibernate/manual/{id}")
	public List<Book> getBook(@PathVariable long id) {
		return bookService.getBook(id);
	}
	
	
	
	// MyBatis
	@RequestMapping(value = "/mybatis/manual/{id}")
	public Book findBookById(@PathVariable int id) {
		Book book = bookService.findBookById(id);
		return book;
	}
	
	@RequestMapping(value = "/mybatis/manual/")
	public List<Book> findAllBooks() {
		return bookService.findAllBooks();
	}

	@RequestMapping(value = "/mybatis/manual/SP/{param1}/{param2}/{param3}")
	public String getCallSPMyBatis(@PathVariable int param1, @PathVariable int param2, @PathVariable String param3) {		
		String sumaResponse = bookService.getCallSPMyBatis(param1, param2, param3);
		return sumaResponse;
	}
	
	
	@RequestMapping(value = "/file", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> file(RequestEntity<Object> request) {
		
		System.out.println("Metodo Nuevo");

		Map<String, Object> resultado = null;
		
		try{
			resultado = bookService.altaRegistro(request);
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
						
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/json")).body(resultado);			
	}
}
