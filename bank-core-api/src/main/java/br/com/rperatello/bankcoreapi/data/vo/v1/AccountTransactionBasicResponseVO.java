package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "agency", "number", "customer", "balance", "status"})
public class AccountTransactionBasicResponseVO extends RepresentationModel<AccountTransactionBasicResponseVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long key;
	
	private AgencyResponseVO agency;
	
	private Long number;
	
	private CustomerResponseVO customer;
	
	public AccountTransactionBasicResponseVO () {};

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(agency, customer, key, number);
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
		AccountTransactionBasicResponseVO other = (AccountTransactionBasicResponseVO) obj;
		return Objects.equals(agency, other.agency) && Objects.equals(customer, other.customer)
				&& Objects.equals(key, other.key) && Objects.equals(number, other.number);
	}
	
}
