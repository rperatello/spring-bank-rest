package br.com.rperatello.bankcoreapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rperatello.bankcoreapi.model.AccountTransactionNotification;
import br.com.rperatello.bankcoreapi.model.Customer;

@Repository
public interface IAccountTransactionRepository extends JpaRepository<AccountTransactionNotification, Long> {}
