package model.data_structures;

import java.util.Iterator;

public class Adyacencias<W,K extends Comparable<K>> implements Iterable<K> {

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
	private HashNode<W, K> nodoActual;
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
	protected void agregarVertice(K key, W value) throws Exception {
		if (value == null)
			throw new Exception("El valor no puede ser null");
		if (key == null)
			throw new Exception("La llave no debe ser null");
		int pos = hash(key);
		HashNode<W, K> nuevo = new HashNode<W, K>(key, value);
		if (tabla[pos] == null) {
			tabla[pos] = nuevo;
			N++;
		} else {
			HashNode<W, K> actual = tabla[pos];
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
	protected boolean agregarEnlace(K idVertexIni, K idVertexFin, double cost)
	{
		Edges e1 = new Edges(idVertexIni, idVertexFin, cost);
		Edges e2 = new Edges(idVertexFin, idVertexIni, cost);
		if ((get(idVertexIni) == null || get(idVertexFin) == null || ((Vertex) get(idVertexIni)).darAdyacentes().buscar(e1) != null))
			return false;
		((Vertex) get(idVertexIni)).addEdge(e1);
		((Vertex) get(idVertexFin)).addEdge(e2);
		return true;
	}
	/**
	 * retorna el valor asociado a una llave;
	 * 
	 * @param key
	 *            la llave buscada
	 * @return valores asociados a la llave
	 */
	public W get(K key) {
		int i = hash(key);
		for (HashNode<W, K> actual = tabla[i]; actual != null; actual = actual.darSiguiente()) {
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
	protected W eliminar(K key) {
		int i = hash(key);
		HashNode<W, K> actual = tabla[i];
		if (actual != null && key.equals(actual.darLlave())) {
			tabla[i] = actual.darSiguiente();
			N--;
			if (N / M <= 2)
				rehash();
			return actual.darValor();
		}
		for (; actual != null; actual = actual.darSiguiente()) {
			if (actual.darSiguiente() != null && key.equals(actual.darSiguiente().darLlave())) {
				W retorno = (W) actual.darSiguiente().darValor();
				actual.borrarSiguiente();
				N--;
				if (N / M <= 2)
					rehash();
				return retorno;
			}
		}
		return null;
	}
	public Iterator<K> darLlaves(){
		Lista<K> rta= new Lista<K> ();
		for (int i = 0; i < M-1; i++) {
			
			HashNode actual= tabla[i];
			while (actual!=null)
			{
				rta.agregarAlFinal((K) actual.darLlave());
				actual= actual.darSiguiente();
			}
		}
		return rta.iterator();
	}

	/**
	 * Cambia el tamaño de la lista segun sea necesario
	 */
	private void rehash() {
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
			HashNode<W, K> actual = anterior[i];
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
	public HashNode<W,K> nodoPorLlave (K key ){
		if (key !=null) {
			int i = hash(key);
			for (HashNode<W, K> actual = tabla[i]; actual != null; actual = actual.darSiguiente()) {
				if (key.equals(actual.darLlave()))
					return actual;
			}
		}
		return null;
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
			nodoActual = new HashNode<W, K>(null, null);
		}

		public boolean hasNext() {
			nodoActual=nodoPorLlave(darSiguienteLLave());
			return nodoActual!=null;
		}

		public K next() {
			return nodoActual.darLlave();
		}

		public void remove() {
		}

		public K darSiguienteLLave() {
			if (nodoActual != null && nodoActual.darSiguiente() != null) {
				nodoActual = nodoActual.darSiguiente();
				return nodoActual.darLlave();
			} else if (indiceActual+1 < tabla.length) {
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
			else
				return null;
		}
	}

}
