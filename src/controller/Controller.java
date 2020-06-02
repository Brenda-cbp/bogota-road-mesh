package controller;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Scanner;

import model.data_structures.Edges;
import model.data_structures.Lista;
import model.data_structures.MaxHeapCP;
import model.logic.Comparendo;
import model.logic.Esquina;
import model.logic.EstacionPolicia;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo */
	private Modelo modelo;

	/* Instancia de la Vista */
	private View view;

	public final int N = 20;

	/**
	 * Crear la vista y el modelo del proyecto
	 * 
	 * @param capacidad
	 *            tamaNo inicial del arreglo
	 */
	public Controller() {
		view = new View();
		modelo = new Modelo();
		correrPrograma();
	}

	/**
	 * Inicializa el programa y recibe las instrucciones del usuario hasta que
	 * se deje de correr el programa
	 */
	public void correrPrograma() {

		Esquina vertice = null;

		try {
			vertice = modelo.cargarDatosGrafoVertices();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Edges<Integer> edge = modelo.cargarDatosGrafoEdges();
		Comparendo comparendo = modelo.cargarDatos();
		EstacionPolicia policia =modelo.cargarPolicias();
		view.imprimir("Comparendo Con mayor ID");
		view.imprimir(comparendo);

		//----------------------
		view.imprimir("Se cargo el grafo a partir de los txt");
		view.imprimir("Aristas totales:" + modelo.darAristas() + " Vertices totales:  " + modelo.darNumVertices());
		view.imprimir("Esquina con mayor id:");
		view.imprimir("ID: " + vertice.darId() + " Coordenadas: (" + vertice.darLatitud() + "," + vertice.darLongitud() +")"); 
		view.imprimir("Enlace con mayor id:");
		view.imprimir("ID:" + edge.darOrigen() + " Destino:" + edge.darDestino() + " Longitud = " + edge.darCosto() + " Costo comparendos = " + edge.darcosto2());
		view.imprimir("Estacion con mayor ID:");
		view.imprimir(policia);


		view.imprimir("LOS REQUERIMIENTOS INICIALES SE REALIZAN JUNTO A LA CARGA DE DATOS");
		view.imprimir("NO SE IMPRIME NADA EN CONSOLA SOBRE ESTO PUES NO SE SOLICITO");
		view.imprimir("EL LISTADO DE LAS OPCIONES ES DEL TALLER PASADO");
		while (true) {
			int opcion = view.printMenu();
			if (opcion == 1) {
				try
				{
					double lat1 = Double.parseDouble(view.pedir("Latitud Inicio:"));
					double long1 = Double.parseDouble(view.pedir("Longitud Inicio:"));
					double lat2 = Double.parseDouble(view.pedir("Latitud Final:"));
					double long2 = Double.parseDouble(view.pedir("Longitud Final:"));
					Lista<Esquina> es =modelo.darCaminoMasCorto(lat1, long1, lat2, long2);
					System.out.println("Total vertices en el camino =" + (es.darTamaño() -1));
					System.out.println("Costo minimo = " + es.darElementoPosicion(0).darLatitud());
					System.out.println("Costo total = " + es.darElementoPosicion(0).darLongitud());
					System.out.println("ID de los vertices:");
					Iterator<Esquina> it = es.iterator();
					while(it.hasNext())
					{
						System.out.println("ID = " + it.next().darId());
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

				
			}
			else if (opcion == 2) {
				try {
					modelo.dibujarMapa();
				} catch (Exception e) {
					view.imprimir("Error, Trate cargar el Json primero (opcion2)");
					e.printStackTrace();
				}

			}
			if (opcion == 3) {


				double latitud1 = Double.parseDouble(view.pedir("latitud del punto inicial"));
				double longitud1 =  Double.parseDouble(view.pedir("longitud del punto inicial"));
				double latitud2 = Double.parseDouble(view.pedir("latitud del punto final"));
				double longitud2=  Double.parseDouble(view.pedir("longitud del punto final"));

				if (modelo.rectificarPuntoEstaEnBogota(latitud1, longitud1)) {
					if (modelo.rectificarPuntoEstaEnBogota(latitud2, longitud2))
					{	System.out.println();
					try {
						Lista<Esquina>rta = modelo.darCaminoCostoMinimoPorNumeroDeComparendos(latitud1, longitud1, latitud2, longitud2);
						view.imprimir("El total de vértices por los que se debe pasar son "+ (rta.darTamaño()-1));
						view.imprimir("El costo minimo para llegar al destino es "+ rta.darElementoPosicion(0).darLatitud());
						view.imprimir("La distancia total para llegar al destino es "+ rta.darElementoPosicion(0).darLongitud());
						view.imprimir("Los vértices que se deben recorrer para llegar al destino son:");
						for (int i =1; i<rta.darTamaño();i++) {
							view.imprimir(rta.darElementoPosicion(i).toString());
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					}else {
						view.imprimir("Hubo un error con las coordenadas del punto final. Por favor vuelva a intentarlo");
						view.printMenu();}
				}else {
					view.imprimir("Hubo un error con las coordenadas del punto inicial. Por favor vuelva a intentarlo");
					view.printMenu();}

			}
			else if (opcion == 4) {

			}
			else if (opcion == 5) {
				try{
					modelo.darCaminosMasCortosPolicia(Integer.parseInt(view.pedir("m")));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

			} 	
			if(opcion == 6)
			{
				try {
					modelo.cc();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(opcion == 7)
			{

			}
		}
	}
}
