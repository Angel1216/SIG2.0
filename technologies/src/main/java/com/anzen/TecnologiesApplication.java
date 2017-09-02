package com.anzen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;


// Especifica el paquete donde se localizan los mappers
// de MyBatis para habilitar la comunicación a la BD
@MapperScan("com.anzen.dao")

// Indica una aplicación Spring Boot
@SpringBootApplication
public class TecnologiesApplication {

	public static void main(String[] args) {
	
		// Inicia la aplicación Spring Boot
		SpringApplication.run(TecnologiesApplication.class, args);
	
	}
	
	// Se habilita el Bean del SessionFactory para
	// realizar la inyección de dependencia en el repositorio 
	@Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }
	
}
