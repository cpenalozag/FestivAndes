package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class NotaDebito {
	@JsonProperty(value = "idCliente")
	private Long idCliente;
	@JsonProperty(value = "nombre")
	private String nombre;
	@JsonProperty(value = "idBoleta")
	private Long idBoleta;
	@JsonProperty(value="precio")
	private Double precio;
	
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente2) {
		this.idCliente = idCliente2;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getIdBoleta() {
		return idBoleta;
	}
	public void setIdBoleta(Long idBoleta2) {
		this.idBoleta = idBoleta2;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
}
