package vos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import rest.EspectaculoServices;

public class Funcion implements Serializable{

	@JsonProperty(value = "id")
	private long id;
	@JsonProperty(value = "realizada")
	private boolean realizada;
	@JsonProperty(value = "dia")
	private Date dia;
	@JsonProperty (value = "hora")
	private int hora;
	@JsonProperty(value = "espectaculo")
	private Espectaculo espectaculo;
	@JsonProperty(value = "sitio")
	private Sitio sitio;
	@JsonProperty(value = "boletas")
	private List<Boleta> boletas;
	@JsonProperty(value = "operadorLogistico")
	private OperadorLogistico operadorLogico;
	public Funcion(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Espectaculo getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(Espectaculo espectaculo) {
		this.espectaculo = espectaculo;
	}

	public Sitio getSitio() {
		return sitio;
	}

	public void setSitio(Sitio sitio) {
		this.sitio = sitio;
	}

	public List<Boleta> getBoletas() {
		return boletas;
	}

	public void setBoletas(List<Boleta> boletas) {
		this.boletas = boletas;
	}

	public OperadorLogistico getOperadorLogico() {
		return operadorLogico;
	}

	public void setOperadorLogico(OperadorLogistico operadorLogico) {
		this.operadorLogico = operadorLogico;
	}

	public boolean isRealizada() {
		return realizada;
	}

	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}
	
	
}
