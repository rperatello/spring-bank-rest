package br.com.rperatello.bankcustomerapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.rperatello.bankcustomerapi.data.vo.v1.AccountRequestVO;
import br.com.rperatello.bankcustomerapi.data.vo.v1.AccountResponseVO;
import br.com.rperatello.bankcustomerapi.data.vo.v1.AgencyResponseVO;
import br.com.rperatello.bankcustomerapi.data.vo.v1.CustomerResponseVO;
import br.com.rperatello.bankcustomerapi.model.Account;
import br.com.rperatello.bankcustomerapi.model.Agency;
import br.com.rperatello.bankcustomerapi.model.Customer;

public class Mapper {

	private static ModelMapper mapper = new ModelMapper();	
	
	static {
		mapper.createTypeMap(Customer.class, CustomerResponseVO.class)
			.addMapping(Customer::getId, CustomerResponseVO::setKey);
		
		mapper.createTypeMap(Agency.class, AgencyResponseVO.class)
		.addMapping(Agency::getId, AgencyResponseVO::setKey);
		
		mapper.createTypeMap(Account.class, AccountResponseVO.class)
		.addMapping(Account::getId, AccountResponseVO::setKey);
		
		mapper.createTypeMap(AccountRequestVO.class, Account.class)
		.addMapping(AccountRequestVO::getAgencyNumber, Account::setAgencyNumber);		
	}	
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destinationObjects = new ArrayList<D>();
		for (O o : origin) {
			destinationObjects.add(mapper.map(o, destination));
		}
		return destinationObjects;
	}
	
	public static <O, D> D copyProperties (O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}

}