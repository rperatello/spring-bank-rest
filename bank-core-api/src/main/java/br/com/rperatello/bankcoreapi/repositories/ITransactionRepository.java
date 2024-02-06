package br.com.rperatello.bankcoreapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rperatello.bankcoreapi.model.Transaction;

public interface ITransactionRepository extends JpaRepository<Transaction, Long> { 

}
