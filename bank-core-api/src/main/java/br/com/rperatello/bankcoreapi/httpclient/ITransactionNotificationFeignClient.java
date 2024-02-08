package br.com.rperatello.bankcoreapi.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.rperatello.bankcoreapi.data.vo.v1.AccountTransactionNotificationVO;
import br.com.rperatello.bankcoreapi.model.CustomerTransactionNotificationResponse;

@FeignClient(name = "CustomerTransactionNotificationFeignClient", url = "https://run.mocky.io/v3")
public interface ITransactionNotificationFeignClient {
	
	@PostMapping("/9769bf3a-b0b6-477a-9ff5-91f63010c9d3")
	CustomerTransactionNotificationResponse sendCustomerTransactionNotification(
			@RequestHeader("Authorization") String tokenJWT, 
			@RequestBody AccountTransactionNotificationVO model
	);

}
