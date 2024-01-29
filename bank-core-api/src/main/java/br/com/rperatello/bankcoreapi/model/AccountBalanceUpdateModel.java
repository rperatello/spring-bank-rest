package br.com.rperatello.bankcoreapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountBalanceUpdateModel implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("account_number")
	private Long accountNumber;

	@JsonProperty("agency_number")
	private Long agencyNumber;

	private BigDecimal amount;
	
	@JsonProperty("transaction_type")
	private TransactionType TransactionType;
	
	private String password;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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

	@Override
	public int hashCode() {
		return Objects.hash(TransactionType, accountNumber, agencyNumber, amount, password);
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
		return TransactionType == other.TransactionType && Objects.equals(accountNumber, other.accountNumber)
				&& Objects.equals(agencyNumber, other.agencyNumber) && Objects.equals(amount, other.amount)
				&& Objects.equals(password, other.password);
	}	
}