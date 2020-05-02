package model.data_structures;
// tomado de https://algs4.cs.princeton.edu/41graph/Graph.java.html
public class Graph<K> {

	protected class listaAdjacent{
		/**
		 * Capacidad maxima del arreglo
		 */
		private int tamanoMax;
		/**
		 * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
		 */
		private int tamanoAct;
		/**
		 * Arreglo de elementos de tamaNo maximo
		 */
		private Lista<K> elementos;

		/**
		 * Construir un arreglo con la capacidad maxima inicial.
		 * @param max Capacidad maxima inicial
		 */
		public  listaAdjacent( int max )
		{
			elementos = new Lista<K>();

			tamanoMax = max;
			//El 0 siempre es vacio en el heap
			tamanoAct = 1;
		}

		public void agregar( K dato )
		{
			if ( tamanoAct == tamanoMax )
			{  // caso de arreglo lleno (aumentar tamaNo)
				tamanoMax = 2 * tamanoMax;
				Lista<K> copia = elementos;
	            //elementos = new T [tamanoMax];
	            elementos =new Lista<K>();
	            for ( int i = 1; i < tamanoAct; i++)
	            {
	             	 elementos[i] = copia[i];
	            } 
	    	   // System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
	       }	
	       elementos[tamanoAct] = dato;
	       tamanoAct++;
		}
		public int darCapacidad() {
			return tamanoMax;
		}

		public int darTamano() {
			return tamanoAct;
		}

		public K darElemento(int i) {
			// TODO HECHO implementar
			return (K) elementos[i];
		}

		public K buscar(K dato) {
			// TODO HECHO implementar
			// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
			for (int i =0; i<tamanoMax; i++)
			{
				if (elementos[i].compareTo(dato)==0)
					return dato;
			}
			return null;
		}

		public K eliminar(K dato) {
			// TODO HECHO implementar
			// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
		K guardar=null;
			boolean encontrado = false;
			for (int i =0; i<tamanoMax && !encontrado; i++)
			{
				if (elementos[i].compareTo(dato)==0)
				{
					encontrado=true; 
					guardar=elementos[i];	
					for (int j=0; j<tamanoMax-1;j++)
					{
						elementos[i]=elementos[j];
					}
				}
			}
			return guardar;

		}
		/**
		 * Trate de eliminar solo el ultimo 
		 * podria haber fallas
		 * @param pos
		 * @return eliminado
		 */
		public K eliminarPosicion(int pos)
		{
			K guardar = elementos[pos];
			elementos[pos] = null;
			tamanoAct--;
			return guardar;
			
		}
		public void exchange(int i, int j)
		{
			K t = elementos[i];
			elementos[i] = elementos[j];
			elementos[j] = t;
		}
		public String toString()
		{
			String respuesta = "";
			for(int i = 1; i < tamanoAct; i++)
			{
				respuesta = respuesta + elementos[i] + "\n" + "\n";

			}
			return respuesta;
		}
		
	}
	private final int V;
	private int E;
	private ArregloDinamico<Lista<K>> adj;

	public Graph(int V) {
		if (V < 0) throw new IllegalArgumentException("El número de vértices no puede ser negativo");
		this.V = V;
		this.E = 0;
		adj = (Lista<K>[]) new Lista[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Lista<K>();
		}
	}  
	public int V() {
		return V;
	}
	public int E() {
		return E;
	}
	private void validarVertices(int v) throws Exception {
		if (v < 0 || v >= V)
			throw new Exception ("El vertice " + v + " no está entre cero y " + (V-1));
	} 
	public void addEdge(K idVertexIni, K idVertexFin, double cost) {
		
		E++;
		adj[v].add(w);
		adj[w].add(v);

	}
}
