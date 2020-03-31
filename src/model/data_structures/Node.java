package model.data_structures;

public class Node<T>
{
	private T elemento;
	private Node siguiente;
	
	
	public Node(T elem)
	{
		elemento = elem;
		siguiente = null;
	}
	
	public T darElemento()
	{
		return elemento;
	}
	public Node darSiguiente()
	{
		return siguiente;
	}
	public void asignarSiguiente(Node sig)
	{
		siguiente = sig;
	}
	public void borrarSiguiente()
	{
		if(siguiente !=null)
		{
			siguiente = siguiente.darSiguiente();
		}
		else
			siguiente = null;
	}
}
