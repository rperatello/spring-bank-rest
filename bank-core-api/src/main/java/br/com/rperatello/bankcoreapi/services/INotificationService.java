package br.com.rperatello.bankcoreapi.services;

import java.util.concurrent.CompletableFuture;

import br.com.rperatello.bankcoreapi.model.AccountTransactionNotification;

public interface INotificationService {
	
	void sentCustomerTransactionNotification(AccountTransactionNotification accountNotification);
	
	CompletableFuture<Boolean> processCustomerTransactionNotificationNotSent();

}
