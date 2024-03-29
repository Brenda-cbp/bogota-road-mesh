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
	 * Costo del enlace por distancia haversiana 
	 */
	private double costo; 
	/**
	 * Costo por total de comparendos 
	 */
	private int costo2;

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
	public Edges(K orig, K dest, double cost, int costo2) {
		origen = orig;
		destino = dest;
		costo = cost;//distancia haversiana
		this.costo2 = costo2; //total de comparendos
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
	
	public int darcosto2()
	{
		return costo2;
	}
	public K other(K llave)
	{
		if(llave.equals(origen))
			return destino;
		return origen;
	}
}
