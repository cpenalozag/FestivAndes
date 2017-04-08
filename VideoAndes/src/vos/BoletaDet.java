package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class BoletaDet {
	
	@JsonProperty(value = "id")
	private long id;
	@JsonProperty(value = "cliente")
	private Cliente cliente;
	@JsonProperty(value = "escliente")
	private boolean esCliente;
	
	public boolean getEsCliente() {
		return esCliente;
	}

	public void setEsCliente(boolean esCliente) {
		this.esCliente = esCliente;
	}

	public BoletaDet(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	

}
