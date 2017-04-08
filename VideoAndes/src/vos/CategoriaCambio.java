package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class CategoriaCambio 
{
	@JsonProperty(value = "categoriaAct")
	private String categoriaAct;
	@JsonProperty(value = "categoriaNueva")
	private String categoriaNueva;
	
	
	public CategoriaCambio(){
		
	}
	
	public String getCategoriaAct() {
		return categoriaAct;
	}
	public void setCategoriaAct(String categoriaAct) {
		this.categoriaAct = categoriaAct;
	}
	public String getCategoriaNueva() {
		return categoriaNueva;
	}
	public void setCategoriaNueva(String categoriaNueva) {
		this.categoriaNueva = categoriaNueva;
	}
	
	
}
