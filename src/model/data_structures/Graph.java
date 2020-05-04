package model.data_structures;

import java.util.Iterator;

import com.sun.javafx.geom.Edge;

import model.data_structures.TablaHashChaining.IteradorTabla;

// tomado de https://algs4.cs.princeton.edu/41graph/Graph.java.html
/**
 * Clase que representa un grafo no dirigido
 * 
 * @param <K>
 *            El tipo de dato que representa los identificadores de los vertices
 */
public class Graph<V,K extends Comparable<K>> {
	protected class Adyacencias<K extends Comparable<K>> implements Iterable<K> {

		/**
		 * La tabla de hash
		 */
		private HashNode[] tabla;
		/**
		 * La cantidad de posiciones que tiene la tabla
		 */
		private int M;
		/**
		 * La cantidad de llaves en la tabla
		 */
		private int N;
		/**
		 * el nodo actual
		 */
		private HashNode<Vertex, K> nodoActual;
		private int indiceActual;
		/**
		 * cantidad de rehashes
		 */
		private int rehash;

		/**
		 * Crea una nueva tabla de hash con capacidad inicial m
		 * 
		 * @param m
		 *            la capacidad inicial de la tabla
		 */
		public Adyacencias(int m) {
			tabla = new HashNode[m];
			M = m;
			rehash = 0;
		}

		/**
		 * retorna la cantidad de posiciones el arreglo tabla
		 * 
		 * @return M
		 */
		public int darTamaño() {
			return M;
		}

		/**
		 * retorna la cantidad de llaves almacenadas
		 * 
		 * @return M
		 */
		public int darCantidadLLaves() {
			return N;
		}

		/**
		 * retorna la cantidad de rehashes
		 * 
		 * @return rehash
		 */
		public int darRehashes() {
			return rehash;
		}

		/**
		 * Agrega el valor que entra por parametro
		 * 
		 * @param key
		 * @param value
		 * @throws Exception
		 *             si el valor o la llave es null
		 */
		public void agregarVertice(K key, Vertex value) throws Exception {
			if (value == null)
				throw new Exception("El valor no puede ser null");
			if (key == null)
				throw new Exception("La llave no debe ser null");
			int pos = hash(key);
			HashNode<Vertex, K> nuevo = new HashNode<Vertex, K>(key, value);
			if (tabla[pos] == null) {
				tabla[pos] = nuevo;
				N++;
			} else {
				HashNode<Vertex, K> actual = tabla[pos];
				while (actual != null && !actual.darLlave().equals(key)) {
					actual = actual.darSiguiente();
				}
				if (actual != null && actual.darLlave().equals(key)) {
					actual.cambiarValor(value);
				} else {
					nuevo.asignarSiguiente(tabla[pos]);
					tabla[pos] = nuevo;
					N++;
					if (N / M >= 5)
						rehash();
				}
			}
		}
		public boolean agregarEnlace(K idVertexIni, K idVertexFin, double cost)
		{
			Edges e1 = new Edges(idVertexIni, idVertexFin, cost);
			Edges e2 = new Edges(idVertexIni, idVertexFin, cost);
			if ((get(idVertexIni) == null || idVertexFin == null || get(idVertexIni).darAdyacentes().buscar(e1) != null))
				return false;
			get(idVertexIni).addEdge(e1);
			get(idVertexFin).addEdge(e2);
			return true;
		}
		/**
		 * retorna el valor asociado a una llave;
		 * 
		 * @param key
		 *            la llave buscada
		 * @return valores asociados a la llave
		 */
		public Vertex get(K key) {
			int i = hash(key);
			for (HashNode<Vertex, K> actual = tabla[i]; actual != null; actual = actual.darSiguiente()) {
				if (key.equals(actual.darLlave()))
					return actual.darValor();
			}
			return null;

		}

		/**
		 * elimina todos los valores asociados a una llvae
		 * 
		 * @param key
		 *            la llave buscada
		 * @return Lista con los valores eliminados
		 */
		public Vertex eliminar(K key) {
			int i = hash(key);
			HashNode<Vertex, K> actual = tabla[i];
			if (actual != null && key.equals(actual.darLlave())) {
				tabla[i] = actual.darSiguiente();
				N--;
				if (N / M <= 2)
					rehash();
				return actual.darValor();
			}
			for (; actual != null; actual = actual.darSiguiente()) {
				if (actual.darSiguiente() != null && key.equals(actual.darSiguiente().darLlave())) {
					Vertex retorno = (Vertex) actual.darSiguiente().darValor();
					actual.borrarSiguiente();
					N--;
					if (N / M <= 2)
						rehash();
					return retorno;
				}
			}
			return null;
		}

