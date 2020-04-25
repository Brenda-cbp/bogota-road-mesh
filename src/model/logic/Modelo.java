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
	/**
	 * Ruta en la que se encuentra el archivo con los comparendos
	 */
	public final String RUTA = "./data/reduccion2.geojson";
	/**
	 * Mensaje que indica al usuario que no se encontro un comparendo con los requerimientos solicitados
	 */
	public final String COMPARENDO_NO_ENCONTRADO = "No se encontro un comparendo con los requerimientos solicitados";
	
	public final String SEPARADOR = ";;;";
	/***
	 * Formato en el que se espera que el usuario ingrese las fechas
	 */
	public final String FORMATO_INGRESO_FECHA="yyyy/MM/dd-HH:mm:ss";
	/**
	 * Formato en el que se encuentran las fechas en el documento de entrada
	 */
	public final String FORMATO_DOCUMENTO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	public final int TAMANIO_INICIAL = 2;
	/**
	 * Numero N, indica cuantos datos imprimir.
	 */
	public final int N = 20;
	/**
	 * Cantidad maxima de comparendos que puede procesar la policia
	 */
	public final int COMPARENDOS_PROCESADOS_DIA= 1500;

	/**
	 * Coordenadas de la estacion de policia del Campin
	 */
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
	/**
	 * Agrega un comparendo a la estructura de datos Heap
	 * @param c el comparendo a agregar
	 */
	public void agregarMaxHeap(Comparendo c)
	{
		Comparendo.ComparatorFecha cmpFecha = new Comparendo.ComparatorFecha();
		heap.agregar(c, cmpFecha);
	}
	/**
	 * Retorna la cantidad de comparendos almacenados en el heap
	 * @return cnatidad de comparendos
	 */
	public int darCantidadComparendos()
	{
		return heap.darNumElmentos();
	}

	/**
	 * Lee el archivo especificado por la constante RUTA y los almacena en un Heap, retorna el comparendo con mayor ID registrado
	 * @return Comparendo con mayor ID
	 */
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
	 * Retorna un maxHeap con la gravedad de cada comparendo como criterio de comparecion
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
	/**
	 * Retorna una lista con los  M comparendos mas cercanos a la estacion de policia del Campin
	 * @param m la cantidad de comparendos buscada
	 * @return la lista con los mas cercanos
	 */
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
	/**
	 * Retorna un arreglo con los comparendos que cumplan los requisitos especificados
	 * @param medioDete el medio de detencion buscado
	 * @param vehiculo el vehiculo buscado
	 * @param localidad la localidad buscada
	 * @return
	 */
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
		Comparendo[] comp = new Comparendo[lista.darTama�o()];
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
		if(comparendos == null)
			return new TablaHashChaining<>(2);
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
	
	/**
	 * Inserta los comparendos en un arbolo rojo negro con su latitud como la llave que los identifica
	 * @return el arbol con los comparendos
	 */
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
	/**
	 * Inserta los comparendos en un arbol rojo negro donde la llave esta dada por la fecha de cada comparendo
	 * @return arbol con los comparendos
	 * @throws Exception en caso de que algun comparendo enlistado sea nulo (Teoricamente no deberia ocurrir)
	 */
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
	/**
	 * Retorna una lista con los comparendos que se encuentren en el rango de fechas  y en la localidad especificados
	 * @param limiteBajo la cota inferior
	 * @param limiteAlto la cota superior
	 * @param localidad la localidad buscada
	 * @return Lista con los comparendos que cumplen las condiciones
	 * @throws Exception
	 */
	public Lista<Comparendo> darComparendosEnRangodeFecha(String limiteBajo, String limiteAlto, String localidad) throws Exception {
		SimpleDateFormat parser = new SimpleDateFormat(FORMATO_INGRESO_FECHA);
		Iterator<Comparendo> iterador = insertarFechasEnArbol().valuesInRange(parser.parse(limiteBajo), parser.parse(limiteAlto)).iterator();
		Lista <Comparendo> rta= new Lista<Comparendo>();
		while(iterador.hasNext() && rta.darTama�o()<=N) {
			Comparendo actual = iterador.next();
			if(actual.darLocalidad().equalsIgnoreCase(localidad))
				rta.agregarAlFinal(actual);
		}
		return rta;
	}
	/**
	 * Retorna una lista de String que representan los comparendos en el a�o con el siguiente formato: fecha1-fecha2--n, la primera posicion de la lista indica
	 * la cantidad de comparendos en el rango con tama�o maximal
	 * donde las fechas denotan un rango de tiempo, n es la  cantidad de comparendos en el rango
	 * @param cantidadDias el numero de dias que tiene cada rango de fechas
	 * @return
	 * @throws Exception
	 */
	public Lista<String> darFechasYasteriscos(int cantidadDias) throws Exception{
		ArbolRojoNegro<Comparendo, Date> arbolConFechas= insertarFechasEnArbol();
		LocalDate fecha1 =LocalDate.parse("2018-01-01");
		LocalDate fecha2= fecha1.plus(Period.ofDays(cantidadDias));
		String alaLista="";
		
		int maximosAsteriscos = 0;
		Lista<String> rta= new Lista<String>() ;
		
		while (fecha1.getYear()<2019) {
			if(fecha2.getYear()==2019)
				fecha2=LocalDate.parse("2018-12-31");
			
			int cantidadAsteriscos=0;
			Date f1= java.sql.Date.valueOf(fecha1);
			Date f2=  java.sql.Date.valueOf(fecha2);
			Lista <Comparendo> comparendosEnRango =arbolConFechas.valuesInRange(f1, f2);
			cantidadAsteriscos=comparendosEnRango.darTama�o();
			if(cantidadAsteriscos > maximosAsteriscos)
				maximosAsteriscos =cantidadAsteriscos;
			alaLista = fecha1+"-"+fecha2+"--"+cantidadAsteriscos;
			rta.agregarAlFinal(alaLista);
			//Actualiza las fechas 
			fecha1 = fecha1.plus(Period.ofDays(cantidadDias));
			fecha2=fecha1.plus(Period.ofDays(cantidadDias));	
		}
		rta.agregarAlComienzo("" + maximosAsteriscos);
		return rta;
	}
	
}

