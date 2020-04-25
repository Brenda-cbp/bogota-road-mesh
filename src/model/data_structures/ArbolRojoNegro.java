package model.data_structures;

import java.util.Iterator;

//Implementacion basada en la solucion disponible en la pagina del libro guia: https://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
//Tambien usamos las diapositivas de la pagina del curso pero estas se basan en el libro.
public class ArbolRojoNegro<V, K extends Comparable<K>> implements Iterable<K>
{
	/**
	 * Representa el color rojo
	 */
	private static final boolean RED = true;
	/**
	 * Representa el color negro
	 */
	private static final boolean BLACK = false;

	/**
	 * La raiz del arbol
	 */
	private Node raiz;

	/**
	 * Clase que representa un nodo del arbol
	 *
	 */
	private class Node
	{
		/**
		 * La llave con la que se identifica el nodo
		 */
		K key;
		/**
		 * El valor que almacena el nodo
		 */
		Lista<V> val;
		/**
		 * El hijo izquierdo del nodo
		 */
		Node left;
		/**
		 * El hijo derecho del nodo
		 */
		Node right;
		/**
		 * El tamaño del subarbol que tiene al nodo como raiz
		 */
		int N;
		/**
		 * El color del enlace de un nodo con su padre
		 */
		boolean color;

		Node(K key, V val, int N, boolean color)
		{
			this.key = key;
			this.val = new Lista<V>();
			this.val.agregarAlFinal(val);
			this.N = N;
			this.color = color;
		}
	}

	/**
	 * Indica si el enlace de un nodo con su padre es rojo (i.e. el nodo es
	 * rojo)
	 * 
	 * @param x
	 *            el nodo
	 * @return true si es rojo false de lo contrario
	 */
	private boolean isRed(Node x)
	{
		if (x == null)
			return false;
		return x.color == RED;
	}

	/**
	 * Rotacion por la izquierda
	 * 
	 * @param h
	 * @return
	 */
	Node rotateLeft(Node h)
	{
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return x;
	}

	Node rotateRight(Node h)
	{
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = h.color;
		h.color = RED;
		x.N = h.N;
		h.N = 1 + size(h.left) + size(h.right);
		return x;
	}

	void flipColors(Node h)
	{
		h.color = RED;
		h.left.color = BLACK;
		h.right.color = BLACK;
	}

	/**
	 * Retorna el tamaño del arbol
	 * 
	 * @return total de nodos en el arbol
	 */
	public int size()
	{
		return size(raiz);
	}

	/**
	 * Retorn el tamaño del subarblo con raiz en el nodo que entra por parametro
	 * 
	 * @param nodo,
	 *            la raiz del subarbol
	 * @return el total de nodos en el subarbol
	 */
	private int size(Node nodo)
	{
		if (nodo == null)
			return 0;
		return nodo.N;
	}

	/**
	 * Inserta una nueva pareja llave,valor en el arbol
	 * 
	 * @param llave
	 *            la llave a insertar
	 * @param valor
	 *            el valor a insertar
	 * @throws Exception si el valor o la llave son null
	 */
	public void insertar(K llave, V valor) throws Exception
	{
		if(llave == null || valor == null)
			throw new Exception("El valor no puede ser null");
		raiz = insertar(raiz, llave, valor);
		raiz.color = BLACK;
	}

	/**
	 * Indica si el arbol esta vacio
	 * 
	 * @return true si no hay nodos en el arbol, false de lo contrario
	 */
	public boolean isEmpty()
	{
		return size() == 0;
	}

