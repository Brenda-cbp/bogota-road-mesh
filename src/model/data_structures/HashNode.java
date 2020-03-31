package model.data_structures;

public class HashNode<V, K extends Comparable<K>> 
{
	private final K llave;
	private V valor;
	private HashNode<V, K> siguiente;	
	
	public HashNode(K key, V value)
	{
		llave = key;
		valor = value;
		siguiente = null;
	}
	public K darLlave()
	{
		return llave;
	}
	public V darValor()
	{
		return valor;
	}
	public V cambiarValor(V value)
	{
		valor = value;
		return value;
	}
	public HashNode darSiguiente()
	{
		return siguiente;
	}
	public void asignarSiguiente(HashNode sig)
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
