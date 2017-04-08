package vos;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Localidad implements Serializable{
	
	@JsonProperty(value = "tipo")
	private TipoLocalidad tipo;
	@JsonProperty (value = "id")
	private long id;
	@JsonProperty (value = "nomSitio")
	private String sitio;
	@JsonProperty (value = "numerada")
	private boolean numerada;
	@JsonProperty(value = "precio")
	private double precio;
	@JsonProperty (value = "capacidad")
	private int capacidad;
//	@JsonProperty(value = "sillas")
//	private List<Silla> sillas;

	public Localidad(){
		
	}

	public TipoLocalidad getTipo() {
		return tipo;
	}

	public void setTipo(TipoLocalidad tipo) {
		this.tipo = tipo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isNumerada() {
		return numerada;
	}

	public void setNumerada(boolean numerada) {
		this.numerada = numerada;
	}

	public String getSitio() {
		return sitio;
	}

	public void setSitio(String sitio) {
		this.sitio = sitio;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

//	public List<Silla> getSillas() {
//		return sillas;
//	}
//
//	public void setSillas(List<Silla> sillas) {
//		this.sillas = sillas;
//	}
	

}
