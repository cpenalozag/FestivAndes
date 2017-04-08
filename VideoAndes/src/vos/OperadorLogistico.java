package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class OperadorLogistico extends Usuario{
	
	@JsonProperty(value = "funciones")
	private List<Funcion2> funciones;
	
	public OperadorLogistico(){super();}

	public List<Funcion2> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion2> funciones) {
		this.funciones = funciones;
	}
	
	
	

}
