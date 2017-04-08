package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Boleta {
	
	@JsonProperty (value = "id")
	private long id;
	@JsonProperty(value = "funcion" )
	private Funcion funcion;
	@JsonProperty(value = "espectaculo")
	private Espectaculo espectaculo;
	@JsonProperty(value = "sitio")
	private Sitio sitio;
	@JsonProperty(value= "silla")
	private Silla silla;
	
	public Boleta(){
		
	}

	public Sitio getSitio() {
		return sitio;
	}

	public void setSitio(Sitio sitio) {
		this.sitio = sitio;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Espectaculo getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(Espectaculo espectaculo) {
		this.espectaculo = espectaculo;
	}

	public Silla getSilla() {
		return silla;
	}

	public void setSilla(Silla silla) {
		this.silla = silla;
	}
	
}
