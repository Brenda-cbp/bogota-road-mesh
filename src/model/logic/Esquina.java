package model.logic;

import java.util.Iterator;

import model.data_structures.Lista;

/**
 * Clase que representa una esquina en la malla vial
 */
public class Esquina 
{
	/**
	 * Identificador de la esquina
	 */
	private int ID;
	/**
	 * Latitud geografica de la esquina
	 */
	private double latitud;
	/**
	 * Longitud geografica de la esquina
	 */
	private double longitud;
	
	private Lista<Comparendo> comparendos;
	
	
	private Lista<EstacionPolicia> estacionesPolicia;
	
	private boolean importante;
	
	/**
	 * Crea una nueva esquina con la informacion dada
	 * @param id el identificador
	 * @param lat la latitud
	 * @param longit la longitud
	 */
	public Esquina(int id, double lat, double longit)
	{
		ID = id;
		latitud = lat;
		longitud = longit;
		comparendos = new Lista<>();
		estacionesPolicia = new Lista<>();
		importante = false;
	}
	
	public boolean tieneEstacion()
	{
		return estacionesPolicia.darTamaño() != 0;
	}
	public Lista<EstacionPolicia> darEstaciones()
	{
		return estacionesPolicia;
	}
	public void agregarEstacion(EstacionPolicia e)
	{
		estacionesPolicia.agregarAlFinal(e);
	}
	public int darId()
	{
		return ID;
	}
	public double darLatitud()
	{
		return latitud;
	}
	public double darLongitud()
	{
		return longitud;
	}
	public void agregarComparendo(Comparendo c)
	{
		comparendos.agregarAlFinal(c);
	}
	public Lista<Comparendo> darLista()
	{
		return comparendos;
	}
	public boolean esImportante()
	{
		return importante;
	}
	public void marcarImportante(boolean importante)
	{
		this.importante = importante;
	}
	public void imprimirMasdistancia()
	{
		Iterator<Comparendo> it = comparendos.iterator();
		while(it.hasNext())
		{
			Comparendo act = it.next();
			double dist = DistanciaHaversiana.distance(latitud, longitud, act.darLatitud(), act.darLongitud());
			if(dist>7)
				System.out.println(dist);
		}
	}
}
