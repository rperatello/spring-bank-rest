package br.com.rperatello.bankcoreapi.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rperatello.bankcoreapi.model.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> { 
	
	Account findByNumber(Long number);
	
	Account findByAgencyNumberAndNumber(Long agencyNumber, Long number);
	
	List<Account> findByCustomerId(long customerId);
	
	List<Account> findByAgencyId(long agencyId);
	
}
