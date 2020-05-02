package model.data_structures;
// tomado de https://algs4.cs.princeton.edu/41graph/Graph.java.html
/**
 * Clase que representa un grafo no dirigido
 * @param <K> El tipo de dato que representa los identificadores de los vertices
 */
public class Graph<K extends Comparable<K>> 
{
	/**
	 *Clase que reperesenta un enlace entre dos nods
	 */
	protected class Edges

	{
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
		 * @param orig llave nodo origen
		 * @param dest llave nodo destino
		 * @param cost costo
		 */
		public Edges(K orig, K dest, double cost)
		{
			origen = orig;
			destino = dest;
			costo = cost;
		}
		/**
		 * Retorna el costo del enlace
		 * @return costo
		 */
		public double darCosto()
		{
			return costo;
		}
		/**
		 * Retorna la llave del nodo origen
		 * @return llave origen
		 */
		public K darOrigen()
		{
			return origen;
		}
		/**
		 * Retrona la llave del nodo destino
		 * @return llave destino
		 */
		public K darDestino()
		{
			return destino;
		}
	}

	/**
	 * Lista de adyacencias del grafo
	 */
	private TablaHashChaining<Edges, K> adj;
	/**
	 * Catnidad de enlaces en el grafo
	 */
	private int E;
	/**
	 * Construye un nuevo grafo con capacidad para V vertices
	 * @param n cantidad de vertices en el grafo
	 * @throws Exception Si la catnidad de vertices es negativa
	 */
    public Graph(int n) throws Exception 
    {
        if (n < 0) throw new Exception("La cantidad de vartices debe ser mayor o igual a cero");
        this.E = 0;
        adj = new TablaHashChaining<>(n);
    }
    /**
     * Retorna la cantidad de vertices en el grafo
     * @return catnidad de vertices
     */
    public int V()
    {
    	return adj.darCantidadLLaves();
    }
    /**
     * Retorna el numero de enlaces en el grafo
     * @return numero enlaces
     */
    public int E()
    {
    	return E;
    }
    /**
     * Agrega un nuevo enlace en el grafo NO dirigido
     * @param idVertexIni identificador origen
     * @param idVertexFin identificador final
     * @param cost costo enlace
     * @throws Exception si algun identificador es null
     */
    public void addEdge(K idVertexIni, K idVertexFin, double cost) throws Exception
    {
    	Edges e1 = new Edges(idVertexIni, idVertexFin, cost);
    	Edges e2 = new Edges(idVertexFin, idVertexIni, cost);
    	if(adj.get(idVertexIni).buscar(e1) != null)
    		return;
    	adj.agregar(idVertexIni, e1);
    	adj.agregar(idVertexFin, e2);
    	E++;
    }
     	
    

    
    

}
