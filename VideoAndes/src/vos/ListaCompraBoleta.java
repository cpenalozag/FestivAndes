package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCompraBoleta {
	
	
	@JsonProperty(value = "listaCompraBoletas")
	public ArrayList <CompraBoleta> listaBoletas;
	
	public ListaCompraBoleta (@JsonProperty (value = "listaCompraBoletas")ArrayList <CompraBoleta> listaBoletas )
	{
		this.listaBoletas =listaBoletas;
	}

}
