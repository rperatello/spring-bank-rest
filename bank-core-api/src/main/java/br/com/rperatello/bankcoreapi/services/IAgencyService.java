package br.com.rperatello.bankcoreapi.services;

import java.util.List;

import br.com.rperatello.bankcoreapi.data.vo.v1.AgencyRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AgencyResponseVO;

public interface IAgencyService {

	AgencyResponseVO findById(Long id);
	
	List<AgencyResponseVO> getAll();	
	
	AgencyResponseVO createNewAgency(AgencyRequestVO agency);
	
	AgencyResponseVO updateAgency(AgencyRequestVO agency);
	
	void deleteAgency(Long id);
	
}
