package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Recibo {
	
	@JsonProperty(value = "idBoleta")
	private Integer idBoleta;
	@JsonProperty(value = "funcion")
	private FuncionBasica funcion;
	@JsonProperty(value= "silla")
	private Silla silla;
	@JsonProperty(value="precio")
	private Double precio;
	
	
	
	public Integer getIdBoleta() {
		return idBoleta;
	}
	public void setIdBoleta(Integer idBoleta) {
		this.idBoleta = idBoleta;
	}
	public FuncionBasica getFuncion() {
		return funcion;
	}
	public void setFuncion(FuncionBasica funcion) {
		this.funcion = funcion;
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
