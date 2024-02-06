package br.com.rperatello.bankcoreapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
//import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tb_accounts")
public class Account implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	

    @NotNull
	private Long number;	
   
	@NotNull
	@ManyToOne
	@JoinColumn(name="agency_number", referencedColumnName = "number")
	private Agency agency;
	
	@Transient
	private Long agencyNbr;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="customer_document", referencedColumnName = "document")
	private Customer customer;	
	
	@Transient
	private String customerNumber;
	
	private BigDecimal balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
	
	@Transient
	private String status;
	
	@Column(name = "is_active", nullable = false, length = 100)
	private Boolean isActive = false;
	
	@OneToMany(mappedBy = "payer", fetch = FetchType.LAZY)
    private List<Transaction> transactionsLikePayer;
	
	@OneToMany(mappedBy = "payee", fetch = FetchType.LAZY)
    private List<Transaction> transactionsLikePayee;

	public Account() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public Long getAgencyNbr() {
		return agencyNbr;
	}

	public void setAgencyNbr(Long agencyNbr) {
		this.agencyNbr = agencyNbr;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public BigDecimal getBalance() {
		return balance.setScale(2, RoundingMode.HALF_UP);
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance.setScale(2, RoundingMode.HALF_UP);
	}

	public String getStatus() {
		return this.status != null ? this.status : this.isActive ? AccountStatus.ACTIVE : AccountStatus.INACTIVE;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agency, agencyNbr, balance, customer, customerNumber, id, isActive, number, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(agencyNbr, other.agencyNbr)
				&& Objects.equals(balance, other.balance) && Objects.equals(customer, other.customer)
				&& Objects.equals(customerNumber, other.customerNumber) && Objects.equals(id, other.id)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(number, other.number)
				&& Objects.equals(status, other.status);
	}
	
}


