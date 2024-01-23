package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"id", "name", "document", "address", "password"})
public class CustomerResponseVO extends RepresentationModel<CustomerResponseVO> implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long key;
	
	private String name;	

	private String document;
	
	private String address;

	

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(address, document, key, name);
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
		CustomerResponseVO other = (CustomerResponseVO) obj;
		return Objects.equals(address, other.address) && Objects.equals(document, other.document)
				&& Objects.equals(key, other.key) && Objects.equals(name, other.name);
	}	
	
}
