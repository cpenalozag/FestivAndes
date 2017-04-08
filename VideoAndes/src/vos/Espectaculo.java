package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;



public class Espectaculo {

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
	
	@JsonProperty (value = "costoRealizacion")
	private double costoRealizacion;
	
	@JsonProperty (value = "otroIdioma")
	private String otroIdioma;
	
	@JsonProperty (value = "publico")
	private List<Publico> publicosObjetivo;
	
	@JsonProperty( value = "elementosExtra")
	private List<ElementoExtra> elementosExtra;
	
	@JsonProperty(value = "reqTecnicos")
	private List<ReqTecnico> requerimientos;
	@JsonProperty (value = "organizadores")
	private List<Organizador> organizadores;
	@JsonProperty(value = "companias")
	private List<Compania> companias;
	@JsonProperty (value = "funciones")
	private List<Funcion>funciones;
	
		
	
	public Espectaculo()
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


	public double getCostoRealizacion() {
		return costoRealizacion;
	}


	public void setCostoRealizacion(double costoRealizacion) {
		this.costoRealizacion = costoRealizacion;
	}


	public String getOtroIdioma() {
		return otroIdioma;
	}


	public void setOtroIdioma(String otroIdioma) {
		this.otroIdioma = otroIdioma;
	}


	public List<Publico> getPublicosObjetivo() {
		return publicosObjetivo;
	}



	public List<ElementoExtra> getElementosExtra() {
		return elementosExtra;
	}



	public List<ReqTecnico> getRequerimientos() {
		return requerimientos;
	}


	public void setRequerimientos(List<ReqTecnico> requerimientos) {
		this.requerimientos = requerimientos;
	}


	public void setPublicosObjetivo(List<Publico> publicosObjetivo) {
		this.publicosObjetivo = publicosObjetivo;
	}


	public void setElementosExtra(List<ElementoExtra> elementosExtra) {
		this.elementosExtra = elementosExtra;
	}
	

}
