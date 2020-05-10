package controller;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Scanner;

import model.data_structures.Lista;
import model.data_structures.MaxHeapCP;
import model.logic.Comparendo;
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
		try {
			modelo.crearGrafo();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		view.imprimir("Se cargo el grafo a partir de los txt");
		view.imprimir("Aristas totales:" + modelo.darAristas() + " Vertices totales:  " + modelo.darVertices());
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
					modelo.cargarDatosGrafo();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (opcion == 4) {
				EstacionPolicia ejemplo =modelo.cargarPolicias().darElementoPosicion(0);
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
