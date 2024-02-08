package br.com.rperatello.bankcoreapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_transactions")
public class Transaction implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne
	@JoinColumn(name="payer_account_id", referencedColumnName = "id")
	private Account payer;
	
	@Transient
	private @Nullable AccountBalanceUpdateModel payerBalanceModel;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="payee_account_id", referencedColumnName = "id")
	private Account payee;
	
	@Transient
	private AccountBalanceUpdateModel payeeBalanceModel;
	
	@NotNull
	private BigDecimal amount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_method", nullable = false, length = 100)
	private TransactionMethod transactionMethod;
	
	@Column(name = "datetime", nullable = false)
	private LocalDateTime datetime = LocalDateTime.now();
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TransactionStatus status = TransactionStatus.PENDING;
	
	@NotNull
	private String request;	

	
	public Transaction(){};

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getPayer() {
		return payer;
	}

	public void setPayer(Account payer) {
		this.payer = payer;
	}

	public @Nullable AccountBalanceUpdateModel getPayerBalanceModel() {
		return payerBalanceModel;
	}

	public void setPayerBalanceModel(@Nullable AccountBalanceUpdateModel payerBalanceModel) {
		this.payerBalanceModel = payerBalanceModel;
	}
	
	public Account getPayee() {
		return payee;
	}

	public void setPayee(Account payee) {
		this.payee = payee;
	}

	public AccountBalanceUpdateModel getPayeeBalanceModel() {
		return payeeBalanceModel;
	}

	public void setPayeeBalanceModel(AccountBalanceUpdateModel payeeBalanceModel) {
		this.payeeBalanceModel = payeeBalanceModel;
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
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount, datetime, id, payee, payeeBalanceModel, payer, payerBalanceModel, request, status,
				transactionMethod);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return Objects.equals(amount, other.amount) && Objects.equals(datetime, other.datetime)
				&& Objects.equals(id, other.id) && Objects.equals(payee, other.payee)
				&& Objects.equals(payeeBalanceModel, other.payeeBalanceModel) && Objects.equals(payer, other.payer)
				&& Objects.equals(payerBalanceModel, other.payerBalanceModel) && Objects.equals(request, other.request)
				&& status == other.status && transactionMethod == other.transactionMethod;
	}	

}