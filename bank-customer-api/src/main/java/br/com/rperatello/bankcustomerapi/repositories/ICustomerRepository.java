package br.com.rperatello.bankcustomerapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.rperatello.bankcustomerapi.model.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> { 
	
	Customer findByDocument(String document);
	
}
