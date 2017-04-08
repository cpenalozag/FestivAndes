package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CompraBoleta {
	@JsonProperty(value = "idFuncion")
	private long idFuncion;
	@JsonProperty(value = "idEspectaculo")
	private long idEspectaculo;
	@JsonProperty(value = "idCliente")
	private long idCliente;
	@JsonProperty(value = "idSitio")
	private long idSitio;
	@JsonProperty(value = "idSilla")
	private long idSilla;
	
	public CompraBoleta(){}

	public long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(long idFuncion) {
		this.idFuncion = idFuncion;
	}

	public long getIdEspectaculo() {
		return idEspectaculo;
	}

	public void setIdEspectaculo(long idEspectaculo) {
		this.idEspectaculo = idEspectaculo;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public long getIdSitio() {
		return idSitio;
	}

	public void setIdSitio(long idSitio) {
		this.idSitio = idSitio;
	}

	public long getIdSilla() {
		return idSilla;
	}

	public void setIdSilla(long idSilla) {
		this.idSilla = idSilla;
	}
	
	
	
}
