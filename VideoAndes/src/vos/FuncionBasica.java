package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FuncionBasica {
	@JsonProperty(value = "id")
	private long id;
	@JsonProperty(value = "dia")
	private String dia;
	@JsonProperty (value = "hora")
	private int hora;
	@JsonProperty(value = "espectaculo")
	private EspectaculoBasica espectaculo;
	@JsonProperty(value = "sitio")
	private SitioBasica sitio;
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
	public EspectaculoBasica getEspectaculo() {
		return espectaculo;
	}
	public void setEspectaculo(EspectaculoBasica espectaculo) {
		this.espectaculo = espectaculo;
	}
	public SitioBasica getSitio() {
		return sitio;
	}
	public void setSitio(SitioBasica sitio) {
		this.sitio = sitio;
	}
	
	
}
