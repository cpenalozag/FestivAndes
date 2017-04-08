package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;



public class EspectaculoBasica {

	@JsonProperty (value = "id")
	private long id;
	
	@JsonProperty (value = "nombre")
	private String nombre;
	
	@JsonProperty (value = "duracion")
	private int duracion;
	
	@JsonProperty (value = "idioma")
	private String idioma;
	
	@JsonProperty (value = "descripcion")
	private String descripcion;
		
	
	public EspectaculoBasica()
	{
		super();
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


	public int getDuracion() {
		return duracion;
	}


	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}


	public String getIdioma() {
		return idioma;
	}


	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
