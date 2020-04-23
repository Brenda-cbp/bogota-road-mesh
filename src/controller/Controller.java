package controller;

import java.util.Scanner;

import model.data_structures.Lista;
import model.data_structures.MaxHeapCP;
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
			int opcion= view.printMenu();
			if(opcion==1) {
				try{
					String s = view.pedir("El tamano");
					if(s != null)
					{
						int n= Integer.parseInt(s);
						Lista<Comparendo> rta= modelo.darMayorGravedad(n);
						for (Comparendo comparendo : rta) {
							view.printMessage(comparendo.toString());
						}
					}
				}
				catch(Exception e)
				{
					view.imprimir("Numero no valido");
				}

			}
			if (opcion==2)
			{

			}
			if (opcion==3)
			{

			}if (opcion==4)
			{

			}if (opcion==5)
			{

			}if (opcion==6)
			{

			}if (opcion==7)
			{

			}if (opcion==8)
			{

			}if (opcion==9)
			{

			}
		}
	}
}

