package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class UsuarioReporte {
	@JsonProperty(value = "id")
	private long id;
	@JsonProperty(value = "nombre")
	private String nombre;
	@JsonProperty(value = "correo")
	private String correo;
	
	public UsuarioReporte(){}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
	
}
