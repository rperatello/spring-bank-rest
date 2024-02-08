package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.rperatello.bankcoreapi.model.TransactionMethod;
import jakarta.validation.constraints.NotNull;

public class TransactionRequestVO implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	Optional<AccountDataModelVO> payer = Optional.empty();
	
	AccountDataModelVO payee;
	
	BigDecimal amount;
	
	@JsonProperty("transaction_method")
	TransactionMethod transactionMethod;
	
	@NotNull
	private LocalDateTime dateTime = LocalDateTime.now();

	public TransactionRequestVO() {}
	
	public Optional<AccountDataModelVO> getPayer() {
		return payer;
	}

//	public void setPayer(Optional<AccountDataModelVO> payer) {
//		this.payer = payer;
//	}
	
	public void setPayer(AccountDataModelVO payer) {
		this.payer = Optional.ofNullable(payer);
	}

	public AccountDataModelVO getPayee() {
		return payee;
	}	

	public void setPayee(AccountDataModelVO payee) {
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

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, dateTime, payee, payer, transactionMethod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionRequestVO other = (TransactionRequestVO) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(dateTime, other.dateTime)
				&& Objects.equals(payee, other.payee) && Objects.equals(payer, other.payer)
				&& transactionMethod == other.transactionMethod;
	}

}
	