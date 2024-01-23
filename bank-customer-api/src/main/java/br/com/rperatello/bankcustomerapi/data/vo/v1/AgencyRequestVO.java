package br.com.rperatello.bankcustomerapi.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"id", "number", "name" })
public class AgencyRequestVO implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long id;
	
	private Long number;	

	private String name;
	

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgencyRequestVO other = (AgencyRequestVO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(number, other.number);
	}

}
