package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;

import model.logic.Esquina;

//Tomado de las diapositivas de clase disponibles en el curso de sicua
public class Dijkstra
{
	private Edges[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq;
	/**
	 * Constructor de la clase calcula los caminos mas cortos en un grafo desde un vertice dado
	 * @param G el grafo
	 * @param s el vertice
	 * @throws Exception No deberia lanzar excepcion
	 */
	public Dijkstra(Graph<Esquina,Integer> G, int s) throws Exception
	{
		edgeTo = new Edges[G.V()];
		distTo = new double[G.V()];
		pq = new IndexMinPQ<Double>(G.V());
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0.0;
		pq.insert(s, 0.0);
		while (!pq.isEmpty())
			relax(G, pq.delMin());
	}
	private void relax(Graph<Esquina,Integer> G, int v)
	{
		for(Edges e : G.adjEnlaces(v))
		{
			int w = (int) e.darDestino();
			if (distTo[w] > distTo[v] + e.darCosto())
			{
				distTo[w] = distTo[v] + e.darCosto();
				edgeTo[w] = e;
				if (pq.contains(w)) pq.change(w, distTo[w]);
				else pq.insert(w, distTo[w]);
			}
		}
	}
	public Edges[] darEdgeTo()
	{
		return edgeTo;
	}
}

