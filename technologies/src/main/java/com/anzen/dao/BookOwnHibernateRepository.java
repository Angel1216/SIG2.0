package com.anzen.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import com.anzen.bean.Book;
import java.util.List;

// Importar librerias de HIBERNATE
import org.hibernate.SessionFactory;
import org.hibernate.Session;

@Repository
@Transactional
public class BookOwnHibernateRepository {

	 // An EntityManager will be automatically injected from entityManagerFactory
	 // setup on DatabaseConfig class.
	 @Autowired  
	 private SessionFactory sessionFactory;
	
	 
	 public void setSessionFactory(SessionFactory sf) {  
		  this.sessionFactory = sf;  
	 }
	
	 // Metodos
	 public List<Book> getAllBooks(){
		 Session session = this.sessionFactory.getCurrentSession();
		 @SuppressWarnings("unchecked")
		 List<Book> listStatus = session.createQuery("from Book").list();
		 return listStatus;
	 }
	 
	 public List<Book> getBook(long id){
		 Session session = this.sessionFactory.getCurrentSession();
		 @SuppressWarnings("unchecked")
		 List<Book> lstBook = session.createQuery("from Book where id= ?").setParameter(0, id).list();
		 return lstBook;
	 }
 
}
