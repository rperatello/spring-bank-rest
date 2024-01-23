package br.com.rperatello.bankcoreapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rperatello.bankcoreapi.model.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> { 
	
	Customer findByDocument(String document);
	
}
