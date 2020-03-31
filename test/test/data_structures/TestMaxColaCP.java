package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.MaxColaCP;



public class TestMaxColaCP {

	private MaxColaCP<Integer> cola;
	private static int NUM_ELEMENTOS=20;
	@Before
	public void setUp1() {
		cola=new MaxColaCP<Integer>();
	}

	public void setUp2() {
		for(int i=0; i<NUM_ELEMENTOS;i++)
		{
			cola.agregar(i);
		}
	}

	@Test
	public void agregar() {
		try {
			cola.agregar(NUM_ELEMENTOS+1);
			assertEquals("debió agregar el número nuevo como el primer elemento de la cola", NUM_ELEMENTOS+1, cola.darMax());
			cola.agregar(NUM_ELEMENTOS);
			assertEquals("debió dejar el número anterior como el primer elemento de la cola", NUM_ELEMENTOS+1, cola.darMax());
		} catch (Exception e) {
			fail("No debió entrar acá");
		}
	}

	@Test
	public void darMax()  {
		setUp2();
		try {
			assertEquals("Ese no es el máximo" , NUM_ELEMENTOS, cola.darMax());
		} catch (Exception e) {
			fail("No debería entrar a este fail");
		}
	}

	@Test
	public void darNumeroElementos() {
		setUp2();
		assertEquals("El tamaño del heap no es correcto", NUM_ELEMENTOS, cola.darNumElmentos());
	}

	@Test
	public void esVacia() {
		assertTrue("El Heap si está vacío",cola.isEmpty());
		setUp2();
		assertFalse("El Heap no está vacío", cola.isEmpty());
	}

	@Test
	public void sacarMax() {
		setUp2();
		try {
			assertEquals("Ese no es el mayor", NUM_ELEMENTOS, cola.sacarMax());
			assertEquals("No actualizó el número de elementos cuando se sacó el máximo", NUM_ELEMENTOS-1, cola.darNumElmentos());
			assertEquals("Ese no es el mayor", NUM_ELEMENTOS-1, cola.sacarMax());
			assertEquals("No actualizó el número de elementos cuando se sacó el máximo", NUM_ELEMENTOS-2, cola.darNumElmentos());
		} catch (Exception e) {
			fail("No debería entrar al Fail");
		}

	}

}

