package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Organizador extends Usuario{
	
	@JsonProperty (value = "companias")
	private List<Compania> companias;
	@JsonProperty(value = "sitios")
	private List<Sitio> sitios;
	@JsonProperty(value = "espectaculos")
	private List<String> espectaculos;
	public Organizador(){
	super();
	}
	public List<Compania> getCompanias() {
		return companias;
	}
	public void setCompanias(List<Compania> companias) {
		this.companias = companias;
	}
	public List<Sitio> getSitios() {
		return sitios;
	}
	public void setSitios(List<Sitio> sitios) {
		this.sitios = sitios;
	}
	public List<String> getEspectaculos() {
		return espectaculos;
	}
	public void setEspectaculos(List<String> espectaculos) {
		this.espectaculos = espectaculos;
	}

}
