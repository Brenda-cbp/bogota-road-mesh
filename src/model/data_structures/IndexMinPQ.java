package model.data_structures;

import java.util.Iterator;

//Tomado del libro guia disponible en: https://algs4.cs.princeton.edu/
//Enlace a la implementacion de esta clase: https://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html
public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer> {
	/**
	 * Capacidad maxima en la cola
	 */
	private int maxN;   
	/**
	 * catidad de elementos en la cola
	 */
	private int n;      
	/**
	 * El heap en el que se almacena todo
	 */
	private int[] pq;    
	/**
	 * "funcion inversa de pq" es decir qp(pq(i)) = i
	 */
	private int[] qp;        
	private Key[] keys;      // keys[i] = priority of i

	/**
	 * Crea una nueva cola con indices entre = y maxN -1
	 * @param  maxN capacidad maxima del arreglo
	 * @throws IllegalArgumentException si MaxN es menor que 0
	 */
	public IndexMinPQ(int maxN) {
		if (maxN < 0) throw new IllegalArgumentException();
		this.maxN = maxN;
		n = 0;
		keys = (Key[]) new Comparable[maxN + 1];   
		pq   = new int[maxN + 1];
		qp   = new int[maxN + 1];                 
		for (int i = 0; i <= maxN; i++)
			qp[i] = -1;
	}

	/**
	 * Indica si la cola es vacia
	 * @return true si es vacia
	 */
	public boolean isEmpty() {
		return n == 0;
	}

	/**
	 * Retorna si la cola contiene un elemento con indice i
	 * @param i indice buscado
	 * @return true si lo contiene
	 */
	public boolean contains(int i) {
		validateIndex(i);
		return qp[i] != -1;
	}

	/**
	 * Retorna el nunmero de llaves en la cola
	 * @return
	 */
	public int size() {
		return n;
	}

	/**
	 * asocia la llave dada con el indice i
	 * @param  i el indice
	 * @param  la llave
	 * @throws IllegalArgumentException si i no se encuentra en [0,maxN)
	 * @throws IllegalArgumentException si ya existe alguien asociado al indice i
	 */
	public void insert(int i, Key key) {
		validateIndex(i);
		if (contains(i)) throw new IllegalArgumentException("index is already in the priority queue");
		n++;
		qp[i] = n;
		pq[n] = i;
		keys[i] = key;
		swim(n);
	}

	/**
	 * Retorna el indice asociado a la menor llave
	 * @return indice asociado a la menor lllave
	 * @throws Exception si la cola es vacia
	 */
	public int minIndex() throws Exception {
		if (n == 0) throw new Exception("Cola vacia");
		return pq[1];
	}

	/**
	 * Retorna una llave minimal
	 */
	public Key minKey() throws Exception {
		if (n == 0) throw new Exception("La cola es vacia");
		return keys[pq[1]];
	}

	/**
	 * Remueve la llave minima y retorna su indice asociado
	 * @throws Exception si la cola es vacia
	 */
	public int delMin() throws Exception {
		if (n == 0) throw new Exception("Cola vacia");
		int min = pq[1];
		exch(1, n--);
		sink(1);
		assert min == pq[n+1];
		qp[min] = -1;        // delete
		keys[min] = null;    // to help with garbage collection
		pq[n+1] = -1;        // not needed
		return min;
	}

	/**
	 * retorna la llave asociada al indice i
	 * @param  i el indice
	 * @return la llave del indice i
	 */
	public Key keyOf(int i) {
		validateIndex(i);
		if (!contains(i)) return null;
		else return keys[i];
	}

	/**
	 * cambia la llave asociada al elemento con idice i
	 *
	 * @param  el indice de la llave a cambiar
	 * @param  la nueva llave
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @throws NoSuchElementException no key is associated with index {@code i}
	 */
	public void changeKey(int i, Key key) {
		validateIndex(i);
		if (!contains(i)) return;
		keys[i] = key;
		swim(qp[i]);
		sink(qp[i]);
	}

	/**
	 * cambia la llave asociada al elemento con idice i
	 * @param  i the index of the key to change
	 * @param  key change the key associated with index {@code i} to this key
	 * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
	 * @deprecated Replaced by {@code changeKey(int, Key)}.
	 */
	@Deprecated
	public void change(int i, Key key) {
		changeKey(i, key);
	}

	public void delete(int i) {
		validateIndex(i);
		if (!contains(i)) return;
		int index = qp[i];
		exch(index, n--);
		swim(index);
		sink(index);
		keys[i] = null;
		qp[i] = -1;
	}

	private void validateIndex(int i) {
		if (i < 0) throw new IllegalArgumentException("index is negative: " + i);
		if (i >= maxN) throw new IllegalArgumentException("index >= capacity: " + i);
	}

	/**
	 * Retorna el si i es mayor que j
	 * @param i llave1
	 * @param j llave2
	 * @return True si es mayor
	 */
	private boolean greater(int i, int j) {
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}

	/**
	 * Intercambia dos llaves en el heap
	 * @param i llave1
	 * @param j llave2
	 */
	private void exch(int i, int j) {
		int swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}

	/**
	 * Swim igual al binary heap
	 * @param k la llave
	 */
	private void swim(int k) {
		while (k > 1 && greater(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}
	/**
	 * Sink igual al binary heap
	 * @param k la llave
	 */
	private void sink(int k) {
		while (2*k <= n) {
			int j = 2*k;
			if (j < n && greater(j, j+1)) j++;
			if (!greater(k, j)) break;
			exch(k, j);
			k = j;
		}
	}


	/**
	 * Returns an iterator that iterates over the keys on the
	 * priority queue in ascending order.
	 * The iterator doesn't implement {@code remove()} since it's optional.
	 *
	 * @return an iterator that iterates over the keys in ascending order
	 */
	public Iterator<Integer> iterator() { return new HeapIterator(); }

	private class HeapIterator implements Iterator<Integer> {

		private IndexMinPQ<Key> copy;

		public HeapIterator() {
			copy = new IndexMinPQ<Key>(pq.length - 1);
			for (int i = 1; i <= n; i++)
				copy.insert(pq[i], keys[pq[i]]);
		}

		public boolean hasNext()  { return !copy.isEmpty();                     }
		public void remove()      { throw new UnsupportedOperationException();  }

		public Integer next() {
			if (!hasNext()) return null;
			try {
				return copy.delMin();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}