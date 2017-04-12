package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class InformeAsistencia {
	@JsonProperty(value = "idUsuario")
	private Long idUsuario;
	@JsonProperty(value = "asistencias")
	private int asistencias;
	@JsonProperty(value = "noAsistidas")
	private int noAsistidas;
	@JsonProperty(value = "funcionesAsistidas")
	private ArrayList<FuncionInfo> funcionesAsistidas;
	@JsonProperty(value = "canceladas")
	private int cancelada;
	
	public InformeAsistencia(){
		
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getAsistencias() {
		return asistencias;
	}

	public void setAsistencias(int asistencias) {
		this.asistencias = asistencias;
	}

	public int getNoAsistidas() {
		return noAsistidas;
	}

	public void setNoAsistidas(int noAsistidas) {
		this.noAsistidas = noAsistidas;
	}

	public ArrayList<FuncionInfo> getFuncionesAsistidas() {
		return funcionesAsistidas;
	}

	public void setFuncionesAsistidas(ArrayList<FuncionInfo> funcionesAsistidas) {
		this.funcionesAsistidas = funcionesAsistidas;
	}

	public int getCancelada() {
		return cancelada;
	}

	public void setCancelada(int cancelada) {
		this.cancelada = cancelada;
	}
	

}
