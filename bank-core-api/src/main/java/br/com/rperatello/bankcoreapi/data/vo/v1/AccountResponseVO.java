package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.rperatello.bankcoreapi.model.AccountStatus;


@JsonPropertyOrder({"id", "agency", "number", "customer", "balance", "status"})
public class AccountResponseVO extends RepresentationModel<AccountResponseVO> implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long key;
	
	private AgencyResponseVO agency;
	
	private Long number;
	
	private CustomerResponseVO customer;
	
	private BigDecimal balance;
	
	@JsonIgnore
	private Boolean isActive;
	
	private String status;

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public AgencyResponseVO getAgency() {
		return agency;
	}

	public void setAgency(AgencyResponseVO agency) {
		this.agency = agency;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public CustomerResponseVO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerResponseVO customer) {
		this.customer = customer;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}	

	public String getStatus() {
		return this.isActive ? AccountStatus.ACTIVE : AccountStatus.INACTIVE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(agency, balance, customer, isActive, key, number, status);
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
		AccountResponseVO other = (AccountResponseVO) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(balance, other.balance)
				&& Objects.equals(customer, other.customer) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(key, other.key) && Objects.equals(number, other.number)
				&& Objects.equals(status, other.status);
	}
	
}
