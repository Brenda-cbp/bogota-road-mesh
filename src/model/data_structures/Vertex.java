package model.data_structures;

public class Vertex<V, K extends Comparable<K>>
{
	
	/**
	 * La informacion del vertice
	 */
	private V infoVertex;
	/**
	 * Llave que identifica el vertice
	 */
	private K key;
	/**
	 * Indica si cierto recorrido ya paso por ese nodo
	 */
	private boolean checked;
	/**
	 * Lista de enlaces a los nodos adyacentes del grafo
	 */
	private Lista<Edges> adyacentes;

	/**
	 * Constructor del vertice
	 * @param info
	 * @param key
	 */
	public Vertex(V info, K key)
	{
		this.key = key;
		infoVertex = info;
		checked = false;
		adyacentes = new Lista<>();

	}
	/**
	 * Retorna la lista de enlaces hacia los adyacentes
	 * @return lista de adyacencia
	 */
	public Lista<Edges> darAdyacentes()
	{
		return adyacentes;
	}
	/**
	 * adiciona un nuevo enlace a la lista de adyacencia
	 * @param edge el enlace
	 */
	public void addEdge(Edges edge)
	{
		adyacentes.agregarAlFinal(edge);
	}
	/**
	 * retorna la informacion del vertice
	 * @return infoVertex
	 */
	public V darInfo()
	{
		return infoVertex;
	}
	/**
	 * Sobreescribe la informacion del vertice
	 * @param info la nueva informacion del vertice
	 */
	public void setInfoVertex(V info)
	{
		infoVertex = info;
	}
	/**
	 * Pone el valor de chek en true
	 */
	public void check()
	{
		checked = true;
	}
	/**
	 * Pone el valor de check en false
	 */
	public void unCheck()
	{
		checked = false;
	}
	/**
	 * Indica si el valor de check es true o false
	 * @return checked
	 */
	public boolean isChecked()
	{
		return checked;
	}
	/**
	 * retorna la llave del vartice
	 * @return llave
	 */
	public K darLLave()
	{
		return key;
	}

}