	/**
	 * Inserta una pareja llave valor en el subarbol con raiz h
	 * 
	 * @param h
	 *            la raiz del subarbol
	 * @param llave
	 *            llave a insertar
	 * @param valor
	 *            valor a insertar
	 * @return
	 */
	private Node insertar(Node h, K llave, V valor)
	{
		if (h == null)
			return new Node(llave, valor, 1, RED);

		if (llave.compareTo(h.key) < 0)
			h.left = insertar(h.left, llave, valor);
		else if (llave.compareTo(h.key) > 0)
			h.right = insertar(h.right, llave, valor);
		else
			h.val.agregarAlFinal(valor);;
		
		if (isRed(h.right) && !isRed(h.left))
			h = rotateLeft(h);
		if (isRed(h.left) && isRed(h.left.left))
			h = rotateRight(h);
		if (isRed(h.left) && isRed(h.right))
			flipColors(h);
		h.N = size(h.left) + size(h.right) + 1;

		return h;
	}

	/**
	 * Retorna el valor asociado a la llave buscada
	 * 
	 * @param key
	 *            llave buscada
	 * @return valor asociado a la llave
	 */
	public Lista<V> get(K key)
	{
		if (key == null)
			throw new IllegalArgumentException("argument to get() is null");
		return get(raiz, key);
	}

	private Lista<V> get(Node x, K key)
	{
		while (x != null)
		{
			if (key.compareTo(x.key) < 0)
				x = x.left;
			else if (key.compareTo(x.key) > 0)
				x = x.right;
			else
				return x.val;
		}
		return null;
	}

	/**
	 * Retorna la altura del arbol (numero de nodos en la rama mas large -1)
	 * 
	 * @return la altura del arbol
	 */
	public int height()
	{
		return height(raiz);
	}

