package vos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcion2 {
	@JsonProperty(value = "id")
	private long id;
	@JsonProperty(value = "realizada")
	private boolean realizada;
	@JsonProperty(value = "dia")
	private Date dia;
	@JsonProperty (value = "hora")
	private int hora;
	@JsonProperty(value = "espectaculo")
	private String espectaculo;
	@JsonProperty(value = "espectaculoId")
	private long espectaculoId;
	@JsonProperty(value = "sitio")
	private String sitio;
	@JsonProperty(value = "boletas")
	private List<Integer> boletas;
	@JsonProperty(value = "operadorLogistico")
	private long operadorLogico;
	
	
	public Funcion2(){
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isRealizada() {
		return realizada;
	}
	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}
	public Date getDia() {
		return dia;
	}
	public void setDia(Date dia) {
		this.dia = dia;
	}
	public int getHora() {
		return hora;
	}
	public void setHora(int hora) {
		this.hora = hora;
	}
	public String getEspectaculo() {
		return espectaculo;
	}
	public void setEspectaculo(String espectaculo) {
		this.espectaculo = espectaculo;
	}
	public String getSitio() {
		return sitio;
	}
	public void setSitio(String sitio) {
		this.sitio = sitio;
	}
	public long getOperadorLogico() {
		return operadorLogico;
	}
	public void setOperadorLogico(long operadorLogico) {
		this.operadorLogico = operadorLogico;
	}
	public long getEspectaculoId() {
		return espectaculoId;
	}
	public void setEspectaculoId(long espectaculoId) {
		this.espectaculoId = espectaculoId;
	}

}
