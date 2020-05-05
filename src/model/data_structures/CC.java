package model.data_structures;

import java.util.Iterator;

/**
 * Clase que realiza calculo de componentes conexas
 * @param <K>
 */
public class CC<K extends Comparable<K>> {
	/**
	 * Esto es solo para poder guardar booleans como nodos
	 */
	private class Booleano {
		public boolean marked;
		Booleano(boolean b)
		{
			marked = b;
		}
	}

	/**
	 * Almacena los nodos e indica si estan marcados
	 */
	private Adyacencias<Booleano, K> marked;
	/**
	 * Almacena los nodos y el paso anterior a ellos en el recorrido de dfs
	 */
	private Adyacencias<K, K> edgeTo;
	/**
	 * Almacena los nodos e indica a que componente conexa pertenecen
	 */
	private Adyacencias<Integer, K> id;
	/**
	 * El numero de componentes conexas encontradas
	 */
	private int count;

	/**
	 * Constructor
	 */
	public CC() {
		marked = new Adyacencias<>(2);
		edgeTo = new Adyacencias<>(2);
	}
	/**
	 * Calcula las componentes conexas, retorna el numero de componentes encontradas
	 * @param G el grafo
	 * @return el numero de componentes conexas en el grafo
	 * @throws Exception 
	 */
	public int cc(Graph G) throws Exception
	{
		count = 0;
		marked = new Adyacencias<>(2);
		edgeTo = new Adyacencias<>(2);
		Iterator<K> it = G.darNodos();
		while(it.hasNext())
		{
			K actual = it.next();
			if(marked.get(actual) == null || !marked.get(actual).marked)
			{
				dfsComponentesConexas(G,actual);
				count++;
			}
		}
		return count;
	}
	/**
	 * Realiza dfs desde el vertice v
	 * @param G grafo
	 * @param v vertice donde incia la busqueda
	 * @throws Exception
	 */
	private void dfsComponentesConexas(Graph G, K v)throws Exception {
		marked.agregarVertice(v, new Booleano(true));
		id.agregarVertice(v, count);
		for (K adyacente : (Iterable<K>) G.adj(v))
			if (marked.get(v) == null || !marked.get(adyacente).marked) {
				edgeTo.agregarVertice(v, adyacente);
				dfsComponentesConexas(G, adyacente);
			}
	}
}
