package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CompraBoleta {
	@JsonProperty(value = "idFuncion")
	private int idFuncion;
	@JsonProperty(value = "idEspectaculo")
	private int idEspectaculo;
	@JsonProperty(value = "idCliente")
	private int idCliente;
	@JsonProperty(value = "idSitio")
	private int idSitio;
	@JsonProperty(value = "idSilla")
	private int idSilla;
	
	public CompraBoleta(){}

	public int getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}

	public int getIdEspectaculo() {
		return idEspectaculo;
	}

	public void setIdEspectaculo(int idEspectaculo) {
		this.idEspectaculo = idEspectaculo;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdSitio() {
		return idSitio;
	}

	public void setIdSitio(int idSitio) {
		this.idSitio = idSitio;
	}

	public int getIdSilla() {
		return idSilla;
	}

	public void setIdSilla(int idSilla) {
		this.idSilla = idSilla;
	}
	
	
	
}
