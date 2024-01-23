package br.com.rperatello.bankcustomerapi.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
//import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

@Entity
//@Table(name = "tb_account", uniqueConstraints = {@UniqueConstraint(columnNames = {"number", "agency_id"})})
@Table(name = "tb_account")
public class Account implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	

    @NotNull
	private Long number;	
   
	@NotNull
	@ManyToOne
	@JoinColumn(name="agency_id")
	private Agency agency;
	
	@Transient
	private Long agencyNumber;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;	
	
	@Transient
	private String customerNumber;
	
	private BigDecimal balance = BigDecimal.ZERO;
	
	@Transient
	private String status;
	
	@Column(name = "is_active", nullable = false, length = 100)
	private Boolean isActive = false;

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

	public Long getAgencyNumber() {
		return agencyNumber;
	}

	public void setAgencyNumber(Long agencyNumber) {
		this.agencyNumber = agencyNumber;
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
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
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
		return Objects.hash(agency, agencyNumber, balance, customer, customerNumber, id, isActive, number, status);
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
		return Objects.equals(agency, other.agency) && Objects.equals(agencyNumber, other.agencyNumber)
				&& Objects.equals(balance, other.balance) && Objects.equals(customer, other.customer)
				&& Objects.equals(customerNumber, other.customerNumber) && Objects.equals(id, other.id)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(number, other.number)
				&& Objects.equals(status, other.status);
	}
	
}


