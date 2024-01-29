package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "account_number", "agency_number", "customer_document", "is_active"})
public class AccountStatusRequestVO implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("account_number")
	private Long number;

	@JsonProperty("agency_number")
	private Long agencyNbr;
	
	@JsonProperty("customer_document")
	private String customerNumber;
	
	@JsonProperty("is_active")
	private Boolean isActive = false;

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

	public Long getAgencyNbr() {
		return agencyNbr;
	}

	public void setAgencyNbr(Long agencyNbr) {
		this.agencyNbr = agencyNbr;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agencyNbr, customerNumber, id, isActive, number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountStatusRequestVO other = (AccountStatusRequestVO) obj;
		return Objects.equals(agencyNbr, other.agencyNbr) && Objects.equals(customerNumber, other.customerNumber)
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(number, other.number);
	}

}
