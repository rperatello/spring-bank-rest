package br.com.rperatello.bankcoreapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Transient;

public class AccountBalanceUpdateModel implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("account_number")
	private Long accountNumber;

	@JsonProperty("agency_number")
	private Long agencyNumber;
	
	@JsonProperty("customer_document")
	private Long document;

	private BigDecimal amount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	@JsonProperty("transaction_type")
	private TransactionType TransactionType;
	
	@Transient
	@JsonIgnore
	private String password;
	
	@Transient
	@JsonIgnore
	private Account account;
	
	@JsonIgnore
	private MessageStatus customerMessageStatus = MessageStatus.PENDING; 

	public AccountBalanceUpdateModel() {}

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

	public BigDecimal getAmount() {
		return amount.setScale(2, RoundingMode.HALF_UP);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(2, RoundingMode.HALF_UP);
	}

	public TransactionType getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		TransactionType = transactionType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public MessageStatus getCustomerMessageStatus() {
		return customerMessageStatus;
	}

	public void setCustomerMessageStatus(MessageStatus customerMessageStatus) {
		this.customerMessageStatus = customerMessageStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(TransactionType, account, accountNumber, agencyNumber, amount, customerMessageStatus,
				document, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountBalanceUpdateModel other = (AccountBalanceUpdateModel) obj;
		return TransactionType == other.TransactionType && Objects.equals(account, other.account)
				&& Objects.equals(accountNumber, other.accountNumber)
				&& Objects.equals(agencyNumber, other.agencyNumber) && Objects.equals(amount, other.amount)
				&& customerMessageStatus == other.customerMessageStatus && Objects.equals(document, other.document)
				&& Objects.equals(password, other.password);
	}
	
}