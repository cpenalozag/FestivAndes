package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ElementoExtra {

	@JsonProperty(value = "id")
	private long id;

	@JsonProperty(value = "tipo")
	private String tipo;

	public ElementoExtra()
	{

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}


