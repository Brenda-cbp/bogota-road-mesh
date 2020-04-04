package test.data_structures;

import model.data_structures.ArregloDinamico;

public class MaxHeapCP<T extends Comparable <T>> {


	private ArregloDinamico<T> arreglo;

	/**
	 * Método construcctor del Heap
	 */
	public MaxHeapCP(){
		arreglo=new ArregloDinamico<>(20); 
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
	public void agregar(T elemento) 
	{
		arreglo.agregar(elemento);
		swim(arreglo.darTamano()-1);
	}
	public void swim(int n)
	{
		while(n > 1 && comparar(n/2, n)<0 )
		{
			arreglo.exchange(n, n/2);
			n = n/2;
		}
	}
	private void sink (int k)
	{
		int N = arreglo.darTamano();
		while (2*k <= N)
		{
			int j = 2*k;
			if (j < N && comparar(j, j+1) < 0)
				j++;
			if (comparar(k, j) >= 0)
				break;
			arreglo.exchange(k, j);
			k = j;
		}
	}

	private T sacarMax()
	{
		T max = arreglo.darElemento(1);
		arreglo.exchange(1, arreglo.darTamano()-1);
		sink(1);
		arreglo.eliminarPosicion(arreglo.darTamano());
		return max;
	}
	
	private T darMaximo()
	{
		return arreglo.darElemento(1);
	}

	public int comparar(int i, int j)
	{
		return arreglo.darElemento(i).compareTo(arreglo.darElemento(j));
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
