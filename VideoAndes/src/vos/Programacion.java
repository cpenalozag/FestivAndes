package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Programacion {
	
	@JsonProperty(value = "espectaculos")
	private List<String> espectaculos;
	
	public Programacion(){}

	public List<String> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(List<String> espectaculos) {
		this.espectaculos = espectaculos;
	}
	
}
