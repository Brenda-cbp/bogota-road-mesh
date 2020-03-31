package model.data_structures;

public class MaxColaCP<T extends Comparable <T>> {
	/**
	 *N�mero de elementos de la cola 
	 */
	private int numeroElementos;
/**
 * Primer nodo de la cola  de prioridad 
 */
	private Node <T> primero;
	/**
	 * Ultimo nodo de la cola de prioridad 
	 */
	private Node <T> ultimo;
	/**
	 * M�todo construcctor de la cola 
	 */
	public MaxColaCP(){
		primero=null;
		ultimo=null;
		numeroElementos=0;
	}
	public T get(int i)
	{
		T respuesta = null;
		if(i< numeroElementos)
		{
			Node actual = primero;
			for(int a = 0; a < i-1 -1;a++)
			{
				actual = actual.darSiguiente();
			}
			respuesta = (T) actual.darElemento();
		}
		return respuesta;
	}
	/**
	 * Retorna el n�mero de elementos de la cola 
	 * @return n�mero de elementos que tiene la cola 
	 */
	public int darNumElmentos() {
		return numeroElementos;
	}
	/**
	 * Agrega un elemento a la cola de prioridad
	 * @param elemento que se desea agregar 
	 */
	public void agregar(T elemento) {
		Node <T> agrega= new Node<T>(elemento);
		Node <T> actual=null;
		Node <T> anterior = null;
		actual=primero;
		boolean agregado=false;
		if ( numeroElementos==0) {
			primero = agrega;
			ultimo=primero;
		}
		else if ( agrega.darElemento().compareTo(ultimo.darElemento())<=0) 
		{
			ultimo.asignarSiguiente(agrega);
			ultimo=agrega;
		}
		else if(agrega.darElemento().compareTo(primero.darElemento())>=0) {
			agrega.asignarSiguiente(primero);
			primero=agrega;
			}
		else {
			while (!agregado) {

				if (agrega.darElemento().compareTo(actual.darElemento())<0) {
					anterior= actual;
					actual=actual.darSiguiente();
					
				}
				else { 
					anterior.asignarSiguiente(agrega);
					agrega.asignarSiguiente(actual);
						agregado=true;
				}
			}
		}
		numeroElementos++;
	}
	/**
	 * Retorna el m�ximo elemento de la cola de prioridad utilizando el comparador T natural 
	 * @return el elemento mas grande de la cola de prioridad
	 * @throws Exception si la cola no tiene alg�n elemento
	 */
	public Node<T> sacarMax() throws Exception {
		if(isEmpty()) throw new Exception ("La cola est� vac�a");
		Node <T> rta = primero;
		primero=primero.darSiguiente();
		return rta;
	}
	/**
	 * Da el m�ximo elemento de la cola, sin eliminarlo de esta 
	 * @return elemento m�ximo encontrado en la cola de prioridad
	 * @throws Exception Si la cola no tiene alg�n elemento 
	 */
	public Node<T>darMax() throws Exception {
		if(isEmpty()) throw new Exception ("La cola est� vac�a");
		return primero;
	}
	/**
	 * Retorna si la cola est� vac�a 
	 * @return true si la cola est� vac�a, False de lo contrario
	 */
	public boolean isEmpty() {
		return  numeroElementos==0; 
	}

	public String toString()
	{
		String respuesta = "";
		Node<T> actual = primero;
		for(int i = 0; i < numeroElementos; i++)
		{
			respuesta = respuesta + actual.darElemento().toString() + "\n" + "\n";
			actual = actual.darSiguiente();
		}
		return respuesta;
	}
}
