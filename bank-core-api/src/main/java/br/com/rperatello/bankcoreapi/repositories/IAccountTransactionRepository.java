package br.com.rperatello.bankcoreapi.repositories;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import br.com.rperatello.bankcoreapi.model.AccountTransactionNotification;
import br.com.rperatello.bankcoreapi.model.MessageStatus;

@Repository
public interface IAccountTransactionRepository extends JpaRepository<AccountTransactionNotification, Long> {
	
	@Async
    CompletableFuture<List<AccountTransactionNotification>> findByCustomerMessageStatus(MessageStatus status);
	
}
