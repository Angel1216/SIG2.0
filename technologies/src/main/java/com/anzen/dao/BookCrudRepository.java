package com.anzen.dao;

import org.springframework.data.repository.CrudRepository;
import com.anzen.bean.Book;

public interface BookCrudRepository extends CrudRepository<Book,Long> {

}
