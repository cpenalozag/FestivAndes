package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Asistencia {
	@JsonProperty(value = "nombreCompania")
	private String nombreCompania;
	@JsonProperty(value = "fechaInicio")
	private String fechaInicio;
	@JsonProperty(value = "fechaFin")
	private String fechaFin;
	@JsonProperty(value = "asistencia")
	private String asistencia;
	@JsonProperty(value = "orderBy")
	private String orderBy;
	
	public Asistencia(){}

	public String getNombreCompania() {
		return nombreCompania;
	}

	public void setNombreCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getAsistencia() {
		return asistencia;
	}

	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
