package model.data_structures;

import java.util.Comparator;

public class MaxHeapCP<T extends Comparable <T>> {


	private ArregloDinamico<T> arreglo;

	/**
	 * Método construcctor del Heap
	 */
	public MaxHeapCP(){
		arreglo=new ArregloDinamico<>(100); 
	}
	/**
	 * Retorna el número de elementos del Heap
	 * @return número de elementos que tiene el Heap 
	 */
	public int darNumElmentos() {
		return arreglo.darTamano();
	}
	/**
	 * Agrega un elemento al Heap 
	 * @param elemento
	 */
	public void agregar(T elemento,  Comparator<T> comp) 
	{
		arreglo.agregar(elemento);  
		swim(arreglo.darTamano()-1, comp);
	}
	public void swim(int n, Comparator<T> comp)
	{
		while(n > 1 && comparar(n/2, n,comp)<0 )
		{
			arreglo.exchange(n, n/2);
			n = n/2;
		}
	}
	private void sink (int k,  Comparator<T> comp)
	{
		int N = arreglo.darTamano();
		while (2*k <= N)
		{
			int j = 2*k;
			if (j < N && comparar(j, j+1, comp) < 0)
				j++;
			if (comparar(k, j,comp) >= 0)
				break;
			arreglo.exchange(k, j);
			k = j;
		}
	}

	public T sacarMax( Comparator<T> comp)
	{
		T max = arreglo.darElemento(1);
		arreglo.exchange(1, arreglo.darTamano()-1);
		sink(1, comp);
		arreglo.eliminarPosicion(arreglo.darTamano());
		return max;
	}
	
	private T darMaximo()
	{
		return arreglo.darElemento(1);
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
}
