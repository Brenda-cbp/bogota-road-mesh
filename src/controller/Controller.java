package controller;

import java.util.Scanner;

import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;
	
	/* Instancia de la Vista*/
	private View view;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
		correrPrograma();
	}
	public void correrPrograma()
	{
		view.imprimir("Cargando datos...");
		Comparendo maxId = modelo.cargarDatos();
		view.imprimir("Carga completa, cantidad comparendos en archivo:" + modelo.darCantidadComparendos());
		view.imprimir("Comparendo con mayor ID registrado:");
		view.imprimir(maxId);
		while(true)
		{
			
		}
	}
}
