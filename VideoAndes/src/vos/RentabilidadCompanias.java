package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RentabilidadCompanias {
	
	@JsonProperty(value = "idCompania")
	private int idCompania;
	@JsonProperty(value = "idEspectaculo")
	private int idEspectaculo;
	@JsonProperty(value = "idFuncion")
	private int idFuncion;
	@JsonProperty(value = "fecha")
	private String fecha;
	@JsonProperty(value = "idsitio")
	private int idsitio;
	@JsonProperty(value = "tipositio")
	private int tipositio;
	@JsonProperty(value = "categoria")
	private String categoria;
	@JsonProperty(value = "cantidadBoletas")
	private int cantidadBoletas;
	@JsonProperty(value = "proporcionAsistencia")
	private Double proporcionAsistencia;
	@JsonProperty(value = "totalVendido")
	private Double totalVendido;
	
	public RentabilidadCompanias(){}
	
	public int getIdEspectaculo() {
		return idEspectaculo;
	}
	public void setIdEspectaculo(int idEspectaculo) {
		this.idEspectaculo = idEspectaculo;
	}
	public int getIdFuncion() {
		return idFuncion;
	}
	public void setIdFuncion(int idFuncion) {
		this.idFuncion = idFuncion;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public int getIdsitio() {
		return idsitio;
	}
	public void setIdsitio(int idsitio) {
		this.idsitio = idsitio;
	}
	public int getTipositio() {
		return tipositio;
	}
	public void setTipositio(int tipositio) {
		this.tipositio = tipositio;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public int getCantidadBoletas() {
		return cantidadBoletas;
	}
	public void setCantidadBoletas(int cantidadBoletas) {
		this.cantidadBoletas = cantidadBoletas;
	}
	public Double getProporcionAsistencia() {
		return proporcionAsistencia;
	}
	public void setProporcionAsistencia(Double proporcionAsistencia) {
		this.proporcionAsistencia = proporcionAsistencia;
	}
	public Double getTotalVendido() {
		return totalVendido;
	}
	public void setTotalVendido(Double totalVendido) {
		this.totalVendido = totalVendido;
	}
	public int getIdCompania() {
		return idCompania;
	}
	public void setIdCompania(int idCompania) {
		this.idCompania = idCompania;
	}
	
	
	
}
