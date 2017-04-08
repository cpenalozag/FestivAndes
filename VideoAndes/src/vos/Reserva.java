package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva {
	
	@JsonProperty (value = "horaInicio")
	private int horaInicio;
	
	@JsonProperty(value = "horaFin")
	private int horaFin;
	
	@JsonProperty (value = "fecha")
	private Date fecha;
	
	@JsonProperty (value = "id")
	private long id;
	
	public Reserva(){
		
	}

	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
