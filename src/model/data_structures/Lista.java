package model.data_structures;

import java.util.Iterator;

public class Lista<T> implements ILista<T>, Iterable<T> {
	private Node cabeza;
	private Node actual;
	private Node ultimo;
	private int tamaño;

	public Lista() {
		cabeza = null;
		actual = null;
		ultimo = null;
		tamaño = 0;
	}
	
	public void agregarAlComienzo(T elemento)
	{
		if(cabeza == null)
			crearLista(elemento);
		else
		{
			Node<T> nueva = new Node<T>(elemento);
			nueva.asignarSiguiente(cabeza);
			cabeza = nueva;
			tamaño++;
		}
	}
	
	public void agregarAlFinal(T elemento) {
		if (cabeza == null)
			crearLista(elemento);
		else {
			ultimo.asignarSiguiente(new Node<T>(elemento));
			ultimo = ultimo.darSiguiente();
			tamaño++;
		}
	}

	/**
	 * Asigna el nodo dado por parametro como el primero de la lista y el actual
	 * 
	 * @param cabe
	 */
	public void crearLista(T cabe) {
		cabeza = new Node<T>(cabe);
		actual = cabeza;
		ultimo = cabeza;
		tamaño = 1;
	}



	/**
	 * Elimina al elemento dado por parametro, si este no existe no hace nada
	 */
	public void eliminarElemento(T elemento) {
		Node act = cabeza;
		if (cabeza != null && cabeza.darElemento().equals(elemento)) {
			Node nuevaCab = cabeza.darSiguiente();
			cabeza.asignarSiguiente(null);
			cabeza = nuevaCab;
			tamaño--;
			return;
		}

		while (act != null && act.darSiguiente() != null) {
			if (actual.darSiguiente().darElemento().equals(elemento))
				actual.borrarSiguiente();
			tamaño--;
		}
	}

	public int darTamaño() {
		return tamaño;
	}

	public T darElementoPosicion(int pos)
	{
		Node act = cabeza;
		int i = 0;
		
		if (pos == tamaño - 1)
			return (T) ultimo.darElemento();
		
		while (act != null && i < pos)
		{
			i++;
			act = act.darSiguiente();
		}
		if (act == null)
			return null;
		return (T) act.darElemento();
	}

	public T recorrerActual(int i) {
		Node act = cabeza;
		while (act != null && i > 0) {
			act = act.darSiguiente();
			i--;
		}
		if (act == null && cabeza == null) {
			actual = null;
			return null;
		} else if (act == null)
			actual = cabeza;
		return (T) actual.darElemento();
	}

	public T darElementoActual() {
		if (actual != null)
			return (T) actual.darElemento();
		return null;
	}

	public T avanzarActual() {
		if (actual != null)
			actual = actual.darSiguiente();
		if (actual == null && cabeza == null)
			return null;
		else if (actual == null)
			actual = cabeza;
		return (T) actual.darElemento();

	}
	/**
	 * define el elemento actual como la cabeza
	 */
	public void reiniciarActual()
	{
		actual = cabeza;
	}
	public T volverActual() {
		Node act = cabeza;
		boolean ya = false;

		if (cabeza == null)
			return null;
		else if (!actual.equals(cabeza)) {
			while (act != null && act.darSiguiente() != null && !ya) {
				if (act.darSiguiente().equals(actual)) {
					actual = act;
					ya = true;
				}

				actual = actual.darSiguiente();
			}
		}
		return (T) actual.darElemento();
	}
	

	@Override
	public Iterator<T> iterator() {
		return new IteradorLista();
	}

	protected class IteradorLista implements Iterator<T> {
		public IteradorLista() {
			if(cabeza == null)
				return;
			Node nuevo = new Node<T>((T) cabeza.darElemento());
			nuevo.asignarSiguiente(cabeza);
			actual = nuevo;
		}

		public boolean hasNext() {
			if (actual == null || actual.darSiguiente() == null)
				return false;
			return actual.darSiguiente().darElemento() != null;
		}

		public T next() {
			if (!hasNext())
				return null;
			actual = actual.darSiguiente();
			return (T) actual.darElemento();
		}

		public void remove() {

		}
	}
}
