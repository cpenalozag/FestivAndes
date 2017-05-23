package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FuncionBasica {
	@JsonProperty(value = "id")
	private long id;
	@JsonProperty(value = "dia")
	private String dia;
	@JsonProperty (value = "hora")
	private int hora;
	@JsonProperty(value = "idEspectaculo")
	private int espectaculo;
	@JsonProperty(value = "idSitio")
	private int sitio;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public int getEspectaculo() {
		return espectaculo;
	}
	public void setEspectaculo(int espectaculo) {
		this.espectaculo = espectaculo;
	}
	public int getSitio() {
		return sitio;
	}
	public void setSitio(int sitio) {
		this.sitio = sitio;
	}
	
	
}
