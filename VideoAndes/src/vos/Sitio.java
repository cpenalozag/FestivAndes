package vos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Sitio  implements Serializable{

	@JsonProperty(value = "id")
	private long id;

	@JsonProperty(value ="capacidad")
	private int capacidad;

	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "horaInicio")
	private int horaInicio;

	@JsonProperty (value = "horaFin")
	private int horaFin;

	@JsonProperty (value = "reqTecnicos")
	private List<ReqTecnico> reqTecnicos;
	
	@JsonProperty(value = "tipo")
	private TipoSitio tipo;

	@JsonProperty (value = "necesidades")
	private List<Necesidad> necesidades;
	@JsonProperty (value = "localidades")
	private List<Localidad> localidades = new LinkedList<>();
	@JsonProperty(value = "reservas")
	private List<Reserva> reservas = new LinkedList<>();
	@JsonProperty(value = "funciones")
	private List<Funcion> funciones = new LinkedList<>();

	public Sitio()
	{

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

	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}

	public List<ReqTecnico> getReqTecnicos() {
		return reqTecnicos;
	}

	public void setReqTecnicos(List<ReqTecnico> reqTecnicos) {
		this.reqTecnicos = reqTecnicos;
	}

	public List<Necesidad> getNecesidades() {
		return necesidades;
	}

	public void setNecesidades(List<Necesidad> necesidades) {
		this.necesidades = necesidades;
	}

	public List<Localidad> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(List<Localidad> localidades) {
		this.localidades = localidades;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}

	public TipoSitio getTipo() {
		return tipo;
	}

	public void setTipo(TipoSitio tipo) {
		this.tipo = tipo;
	}
	
	public void addLocalidad(Localidad loc)
	{
		localidades.add(loc);
	}
	public void addFuncion(Funcion fun)
	{
		funciones.add(fun);
	}

}
