package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FiltrosBoletas {
	@JsonProperty(value = "fechaInicial")
	private String fechaInicial;
	@JsonProperty(value = "fechaFinal")
	private String fechaFinal;
	@JsonProperty(value = "diaSemana")
	private String diaSemana;
	@JsonProperty(value = "idElementoExtra")
	private int idElementoExtra;
	@JsonProperty(value = "horaInicial")
	private int horaInicial;
	@JsonProperty(value = "horaFinal")
	private int horaFinal;
	@JsonProperty(value = "tipoLocalidad")
	private int tipoLocalidad;
	
	public FiltrosBoletas(){}

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

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public int getIdElementoExtra() {
		return idElementoExtra;
	}

	public void setIdElementoExtra(int idElementoExtra) {
		this.idElementoExtra = idElementoExtra;
	}

	public int getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(int horaInicial) {
		this.horaInicial = horaInicial;
	}

	public int getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(int horaFinal) {
		this.horaFinal = horaFinal;
	}

	public int getTipoLocalidad() {
		return tipoLocalidad;
	}

	public void setTipoLocalidad(int tipoLocalidad) {
		this.tipoLocalidad = tipoLocalidad;
	}

	
}
