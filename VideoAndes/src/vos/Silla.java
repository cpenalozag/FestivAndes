package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Silla {

	@JsonProperty(value = "id")
	private long id;
	@JsonProperty(value = "localidad")
	private long localidad;
	@JsonProperty(value = "fila")
	private String fila;
	@JsonProperty (value = "numero")
	private String numero;
	@JsonProperty(value = "sitio")
	private long idSitio;
	
	
	public Silla(){
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLocalidad() {
		return localidad;
	}
	public void setLocalidad(long localidad) {
		this.localidad = localidad;
	}
	public String getFila() {
		return fila;
	}
	public void setFila(String fila) {
		this.fila = fila;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public long getIdSitio() {
		return idSitio;
	}

	public void setIdSitio(long idSitio) {
		this.idSitio = idSitio;
	}
	
	
	
}
