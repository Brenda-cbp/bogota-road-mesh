package model.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
//import com.sun.tools.jdi.EventSetImpl.Itr;

import model.data_structures.ArbolRojoNegro;
import model.data_structures.ArregloDinamico;
import model.data_structures.Lista;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import model.data_structures.TablaHashChaining;
//import sun.font.LayoutPathImpl;
//import sun.util.resources.LocaleData;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo
{

	// --------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------
	public final String RUTA = "./data/reduccion2.geojson";
	
	public final String COMPARENDO_NO_ENCONTRADO = "No se encontro un comparendo con los requerimientos solicitados";
	public final String SEPARADOR = ";;;";
	public final String FORMATO_INGRESO_FECHA="yyyy/MM/dd-HH:mm:ss";
	public final String FORMATO_ESPERADO = "yyyy-MM-dd HH:mm";
	public final String FORMATO_DOCUMENTO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	public final int TAMANIO_INICIAL = 2;
	public final int N = 20;
	public final int COMPARENDOS_PROCESADOS_DIA= 1500;

	public final static double LAT_POLICIA = 4.647586;
	public final static double LONG_POLICIA = -74.078122;
	// --------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------

	private Lista<Comparendo> comparendos;
	private MaxHeapCP<Comparendo> heap;

	private String ejemploFecha;

	// --------------------------------------------------------------------------
	// Metodos
	// --------------------------------------------------------------------------

	/**
	 * Constructor del modelo del mundo
	 */
	public Modelo()
	{
		comparendos = new Lista<>();
		heap = new MaxHeapCP<>();
	}

	public void agregarMaxHeap(Comparendo c)
	{
		Comparendo.ComparatorFecha cmpFecha = new Comparendo.ComparatorFecha();
		heap.agregar(c, cmpFecha);
	}

	public int darCantidadComparendos()
	{
		return heap.darNumElmentos();
	}

	public Comparendo cargarDatos()
	{
		// solucion publicada en la pagina del curso
		// TODO Cambiar la clase del contenedor de datos por la Estructura de
		// Datos propia adecuada para resolver el requerimiento
		JsonReader reader;
		Comparendo c = null;
		try
		{
			File ar = new File(RUTA);
			reader = new JsonReader(new FileReader(ar));
			JsonObject elem = JsonParser.parseReader(reader).getAsJsonObject();
			JsonArray e2 = elem.get("features").getAsJsonArray();

			int maxId = -1;
			Comparendo maximo = null;
			SimpleDateFormat parser = new SimpleDateFormat(FORMATO_DOCUMENTO);

			for (JsonElement e : e2)
			{
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

				String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();
				Date FECHA_HORA = parser.parse(s);

				String MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETECCION")
						.getAsString();
				String CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO")
						.getAsString();
				String TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO")
						.getAsString();
				String INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION")
						.getAsString();
				String DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRACCION")
						.getAsString();
				String LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD")
						.getAsString();
				String MUNICIPIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("MUNICIPIO")
						.getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates")
						.getAsJsonArray().get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates")
						.getAsJsonArray().get(1).getAsDouble();

				c = new Comparendo(OBJECTID, FECHA_HORA, DES_INFRAC, MEDIO_DETE, CLASE_VEHI, TIPO_SERVI, INFRACCION,
						LOCALIDAD, MUNICIPIO, longitud, latitud);
				agregarMaxHeap(c);
				if (OBJECTID > maxId)
				{
					maxId = OBJECTID;
					maximo = c;
				}
			}
			ejemploFecha = c.darfecha() + "";
			return maximo;
		}
		catch (FileNotFoundException | ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param maxheap
	 */
	public MaxHeapCP<Comparendo> ordenaGravedad()
	{
		Iterator<Comparendo> comparendos = heap.iterator();
		MaxHeapCP<Comparendo> copia = new MaxHeapCP<>();
		while (comparendos.hasNext())
		{
			copia.agregar(comparendos.next(), new Comparendo.ComparatorGravedad());
		}
		return copia;
	}

	public Lista<Comparendo> darMayorGravedad(int cantidad)
	{
		Comparendo.ComparatorGravedad compGravedad = new Comparendo.ComparatorGravedad();
		Lista<Comparendo> rta = new Lista<Comparendo>();
		MaxHeapCP<Comparendo> ordenado = ordenaGravedad();
		for (int i = 0; i < cantidad; i++)
		{
			rta.agregarAlFinal(ordenado.sacarMax(compGravedad));
		}
		return rta;
	}

	/**
	 * Retorna un Max-heap con los comparendos usando la "cercania" a la
	 * estacion de policia como criterio de comparacion
	 * 
	 * @return
	 */
	public MaxHeapCP<Comparendo> ordenarPorDistanciaEstacion()
	{
		Iterator<Comparendo> comparendos = heap.iterator();
		MaxHeapCP<Comparendo> copia = new MaxHeapCP<>();
		while (comparendos.hasNext())
		{
			copia.agregar(comparendos.next(), new Comparendo.ComparatorDistanciaPolicia());
		}
		return copia;
	}

	public Lista<Comparendo> darMasCercanosEstacionPolicia(int m)
	{
		MaxHeapCP<Comparendo> copia = ordenarPorDistanciaEstacion();
		Lista<Comparendo> respuesta = new Lista<>();
		while (m > 0 && copia.darNumElmentos() > 0)
		{
			respuesta.agregarAlFinal(copia.sacarMax(new Comparendo.ComparatorDistanciaPolicia()));
			m--;
		}
		return respuesta;
	}

	public Comparendo[] darComprendosPorLLave(String medioDete, String vehiculo, String localidad)
	{
		TablaHashChaining<Comparendo, String> tablaChain = new TablaHashChaining<>(2);
		meterEnTabla(tablaChain);
		Comparendo[] respuesta = darComparendosPorllaveChain(medioDete, vehiculo, localidad, tablaChain);
		tablaChain = null;
		return respuesta;
	}

	/**
	 * Copia los comparendos en la tabla de hash, la llave del comparendo es
	 * dada por: medio deteccion, clase vehiculo, localidad
	 */
	public void meterEnTabla(TablaHashChaining<Comparendo, String> tablaChain)
	{
		Iterator<Comparendo> comparendos = heap.iterator();
		while (comparendos.hasNext())
		{
			agregarTablaChaining(comparendos.next(), tablaChain);
		}

	}

	/**
	 * Retorna la identificador del comparendo que llega por parametro en el
	 * formato de la tabla
	 * 
	 * @param dato
	 *            el Comparendo
	 * @return llave
	 */
	public String darLlaveTablaChain(Comparendo dato)
	{
		return dato.darMedioDete() + SEPARADOR + dato.darclaseVehiculo() + SEPARADOR + dato.darLocalidad();
	}

	/**
	 * Agrega un comparendo en la tabla de hash por chaining
	 * 
	 * @param dato
	 */
	public void agregarTablaChaining(Comparendo dato, TablaHashChaining<Comparendo, String> tablaChain)
	{
		try
		{
			tablaChain.agregar(darLlaveTablaChain(dato), dato);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Retrona los comparendos que cumplen los siguientes parametros ordenados
	 * por fecha
	 * 
	 * @param medioDete
	 *            el medio de deteccion
	 * @param vehiculo,
	 *            el tipo de vehiculo
	 * @param localidad,
	 *            la localidad
	 * @return un arreglo con los comparendos ordenados
	 */
	public Comparendo[] darComparendosPorllaveChain(String medioDete, String vehiculo, String localidad,
			TablaHashChaining<Comparendo, String> tablaChain)
	{
		Lista<Comparendo> lista = tablaChain.get(medioDete + SEPARADOR + vehiculo + SEPARADOR + localidad);
		if (lista == null)
			return new Comparendo[0];
		Comparendo[] comp = new Comparendo[lista.darTamaño()];
		int i = 0;
		Iterator<Comparendo> it = lista.iterator();
		while (it.hasNext())
		{
			try
			{
				comp[i] = it.next();
				i++;
			}
			catch (Exception e)
			{
				System.out.println(lista.darElementoActual());
			}
		}
		Sorting.quickSort(comp);
		return comp;
	}

	public TablaHashChaining<Comparendo, String> ordenarTablaMesyDia() throws Exception
	{
		Iterator<Comparendo> comparendos = heap.iterator();
		TablaHashChaining<Comparendo, String> rta = new TablaHashChaining<Comparendo, String>(58);
		while (comparendos.hasNext())
		{
			Comparendo c=comparendos.next();
			rta.agregar("" + c.darNumeroMes() + "-" 
					+ c.darInicialSemana()+c.darInicialSemana()+c.darInicialSemana()+c.darInicialSemana()					
					+"-"+c.darNombreMes(c.darNumeroMes()),
					c);
		}
		return rta;
	}

	public Lista<Comparendo> darComparendoMesyDia(int mes, String dia) throws Exception
	{
		dia = dia.toUpperCase();
		Lista<Comparendo> rta = null;
		return ordenarTablaMesyDia().get("" + mes + "-" + dia+dia+dia+dia+"-"+darNombreMes(mes));
	}
	public String darNombreMes(int mes) {
		Date fecha= new Date(120,mes,11);
		Calendar calendario= Calendar.getInstance();
		calendario.setTime(fecha);
		return calendario.getDisplayName(Calendar.MONTH,Calendar.LONG_FORMAT,Locale.ENGLISH);
	}

	public ArbolRojoNegro<Comparendo, Double> meterEnRedBlackLatitud()
	{
		ArbolRojoNegro<Comparendo, Double> arbol = new ArbolRojoNegro<>();
		Iterator<Comparendo> comparendos = heap.iterator();
		while (comparendos.hasNext())
		{
			Comparendo act = comparendos.next();
			try
			{
				arbol.insertar(act.darLatitud(), act);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arbol;
	}

	public Iterator<Comparendo> darComparendosEnRangoLatitud(double lat1, double lat2)
	{
		return meterEnRedBlackLatitud().valuesInRange(lat1, lat2).iterator();
	}
	
	public ArbolRojoNegro<Comparendo, Date> insertarFechasEnArbol() throws Exception {
		Iterator<Comparendo> comparendos = heap.iterator();
		ArbolRojoNegro<Comparendo, Date> rta= new ArbolRojoNegro<Comparendo, Date>();
		while (comparendos.hasNext())
		{
			Comparendo actual = comparendos.next();
			rta.insertar(actual.darfecha(),actual);
		}
		return rta;
	}
	public Lista<Comparendo> darComparendosEnRangodeFecha(String limiteBajo, String limiteAlto, String localidad) throws Exception {
		SimpleDateFormat parser = new SimpleDateFormat(FORMATO_INGRESO_FECHA);
		Iterator<Comparendo> iterador = insertarFechasEnArbol().valuesInRange(parser.parse(limiteBajo), parser.parse(limiteAlto)).iterator();
		Lista <Comparendo> rta= new Lista<Comparendo>();
		while(iterador.hasNext() && rta.darTamaño()<=N) {
			Comparendo actual = iterador.next();
			if(actual.darLocalidad().equalsIgnoreCase(localidad))
				rta.agregarAlFinal(actual);
		}
		return rta;
	}
	public Lista<String> darFechasYasteriscos(int cantidadDias) throws Exception{
		ArbolRojoNegro<Comparendo, Date> arbolConFechas= insertarFechasEnArbol();
		LocalDate fecha1 =LocalDate.parse("2018-01-01");
		LocalDate fecha2= fecha1.plus(Period.ofDays(cantidadDias));
		String alaLista="";
		
		Lista<String> rta= new Lista<String>() ;

		while (fecha2.getYear()<2019) {
			if(fecha2.getYear()==2019)
				fecha2=LocalDate.parse("2018-12-31");
			
			int cantidadAsteriscos=0;
			Date f1= java.sql.Date.valueOf(fecha1);
			Date f2=  java.sql.Date.valueOf(fecha2);
			Lista <Comparendo> comparendosEnRango =arbolConFechas.valuesInRange(f1, f2);
			cantidadAsteriscos=comparendosEnRango.darTamaño()/50; 
			if(comparendosEnRango.darTamaño()%50 !=1) {
				cantidadAsteriscos++;
			}
			
			alaLista = fecha1+"-"+fecha2+"--"+cantidadAsteriscos;		
			rta.agregarAlFinal(alaLista);
			//Actualiza las fechas 
			fecha1 = fecha1.plus(Period.ofDays(1));
			fecha2=fecha1.plus(Period.ofDays(cantidadDias));	
		}
		return rta;
	}
}

