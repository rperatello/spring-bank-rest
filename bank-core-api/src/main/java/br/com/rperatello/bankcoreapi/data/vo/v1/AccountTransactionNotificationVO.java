package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"transaction_id", "agency_number", "account_number", "amount", "transaction_type", "transaction_datetime"})
public class AccountTransactionNotificationVO implements Serializable {

	private static final long serialVersionUID = 1L;


	@JsonProperty("transaction_id")
	private Long transactionId;

	@JsonProperty("agency_number")
	private Long agencyNumber;

	@JsonProperty("account_number")
	private Long accountNumber;

	private BigDecimal amount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	@JsonProperty("transaction_type")
	private String TransactionType;	
	
	@JsonProperty("transaction_datetime")
	private LocalDateTime transactionDatetime;

	public AccountTransactionNotificationVO() { }

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getAgencyNumber() {
		return agencyNumber;
	}

	public void setAgencyNumber(Long agencyNumber) {
		this.agencyNumber = agencyNumber;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public LocalDateTime getTransactionDatetime() {
		return transactionDatetime;
	}

	public void setTransactionDatetime(LocalDateTime transactionDatetime) {
		this.transactionDatetime = transactionDatetime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber, agencyNumber, amount, transactionDatetime, transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountTransactionNotificationVO other = (AccountTransactionNotificationVO) obj;
		return Objects.equals(accountNumber, other.accountNumber) && Objects.equals(agencyNumber, other.agencyNumber)
				&& Objects.equals(amount, other.amount)
				&& Objects.equals(transactionDatetime, other.transactionDatetime)
				&& Objects.equals(transactionId, other.transactionId);
	}	
}