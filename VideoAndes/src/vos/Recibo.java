package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Recibo {
	@JsonProperty(value = "funcion")
	private FuncionBasica funcion;
	@JsonProperty(value = "sitio")
	private SitioBasica sitio;
	@JsonProperty(value= "silla")
	private Silla silla;
	@JsonProperty(value="precio")
	private Double precio;
	
	public FuncionBasica getFuncion() {
		return funcion;
	}
	public void setFuncion(FuncionBasica funcion) {
		this.funcion = funcion;
	}

	public SitioBasica getSitio() {
		return sitio;
	}
	public void setSitio(SitioBasica sitio) {
		this.sitio = sitio;
	}
	public Silla getSilla() {
		return silla;
	}
	public void setSilla(Silla silla) {
		this.silla = silla;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
}
