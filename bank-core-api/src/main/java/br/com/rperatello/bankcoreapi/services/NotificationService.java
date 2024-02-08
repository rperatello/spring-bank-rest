package br.com.rperatello.bankcoreapi.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rperatello.bankcoreapi.controller.TransactionController;
import br.com.rperatello.bankcoreapi.data.vo.v1.AccountTransactionNotificationVO;
import br.com.rperatello.bankcoreapi.httpclient.ITransactionNotificationFeignClient;
import br.com.rperatello.bankcoreapi.mapper.Mapper;
import br.com.rperatello.bankcoreapi.model.AccountTransactionNotification;
import br.com.rperatello.bankcoreapi.model.CustomerTransactionNotificationResponse;
import br.com.rperatello.bankcoreapi.model.MessageStatus;
import br.com.rperatello.bankcoreapi.repositories.IAccountTransactionRepository;
import br.com.rperatello.bankcoreapi.utils.serealization.converter.GsonUtil;


@Service
public class NotificationService implements INotificationService {	
	
	@Autowired
	IAccountTransactionRepository atNotificationRepository;
	
	@Autowired
	ITransactionNotificationFeignClient custumerTransactionNotificationClient;
	

	private Logger logger = Logger.getLogger(NotificationService.class.getName());


	@Override
	public void sentCustomerTransactionNotification(AccountTransactionNotification accountNotification) {
		try {
			boolean validatePassed = ValidateAccountTransactionNotification(accountNotification);		
			if (!validatePassed) {
				logger.log(Level.SEVERE, String.format("NotificationService | Notification not sent: %s", GsonUtil.Serialize(accountNotification)));
				return;
			}
			
			AccountTransactionNotificationVO message = Mapper.parseObject(accountNotification, AccountTransactionNotificationVO.class);			
			CustomerTransactionNotificationResponse response = custumerTransactionNotificationClient.sendCustomerTransactionNotification(getToken(), message);
			
			logger.info(String.format("NotificationService | response: %s", GsonUtil.Serialize(response)));
			
			if(response.isMessageSent()) {
				accountNotification.setSentAt(LocalDateTime.now());
				accountNotification.setCustomerMessageStatus(MessageStatus.SENT);
			}
			atNotificationRepository.save(accountNotification);
			return;
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, String.format("NotificationService | Notification not sent: %s", GsonUtil.Serialize(accountNotification)));
			logger.log(Level.SEVERE, String.format("NotificationService | Error: %s", e.getMessage()));
			atNotificationRepository.save(accountNotification);
			return;
		}
		
	}
	
	private boolean ValidateAccountTransactionNotification(AccountTransactionNotification notification) {
		logger.info(String.format("%s", GsonUtil.Serialize(notification)));
		boolean validated = true;
		if (notification.getTransactionId() == null || notification.getTransactionId() == 0L) validated = false;
		if (notification.getCustomerDocument() == null || notification.getCustomerDocument() == "") validated = false;
		if (notification.getAgencyNumber() == null || notification.getAgencyNumber() == 0L) validated = false;
		if (notification.getAccountNumber() == null || notification.getAccountNumber() == 0L) validated = false;
		if (notification.getTransactionDatetime() == null || notification.getTransactionDatetime().equals(LocalDateTime.of(1900, 1, 1, 0, 0, 0))) validated = false;
		if (notification.getTransactionType() == null) validated = false;
		if (notification.getAmount() == null || notification.getAmount() == BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP)) validated = false;
		return validated;
	}	
	
	private String getToken() {
		return "token";
	}
	

}
