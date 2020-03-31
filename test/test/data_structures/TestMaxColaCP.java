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
			assertEquals("debi� agregar el n�mero nuevo como el primer elemento de la cola", NUM_ELEMENTOS+1, cola.darMax());
			cola.agregar(NUM_ELEMENTOS);
			assertEquals("debi� dejar el n�mero anterior como el primer elemento de la cola", NUM_ELEMENTOS+1, cola.darMax());
		} catch (Exception e) {
			fail("No debi� entrar ac�");
		}
	}

	@Test
	public void darMax()  {
		setUp2();
		try {
			assertEquals("Ese no es el m�ximo" , NUM_ELEMENTOS, cola.darMax());
		} catch (Exception e) {
			fail("No deber�a entrar a este fail");
		}
	}

	@Test
	public void darNumeroElementos() {
		setUp2();
		assertEquals("El tama�o del heap no es correcto", NUM_ELEMENTOS, cola.darNumElmentos());
	}

	@Test
	public void esVacia() {
		assertTrue("El Heap si est� vac�o",cola.isEmpty());
		setUp2();
		assertFalse("El Heap no est� vac�o", cola.isEmpty());
	}

	@Test
	public void sacarMax() {
		setUp2();
		try {
			assertEquals("Ese no es el mayor", NUM_ELEMENTOS, cola.sacarMax());
			assertEquals("No actualiz� el n�mero de elementos cuando se sac� el m�ximo", NUM_ELEMENTOS-1, cola.darNumElmentos());
			assertEquals("Ese no es el mayor", NUM_ELEMENTOS-1, cola.sacarMax());
			assertEquals("No actualiz� el n�mero de elementos cuando se sac� el m�ximo", NUM_ELEMENTOS-2, cola.darNumElmentos());
		} catch (Exception e) {
			fail("No deber�a entrar al Fail");
		}

	}

}

