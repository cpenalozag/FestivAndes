package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class TipoLocalidad {
	
	@JsonProperty(value = "id")
	private long id;
	
	@JsonProperty(value = "tipo")
	private String tipo;
	
	public TipoLocalidad()
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
