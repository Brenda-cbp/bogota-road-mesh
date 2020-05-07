package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.ArregloDinamico;
import model.data_structures.Graph;
import model.data_structures.Lista;

public class TestGraph {

	private Graph<Integer,Integer> grafo;
	private static int TAMANO=100;


	@Before
	public void setUp1() {
		try {
			grafo= new Graph<Integer, Integer>(TAMANO);
		} catch (Exception e) {
			fail("No debería ocurrir la excepción");;
		}
	}
	public void setUp2() {
		for (int i = 0; i < TAMANO; i++) {
			try {
				grafo.addVertex(i, i);
			} catch (Exception e) {
				fail("No debería ocurrir la excepción");
			}
		}
	}
	public void setUp3() {
		try {
			for (int j = 0; j < 6; j++) {
				grafo.addVertex(j, j);
			}
			grafo.addEdge(0, 6, 1);
			grafo.addEdge(6, 4, 1);
			grafo.addEdge(4, 5, 1);
			grafo.addEdge(5, 0, 1);
			grafo.addEdge(4, 3, 1);
			grafo.addEdge(3, 5, 1);
			grafo.addEdge(0, 1, 1);
			grafo.addEdge(0, 2, 1);
		} catch (Exception e) {
			fail("No debería ocurrir la excepción");
		}	

	}


	@Test
	public void agregarVertice()
	{
		try {
			grafo.addVertex(3, 3);
			assertEquals("No añadió correctamente", 1, grafo.V());
			grafo.addVertex(15, 15);
			assertEquals("No añadió correctamente", 2, grafo.V());
			grafo.addVertex(68, 68);
			assertEquals("No añadió correctamente", 3, grafo.V());
			setUp2();
			assertEquals("Ese no es el número de vértices", TAMANO, grafo.V());

		} catch (Exception e) {
			fail("No debería ocurrir la excepción");
		}
	}

	@Test
	public void agregarArcos() {
		setUp2();
		try {
			grafo.addEdge(3, 2, 1);
			grafo.addEdge(3, 15, 1);
			grafo.addEdge(3, 98, 1);
			grafo.addEdge(98, 67, 1);
			Iterator<Integer> it =grafo.adj(3).iterator();
			int rta=it.next();
			assertEquals("No agregó el vértice correctamente", 2, rta);
			rta=it.next();
			assertEquals("No agregó el vértice correctamente", 15, rta);
			rta=it.next();
			assertEquals("No agregó el vértice correctamente", 98, rta);
			assertEquals(4, grafo.E());

		} catch (Exception e) {
			fail("No debería ocurrir la excepción");
		}
	}
	@Test
	public void verificarCostos() {
		try {
			grafo.addVertex(3, 3);
			grafo.addVertex(2, 2);
			grafo.addVertex(15, 15);
			grafo.addVertex(98, 98);
			grafo.addVertex(67, 67);
			
			
			grafo.addEdge(3, 2, 1);
			grafo.addEdge(3, 15, 1);
			grafo.addEdge(3, 98, 3);
			grafo.addEdge(98, 67, 2);
			assertEquals("Ese no es el costo",1,(int)grafo.getCostArc(3, 15));
			assertEquals("No agregó el vértice correctamente", 3, (int)grafo.getCostArc(3, 98));
			
			grafo.setCostArc(3, 2, 5);
			assertEquals("No agregó el costo correctamente ", 5,(int) grafo.getCostArc(3, 2));
		} catch (Exception e) {
			fail("No debería ocurrir la excepción");
		}
	}
	@Test
	public void verificarVertex() {
		setUp2();
		assertEquals("la informacion del vértice no es correcta ", 5, (int) grafo.getInfoVertex(5));
		assertNull("Deberia ser null",grafo.getInfoVertex(655) );
		grafo.setInfoVertex(79, 777777);
		assertEquals("la informacion del vértice no cambio ",777777, (int) grafo.getInfoVertex(79));
	}
	@Test
	public void cc() {
		setUp3();
		try {
			assertEquals("El numero de componentes conectados es incorrecto", 1, grafo.cc());
		} catch (Exception e) {
			fail("No debería ocurrir la excepción");
		}
	}
	public void getcc() {
		setUp3();
	//	Iterable<Integer> it= grafo.getCC(0);	
	}
}


