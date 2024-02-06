package br.com.rperatello.bankcoreapi.services;

import java.util.List;

import br.com.rperatello.bankcoreapi.data.vo.v1.TransactionRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.TransactionResponseVO;
import br.com.rperatello.bankcoreapi.model.Transaction;

public interface ITransactionService {

	TransactionResponseVO findById(Long id);
	
	TransactionResponseVO createNewTransaction(TransactionRequestVO account);
	
}
