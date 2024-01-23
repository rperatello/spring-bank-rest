package br.com.rperatello.bankcustomerapi.services;

import java.util.List;

import br.com.rperatello.bankcustomerapi.data.vo.v1.AgencyRequestVO;
import br.com.rperatello.bankcustomerapi.data.vo.v1.AgencyResponseVO;

public interface IAgencyService {

	AgencyResponseVO findById(Long id);
	
	List<AgencyResponseVO> getAll();	
	
	AgencyResponseVO createNewAgency(AgencyRequestVO agency);
	
	AgencyResponseVO updateAgency(AgencyRequestVO agency);
	
	void deleteAgency(Long id);
	
}
