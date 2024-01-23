package br.com.rperatello.bankcustomerapi.services;

import java.util.List;

import br.com.rperatello.bankcustomerapi.data.vo.v1.AccountRequestVO;
import br.com.rperatello.bankcustomerapi.data.vo.v1.AccountResponseVO;

public interface IAccountService {

	AccountResponseVO findById(Long id);
	
	List<AccountResponseVO> getAll();	
	
	AccountResponseVO createNewAccount(AccountRequestVO account);
	
	AccountResponseVO updateAccount(AccountRequestVO account);
	
	void deleteAccount(Long id);
	
}
