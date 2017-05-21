package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FiltrosConsultaFunciones {
	@JsonProperty(value = "fechaInicial")
	private String fechaInicial;
	@JsonProperty(value = "fechaFinal")
	private String fechaFinal;
	@JsonProperty(value = "categoria")
	private String categoria;
	@JsonProperty(value = "idioma")
	private String idioma;
	@JsonProperty(value = "idCompania")
	private String nombreCompania;
	@JsonProperty(value = "traduccion")
	private String traduccion;
	public String getFechaInicial() {
		return fechaInicial;
	}
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}
	public String getFechaFinal() {
		return fechaFinal;
	}
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getNombreCompania() {
		return nombreCompania;
	}
	public void setIdCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}
	public String getTraduccion() {
		return traduccion;
	}
	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}
	
	
}