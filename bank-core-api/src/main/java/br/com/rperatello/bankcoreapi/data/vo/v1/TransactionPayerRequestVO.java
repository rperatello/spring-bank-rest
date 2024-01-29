package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionPayerRequestVO implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("account_number")
	private Long number;

	@JsonProperty("agency_number")
	private Long agencyNbr;
	
	@JsonProperty("customer_document")
	private String customerNumber;
	
	private String password;

	public TransactionPayerRequestVO() {
		super();
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(agencyNbr, customerNumber, number, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionPayerRequestVO other = (TransactionPayerRequestVO) obj;
		return Objects.equals(agencyNbr, other.agencyNbr) && Objects.equals(customerNumber, other.customerNumber)
				&& Objects.equals(number, other.number) && Objects.equals(password, other.password);
	}
	
	
}
	