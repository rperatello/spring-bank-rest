package br.com.rperatello.bankcustomerapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rperatello.bankcustomerapi.model.Agency;

@Repository
public interface IAgencyRepository extends JpaRepository<Agency, Long> { 
	
	Agency findByNumber(Long number);
	
}
