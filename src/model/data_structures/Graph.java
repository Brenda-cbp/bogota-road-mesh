package model.data_structures;

import java.util.Iterator;

import com.sun.javafx.geom.Edge;

import model.data_structures.TablaHashChaining.IteradorTabla;

// tomado de https://algs4.cs.princeton.edu/41graph/Graph.java.html
/**
 * Clase que representa un grafo no dirigido
 * 
 * @param <K>
 *            El tipo de dato que representa los identificadores de los vertices
 */
public class Graph<V,K extends Comparable<K>> {
	/**
	 * Lista de adyacencias del grafo
	 */
	private Adyacencias<Vertex, K> adj;
	/**
	 * Catnidad de enlaces en el grafo
	 */
	private int E;

	/**
	 * Construye un nuevo grafo con capacidad para V vertices
	 * 
	 * @param n
	 *            cantidad de vertices en el grafo
	 * @throws Exception
	 *             Si la catnidad de vertices es negativa
	 */
	public Graph(int n) throws Exception {
		if (n < 0)
			throw new Exception("La cantidad de vartices debe ser mayor o igual a cero");
		this.E = 0;
		adj = new Adyacencias(n);
	}

	/**
	 * Retorna la cantidad de vertices en el grafo
	 * 
	 * @return catnidad de vertices
	 */
	public int V() {
		return adj.darCantidadLLaves();
	}

	/**
	 * Retorna el numero de enlaces en el grafo
	 * 
	 * @return numero enlaces
	 */
	public int E() {
		return E;
	}

	/**
	 * Agrega un nuevo enlace en el grafo NO dirigido
	 * 
	 * @param idVertexIni
	 *            identificador origen
	 * @param idVertexFin
	 *            identificador final
	 * @param cost
	 *            costo enlace
	 * @throws Exception
	 *             si algun identificador es null
	 */
	public void addEdge(K idVertexIni, K idVertexFin, double cost) throws Exception {
		if(adj.agregarEnlace(idVertexIni, idVertexFin, cost))
			E++;
		return;
	}

	public V getInfoVertex(K idVertex)
	{
		try
		{
			return (V) adj.get(idVertex).darInfo();
		}
		catch(Exception e)
		{
			
		}
		return null;
	}
	public void setInfoVertex(K idVertex,V infoVertex)
	{
		try
		{
			adj.get(idVertex).setInfoVertex(infoVertex);
		}
		catch(Exception e)
		{
			
		}
	}
	public double getCostArc(K idVertexIni, K idVertexFin)throws Exception
	{
		try
		{
			Iterator<Edges> it = adj.get(idVertexIni).darAdyacentes().iterator();
			while(it.hasNext())
			{
				Edges actual = it.next();
				if(actual.darDestino().equals(idVertexFin))
					return actual.darCosto();
			}
			throw new Exception();
		}
		catch(Exception e)
		{
			throw new Exception("No se encontro un enlace entre los nodos");
		}
		
	}
	public void setCostArc(K idVertexIni, K idVertexFin, double cost)throws Exception
	{
		try
		{
			Iterator<Edges> it = adj.get(idVertexIni).darAdyacentes().iterator();
			while(it.hasNext())
			{
				Edges actual = it.next();
				if(actual.darDestino().equals(idVertexFin))
					actual.cambiarCosto(cost);
			}
			throw new Exception();
		}
		catch(Exception e)
		{
			throw new Exception("No se encontro un enlace entre los nodos");
		}
		
	}
	public void addVertex(K idVertex, V infoVertex) throws Exception
	{
		Vertex<V, K> vertice = new Vertex<V, K>(infoVertex, idVertex);
		adj.agregarVertice(idVertex, vertice);
	}
	public Iterable <K> adj (K idVertex)
	{
		if(idVertex != null && adj.get(idVertex) != null)
		{
			Iterator<Edges> it = adj.get(idVertex).darAdyacentes().iterator();
			Lista<K> respuesta = new Lista<>();
			while(it.hasNext())
			{
				respuesta.agregarAlFinal((K) it.next().darDestino());
			}
			return respuesta;
		}
		return null;
	}
	public void uncheck() 
	{
		adj.unCheck();
	}
	public Iterator<K> darNodos()
	{
		return adj.iterator();
	}
}
