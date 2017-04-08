package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReporteEspectaculo {
	public final static String USUARIO = "Usuario";
	public final static String NO_USUARIO = "No usuario";
	
	@JsonProperty(value = "localidad")
	private String localidad;
	@JsonProperty(value = "sitio")
	private SitioBasica sitio;
	@JsonProperty(value = "cantidadBoletas")
	private int cantidadBoletas;
	@JsonProperty(value = "producido")
	private Double producido;
	@JsonProperty(value = "porcentajeOcupacion")
	private Double porcentajeOcupacion;
	@JsonProperty(value = "tipoComprador")
	private String tipoComprador;
	
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public SitioBasica getSitio() {
		return sitio;
	}
	public void setSitio(SitioBasica sitio) {
		this.sitio = sitio;
	}
	public int getCantidadBoletas() {
		return cantidadBoletas;
	}
	public void setCantidadBoletas(int cantidadBoletas) {
		this.cantidadBoletas = cantidadBoletas;
	}
	public Double getProducido() {
		return producido;
	}
	public void setProducido(Double producido) {
		this.producido = producido;
	}
	public Double getPorcentajeOcupacion() {
		return porcentajeOcupacion;
	}
	public void setPorcentajeOcupacion(Double porcentajeOcupacion) {
		this.porcentajeOcupacion = porcentajeOcupacion;
	}
	public String getTipoComprador() {
		return tipoComprador;
	}
	public void setTipoComprador(String tipoComprador) {
		this.tipoComprador = tipoComprador;
	}
	
	
}
