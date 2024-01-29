package br.com.rperatello.bankcoreapi.services;

import java.util.List;

import br.com.rperatello.bankcoreapi.data.vo.v1.AccountStatusRequestVO;
import br.com.rperatello.bankcoreapi.model.AccountBalanceUpdateModel;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountResponseVO;

public interface IAccountService {

	AccountResponseVO findById(Long id);
	
	List<AccountResponseVO> getAll();	
	
	AccountResponseVO createNewAccount(AccountStatusRequestVO account);
	
	AccountResponseVO updateAccountStatus(AccountStatusRequestVO account);
	
	boolean updateAccountBalance(AccountBalanceUpdateModel model);
	
	void deleteAccount(Long id);

	
}
