package br.com.rperatello.bankcoreapi.services;

import br.com.rperatello.bankcoreapi.model.AccountTransactionNotification;

public interface INotificationService {
	
	void sentCustomerTransactionNotification(AccountTransactionNotification accountNotification);

}