	private int height(Node x)
	{
		if (x == null)
			return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

	/**
	 * Retorna la longitud del camino entre la raiz y el nodo con la llave dada
	 * (la altura del camino)
	 * 
	 * @param key
	 *            la llave buscada
	 * @return la altura, -1 si la llave no existe
	 * @throws Exception
	 *             si la llave es null
	 */
	public int getHeight(K key) throws Exception
	{
		if (key == null)
			throw new Exception("La llave es null");
		if (contains(key))
			return getHeight(raiz, key);
		return -1;
	}

	private int getHeight(Node x, K key)
	{
		if (x == null)
			return -1;
		int comp = key.compareTo(x.key);
		if (comp < 0)
			return 1 + getHeight(x.left, key);
		if (comp > 0)
			return 1 + getHeight(x.right, key);
		return 1;
	}

	/**
	 * Retorna la llave mas pequeña del arbol, null si el arbol es vacio
	 * 
	 * @return llave minima, null si no hay llaves en el arbol
	 */
	public K min()
	{
		if (raiz == null)
			return null;
		return min(raiz);
	}

	private K min(Node x)
	{
		if (x.left == null)
			return x.key;
		return min(x.left);
	}

	/**
	 * Retorna la llave mas grande del arbol, null si el arbol es vacio
	 * 
	 * @return llave maxima, null si no hay llaves en el arbol
	 */
	public K max()
	{
		if (raiz == null)
			return null;
		return max(raiz);
	}

	private K max(Node x)
	{
		if (x.right == null)
			return x.key;
		return max(x.right);
	}

	/**
	 * Indica si el arbol contiene la llave buscada
	 * 
	 * @param key
	 *            llave buscada
	 * @return true si la contiene false de lo contrario
	 */
	public boolean contains(K key)
	{
		return get(key) != null;
	}

	/**
	 * Valida si el arbol se encuentra balanceado, todas las ramas tienen el
	 * mismo numero de enlaces negros si todos los enlaces rojos estan a la
	 * izquierda, si esta ordenado
	 * 
	 * @return true si cumple todas las condiciones falso de lo contrario.
	 */
	public boolean check()
	{
		// Metodos tomados de la pagina del libro guia.
		if (esBinarioOrdenado() && esArbol23() && balanceado())
			return true;
		return false;
	}

	/**
	 * Verifica si es un arbol binario ordenado de busqueda
	 * 
	 * @return true si lo es falso si no
	 */
	private boolean esBinarioOrdenado()
	{
		return esBinarioOrdenado(raiz, null, null);
	}

	/**
	 * Verifica si el subarbol es binario ordenado, verificando que cada hijo
	 * este en el rango especificad
	 * 
	 * @param x
	 *            raiz del subarbol
	 * @param min
	 *            minimo del rango
	 * @param max
	 *            maximo del rango
	 * @return true si lo es, false si no
	 */
	private boolean esBinarioOrdenado(Node x, K min, K max)
	{
		if (x == null)
			return true;
		if (min != null && x.key.compareTo(min) <= 0)
			return false;
		if (max != null && x.key.compareTo(max) >= 0)
			return false;
		return esBinarioOrdenado(x.left, min, x.key) && esBinarioOrdenado(x.right, x.key, max);
	}

	/**
	 * verifica que el arbol tenga todos sus enlaces rojos izquierda, que no
	 * tenga 2 enlaces rojos seguidos y que cada rama tenga al menos un enlace
	 * negro.
	 * 
	 * @return true si cumple todo false si no
	 */
	private boolean esArbol23()
	{
		return esArbol23(raiz);
	}

	/**
	 * Verifica que el subArbol con raiz x sea 2-3
	 * 
	 * @param x
	 *            raiz del subarbol
	 * @return true si lo es
	 */
	private boolean esArbol23(Node x)
	{
		if (x == null)
			return true;
		else if (isRed(x.right))
			return false;
		else if (x != raiz && isRed(x) && isRed(x.left))
			return false;
		return esArbol23(x.left) && esArbol23(x.right);
	}

	/**
	 * verifica que cada rama tenga el mismo numero de enlaces negros
	 * 
	 * @return true si cumple, false si no
	 */
	private boolean balanceado()
	{
		int negro = 0;
		Node x = raiz;
		while (x != null)
		{
			if (isRed(x) == false)
				negro++;
			x = x.left;
		}
		return balanceado(raiz, negro);
	}

	/**
	 * Verifica que el subarbol sea balanceado
	 * 
	 * @param x
	 *            la raiz del subarbol
	 * @param black
	 *            contador de enlaces negros
	 * @return true si es balanceado
	 */
	private boolean balanceado(Node x, int black)
	{
		if (x == null && black == 0)
			return true;
		if (x == null)
			return false;
		if (isRed(x) == false)
			black--;
		return balanceado(x.left, black) && balanceado(x.right, black);
	}

	@Override
	/**
	 * Retorna un nuevo iterador sobre las llaves de todo el arbol
	 */
	public Iterator<K> iterator()
	{
		return new IteradorArbol(null, null);
	}

	/**
	 * Retorna un iterador sobre las llaves del arbol en un rango dado
	 * 
	 * @param key1
	 *            limite inferior si es null se asume que no hay cota inferior
	 * @param key2
	 *            limite superior si es null se asume que no hay cota superior
	 * @return iterador de las llaves
	 */
	public Iterator<K> keysInrange(K key1, K key2)
	{
		return new IteradorArbol(key1, key2);
	}

	/**
	 * Retorna un iterador sobre los valores del arbol asociados a un rango de
	 * llaves dado
	 * 
	 * @param key1
	 *            limite inferior si es null se asume que no hay cota inferior
	 * @param key2
	 *            limite superior si es null se asume que no hay cota superior
	 * @return iterador de las llaves
	 */
	public Lista<V> valuesInRange(K key1, K key2)
	{
		Iterator<K> it = keysInrange(key1, key2);
		Lista<V> lista = new Lista<>();
		while (it.hasNext())
		{
			Iterator<V> it2 = (get(it.next())).iterator();
			while(it2.hasNext())
				lista.agregarAlFinal(it2.next());
		}
		return lista;
	}

	/**
	 * Clase que representa un iterador sobre las llaves del arbol en un rango
	 * dado
	 */
	protected class IteradorArbol implements Iterator<K>
	{

		private Lista<K> lista;
		Iterator<K> iterador;

		public IteradorArbol(K key1, K key2)
		{
			lista = new Lista<>();
			darListadeLLaves(key1, key2);
			iterador = lista.iterator();
		}

		@Override
		public boolean hasNext()
		{
			return iterador.hasNext();
		}

		@Override
		public K next()
		{
			return iterador.next();
		}

		/**
		 * Retorna las llaves del arbol en el rango dado si alguno de los
		 * limites superior o inferior es null se asume que no existe cota
		 * superior o inferior, respectivamente
		 * 
		 * @param key1
		 *            el limite inferior
		 * @param key2
		 *            el limite superior
		 */
		public void darListadeLLaves(K key1, K key2)
		{
			darListadeLLaves(key1, key2, raiz);
		}

		/**
		 * Retorna las llaves del subarbol en el rango si algun limite es null
		 * se asume que no hay limite Se retornaran ordenadas
		 * 
		 * @param key1
		 *            limite inferior
		 * @param key2
		 *            limite superior
		 * @param x
		 *            raiz del subarbol
		 */
		public void darListadeLLaves(K key1, K key2, Node x)
		{
			if (x == null)
				return;

			if (key1 == null && key2 == null)
			{
				darListadeLLaves(key1, key2, x.right);
				lista.agregarAlFinal(x.key);
				darListadeLLaves(key1, key2, x.left);
			}
			else if (key1 == null)
			{
				int comp = key2.compareTo(x.key);

				darListadeLLaves(key1, key2, x.left);
				if (comp >= 0)
					lista.agregarAlFinal(x.key);
				if (comp > 0)
					darListadeLLaves(key1, key2, x.right);
			}
			else if (key2 == null)
			{
				int comp = key1.compareTo(x.key);

				if (comp < 0)
					darListadeLLaves(key1, key2, x.left);
				if (comp <= 0)
				{
					lista.agregarAlFinal(x.key);
				}
				darListadeLLaves(key1, key2, x.right);
			}
			else
			{
				int comp1 = key1.compareTo(x.key);
				int comp2 = key2.compareTo(x.key);

				if (comp1 < 0)
					darListadeLLaves(key1, key2, x.left);
				if (comp1 <= 0 && comp2 >= 0)
					lista.agregarAlFinal(x.key);
				if (comp2 > 0)
					darListadeLLaves(key1, key2, x.right);

			}
		}

	}

	public Lista<Integer> darAlturaHojas()
	{
		Lista<Integer> hojas = new Lista<>();
		Lista<Node> nivel = new Lista<>();
		nivel.agregarAlFinal(raiz);
		return darHojas(nivel, hojas);
	}
	private Lista<Integer> darHojas(Lista<Node> nivel, Lista<Integer> hojas)
	{
		Lista<Node> nueva = new Lista<>();
		if(nivel.darTamaño() == 0)
			return hojas;
		for (Node node : nivel)
		{
			if(node.left != null)
				nueva.agregarAlFinal(node.left);
			if(node.right != null)
				nueva.agregarAlFinal(node.right);
			if(node.left == null && node.right == null)
				try
				{
					hojas.agregarAlFinal(getHeight(node.key));
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		nivel = null;
		return darHojas(nueva, hojas);
	}
	/*public void print()
	{
		System.out.println(raiz.key);
		ArrayList<Node> cajita = new ArrayList<>();
		cajita.add(raiz);
		println(cajita, 0);
	}

	private void println(ArrayList<Node> x, int i)
	{
		if(i < 6)
		{
			ArrayList<Node> y = new ArrayList<>();
			System.out.println();
			for(int k = 0; k < 30 - 5*i; k++)
				System.out.print(" ");
			for (Node o : x)
			{
				System.out.print(o.key + "     ");
				if(o.left != null)
					y.add(o.left);
				if(o.right != null)
					y.add(o.right);
				
			}
			x = null;
			println(y, ++i);
		}
	}*/
}
