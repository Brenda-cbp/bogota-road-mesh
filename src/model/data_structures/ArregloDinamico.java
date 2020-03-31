package model.data_structures;

/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 * @author Fernando De la Rosa
 *
 */
public class ArregloDinamico<T extends Comparable<T>>  {
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
	private T elementos[ ];

	/**
	 * Construir un arreglo con la capacidad maxima inicial.
	 * @param max Capacidad maxima inicial
	 */
	public ArregloDinamico( int max )
	{
		elementos = (T[]) new Comparable [max];
		tamanoMax = max;
		//El 0 siempre es vacio en el heap
		tamanoAct = 1;
	}

	public void agregar( T dato )
	{
		if ( tamanoAct == tamanoMax )
		{  // caso de arreglo lleno (aumentar tamaNo)
			tamanoMax = 2 * tamanoMax;
            T [ ] copia = elementos;
            //elementos = new T [tamanoMax];
            elementos =(T[]) new Comparable [tamanoMax];
            for ( int i = 1; i < tamanoAct; i++)
            {
             	 elementos[i] = copia[i];
            } 
    	    System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
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

	public T darElemento(int i) {
		// TODO HECHO implementar
		return (T) elementos[i];
	}

	public T buscar(T dato) {
		// TODO HECHO implementar
		// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
		for (int i =0; i<tamanoMax; i++)
		{
			if (elementos[i].compareTo(dato)==0)
				return dato;
		}
		return null;
	}

	public T eliminar(T dato) {
		// TODO HECHO implementar
		// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
		T guardar=null;
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
	public T eliminarPosicion(int pos)
	{
		T guardar = elementos[pos];
		elementos[pos] = null;
		tamanoAct--;
		return guardar;
		
	}
	public void exchange(int i, int j)
	{
		T t = elementos[i];
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
