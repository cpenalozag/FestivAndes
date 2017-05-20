package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Recibo {
	
	@JsonProperty(value = "idBoleta")
	private Integer idBoleta;
	@JsonProperty(value = "idFuncion")
	private Integer idFuncion;
	@JsonProperty(value = "idEspectaculo")
	private Integer idEspectaculo;
	@JsonProperty(value = "idSilla")
	private Integer idSilla;
	@JsonProperty(value="precio")
	private Double precio;
	
	
	public Integer getIdSilla() {
		return idSilla;
	}
	public void setIdSilla(Integer idSilla) {
		this.idSilla = idSilla;
	}
	public Integer getIdBoleta() {
		return idBoleta;
	}
	public void setIdBoleta(Integer idBoleta) {
		this.idBoleta = idBoleta;
	}
	public Integer getIdFuncion() {
		return idFuncion;
	}
	public void setIdFuncion(Integer idFuncion) {
		this.idFuncion = idFuncion;
	}
	public Integer getIdEspectaculo() {
		return idEspectaculo;
	}
	public void setIdEspectaculo(Integer idEspectaculo) {
		this.idEspectaculo = idEspectaculo;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
}
