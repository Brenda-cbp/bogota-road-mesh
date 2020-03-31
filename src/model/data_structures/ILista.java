package model.data_structures;

public interface ILista<T>
{
	public Node agregarElemento(T elem);
	
	public void eliminarElemento(T elem);
	/**
	 * Retorna la cantidad de elementos en la lista
	 * @return cantidad de elementos en la lista
	 */
	public int darTamaño();
	/**
	 * Retorna el elemento en la posicion dada por parametro inciando desde la posicion 0
	 * @param pos 
	 * @return elemento en la posicion dada si no lo encuentra retorna null
	 */
	public T darElementoPosicion(int pos);
	/**
	 * Avanza i veces en la lista respecto al elemento acutal y almacena este elemento como el actual,
	 * si no existe dicho elemento almacena la cabeza como actual
	 * @param i
	 * @return el elemento en la nueva posicion actual si existe dicha posicion o la cabeza de lista de lo contrario
	 */
	public T recorrerActual(int i);
	/**
	 * Retorna el elemento guardado como actual.
	 * @return elemento actual, por defecto es la cabeza de lista o null si no hay elemetos en la lista
	 */
	public T darElementoActual();
	/**
	 * Se almacena al elemento siguiente del actual como el nuevo elemento actual
	 * @return el nuevo elemento actual, la cabeza delista	/**
	 * Almacena al elemeto anterior al actual como el nuevo elemento actual
	 * @return el nuevo elemento actual, la cabeza de lista si el elemento actual era el primero antes de llamar el metodo
	 */
	public T volverActual();
}
