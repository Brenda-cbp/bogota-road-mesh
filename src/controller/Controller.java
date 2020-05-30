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
		view.imprimir("Se cargo el grafo a partir de los txt");
		view.imprimir("Aristas totales:" + modelo.darAristas() + " Vertices totales:  " + modelo.darNumVertices());
		view.imprimir("Esquina con mayor id:");
		view.imprimir("ID: " + vertice.darId() + " Coordenadas: (" + vertice.darLatitud() + "," + vertice.darLongitud() ); 
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
				modelo.generarJson();
			}
			if (opcion == 2) {
				try {
					modelo.dibujarMapa();
				} catch (Exception e) {
					view.imprimir("Error, Trate cargar el Json primero (opcion2)");
					e.printStackTrace();
				}

			}
			if (opcion == 3) {
				try {
					modelo.cargaDatosProyecto();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (opcion == 4) {
				EstacionPolicia ejemplo =modelo.cargarPolicias();
				view.imprimir("Ejemplo Estacion:");
				view.imprimir(ejemplo);
				view.imprimir("De cada estacion se decidio cargar su respectiva: Descripcion, latitud, longitud, telefono,direccion,horario, iuLocal, servicio, id");

			}
			if (opcion == 5) {
				try {
					modelo.dibujarEstacionesMapa();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
