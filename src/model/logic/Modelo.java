package model.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.ArregloDinamico;
import model.data_structures.Lista;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import model.data_structures.TablaHashChaining;





/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {

	//--------------------------------------------------------------------------
	//Constantes
	//--------------------------------------------------------------------------
	public final String RUTA = "./data/reduccion2.geojson";
	public final String COMPARENDO_NO_ENCONTRADO = "No se encontro un comparendo con los requerimientos solicitados";
	public final String SEPARADOR = ";;;";
	public final String FORMATO_ESPERADO = "yyyy-MM-dd HH:mm";
	public final String FORMATO_DOCUMENTO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public final int TAMANIO_INICIAL = 2;
	public final int N= 20;

	public final double LAT_POLICIA = 4.647586;
	public final double LONG_POLICIA = 74.078122;
	//--------------------------------------------------------------------------
	//Atributos
	//--------------------------------------------------------------------------


	private Lista<Comparendo> comparendos;
	private MaxHeapCP<Comparendo> heap; 
	private TablaHashChaining<Comparendo, String> tablaChain;

	private String ejemploFecha;


	//--------------------------------------------------------------------------
	//Metodos
	//--------------------------------------------------------------------------

	/**
	 * Constructor del modelo del mundo 
	 */
	public Modelo()
	{
		comparendos = new Lista<>();
		heap = new  MaxHeapCP<>();
	}

	public void agregarMaxHeap(Comparendo c)
	{
		Comparendo.ComparatorFecha cmpFecha= new Comparendo.ComparatorFecha();
		heap.agregar(c, cmpFecha);	
	}
	public int darCantidadComparendos()
	{
		return heap.darNumElmentos();
	}
	public Comparendo cargarDatos() {
		//solucion publicada en la pagina del curso
		//TODO Cambiar la clase del contenedor de datos por la Estructura de Datos propia adecuada para resolver el requerimiento 
		JsonReader reader;
		Comparendo c = null;
		try {
			File ar=new File(RUTA);
			reader = new JsonReader(new FileReader(ar));
			JsonObject elem = JsonParser.parseReader(reader).getAsJsonObject();
			JsonArray e2 = elem.get("features").getAsJsonArray();

			int maxId = -1;
			Comparendo maximo = null;
			SimpleDateFormat parser=new SimpleDateFormat(FORMATO_DOCUMENTO);

			for(JsonElement e: e2) {
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

				String s = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();
				Date FECHA_HORA = parser.parse(s); 

				String MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETECCION").getAsString();
				String CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				String TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				String INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRACCION").getAsString();	
				String LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();
				String MUNICIPIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("MUNICIPIO").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				c = new Comparendo(OBJECTID, FECHA_HORA, DES_INFRAC, MEDIO_DETE, CLASE_VEHI, TIPO_SERVI, INFRACCION, LOCALIDAD, MUNICIPIO, longitud, latitud);
				agregarMaxHeap(c);
				if(OBJECTID > maxId)
				{
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
	/**
	 * 
	 * @param maxheap
	 */
	public MaxHeapCP<Comparendo> ordenaGravedad( ) {
		MaxHeapCP<Comparendo> copia= heap;
		MaxHeapCP<Comparendo> rta= new MaxHeapCP<Comparendo>();
		int tamanoooo= copia.darNumElmentos();
		Comparendo.ComparatorGravedad compGravedad= new Comparendo.ComparatorGravedad();
		Comparendo.ComparatorFecha cmpFecha= new Comparendo.ComparatorFecha();
		while(copia.darNumElmentos()>0) {
			Comparendo prueba= copia.sacarMax(cmpFecha);
			rta.agregar(prueba, compGravedad);
		}return rta;
	}

	public Lista<Comparendo> darMayorGravedad(int cantidad  )
	{	
		Comparendo.ComparatorGravedad compGravedad= new Comparendo.ComparatorGravedad();
		Lista<Comparendo> rta= new Lista<Comparendo>();
		MaxHeapCP<Comparendo> ordenado = ordenaGravedad();
		for (int i=0; i<cantidad; i++) {
			rta.agregarElemento(ordenado.sacarMax(compGravedad));
		}
		return rta; 
	}
	public Lista<Comparendo> prueba()
	{
		Lista<Comparendo> rta= new Lista<Comparendo>();
		Comparendo.ComparatorFecha cmpFecha= new Comparendo.ComparatorFecha();
		return rta;
	}
}
