package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class BoletaConsulta {
	
	@JsonProperty (value = "espectaculo")
	private String espectaculo;
	@JsonProperty(value = "idSitio" )
	private int idSitio;
	@JsonProperty(value = "dia")
	private String dia;
	@JsonProperty(value= "boletasVendidas")
	private int boletasVendidas;
	@JsonProperty(value= "usuariosRegistrados")
	private int usuariosRegistrados;
	
	public BoletaConsulta(){
		
	}

	public String getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(String espectaculo) {
		this.espectaculo = espectaculo;
	}

	public int getIdSitio() {
		return idSitio;
	}

	public void setIdSitio(int idSitio) {
		this.idSitio = idSitio;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public int getBoletasVendidas() {
		return boletasVendidas;
	}

	public void setBoletasVendidas(int boletasVendidas) {
		this.boletasVendidas = boletasVendidas;
	}

	public int getUsuariosRegistrados() {
		return usuariosRegistrados;
	}

	public void setUsuariosRegistrados(int usuariosRegistrados) {
		this.usuariosRegistrados = usuariosRegistrados;
	}

	
	
}
