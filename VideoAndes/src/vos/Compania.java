package vos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
public class Compania {

	@JsonProperty(value = "id")
	private Long id;
	@JsonProperty(value = "nombre")
	private String nombre;

	@JsonProperty(value = "representante")
	private String representante;
	
	@JsonProperty(value = "pais")
	private String pais;

	@JsonProperty(value = "fechaLlegada")
	private Date fechaLlegada;

	@JsonProperty(value = "fechaSalida")
	private Date fechaSalida;
	
	@JsonProperty(value = "paginaWeb")
	private String paginaWeb;
	
	@JsonProperty(value = "ciudadOrigen")
	private String ciudadOrigen;
	@JsonProperty(value = "organizador")
	private Organizador organizador;
	@JsonProperty(value = "espectaculos")
	private List<String> espectaculos;
	

	/**
	 * @param nombre
	 * @param representante
	 * @param pais
	 * @param fechaLlegada
	 * @param fechaSalida
	 */
	public Compania(@JsonProperty(value = "nombre") String nombre, 	@JsonProperty(value = "representante")String representante, 
			@JsonProperty(value = "pais") String pais, @JsonProperty(value = "fechaLlegada")Date fechaLlegada, @JsonProperty(value = "fechaSalida")Date fechaSalida,
			@JsonProperty(value = "paginaWeb") String paginaWeb, @JsonProperty(value = "ciudadOrigen") String ciudadOrigen, @JsonProperty(value = "id")long id) {
		super();
		this.nombre = nombre;
		this.representante = representante;
		this.pais = pais;
		this.fechaLlegada = fechaLlegada;
		this.fechaSalida = fechaSalida;
		this.ciudadOrigen = ciudadOrigen;
		this.id = id;
		this.paginaWeb = paginaWeb;
		
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPaginaWeb() {
		return paginaWeb;
	}


	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}


	public String getCiudadOrigen() {
		return ciudadOrigen;
	}


	public void setCiudadOrigen(String ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}


	public Organizador getOrganizador() {
		return organizador;
	}


	public void setOrganizador(Organizador organizador) {
		this.organizador = organizador;
	}


	public List<String> getEspectaculos() {
		return espectaculos;
	}


	public void setEspectaculos(List<String> espectaculos) {
		this.espectaculos = espectaculos;
	}


}
