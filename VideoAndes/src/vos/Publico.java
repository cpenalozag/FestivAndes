package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Publico {

	@JsonProperty(value = "id")
	private long id;
	
	@JsonProperty(value = "tipo")
	private String tipo;
	
	public Publico()
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
