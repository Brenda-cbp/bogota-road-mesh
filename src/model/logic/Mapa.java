package model.logic;

import java.io.PrintWriter;
import java.util.Iterator;

import model.data_structures.Edges;
import model.data_structures.Graph;

public class Mapa {

	public final String RUTA_EXPORTAR = "";

	public void escribirJson(Graph<Esquina, Integer> grafoCargado) {
		PrintWriter writer;

		try {
			//Para los vértices
			writer = new PrintWriter(RUTA_EXPORTAR);
			writer.println("{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{\"");
			Iterator  it =  grafoCargado.darNodos();  //da los vertices de la la tabla
			while (it.hasNext()) {
				Esquina actual=(Esquina) it.next(); //si es esquina?
				writer.println("{");
				writer.println("\"type\":\"Feature\",");
				writer.println("\"properties\":{\"distancia|id\":"+actual.darId()+"},");
				writer.println("\"geometry\":{\"coordinates\": [["+actual.darLongitud()+","+actual.darLatitud()+"]");
				if(it.hasNext()) {
					writer.println("},");
				}
				else  writer.println("}");
				actual=(Esquina) it.next();
			}
			//para los arcos 
			//nodos 
			//while 
			//writer.println("{");
		//	writer.println("\"type\":\"Feature\",");
	//		writer.println("\"properties\":{\"distancia|id\":"+"},");
			
			
		/*	
			if(it.hasNext()) {
				writer.println("},");
			}
			else  writer.println("}");
			
			writer.println("}}");
			writer.close();
			
		*/	
		}
		catch(Exception e ) {
		}
	}
}



