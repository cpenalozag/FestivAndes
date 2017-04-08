package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RangoFechas {
	@JsonProperty(value = "fechaInicial")
	private String fechaInicial;
	@JsonProperty(value = "fechaFinal")
	private String fechaFinal;
	
	public RangoFechas(){}
	
	public String getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	

}
