package com.anzen.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Options;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import org.springframework.stereotype.Repository;
import com.anzen.bean.Book;


@Repository
public interface BookMyBatisMapper {
	
	// Seleccionar todos los registros
	@Select("select id, author, name, price from book")
	public List<Book> findAllBooks();
	
	// Seleccionar registro por ID
	@Select("select id, author, name, price from book WHERE id=#{id}")
	public Book findBookById(long id);
	
	// Invocar SP con parametros de retorno
	@Select(value= "{ CALL sumaXY( "
	        + "#{param1, mode=IN, jdbcType=INTEGER},"
	        + "#{param2, mode=IN, jdbcType=INTEGER},"
	        + "#{suma, mode=OUT, jdbcType=VARCHAR}"
	        + ")}")
	@Options(statementType=StatementType.CALLABLE)
	public void getCallSPMyBatis(Map<String, Object> params);
}
