package br.com.rperatello.bankcoreapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_account_transaction_notification")
public class AccountTransactionNotification implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "transaction_id", nullable = false)
	private Long transactionId;
	
	@Column(name = "customer_document", nullable = false)
	private String CustomerDocument;
	
	@Column(name = "agency_number", nullable = false)
	private Long agencyNumber;
	
	@Column(name = "account_number", nullable = false)
	private Long accountNumber;

	private BigDecimal amount = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);	

	@Enumerated(EnumType.STRING)
	private TransactionType TransactionType;	
	
	@Column(name = "transaction_datetime", nullable = false)
	private LocalDateTime transactionDatetime = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
	
	@Column(name = "sent_at", nullable = true)
	private LocalDateTime sentAt;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private MessageStatus customerMessageStatus = MessageStatus.PENDING;

	public AccountTransactionNotification() { }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getCustomerDocument() {
		return CustomerDocument;
	}

	public void setCustomerDocument(String customerDocument) {
		CustomerDocument = customerDocument;
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

	public TransactionType getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		TransactionType = transactionType;
	}

	public LocalDateTime getTransactionDatetime() {
		return transactionDatetime;
	}

	public void setTransactionDatetime(LocalDateTime transactionDatetime) {
		this.transactionDatetime = transactionDatetime;
	}

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}

	public MessageStatus getCustomerMessageStatus() {
		return customerMessageStatus;
	}

	public void setCustomerMessageStatus(MessageStatus customerMessageStatus) {
		this.customerMessageStatus = customerMessageStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(CustomerDocument, TransactionType, accountNumber, agencyNumber, amount,
				customerMessageStatus, id, sentAt, transactionDatetime, transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountTransactionNotification other = (AccountTransactionNotification) obj;
		return Objects.equals(CustomerDocument, other.CustomerDocument) && TransactionType == other.TransactionType
				&& Objects.equals(accountNumber, other.accountNumber)
				&& Objects.equals(agencyNumber, other.agencyNumber) && Objects.equals(amount, other.amount)
				&& customerMessageStatus == other.customerMessageStatus && Objects.equals(id, other.id)
				&& Objects.equals(sentAt, other.sentAt)
				&& Objects.equals(transactionDatetime, other.transactionDatetime)
				&& Objects.equals(transactionId, other.transactionId);
	}
	
}