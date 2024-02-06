package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.rperatello.bankcoreapi.model.TransactionMethod;
import jakarta.validation.constraints.NotNull;

@JsonPropertyOrder({"id", "payer", "payee", "transactionMethod", "dateTime"})
public class TransactionResponseVO extends RepresentationModel<TransactionResponseVO> implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long key;
	
	Optional<AccountTransactionBasicResponseVO> payer = Optional.empty();
	
	AccountTransactionBasicResponseVO payee;
	
	BigDecimal amount;
	
	@JsonProperty("transaction_method")
	TransactionMethod transactionMethod;
	
	@NotNull
	private LocalDateTime datetime;

	public TransactionResponseVO() {}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Optional<AccountTransactionBasicResponseVO> getPayer() {
		return payer;
	}

	public void setPayer(Optional<AccountTransactionBasicResponseVO> payer) {
		this.payer = payer;
	}

	public AccountTransactionBasicResponseVO getPayee() {
		return payee;
	}

	public void setPayee(AccountTransactionBasicResponseVO payee) {
		this.payee = payee;
	}

	public BigDecimal getAmount() {
		return amount.setScale(2, RoundingMode.HALF_UP);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(2, RoundingMode.HALF_UP);
	}

	public TransactionMethod getTransactionMethod() {
		return transactionMethod;
	}

	public void setTransactionMethod(TransactionMethod transactionMethod) {
		this.transactionMethod = transactionMethod;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(amount, datetime, key, payee, payer, transactionMethod);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionResponseVO other = (TransactionResponseVO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(datetime, other.datetime)
				&& Objects.equals(key, other.key) && Objects.equals(payee, other.payee)
				&& Objects.equals(payer, other.payer) && transactionMethod == other.transactionMethod;
	}
	
}
	