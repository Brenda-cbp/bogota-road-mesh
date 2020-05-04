package model.data_structures;

public class Vertex<V, K extends Comparable<K>>
{
	
	private V infoVertex;
	private K key;
	private boolean checked;
	private Lista<Edges> adyacentes;
	
	public Vertex(V info, K key)
	{
		this.key = key;
		infoVertex = info;
		checked = false;
		adyacentes = new Lista<>();
	}
	public Lista<Edges> darAdyacentes()
	{
		return adyacentes;
	}
	public void addEdge(Edges edge)
	{
		adyacentes.agregarAlFinal(edge);
	}
	public V darInfo()
	{
		return infoVertex;
	}
	public void setInfoVertex(V info)
	{
		infoVertex = info;
	}
	public void check()
	{
		checked = true;
	}
	public void unCheck()
	{
		checked = false;
	}
	public boolean isChecked()
	{
		return checked;
	}
	public K darLLave()
	{
		return key;
	}
}
