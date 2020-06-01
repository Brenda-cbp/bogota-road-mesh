package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;

import model.data_structures.MaxHeapCP.IteradorHeap;

public class MinHeapCP2<T extends Comparable<T>> 
{
	private ArregloDinamico<T> arreglo;

	/**
	 * Método construcctor del Heap
	 */
	public MinHeapCP2()
	{
		arreglo = new ArregloDinamico<>(100);
	}

	/**
	 * Retorna el número de elementos del Heap
	 * 
	 * @return número de elementos que tiene el Heap
	 */
	public int darNumElmentos()
	{
		return arreglo.darTamano() - 1;
	}

	/**
	 * Agrega un elemento al Heap
	 * 
	 * @param elemento
	 */
	public void agregar(T elemento, Comparator<T> comp)
	{
		arreglo.agregar(elemento);
		swim(arreglo.darTamano() - 1, comp);
	}

	public void swim(int n, Comparator<T> comp)
	{
		while (n > 1 && comparar(n / 2, n, comp) > 0)
		{
			arreglo.exchange(n, n / 2);
			n = n / 2;
		}
	}

	private void sink(int k, Comparator<T> comp)
	{
		int N = arreglo.darTamano();
		while (2 * k <= N)
		{
			int j = 2 * k;
			if (j < N && comparar(j, j + 1, comp) >0)
				j++;
			if (comparar(k, j, comp) <= 0)
				break;
			arreglo.exchange(k, j);
			k = j;
		}
	}

	public T sacarMin (Comparator<T> comp)
	{
		if(esVacia())
			return null;
		T max = arreglo.darElemento(1);
		arreglo.exchange(1, arreglo.darTamano() - 1);
		arreglo.eliminarPosicion(arreglo.darTamano());
		sink(1, comp);
		return max;
	}
	private T darMin()
	{
		return arreglo.darElemento(1);
	}
	public boolean contains(T elemento) {
		if (arreglo.buscar(elemento)!=null)
			return true;
		return false;
	}
	public int comparar(int i, int j, Comparator<T> comp)
	{
		return comp.compare(arreglo.darElemento(i), arreglo.darElemento(j));
	}
	public boolean esVacia()
	{
		return arreglo.darTamano() == 1;
	}

	public String toString()
	{
		return arreglo.toString();
	}

	public T dar(int i)
	{
		return arreglo.darElemento(i);
	}

	public Iterator<T> iterator()
	{
		return new IteradorHeap();
	}
	protected class IteradorHeap implements Iterator<T>
	{
		private int indice;
		public IteradorHeap()
		{
			indice = 0;
		}
		@Override
		public boolean hasNext()
		{
			if(indice >= arreglo.darTamano())
				return false;
			return arreglo.darElemento(indice +1) != null;
		}

		@Override
		public T next()
		{
			if(!hasNext())
				return null;
			return arreglo.darElemento(++indice);
		}
	}
}
