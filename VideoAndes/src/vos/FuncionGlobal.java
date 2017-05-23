package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class FuncionGlobal {
	
	@JsonProperty(value = "funcionBasica")
	private FuncionBasica funcionBasica;
	@JsonProperty(value="proveedor")
	private String proveedor;
	public FuncionBasica getFuncionBasica() {
		return funcionBasica;
	}
	public void setFuncionBasica(FuncionBasica funcionBasica) {
		this.funcionBasica = funcionBasica;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}
	
	

}
