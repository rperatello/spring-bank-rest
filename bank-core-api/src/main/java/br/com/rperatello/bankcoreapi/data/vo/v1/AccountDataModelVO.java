package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.rperatello.bankcoreapi.model.TransactionType;

public class AccountDataModelVO implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("account_number")
	private Long accountNumber;

	@JsonProperty("agency_number")
	private Long agencyNumber;
	
	@JsonProperty("customer_document")
	private Long document;
	
	private String password;

	public AccountDataModelVO() { }

	public AccountDataModelVO(Long accountNumber, Long agencyNumber, Long document, String password) {
		this.accountNumber = accountNumber;
		this.agencyNumber = agencyNumber;
		this.document = document;
		this.password = password;		
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getAgencyNumber() {
		return agencyNumber;
	}

	public void setAgencyNumber(Long agencyNumber) {
		this.agencyNumber = agencyNumber;
	}

	public Long getDocument() {
		return document;
	}

	public void setDocument(Long document) {
		this.document = document;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber, agencyNumber, document, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountDataModelVO other = (AccountDataModelVO) obj;
		return Objects.equals(accountNumber, other.accountNumber) && Objects.equals(agencyNumber, other.agencyNumber)
				&& Objects.equals(document, other.document) && Objects.equals(password, other.password);
	}
	
}
