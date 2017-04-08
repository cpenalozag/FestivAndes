package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class EspectaculoPopular {
	@JsonProperty (value = "idEspectaculo")
	private int id;
	
	@JsonProperty (value = "fecha")
	private String fecha;
	
	@JsonProperty (value = "cantidadAsistentes")
	private int cantidadAsistentes;
	
	@JsonProperty (value = "nombre")
	private String nombre;
	
	@JsonProperty (value = "duracion")
	private int duracion;
	
	@JsonProperty (value = "idioma")
	private String idioma;
	
	@JsonProperty (value = "descripcion")
	private String descripcion;
	
	@JsonProperty (value = "costoRealizacion")
	private String costoRealizacion;
	
	@JsonProperty (value = "otroIdioma")
	private boolean otroIdioma;
	
	@JsonProperty (value = "idOrganizador")
	private int idOrganizador;
	
	public EspectaculoPopular(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getCantidadAsistentes() {
		return cantidadAsistentes;
	}

	public void setCantidadAsistentes(int cantidadAsistentes) {
		this.cantidadAsistentes = cantidadAsistentes;
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

	public String getCostoRealizacion() {
		return costoRealizacion;
	}

	public void setCostoRealizacion(String costoRealizacion) {
		this.costoRealizacion = costoRealizacion;
	}

	public boolean isOtroIdioma() {
		return otroIdioma;
	}

	public void setOtroIdioma(boolean otroIdioma) {
		this.otroIdioma = otroIdioma;
	}

	public int getIdOrganizador() {
		return idOrganizador;
	}

	public void setIdOrganizador(int idOrganizador) {
		this.idOrganizador = idOrganizador;
	}
	
	
	
}
