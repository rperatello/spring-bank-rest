package br.com.rperatello.bankcoreapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rperatello.bankcoreapi.model.Agency;

@Repository
public interface IAgencyRepository extends JpaRepository<Agency, Long> { 
	
	Agency findByNumber(Long number);
	
}
