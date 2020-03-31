package model.data_structures;

import java.util.Iterator;

import model.data_structures.Lista.IteradorLista;

public class TablaHashChaining<V, K extends Comparable<K>> implements Iterable<K>
{

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
	private HashNode<Lista<V>, K> nodoActual;
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
	public TablaHashChaining(int m)
	{
		tabla = new HashNode[m];
		M = m;
		rehash = 0;
	}
	/**
	 * retorna la cantidad de posiciones el arreglo tabla
	 * @return M
	 */
	public int darTamaño()
	{
		return M;
	}
	/**
	 * retorna la cantidad de llaves almacenadas
	 * @return M
	 */
	public int darCantidadLLaves()
	{
		return N;
	}
	/**
	 * retorna la cantidad de rehashes
	 * @return rehash
	 */
	public int darRehashes()
	{
		return rehash;
	}
	/**
	 * Agrega el valor que entra por parametro 
	 * @param key
	 * @param value
	 * @throws Exception si el valor o la llave es null
	 */
	public void agregar(K key, V value) throws Exception
	{
		if (value == null)
			throw new Exception("El valor no puede ser null");
		if(key == null)
			throw new Exception("La llave no debe ser null");
		Lista<V> lista = new Lista<>();
		lista.agregarAlFinal(value);
		HashNode<Lista<V>, K> nuevo = new HashNode<Lista<V>, K>(key, lista);
		int pos = hash(key);
		if (tabla[pos] == null)
		{
			tabla[pos] = nuevo;
			N++;
		}
		else
		{
			HashNode<Lista<V>, K> actual = tabla[pos];
			while (actual != null && actual.darLlave() != key)
			{
				actual = actual.darSiguiente();
			}
			if (actual != null && actual.darLlave().equals(key))
			{
				actual.darValor().agregarAlFinal(value);
			}
			else
			{
				nuevo.asignarSiguiente(tabla[pos]);
				tabla[pos] = nuevo;
				N++;
				if (N / M >= 5)
					rehash();
			}

		}
	}
	/**
	 * retorna una lista de los valores asociados a una llave;
	 * @param key la llave buscada
	 * @return valores asociados a la llave
	 */
	public Lista<V> get(K key)
	{
		int i = hash(key);
		for (HashNode<Lista<V>, K> actual = tabla[i]; actual != null; actual = actual.darSiguiente())
			if (key.equals(actual.darLlave()))
				return actual.darValor();
		return null;

	}
	/**
	 * elimina todos los valores asociados a una llvae
	 * @param key la llave buscada
	 * @return Lista con los valores eliminados
	 */
	public Lista<V> eliminar(K key)
	{
		int i = hash(key);
		HashNode<Lista<V>, K> actual = tabla[i];
		if (actual != null && key.equals(actual.darLlave()))
		{
			tabla[i] = actual.darSiguiente();
			N--;
			if (N / M <= 2)
				rehash();
			return actual.darValor();
		}
		for (; actual != null; actual = actual.darSiguiente())
		{
			if (actual.darSiguiente() != null && key.equals(actual.darSiguiente().darLlave()))
			{
				Lista<V> retorno = (Lista<V>) actual.darSiguiente().darValor();
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
	public void rehash()
	{
		rehash++;
		int valor = M / 2;

		if (N / M >= 5)
		{
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
		for (int i = 0; i < anterior.length; i++)
		{
			HashNode<V, K> actual = anterior[i];
			while (actual != null)
			{
				try
				{
					agregar(actual.darLlave(), actual.darValor());
					actual = actual.darSiguiente();
				}
				catch (Exception e)
				{
				}

			}
		}
	}
	public int hash(K key)
	{
		return (key.hashCode() & 0x7fffffff) % M;
	}

	@Override
	public Iterator<K> iterator()
	{
		return new IteradorTabla();
	}

	protected class IteradorTabla implements Iterator<K>
	{
		public IteradorTabla()
		{
			indiceActual = -1;
			nodoActual = new HashNode<Lista<V>, K>(null, null);
		}

		public boolean hasNext()
		{
			if (nodoActual == null || darSiguienteLLave() == null)
				return false;
			return darSiguienteLLave() != null;
		}

		public K next()
		{
			if (!hasNext())
				return null;
			return darSiguienteLLave();
		}

		public void remove()
		{
		}

		public K darSiguienteLLave()
		{
			if(nodoActual != null && nodoActual.darSiguiente() != null)
			{
				nodoActual = nodoActual.darSiguiente();
				return nodoActual.darLlave();
			}
			else
			{
				nodoActual = tabla[++indiceActual];
				while(nodoActual == null && indiceActual < tabla.length)
				{
					indiceActual++;
					if(indiceActual < tabla.length)
						nodoActual = tabla[indiceActual];
				}
				if(nodoActual == null)
					return null;
				else
					return nodoActual.darLlave();
			}
		}
	}

}
