package model.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
//import com.sun.tools.jdi.EventSetImpl.Itr;

import edu.princeton.cs.introcs.StdRandom;
import model.data_structures.ArbolRojoNegro;
import model.data_structures.ArregloDinamico;
import model.data_structures.Edges;
import model.data_structures.Graph;
import model.data_structures.Lista;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import model.data_structures.TablaHashChaining;
import model.data_structures.Vertex;
//import sun.font.LayoutPathImpl;
//import sun.util.resources.LocaleData;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {

	// --------------------------------------------------------------------------
	// Constantes
	// --------------------------------------------------------------------------
	/**
	 * Ruta en la que se encuentra el archivo con los comparendos
	 */
	public final String RUTA = "./data/Comparendos_DEI_2018_Bogotá_D.C.geojson";

	public final String RUTA_VERTICES = "./data/bogota_vertices.txt";

	public final String RUTA_ARCOS = "./data/bogota_arcos.txt";

	/**
	 * Mensaje que indica al usuario que no se encontro un comparendo con los
	 * requerimientos solicitados
	 */
	public final String COMPARENDO_NO_ENCONTRADO = "No se encontro un comparendo con los requerimientos solicitados";

	public final static String SEPARADOR = ";;;";
	/***
	 * Formato en el que se espera que el usuario ingrese las fechas
	 */
	public final String FORMATO_INGRESO_FECHA = "yyyy/MM/dd-HH:mm:ss";
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
	public final int COMPARENDOS_PROCESADOS_DIA = 1500;

	/**
	 * Coordenadas de la estacion de policia del Campin
	 */
	public final static double LAT_POLICIA = 4.647586;
	public final static double LONG_POLICIA = -74.078122;

	public final String RUTA_EXPORTAR = "./data/grafo.json";
	// --------------------------------------------------------------------------
	// Atributos
	// --------------------------------------------------------------------------

	private Lista<Comparendo> comparendos;
	private MaxHeapCP<Comparendo> heap;
	private Graph<Esquina, Integer> grafo;

	private String ejemploFecha;

	// --------------------------------------------------------------------------
	// Metodos
	// --------------------------------------------------------------------------

	/**
	 * Constructor del modelo del mundo @throws
	 */
	public Modelo() {
		comparendos = new Lista<>();
		heap = new MaxHeapCP<>();
		try {
			grafo = new Graph<>(71283);
		} catch (Exception e) {
		}
	}

	public int darVertices() {
		return grafo.V();
	}

	public int darAristas() {
		return grafo.E();
	}

	/**
	 * Agrega un comparendo a la estructura de datos Heap
	 * 
	 * @param c
	 *            el comparendo a agregar
	 */
	public void agregarMaxHeap(Comparendo c) {
		Comparendo.ComparatorFecha cmpFecha = new Comparendo.ComparatorFecha();
		heap.agregar(c, cmpFecha);
	}

	/**
	 * Retorna la cantidad de comparendos almacenados en el heap
	 * 
	 * @return cnatidad de comparendos
	 */
	public int darCantidadComparendos() {
		return heap.darNumElmentos();
	}

	/**
	 * Crea un nuevo grafo a partir de los archivos especificados en las rutas
	 * de vertices y arcos
	 * 
	 * @throws Exception
	 *             si los archivos no estan en formato esperado o si occurre
	 *             algun error en lectura
	 */
	public void crearGrafo() throws Exception {
		cargarNodos();
		cargarArcos();
	}

	/**
	 * Crea los vertices del grafo a partir del archivo de vertices
	 * 
	 * @throws Exception
	 *             si los archivos no estan en formato esperado o si occurre
	 *             algun error en lectura
	 */
	public void cargarNodos() throws Exception {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(new File(RUTA_VERTICES)));
			String v = bf.readLine();

			while (v != null || v != "") {
				if (v == null)
					break;
				String[] vertice = v.split(",");
				Esquina nueva = new Esquina(Integer.parseInt(vertice[0]), Double.parseDouble(vertice[2]),
						Double.parseDouble(vertice[1]));
				grafo.addVertex(Integer.parseInt(vertice[0]), nueva);
				v = bf.readLine();
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new Exception("Error, El archivo de vertices no se encuentra en formato esperado");
		} catch (NumberFormatException e) {
			throw new Exception("Error, el archivo de vertices no se encuentra en formato esperado");
		} catch (FileNotFoundException e) {
			throw new Exception("Archivo no encontrado");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ocurrio un error cargando los vertices, favor vuelva a intentarlo");

		}
	}

	/**
	 * Crea los enlaces entre los nodos del grafo a partir del archiv de arcos
	 * 
	 * @throws Exception
	 *             si los archivos no estan en formato esperado o si occurre
	 *             algun error en lectura
	 */
	public void cargarArcos() throws Exception {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(new File(RUTA_ARCOS)));
			String v = bf.readLine();
			v = bf.readLine();
			v = bf.readLine();
			v = bf.readLine();

			while (v != null) {
				String[] vertice = v.split(" ");
				int origen = Integer.parseInt(vertice[0]);

				for (int i = 1; i < vertice.length; i++) {
					Esquina esqOrigen = grafo.getInfoVertex(origen);
					Esquina esqDestino = grafo.getInfoVertex(Integer.parseInt(vertice[i]));

					if (esqOrigen != null && esqDestino != null) {
						double costo = DistanciaHaversiana.distance(esqOrigen.darLatitud(), esqOrigen.darLongitud(),
								esqDestino.darLatitud(), esqDestino.darLongitud());
						grafo.addEdge(origen, esqDestino.darId(), costo);
					}
				}
				v = bf.readLine();
			}
		} catch (FileNotFoundException e) {
			throw new Exception("Archivo no encontrado");
		} catch (IOException e) {
			throw new Exception("Error de lectura");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Ocurrio un error cargando los arcos, favor vuelva a intentarlo");
		}
	}

	/**
	 * Lee el archivo especificado por la constante RUTA y los almacena en un
	 * Heap, retorna el comparendo con mayor ID registrado
	 * 
	 * @return Comparendo con mayor ID
	 */
	public Comparendo cargarDatos() {
		// solucion publicada en la pagina del curso
		// TODO Cambiar la clase del contenedor de datos por la Estructura de
		// Datos propia adecuada para resolver el requerimiento
		JsonReader reader;
		Comparendo c = null;
		try {
			File ar = new File(RUTA);
			reader = new JsonReader(new FileReader(ar));
			JsonObject elem = JsonParser.parseReader(reader).getAsJsonObject();
			JsonArray e2 = elem.get("features").getAsJsonArray();

			int maxId = -1;
			Comparendo maximo = null;
			SimpleDateFormat parser = new SimpleDateFormat(FORMATO_DOCUMENTO);

			for (JsonElement e : e2) {
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
						LOCALIDAD, MUNICIPIO, longitud, latitud, null);
				agregarMaxHeap(c);
				if (OBJECTID > maxId) {
					maxId = OBJECTID;
					maximo = c;
				}
			}
			ejemploFecha = c.darfecha() + "";
			return maximo;
		} catch (FileNotFoundException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ---------------------------------------------------------------------
	public void cargarDatosGrafo() throws Exception {
		Graph<Esquina, Integer>grafo2 = new Graph<>(40);
		// solucion publicada en la pagina del curso
		// TODO Cambiar la clase del contenedor de datos por la Estructura de
		// Datos propia adecuada para resolver el requerimiento
		JsonReader reader;
		Esquina c = null;
		try {
			File ar = new File(RUTA_EXPORTAR);
			reader = new JsonReader(new FileReader(ar));
			JsonObject elem = JsonParser.parseReader(reader).getAsJsonObject();
			JsonArray esquinas = elem.get("Esquinas").getAsJsonArray();

			for (JsonElement e : esquinas) {
				double longit = e.getAsJsonObject().get("longitud").getAsDouble();
				double lat = e.getAsJsonObject().get("latitud").getAsDouble();
				int id = e.getAsJsonObject().get("ID").getAsInt();

				c = new Esquina(id, lat, longit);
				grafo2.addVertex(id, c);
			}
			JsonArray arcos = elem.get("Arcos").getAsJsonArray();
			int i = 0;
			for (JsonElement e : arcos) {
				int id = e.getAsJsonObject().get("ID").getAsInt();
				JsonArray adjuntos = e.getAsJsonObject().get("ajunta").getAsJsonArray();
				int costo = e.getAsJsonObject().get("Costo").getAsInt();
				for (JsonElement actual : adjuntos) {
					i++;
					int destino = actual.getAsInt();
					grafo2.addEdge(id, destino, costo);
				}
			}
		System.out.println("" + i);
		System.out.println("vertices = " + grafo2.V() + " arcos= " + grafo2.E());	
		Iterator<Edges> lista = grafo2.darArcos().iterator();
		while(lista.hasNext())
		{
			Edges actual = lista.next();
			System.out.println("origen-" + actual.darOrigen() + " destino-" + actual.darDestino());
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------

	/**
	 * Retorna un maxHeap con la gravedad de cada comparendo como criterio de
	 * comparecion
	 * 
	 * @param maxheap
	 */
	public MaxHeapCP<Comparendo> ordenaGravedad() {
		Iterator<Comparendo> comparendos = heap.iterator();
		MaxHeapCP<Comparendo> copia = new MaxHeapCP<>();
		while (comparendos.hasNext()) {
			copia.agregar(comparendos.next(), new Comparendo.ComparatorGravedad());
		}
		return copia;

	}

	public Lista<Comparendo> darMayorGravedad(int cantidad) {
		Comparendo.ComparatorGravedad compGravedad = new Comparendo.ComparatorGravedad();
		Lista<Comparendo> rta = new Lista<Comparendo>();
		MaxHeapCP<Comparendo> ordenado = ordenaGravedad();
		for (int i = 0; i < cantidad; i++) {
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
	public MaxHeapCP<Comparendo> ordenarPorDistanciaEstacion() {
		Iterator<Comparendo> comparendos = heap.iterator();
		MaxHeapCP<Comparendo> copia = new MaxHeapCP<>();
		while (comparendos.hasNext()) {
			copia.agregar(comparendos.next(), new Comparendo.ComparatorDistanciaPolicia());
		}
		return copia;
	}

	/**
	 * Retorna una lista con los M comparendos mas cercanos a la estacion de
	 * policia del Campin
	 * 
	 * @param m
	 *            la cantidad de comparendos buscada
	 * @return la lista con los mas cercanos
	 */
	public Lista<Comparendo> darMasCercanosEstacionPolicia(int m) {
		MaxHeapCP<Comparendo> copia = ordenarPorDistanciaEstacion();
		Lista<Comparendo> respuesta = new Lista<>();
		while (m > 0 && copia.darNumElmentos() > 0) {
			respuesta.agregarAlFinal(copia.sacarMax(new Comparendo.ComparatorDistanciaPolicia()));
			m--;
		}
		return respuesta;
	}

	/**
	 * Retorna un arreglo con los comparendos que cumplan los requisitos
	 * especificados
	 * 
	 * @param medioDete
	 *            el medio de detencion buscado
	 * @param vehiculo
	 *            el vehiculo buscado
	 * @param localidad
	 *            la localidad buscada
	 * @return
	 */
	public Comparendo[] darComprendosPorLLave(String medioDete, String vehiculo, String localidad) {
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
	public void meterEnTabla(TablaHashChaining<Comparendo, String> tablaChain) {
		Iterator<Comparendo> comparendos = heap.iterator();
		while (comparendos.hasNext()) {
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
	public String darLlaveTablaChain(Comparendo dato) {
		return dato.darMedioDete() + SEPARADOR + dato.darclaseVehiculo() + SEPARADOR + dato.darLocalidad();
	}

	/**
	 * Agrega un comparendo en la tabla de hash por chaining
	 * 
	 * @param dato
	 */
	public void agregarTablaChaining(Comparendo dato, TablaHashChaining<Comparendo, String> tablaChain) {
		try {
			tablaChain.agregar(darLlaveTablaChain(dato), dato);
		} catch (Exception e) {
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
			TablaHashChaining<Comparendo, String> tablaChain) {
		Lista<Comparendo> lista = tablaChain.get(medioDete + SEPARADOR + vehiculo + SEPARADOR + localidad);
		if (lista == null)
			return new Comparendo[0];
		Comparendo[] comp = new Comparendo[lista.darTamaño()];
		int i = 0;
		Iterator<Comparendo> it = lista.iterator();
		while (it.hasNext()) {
			try {
				comp[i] = it.next();
				i++;
			} catch (Exception e) {
				System.out.println(lista.darElementoActual());
			}
		}
		Sorting.quickSort(comp);
		return comp;
	}

	public TablaHashChaining<Comparendo, String> ordenarTablaMesyDia() throws Exception {
		Iterator<Comparendo> comparendos = heap.iterator();
		if (comparendos == null)
			return new TablaHashChaining<>(2);
		TablaHashChaining<Comparendo, String> rta = new TablaHashChaining<Comparendo, String>(58);
		while (comparendos.hasNext()) {
			Comparendo c = comparendos.next();
			rta.agregar("" + c.darNumeroMes() + "-" + c.darInicialSemana() + c.darInicialSemana() + c.darInicialSemana()
			+ c.darInicialSemana() + "-" + c.darNombreMes(c.darNumeroMes()), c);
		}
		return rta;
	}

	public Lista<Comparendo> darComparendoMesyDia(int mes, String dia) throws Exception {
		dia = dia.toUpperCase();
		Lista<Comparendo> rta = null;
		return ordenarTablaMesyDia().get("" + mes + "-" + dia + dia + dia + dia + "-" + darNombreMes(mes));
	}

	public String darNombreMes(int mes) {
		Date fecha = new Date(120, mes, 11);
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		return calendario.getDisplayName(Calendar.MONTH, Calendar.LONG_FORMAT, Locale.ENGLISH);
	}

	/**
	 * Inserta los comparendos en un arbolo rojo negro con su latitud como la
	 * llave que los identifica
	 * 
	 * @return el arbol con los comparendos
	 */
	public ArbolRojoNegro<Comparendo, Double> meterEnRedBlackLatitud() {
		ArbolRojoNegro<Comparendo, Double> arbol = new ArbolRojoNegro<>();
		Iterator<Comparendo> comparendos = heap.iterator();
		while (comparendos.hasNext()) {
			Comparendo act = comparendos.next();
			try {
				arbol.insertar(act.darLatitud(), act);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return arbol;
	}

	public Iterator<Comparendo> darComparendosEnRangoLatitud(double lat1, double lat2) {
		return meterEnRedBlackLatitud().valuesInRange(lat1, lat2).iterator();
	}

	/**
	 * Inserta los comparendos en un arbol rojo negro donde la llave esta dada
	 * por la fecha de cada comparendo
	 * 
	 * @return arbol con los comparendos
	 * @throws Exception
	 *             en caso de que algun comparendo enlistado sea nulo
	 *             (Teoricamente no deberia ocurrir)
	 */
	public ArbolRojoNegro<Comparendo, Date> insertarFechasEnArbol() throws Exception {
		Iterator<Comparendo> comparendos = heap.iterator();
		ArbolRojoNegro<Comparendo, Date> rta = new ArbolRojoNegro<Comparendo, Date>();
		while (comparendos.hasNext()) {
			Comparendo actual = comparendos.next();
			rta.insertar(actual.darfecha(), actual);
		}
		return rta;
	}

	/**
	 * Retorna una lista con los comparendos que se encuentren en el rango de
	 * fechas y en la localidad especificados
	 * 
	 * @param limiteBajo
	 *            la cota inferior
	 * @param limiteAlto
	 *            la cota superior
	 * @param localidad
	 *            la localidad buscada
	 * @return Lista con los comparendos que cumplen las condiciones
	 * @throws Exception
	 */
	public Lista<Comparendo> darComparendosEnRangodeFecha(String limiteBajo, String limiteAlto, String localidad)
			throws Exception {
		SimpleDateFormat parser = new SimpleDateFormat(FORMATO_INGRESO_FECHA);
		Iterator<Comparendo> iterador = insertarFechasEnArbol()
				.valuesInRange(parser.parse(limiteBajo), parser.parse(limiteAlto)).iterator();
		Lista<Comparendo> rta = new Lista<Comparendo>();
		while (iterador.hasNext() && rta.darTamaño() <= N) {
			Comparendo actual = iterador.next();
			if (actual.darLocalidad().equalsIgnoreCase(localidad))
				rta.agregarAlFinal(actual);
		}
		return rta;
	}

	/**
	 * Retorna una lista de String que representan los comparendos en el año con
	 * el siguiente formato: fecha1-fecha2--n, la primera posicion de la lista
	 * indica la cantidad de comparendos en el rango con tamaño maximal donde
	 * las fechas denotan un rango de tiempo, n es la cantidad de comparendos en
	 * el rango
	 * 
	 * @param cantidadDias
	 *            el numero de dias que tiene cada rango de fechas
	 * @return
	 * @throws Exception
	 */
	public Lista<String> darFechasYasteriscos(int cantidadDias) throws Exception {
		ArbolRojoNegro<Comparendo, Date> arbolConFechas = insertarFechasEnArbol();
		LocalDate fecha1 = LocalDate.parse("2018-01-01");
		LocalDate fecha2 = fecha1.plus(Period.ofDays(cantidadDias));
		String alaLista = "";

		int maximosAsteriscos = 0;
		Lista<String> rta = new Lista<String>();

		while (fecha1.getYear() < 2019) {
			if (fecha2.getYear() == 2019)
				fecha2 = LocalDate.parse("2018-12-31");

			int cantidadAsteriscos = 0;
			Date f1 = java.sql.Date.valueOf(fecha1);
			Date f2 = java.sql.Date.valueOf(fecha2);
			Lista<Comparendo> comparendosEnRango = arbolConFechas.valuesInRange(f1, f2);
			cantidadAsteriscos = comparendosEnRango.darTamaño();
			if (cantidadAsteriscos > maximosAsteriscos)
				maximosAsteriscos = cantidadAsteriscos;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			alaLista = fecha1.getYear() + "/" + fecha1.getMonthValue() + "/" + fecha1.getDayOfMonth() + " - "
					+ fecha2.getYear() + "/" + fecha2.getMonthValue() + "/" + fecha2.getDayOfMonth() + "--"
					+ cantidadAsteriscos;
			rta.agregarAlFinal(alaLista);
			// Actualiza las fechas
			fecha1 = fecha1.plus(Period.ofDays(cantidadDias + 1));
			fecha2 = fecha1.plus(Period.ofDays(cantidadDias));
		}
		rta.agregarAlComienzo("" + maximosAsteriscos);
		return rta;
	}

	// ___________________________________________________
	/**
	 * Calcula el costo por dia de retraso en publicar el comparendo
	 * 
	 * @param c
	 *            comparendo a calcular costo
	 * @return costo por dia de retraso en publicar el comparendo
	 */
	public int calcularPrecios(Comparendo c) {
		if (c.darDescripcion().contains("SERA INMOVILIZADO") || c.darDescripcion().contains("SERÁ INMOVILIZADO")
				|| c.darDescripcion().contains(" VEHÃ?CULO SERÃ? INMOVILIZADO")) {
			return 400;
		} else if (c.darDescripcion().contains("LICENCIA DE CONDUCCIÓN")
				|| c.darDescripcion().contains("LICENCIA DE CONDUCCIÃ“N")) {
			return 40;
		}
		return 4;
	}

	/**
	 * Retorna una lista con la cantidad total de comparendos de cada precio que
	 * se impuso en cada dia.
	 * 
	 * @return lista con costos por dia
	 * @throws Exception
	 */
	public Lista<String> darComparendosDiasPrecios() throws Exception {
		ArbolRojoNegro<Comparendo, Date> arbolConFechas = insertarFechasEnArbol();

		LocalDate fecha1 = LocalDate.parse("2018-01-01");
		Lista<String> rta = new Lista<String>();

		while (fecha1.getYear() < 2019) {
			int cantidad400 = 0;
			int cantidad40 = 0;
			int cantidad4 = 0;
			Date f1 = java.sql.Date.valueOf(fecha1);
			Date f2 = java.sql.Date.valueOf(fecha1.plus(Period.ofDays(1)));
			Lista<Comparendo> delDia = arbolConFechas.valuesInRange(f1, f2);
			Iterator<Comparendo> it = delDia.iterator();
			while (it.hasNext()) {
				int precio = calcularPrecios(it.next());
				if (precio == 400)
					cantidad400++;
				else if (precio == 40)
					cantidad40++;
				else
					cantidad4++;
			}
			rta.agregarAlFinal(cantidad400 + SEPARADOR + cantidad40 + SEPARADOR + cantidad4);
			// Actualiza las fechas
			fecha1 = fecha1.plus(Period.ofDays(1));
		}
		return rta;
	}

	public String darCostos() throws Exception {
		return CalculadordeCostos.darCostos(darComparendosDiasPrecios());
	}

	public Lista<String> darHistogramaProcesadosyEsperando() throws Exception {
		Lista<String> rta = new Lista<String>();
		Lista<String> listafechayAsteriscos = darFechasYasteriscos(1);

		ArbolRojoNegro<Comparendo, Date> arbolConFechas = insertarFechasEnArbol();
		LocalDateTime fecha1 = LocalDateTime.of(2018, 01, 01, 00, 00, 00);
		LocalDateTime fecha2 = LocalDateTime.of(2018, 01, 01, 23, 59, 59);

		int costos = 0;
		int max = 0;
		int comparendosEnEspera = 0;
		while (fecha1.getYear() < 2019) {

			int cantidadComparendosEseDia = 0;
			int comparendosProcesados = 0;

			Date f1 = Date.from(fecha1.atZone(ZoneId.systemDefault()).toInstant());
			Date f2 = Date.from(fecha2.atZone(ZoneId.systemDefault()).toInstant());

			Lista<Comparendo> comparendosEnRango = arbolConFechas.valuesInRange(f1, f2);
			cantidadComparendosEseDia = comparendosEnRango.darTamaño();

			if (cantidadComparendosEseDia > COMPARENDOS_PROCESADOS_DIA) {
				comparendosProcesados = COMPARENDOS_PROCESADOS_DIA;
				for (int i = COMPARENDOS_PROCESADOS_DIA; i < cantidadComparendosEseDia; i++) {
					costos += calcularPrecios(comparendosEnRango.darElementoPosicion(i));
					comparendosEnEspera++;
				}
			} else
				comparendosProcesados = cantidadComparendosEseDia; // ...y
			// comparendos
			// en
			// espera=0
			if (comparendosEnEspera > max)/// hallar el maximo
				max = comparendosEnEspera;

			rta.agregarAlFinal("" + fecha1.getYear() + "/" + fecha1.getMonthValue() + "/" + fecha1.getDayOfMonth()
			+ "--" + comparendosProcesados + "--" + comparendosEnEspera);
			fecha1 = fecha1.plusHours(24);
			fecha2 = fecha2.plusHours(24);
		}
		rta.agregarAlComienzo("" + max);
		rta.agregarAlComienzo("" + costos);
		return rta;
	}

	public void generarJson() {

		JSONArray parteVertices = new JSONArray();
		JSONArray parteArcos = new JSONArray();
		JSONObject docCompleto = new JSONObject();

		Iterator<Vertex> llaves = grafo.darVertices().iterator();// da las
		// llaves
		// del
		// vertice

		while (llaves.hasNext()) {
			Vertex<Esquina, Integer> actual = llaves.next();
			Esquina esquinaActual = actual.darInfo();

			JSONObject vertices = new JSONObject();
			JSONObject adjacentes = new JSONObject();
			JSONArray verticesAdjacentes = new JSONArray();

			vertices.put("ID", esquinaActual.darId());
			vertices.put("longitud", esquinaActual.darLongitud());
			vertices.put("latitud", esquinaActual.darLatitud());

			Iterator<Edges> arcos = grafo.getArcosVertex(actual.darLLave());

			adjacentes.put("ID", esquinaActual.darId());

			while (arcos.hasNext()) {
				Edges arcoActual = arcos.next();
				adjacentes.put("Costo", arcoActual.darCosto());
				if (esquinaActual.darId() == (int) arcoActual.darOrigen())
					verticesAdjacentes.add(grafo.getInfoVertex((Integer) arcoActual.darDestino()).darId());
				else
					verticesAdjacentes.add(grafo.getInfoVertex((Integer) arcoActual.darOrigen()).darId());
			}
			adjacentes.put("ajunta", verticesAdjacentes);
			parteVertices.add(vertices);
			parteArcos.add(adjacentes);
			docCompleto.put("Esquinas", parteVertices);
			docCompleto.put("Arcos", parteArcos);
		}
		try {
			FileWriter file = new FileWriter(RUTA_EXPORTAR);
			file.write(docCompleto.toJSONString());
			file.flush();
			System.out.println("Logrado");

		} catch (IOException e) {
			System.out.println("Eror " + e.getMessage());
		}

	}
}
