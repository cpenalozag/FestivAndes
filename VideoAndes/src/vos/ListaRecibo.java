package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRecibo {
	@JsonProperty(value = "listaRecibo")
	private ArrayList<Recibo> listaRecibos = new ArrayList<>();

	
	public ArrayList<Recibo> getListaRecibos() {
		return listaRecibos;
	}

	public void setListaRecibos(ArrayList<Recibo> listaRecibos) {
		this.listaRecibos = listaRecibos;
	}

}
