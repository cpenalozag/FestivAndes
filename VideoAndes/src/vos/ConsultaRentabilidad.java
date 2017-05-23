package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ConsultaRentabilidad {
	@JsonProperty(value = "nombre")
	private String nombre;
	@JsonProperty(value = "rango")
	private RangoFechas rango;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public RangoFechas getRango() {
		return rango;
	}
	public void setRango(RangoFechas rango) {
		this.rango = rango;
	}
	
	
}
