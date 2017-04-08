package vos;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Sitio2 {

	
	@JsonProperty(value = "id")
	private long id;

	@JsonProperty(value ="capacidad")
	private int capacidad;

	@JsonProperty(value = "nombre")
	private String nombre;
	
	@JsonProperty(value = "tipo")
	private TipoSitio tipo;

	@JsonProperty(value = "funciones")
	private List<Funcion2> funciones = new LinkedList<>();
	@JsonProperty(value = "localidades")
	private List<Localidad> localidades = new LinkedList<>();

	
	public Sitio2(){
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TipoSitio getTipo() {
		return tipo;
	}

	public void setTipo(TipoSitio tipo) {
		this.tipo = tipo;
	}

	public List<Funcion2> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion2> funciones) {
		this.funciones = funciones;
	}
	
	public void addLocalidad(Localidad loc)
	{
		localidades.add(loc);
	}
	public void addFuncion(Funcion2 fun)
	{
		funciones.add(fun);
	}
}
