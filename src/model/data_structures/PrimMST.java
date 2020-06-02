package model.data_structures;

import model.logic.Esquina;
//Tomado de las diapositivas disponibles en la pagina del curso en Sicua.
/**
 * Calcula el MST de un grafo que entra por parametro
 */
public class PrimMST
{
	private Edges[] edgeTo;
	private double[] distTo; 
	private boolean[] marked; 
	private IndexMinPQ<Double> pq; 
	/**
	 * Constructor de la clase
	 * @param G el grafo para el cual se calcula el MST
	 * @throws Exception no deberia lanzar excepcion
	 */
	public PrimMST(Graph<Esquina,Integer> G, int s) throws Exception
	{
		edgeTo = new Edges[G.V()];
		distTo = new double[G.V()];
		marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		pq = new IndexMinPQ<Double>(G.V());
		distTo[0] = 0.0;
		pq.insert(s, 0.0); 
		while (!pq.isEmpty())
			visit(G, pq.delMin()); 
	}
	private void visit(Graph<Esquina,Integer> G, int v)
	{ 
		marked[v] = true;
		for (Edges e : G.adjEnlaces(v))
		{
			int w = (int) e.other(v);
			if (marked[w]) continue; 
			if (e.darCosto() < distTo[w])
			{ 
				edgeTo[w] = e;
				distTo[w] = e.darCosto();
				if (pq.contains(w)) pq.change(w, distTo[w]);
				else pq.insert(w, distTo[w]);
			}
		}
	}
	/**
	 * Retorna un arreglo que representa el MST
	 * @return arreglo con las aristas del MST
	 */
	public Edges[] darMST()
	{
		return edgeTo;
	}
}