package br.com.rperatello.bankcoreapi.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rperatello.bankcoreapi.controller.AgencyController;
import br.com.rperatello.bankcoreapi.data.vo.v1.AgencyRequestVO;
import br.com.rperatello.bankcoreapi.data.vo.v1.AgencyResponseVO;
import br.com.rperatello.bankcoreapi.exceptions.DatabaseActionException;
import br.com.rperatello.bankcoreapi.exceptions.RequiredObjectIsNullException;
import br.com.rperatello.bankcoreapi.exceptions.ResourceNotFoundException;
import br.com.rperatello.bankcoreapi.mapper.Mapper;
import br.com.rperatello.bankcoreapi.model.Agency;
import br.com.rperatello.bankcoreapi.model.builder.AgencyBuilder;
import br.com.rperatello.bankcoreapi.repositories.IAccountRepository;
import br.com.rperatello.bankcoreapi.repositories.IAgencyRepository;
import jakarta.transaction.Transactional;

//

@Service
public class AgencyService implements IAgencyService {
	
	private Logger logger = Logger.getLogger(AgencyService.class.getName());
	
	@Autowired
	IAgencyRepository repository;
	
	@Autowired
	IAccountRepository accountRepository;

	@Override
	public AgencyResponseVO findById(Long id) {
		logger.info(String.format("Find agency with ID %s ...", id));
		var agency = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = Mapper.parseObject(agency, AgencyResponseVO.class);
		return addAgencyHateoasLinks(vo);
	}

	@Override
	public List<AgencyResponseVO> getAll() {
		logger.info("Get all agencies ...");		
		var agencies = Mapper.parseListObjects(repository.findAll(), AgencyResponseVO.class);
		agencies
			.stream()
			.forEach(p -> addAgencyHateoasLinks(p));
		return agencies;
	}

	@Override
	public AgencyResponseVO createNewAgency(AgencyRequestVO agencyVO) {
		logger.info("Create a new agency ...");		
		var agencyReceived = Mapper.parseObject(agencyVO, Agency.class);
		var builder = AgencyBuilder.getbuilder();
		var agency = builder
				.id(agencyReceived.getId())
				.name(agencyReceived.getName())
				.number(agencyReceived.getNumber())
				.build();
		validateObject(agency);
		var vo = Mapper.parseObject(repository.save(agency), AgencyResponseVO.class);
		return addAgencyHateoasLinks(vo);
	}

	@Override
	@Transactional
	public AgencyResponseVO updateAgency(AgencyRequestVO agencyVO) {
		logger.info(String.format("Update agency with ID %s ...", agencyVO.getId()));		
		var agencyReceived = Mapper.parseObject(agencyVO, Agency.class);
		var builder = AgencyBuilder.getbuilder();
		var agency = builder
				.id(agencyReceived.getId())
				.name(agencyReceived.getName())
				.number(agencyReceived.getNumber())
				.build();		
		validateObject(agency);		
		var agencySaved = repository.findById(agency.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));		
		agencySaved = Mapper.copyProperties(agency, agencySaved.getClass());
		var vo = Mapper.parseObject(repository.save(agencySaved), AgencyResponseVO.class);
		return addAgencyHateoasLinks(vo);
	}

	@Override
	public void deleteAgency(Long id) {
		logger.info(String.format("Delete agency with ID %s ...", id));		
		var agencySaved = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var accounts = accountRepository.findByAgencyId(agencySaved.getId());
		if (!accounts.isEmpty()) throw new DatabaseActionException("Agency has associated account");
		repository.delete(agencySaved);		
		return;		
	}	
	
	public AgencyResponseVO addAgencyHateoasLinks( AgencyResponseVO vo) {
		try {
			if (vo == null) throw new ResourceNotFoundException("Agency data is required");
			return vo.add(linkTo(methodOn(AgencyController.class).findById(vo.getKey())).withSelfRel());
		} 
		catch (Exception e) {
			logger.log(Level.SEVERE, String.format("addAgencyHateoasLinks - Error: %s ", e.getMessage()));
			return vo;
		}
	}
	
	private boolean validateObject(Agency agency) {		
		var agencyInDatabaseByNumber = repository.findByNumber(agency.getNumber());
		var agencyInDatabaseById = repository.findById(agency.getId()).orElse(null);
		if (agencyInDatabaseByNumber != null && agencyInDatabaseByNumber.getId() != agency.getId())
			throw new DatabaseActionException("Database already contains this agency");
		if (agency != null && !agency.getNumber().toString().matches("\\d+"))
			throw new RequiredObjectIsNullException("A valid agency number is required");		
		if (agencyInDatabaseById != null && agencyInDatabaseById.getNumber().longValue() != agency.getNumber().longValue())
			throw new DatabaseActionException("Agency number cannot be changed");		
		if (agency != null && agency.getName() == "")
			throw new RequiredObjectIsNullException("A valid name is required");
		return true;
	}

}