		/**
		 * Cambia el tamaño de la lista segun sea necesario
		 */
		public void rehash() {
			rehash++;
			int valor = M / 2;

			if (N / M >= 5) {
				valor = M * 2 + 1;
			}
			if (valor % 3 == 0)
				valor += 2;
			else if (valor % 3 == 1)
				valor += 1;
			if (valor % 5 == 0 && valor != 5)
				valor += 6;

			HashNode[] anterior = tabla;
			M = valor;
			N = 0;
			tabla = new HashNode[M];
			for (int i = 0; i < anterior.length; i++) {
				HashNode<Vertex, K> actual = anterior[i];
				while (actual != null) {
					try {
						agregarVertice(actual.darLlave(), actual.darValor());
						actual = actual.darSiguiente();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		public int hash(K key) {
			return (key.hashCode() & 0x7fffffff) % M;
		}

		@Override
		public Iterator<K> iterator() {
			return new IteradorTabla();
		}
		public void unCheck()
		{
			for(int i = 0; i < tabla.length; i++)
			{
				HashNode<Vertex, K> actual = tabla[i];
				while(actual != null)
				{
					actual.darValor().unCheck();
					actual = actual.darSiguiente();
				}
			}
		}

		protected class IteradorTabla implements Iterator<K> {
			public IteradorTabla() {
				indiceActual = -1;
				nodoActual = new HashNode<Vertex, K>(null, null);
			}

			public boolean hasNext() {
				if (nodoActual == null || darSiguienteLLave() == null)
					return false;
				return darSiguienteLLave() != null;
			}

			public K next() {
				if (!hasNext())
					return null;
				return darSiguienteLLave();
			}

			public void remove() {
			}

			public K darSiguienteLLave() {
				if (nodoActual != null && nodoActual.darSiguiente() != null) {
					nodoActual = nodoActual.darSiguiente();
					return nodoActual.darLlave();
				} else {
					nodoActual = tabla[++indiceActual];
					while (nodoActual == null && indiceActual < tabla.length) {
						indiceActual++;
						if (indiceActual < tabla.length)
							nodoActual = tabla[indiceActual];
					}
					if (nodoActual == null)
						return null;
					else
						return nodoActual.darLlave();
				}
			}
		}

	}

	/**
	 * Lista de adyacencias del grafo
	 */
	private Adyacencias<K> adj;
	/**
	 * Catnidad de enlaces en el grafo
	 */
	private int E;

	/**
	 * Construye un nuevo grafo con capacidad para V vertices
	 * 
	 * @param n
	 *            cantidad de vertices en el grafo
	 * @throws Exception
	 *             Si la catnidad de vertices es negativa
	 */
	public Graph(int n) throws Exception {
		if (n < 0)
			throw new Exception("La cantidad de vartices debe ser mayor o igual a cero");
		this.E = 0;
		adj = new Adyacencias(n);
	}

	/**
	 * Retorna la cantidad de vertices en el grafo
	 * 
	 * @return catnidad de vertices
	 */
	public int V() {
		return adj.darCantidadLLaves();
	}

	/**
	 * Retorna el numero de enlaces en el grafo
	 * 
	 * @return numero enlaces
	 */
	public int E() {
		return E;
	}

	/**
	 * Agrega un nuevo enlace en el grafo NO dirigido
	 * 
	 * @param idVertexIni
	 *            identificador origen
	 * @param idVertexFin
	 *            identificador final
	 * @param cost
	 *            costo enlace
	 * @throws Exception
	 *             si algun identificador es null
	 */
	public void addEdge(K idVertexIni, K idVertexFin, double cost) throws Exception {
		if(adj.agregarEnlace(idVertexIni, idVertexFin, cost))
			E++;
		return;
	}

	public V getInfoVertex(K idVertex)
	{
		try
		{
			return (V) adj.get(idVertex).darInfo();
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	public void setInfoVertex(K idVertex,V infoVertex)
	{
		try
		{
			adj.get(idVertex).setInfoVertex(infoVertex);
		}
		catch(Exception e)
		{
			
		}
	}
	public double getCostArc(K idVertexIni, K idVertexFin)throws Exception
	{
		try
		{
			Iterator<Edges> it = adj.get(idVertexIni).darAdyacentes().iterator();
			while(it.hasNext())
			{
				Edges actual = it.next();
				if(actual.darDestino().equals(idVertexFin))
					return actual.darCosto();
			}
			throw new Exception();
		}
		catch(Exception e)
		{
			throw new Exception("No se encontro un enlace entre los nodos");
		}
		
	}
	public void setCostArc(K idVertexIni, K idVertexFin, double cost)throws Exception
	{
		try
		{
			Iterator<Edges> it = adj.get(idVertexIni).darAdyacentes().iterator();
			while(it.hasNext())
			{
				Edges actual = it.next();
				if(actual.darDestino().equals(idVertexFin))
					actual.cambiarCosto(cost);
			}
			throw new Exception();
		}
		catch(Exception e)
		{
			throw new Exception("No se encontro un enlace entre los nodos");
		}
		
	}
	public void addVertex(K idVertex, V infoVertex) throws Exception
	{
		Vertex<V, K> vertice = new Vertex<V, K>(infoVertex, idVertex);
		adj.agregarVertice(idVertex, vertice);
	}
	public Iterable <K> adj (K idVertex)
	{
		if(idVertex != null && adj.get(idVertex) != null)
		{
			Iterator<Edges> it = adj.get(idVertex).darAdyacentes().iterator();
			Lista<K> respuesta = new Lista<>();
			while(it.hasNext())
			{
				respuesta.agregarAlFinal((K) it.next().darDestino());
			}
			return respuesta;
		}
		return null;
	}
	public void uncheck() 
	{
		adj.unCheck();
	}
	
}
