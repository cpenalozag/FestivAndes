package vos;


import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {
	
	@JsonProperty(value = "id")
	private long id;
	@JsonProperty(value = "nombre")
	private String nombre;
	@JsonProperty(value = "correo")
	private String correo;
	@JsonProperty(value = "contrasena")
	private String contrasena;
	
	
	
	public Usuario(){
		
	}
	
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
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	

}
