package vos;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente extends Usuario{

	@JsonProperty(value = "edad")
	private int edad;
	@JsonProperty(value = "preferencias")
	private List<Categoria> preferencias = new LinkedList<>();
	//@JsonProperty(value = "boletas")
	//private List<Boleta> boletas;
	
	public Cliente(){
		super();
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public List<Categoria> getPreferencias() {
		return preferencias;
	}

	public void setPreferencias(List<Categoria> preferencias) {
		this.preferencias = preferencias;
	}

//	public List<Boleta> getBoletas() {
//		return boletas;
//	}
//
//	public void setBoletas(List<Boleta> boletas) {
//		this.boletas = boletas;
//	}
	public void addCategoria(Categoria cat)
	{
		preferencias.add(cat);
	}
	
}
