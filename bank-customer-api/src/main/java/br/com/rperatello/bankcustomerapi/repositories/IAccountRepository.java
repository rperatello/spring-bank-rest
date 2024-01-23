package br.com.rperatello.bankcustomerapi.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rperatello.bankcustomerapi.model.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> { 
	
	Account findByNumber(Long number);
	
	List<Account> findByCustomerId(long customerId);
	
	List<Account> findByAgencyId(long agencyId);
	
}
