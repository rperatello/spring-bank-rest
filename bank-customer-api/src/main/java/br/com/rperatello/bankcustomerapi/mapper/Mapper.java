package br.com.rperatello.bankcustomerapi.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import br.com.rperatello.bankcustomerapi.data.vo.v1.CustomerResponseVO;
import br.com.rperatello.bankcustomerapi.model.Customer;

public class Mapper {

	private static ModelMapper mapper = new ModelMapper();	
	
	static {
		mapper.createTypeMap(Customer.class, CustomerResponseVO.class)
			.addMapping(Customer::getId, CustomerResponseVO::setKey);
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