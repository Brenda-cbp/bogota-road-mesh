package model.data_structures;

/**
 * Clase que reperesenta un enlace entre dos nods
 */
public class Edges<K extends Comparable <K>> {
	/**
	 * LLave nodo origen
	 */
	private K origen;
	/**
	 * LLave nodo destino
	 */
	private K destino;
	/**
	 * Costo del enlace
	 */
	private double costo;

	/**
	 * Constructor de un enlace
	 * 
	 * @param orig
	 *            llave nodo origen
	 * @param dest
	 *            llave nodo destino
	 * @param cost
	 *            costo
	 */
	public Edges(K orig, K dest, double cost) {
		origen = orig;
		destino = dest;
		costo = cost;
	}

	/**
	 * Retorna el costo del enlace
	 * 
	 * @return costo
	 */
	public double darCosto() {
		return costo;
	}

	/**
	 * Retorna la llave del nodo origen
	 * 
	 * @return llave origen
	 */
	public K darOrigen() {
		return origen;
	}

	/**
	 * Retrona la llave del nodo destino
	 * 
	 * @return llave destino
	 */
	public K darDestino() {
		return destino;
	}
	public void cambiarCosto(double cost)
	{
		costo = cost;
	}
}