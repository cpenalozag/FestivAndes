package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SitioBasica {

	@JsonProperty(value = "id")
	private long id;

	@JsonProperty(value ="capacidad")
	private int capacidad;

	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "tipo")
	private String tipo;

	public SitioBasica()
	{

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
