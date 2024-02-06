package br.com.rperatello.bankcoreapi.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"id", "number", "name"})
public class AgencyResponseVO extends RepresentationModel<AgencyResponseVO> implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long key;
	
	private Long number;	

	private String name;
	
	public AgencyResponseVO() {};

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(key, name, number);
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
		AgencyResponseVO other = (AgencyResponseVO) obj;
		return Objects.equals(key, other.key) && Objects.equals(name, other.name)
				&& Objects.equals(number, other.number);
	}	
	
	
	
	
}
