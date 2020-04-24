package controller;

import java.util.Iterator;
import java.util.Scanner;

import model.data_structures.Lista;
import model.data_structures.MaxHeapCP;
import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller
{

	/* Instancia del Modelo */
	private Modelo modelo;

	/* Instancia de la Vista */
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * 
	 * @param capacidad
	 *            tamaNo inicial del arreglo
	 */
	public Controller()
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
		while (true)
		{
			int opcion = view.printMenu();
			if (opcion == 1)
			{
				String s = view.pedir("El tamano");
				// Si s es null significa que la persona escribio CANCELAR
				if (s != null)
				{
					try
					{
						int n = Integer.parseInt(s);
						Lista<Comparendo> rta = modelo.darMayorGravedad(n);
						for (Comparendo comparendo : rta)
						{
							view.printMessage(comparendo.toString());

						}
					}
					// SI ingresa una letra o algo raros
					catch (Exception e)
					{
						view.imprimir("Numero no valido");
						e.printStackTrace();
					}

				}
			}
			if (opcion == 2)
			{
				try
				{
					String mes = view.pedir("nï¿½mero del mes");
					String dia = view.pedir("el dï¿½a");
					// Si s es null significa que la persona escribio CANCELAR
					if (mes != null && dia != null)
					{
						int Mes = Integer.parseInt(mes);
						Lista<Comparendo> rta = modelo.darComparendoMesyDia(Mes, dia);
						System.out.println("Controller comparendo" + rta.darTamaño());
						for (Comparendo comparendo : rta)
						{
							view.imprimir(comparendo.toString());
							System.out.println("Controller comparendo" + rta.darTamaño());
						}
					}
				}
				// SI ingresa una letra o algo raros
				catch (Exception e)
				{
					view.imprimir(e.getMessage());

				}

			}
			if (opcion == 3)
			{

			}
			if (opcion == 4)
			{
				String respuesta = view.pedir("Cantidad de comparendos");
				if (respuesta != null)
				{
					try
					{
						int m = Integer.parseInt(respuesta);
						Lista<Comparendo> lista = modelo.darMasCercanosEstacionPolicia(m);
						Iterator<Comparendo> it = lista.iterator();
						view.imprimir("Comparendos encontrados: " + lista.darTamaño());
						view.imprimir(modelo.LAT_POLICIA + "," + modelo.LONG_POLICIA);
						while (it.hasNext())
						{
							view.imprimir(it.next());
						}
					}
					catch (Exception e)
					{
						view.imprimir("Entrada invalida");
						e.printStackTrace();
					}

				}
			}
			if (opcion == 5)
			{
				String medioDete = view.pedir("medio de deteccion");
				if (medioDete != null)
				{
					String vehi = view.pedir("Tipo de vehiculo");
					if (vehi != null)
					{
						String localidad = view.pedir("la Localidad");
						if (localidad != null)
						{
							Comparendo[] comparendos = modelo.darComprendosPorLLave(medioDete, vehi, localidad);
							view.imprimir("Comparendos encontrados: " + comparendos.length);
							if (comparendos.length > modelo.N)
								view.imprimir("Se imprimen los " + modelo.N + " primeros");
							for (int i = 0; i < modelo.N && i < comparendos.length; i++)
							{
								view.imprimir(comparendos[i]);
							}
						}
					}
				}
			}
			if (opcion == 6)
			{
				String latmin = view.pedir("Latitud minima");
				if (latmin != null)
				{
					double l1 = Double.parseDouble(latmin);
					try
					{

						String latMax = view.pedir("Latitud maxima");
						if (latMax != null)
						{
							double l2 = Double.parseDouble(latMax);
							Iterator<Comparendo> it = modelo.darComparendosEnRangoLatitud(l1, l2);
							view.imprimir("Se imprimen los primeros " + modelo.N + " encontrados");
							int i = 0;
							while(it.hasNext() && i < modelo.N)
							{
								view.imprimir(it.next());
								i++;
							}
						}
					}
					catch (Exception e)
					{
						view.imprimir("Entrada invalida");
						e.printStackTrace();
					}
				}
			}
			if (opcion == 7)
			{

			}
			if (opcion == 8)
			{

			}
			if (opcion == 9)
			{

			}
		}
	}
}
