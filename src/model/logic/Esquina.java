package model.logic;

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
}
