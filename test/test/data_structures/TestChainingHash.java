package test.data_structures;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;


import model.data_structures.Lista;
import model.data_structures.TablaHashChaining;

public class TestChainingHash
{
	private TablaHashChaining<Integer, Integer> tabla;
	private static int CAP_INICIAL = 7;
	private int tamano = 0;

	@Before
	public void setUp1()
	{
		tabla = new TablaHashChaining<>(CAP_INICIAL);
		tamano = 0;
	}

	public void setUp2()
	{
		for (int i = 0; i < CAP_INICIAL; i++)
		{
			try
			{
				tabla.agregar(i, i);
				tamano++;
			}
			catch (Exception e)
			{
				fail("No deberia ocurrir excepcion");
			}
		}
	}
	public void setUp3()
	{
		for(int i = 0; i < CAP_INICIAL*5; i++)
		{
			try
			{
				tabla.agregar(i, i);
				tamano++;
			}
			catch (Exception e)
			{
				fail("no debio lanzar excepcion");
			}
		}
	}
	@Test
	public void agregar()
	{
		try
		{
			tabla.agregar(1, 1);
			assertEquals("La cantidad de llaves debio aumentar en 1", 1, tabla.darCantidadLLaves());
			tabla.agregar(1, 2);
			assertEquals("No debio agregar una nueva llave",1,tabla.darCantidadLLaves());
			setUp3();
			assertTrue("Deberia haber hecho rehash", tabla.darTamaño() != CAP_INICIAL);
		}
		catch(Exception e)
		{
			fail("No deberia lanzar excepcion");
		}
	}
	
	@Test
	public void get()
	{
		setUp2();
		try
		{
			tabla.agregar(0, 3);
		}
		catch (Exception e)
		{
			fail("No deberia lanzar excepcion");
		}
		Lista<Integer> resultados = tabla.get(0);
		assertEquals("Deberia retronar una cadena con 2 elementos", 2, resultados.darTamaño());
	}
	
	@Test
	public void eliminar()
	{
		Lista<Integer> resultados = tabla.eliminar(0);
		assertNull("Deberia retornar null", resultados);
		setUp2();
		try
		{
			tabla.agregar(1, 2);
		}
		catch (Exception e)
		{
			fail("No deberia lanzar excepcion");
		}
		resultados = tabla.eliminar(1);
		assertEquals("Deberia retornar una lista de dos elementos", 2, resultados.darTamaño());
		for(int i = 0; i < CAP_INICIAL; i++)
		{
			tabla.eliminar(i);
		}
		assertTrue("Deberia haber hecho rehash", tabla.darTamaño() < CAP_INICIAL);
	}
	
	@Test
	public void rehash()
	{
		setUp3();
		assertEquals("No deberia alterar la cantidad de llaves", tamano, tabla.darCantidadLLaves());
		assertTrue("Deberia haber aumentado el tamaño del arreglo", CAP_INICIAL < tabla.darTamaño());
	}
	
}
