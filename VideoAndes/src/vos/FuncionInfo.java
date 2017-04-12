package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FuncionInfo {
	
	@JsonProperty(value = "idFuncion")
	private Long idFuncion;
	@JsonProperty(value = "nombreEspectaculo")
	private String nombreEspectaculo;
	
	public FuncionInfo(){
		
	}

	public Long getIdFuncion() {
		return idFuncion;
	}

	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}

	public String getNombreEspectaculo() {
		return nombreEspectaculo;
	}

	public void setNombreEspectaculo(String nombreEspectaculo) {
		this.nombreEspectaculo = nombreEspectaculo;
	}

}
