package model.data_structures;

import java.util.Iterator;

//import com.sun.javafx.geom.Edge;

import model.data_structures.TablaHashChaining.IteradorTabla;

// tomado de https://algs4.cs.princeton.edu/41graph/Graph.java.html
/**
 * Clase que representa un grafo no dirigido
 * 
 * @param <K>
 *            El tipo de dato que representa los identificadores de los vertices
 */
public class Graph<V, K extends Comparable<K>> {
	/**
	 * Lista de adyacencias del grafo
	 */
	private Adyacencias<Vertex, K> adj;
	/**
	 * Catnidad de enlaces en el grafo
	 */
	private int E;

	/**
	 * Numero de componentes conexas
	 */
	private int count;

	/**
	 * Endge del dfs
	 */
	private Adyacencias<K, K> edgeTo;

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
		edgeTo = new Adyacencias<>(2);
		count = -1;
	}

	/**0
	 * 
	 * .
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
		if (adj.agregarEnlace(idVertexIni, idVertexFin, cost))
			E++;
		return;
	}

	/**
	 * Retorna la informacion del vertice, infovertex
	 * 
	 * @param idVertex
	 * @return
	 */
	public V getInfoVertex(K idVertex) {
		try {
			return (V) adj.get(idVertex).darInfo();
		} catch (Exception e) {

		}
		return null;
	}
	/**
	 * Retorna un iterador con los arcos del vertice 
	 * 
	 * @param idVertex
	 * @return
	 */
	public Iterator getArcosVertex(K idVertex) {
		try {
			return  adj.get(idVertex).darAdyacentes().iterator();
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * Sobrescribe la informacion del vertice
	 * 
	 * @param idVertex
	 *            llave del vertice
	 * @param infoVertex
	 *            nueva informacions
	 */
	public void setInfoVertex(K idVertex, V infoVertex) {
		try {
			adj.get(idVertex).setInfoVertex(infoVertex);
		} catch (Exception e) {

		}
	}

	/**
	 * Retorna el costo del arco entre dos vertices
	 * 
	 * @param idVertexIni
	 *            vertice origen
	 * @param idVertexFin
	 *            vertice destino
	 * @return costo del arco
	 * @throws Exception
	 *             si no existe un arco entre los vertices
	 */
	public double getCostArc(K idVertexIni, K idVertexFin) throws Exception {

		Iterator<Edges> it = adj.get(idVertexIni).darAdyacentes().iterator();
		while (it.hasNext()) {
			Edges actual = it.next();
			if (actual.darDestino().equals(idVertexFin))
				return actual.darCosto();
		}
		throw new Exception();
	}

	/**
	 * Sobre escribe el costo del arco entre los vertices especificados
	 * 
	 * @param idVertexIni
	 *            vertice inicia
	 * @param idVertexFin
	 *            vertice final
	 * @param cost
	 *            costo
	 * @throws Exception
	 *             si no existe dicho arco
	 */
	public void setCostArc(K idVertexIni, K idVertexFin, double cost) throws Exception {
		try {
			Iterator<Edges> it = adj.get(idVertexIni).darAdyacentes().iterator();
			while (it.hasNext()) {
				Edges actual = it.next();
				if (actual.darDestino().equals(idVertexFin)) {
					actual.cambiarCosto(cost);
				}

			}
			Iterator<Edges> it2 = adj.get(idVertexFin).darAdyacentes().iterator();
			while (it2.hasNext()) {
				System.out.println("ss");
				Edges actual = it2.next();
				if (actual.darDestino().equals(idVertexIni)) {
					System.out.println("ss");
					actual.cambiarCosto(cost);
					return;
				}	
			}
			throw new Exception();
		} catch (Exception e) {
			throw new Exception("No se encontro un enlace entre los nodos");
		}

	}

	/**
	 * Añade un vertice en el grafo
	 * 
	 * @param idVertex
	 *            llave
	 * @param infoVertex
	 *            informacion del vertice
	 * @throws Exception
	 *             si alguno de los parametros es null
	 */
	public void addVertex(K idVertex, V infoVertex) throws Exception {
		Vertex<V, K> vertice = new Vertex<V, K>(infoVertex, idVertex);
		adj.agregarVertice(idVertex, vertice);
	}

	/**
	 * Retorna una lista con las llvaes de los nodos adyacentes al vertice dado
	 * 
	 * @param idVertex
	 *            llave del vertice dado
	 * @return lista iterable con llave de los adyacentes al vertice
	 */
	public Iterable<K> adj(K idVertex) {
		if (idVertex != null && adj.get(idVertex) != null) {
			Iterator<Edges> it = adj.get(idVertex).darAdyacentes().iterator();
			Lista<K> respuesta = new Lista<>();
			while (it.hasNext()) {
				respuesta.agregarAlFinal((K) it.next().darDestino());
			}
			return respuesta;
		}
		return null;
	}

	/**
	 * Desmarca todos los nodos
	 */
	public void uncheck() {
		adj.unCheck();
	}

	/**
	 * Retorna la lista de los nodos en el grafo
	 * 
	 * @return el grafo
	 */
	public Iterator<K> darNodos() {
		return adj.iterator();
	}

	/**
	 * Metodo para ejecutar dfs, demsarca todos los nodos primero y reinica el
	 * edgeTo
	 * 
	 * @param llave
	 * @throws Exception
	 */
	public void dfsLlamado(K llave) throws Exception {
		uncheck();
		edgeTo = new Adyacencias<>(2);
		dfsAlgoritmo(llave);
	}

	/**
	 * Realiza dfs desde el vertice v
	 * 
	 * @param G
	 *            grafo
	 * @param llave
	 *            vertice donde incia la busqueda
	 * @throws Exception
	 */
	private void dfsAlgoritmo(K llave) throws Exception {
		adj.get(llave).check();
		adj.get(llave).cambiarIdComponenteConexa(count);
		for (K adyacente : (Iterable<K>) adj(llave))
			if (!adj.get(adyacente).isChecked()) {
				edgeTo.agregarVertice(llave, adyacente);
				dfsAlgoritmo(adyacente);
			}
	}

	/**
	 * Calcula las componentes conexas, retorna el numero de componentes
	 * encontradas
	 * 
	 * @param G
	 *            el grafo
	 * @return el numero de componentes conexas en el grafo
	 * @throws Exception
	 */
	public int cc() throws Exception {
		count = 0;
		uncheck();
		Iterator<K> it = darNodos();
		while (it.hasNext()) {
			K actual = it.next();
			if (!adj.get(actual).isChecked()) {
				dfsLlamado(actual);
				count++;
			}
		}
		return count;
	}
	/**
	 * Obtiene los vértices alcanzados a partir del vértice idVertex después de la ejecución de los metodos dfs(K) y cc(). 
	 * @param idVertex
	 * @return
	 * @throws Exception
	 */
	public Iterable<K> getCC(K idVertex) throws Exception {
		cc();
		Iterator<K> it = darNodos();
		int id = adj.get(idVertex).darIdComponenteConexa();
		Lista<K> respuesta = new Lista<>();
		while (it.hasNext()) {
			K actual = it.next();
			if (adj.get(actual).darIdComponenteConexa() == id)
				respuesta.agregarAlFinal(actual);
		}
		return respuesta;
	}
	public Lista<Edges> darArcos(){
		Lista<Edges> rta = new Lista<Edges>();
		Iterator<Vertex> it = darVertices().iterator();
		while(it.hasNext())
		{
			Iterator<Edges> actual = it.next().darAdyacentes().iterator();
			while(actual.hasNext())
			{
				rta.agregarAlFinal(actual.next());
			}
		}
		return rta;
	}
	public Lista<Vertex> darVertices(){
		return adj.darValores();
	}

}
