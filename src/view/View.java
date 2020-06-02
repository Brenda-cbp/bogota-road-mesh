package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import model.data_structures.Lista;
import model.logic.Modelo;

public class View {
	private final String CANCELAR = "CANCELAR";

	/**
	 * Metodo constructor
	 */
	public View() {

	}
	/**
	 * Imprime el menu de opciones del programa en la consola
	 * @return la opcion que escogio el usuario
	 */
	public int printMenu() {
		System.out.println();
		int rta=0;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("1. Obtener el camino de costo mínimo entre dos ubicaciones geograficas por Distancia haversiana ");
		System.out.println("2. Determinar la red de comunicaciones que soporte la instalacion de cámaras de video en los M puntos en los están los comparendos con mayor gravedad");
		System.out.println("3. Obtener el camino de costo mínimo entre dos ubicaciones geograficas por numero de Comparendos ");
		System.out.println("4. Determinar la red de comunicaciones que soporte la instalacion de cámaras de video en los M puntos en los que hay más comparendos");
		System.out.println("5.Obtener caminos mas cortos entre los comparendos mas graves y la polica");
		System.out.println();

		try {
			rta= Integer.parseInt(bf.readLine());
		}
		catch(Exception e) {
			rta= printMenu();
		}
		return rta;
	}

	public void printMessage(String mensaje) {

		System.out.println(mensaje);
	}

	/**
	 * Solicita al usuario que ingrese lo que desea buscar de los comparendos,
	 * retorna null para cancelar
	 * 
	 * @param parametro,
	 *            lo que se solicitara al usuario
	 * @return la localidad solicitada o Null si desea cancelar
	 */
	public String pedir(String parametro) {
		System.out.println("Favor inserte " + parametro + " buscado.");
		System.out.println("Escriba CANCELAR para volver al menu principal");
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String respuesta = "";
		try {
			respuesta = bf.readLine();
		} catch (Exception e) {
			pedir(parametro);
		}
		if (respuesta.equals(CANCELAR))
			return null;
		return respuesta;
	}

	/**
	 * Imprime el objeto generico que recibe
	 * 
	 * @param cosa,
	 *            objeto a imprimir, debe poder convertirse a string (los
	 *            copmarendos si sirven)
	 */
	public void imprimir(Object cosa) {
		System.out.println();
		System.out.println(cosa);
		System.out.println();
	}
	/**
	 * Imprime una tabla de dos columnas que representa con asteriscos los comparendos en el año particionados por rangos
	 * Ajusta automaticamente la cantidad de comparendos que representa cada asterisco
	 * @param losDatos, lista la cantidad de comparendos por rango y debe tener en la primera posicion la cantidad maxima de comparendos que tiene un rango
	 * 
	 */
	
	public void imprimirTablaAsci(Lista<String> losDatos ) {
		System.out.println("    Rango de fechas     |  Comparendos durante el año ");
		System.out.println("--------------------------------------------------------");
		int maximo = Integer.parseInt(losDatos.darElementoPosicion(0));
		maximo = maximo / 30;
		losDatos.eliminarElemento(losDatos.darElementoPosicion(0));
		for (String actual : losDatos) {
			String[] partes = actual.split("--");
			int numCaracteres= 24-partes[0].length();
			String numeroEspacios="";
			for (int i = 0; i < numCaracteres; i++) {
				numeroEspacios+=" ";
			}
			String numeroAsteriscos="";
			if(Integer.parseInt(partes[1])/maximo == 0)
				numeroAsteriscos = "*";
			for (int i = 0; i < (Integer.parseInt(partes[1])/maximo); i++) {
				numeroAsteriscos+="*";	
			}
			System.out.println(partes[0]+numeroEspacios+"|"+numeroAsteriscos);
		}
		System.out.println("Cada * representa "+ maximo +" comparendos (o fraccion)");
	}
	public void imprimirHistogramaProcesadosYespera(Lista<String> losDatos ) {
		int costos= Integer.parseInt(losDatos.darElementoPosicion(0));
		System.out.println("Los costos a lo largo del año 2018 si no se implementa el nuevo sistema son: "+costos);
		
		System.out.println("Fecha        | Comparendos procesados            ***");
		System.out.println("             | Comparendos que están en espera   ###");
		System.out.println("---------------------------------------------------");
		
			int max= Integer.parseInt(losDatos.darElementoPosicion(1));
		max=max/50;
		losDatos.eliminarElemento(losDatos.darElementoPosicion(0));
		losDatos.eliminarElemento(losDatos.darElementoPosicion(0));
		for (String actual : losDatos) {
			String[] partes = actual.split("--");
			int numCaracteres= 12-partes[0].length();
			String numeroEspacios="";
			for (int i = 0; i < numCaracteres; i++) {
				numeroEspacios+=" ";
			}
			String numeroAsteriscos="";
			if(Integer.parseInt(partes[1])/max == 0)
				numeroAsteriscos = "*";
			for (int i = 0; i < (Integer.parseInt(partes[1])/max); i++) {
				numeroAsteriscos+="*";	
			}
			String numeroNumerales="";
			if(Integer.parseInt(partes[1])/max == 0)
				numeroNumerales = "#";
			for (int i = 0; i < (Integer.parseInt(partes[2])/max); i++) {
				numeroNumerales+="#";	
			}
			System.out.println(partes[0]+numeroEspacios+"|"+numeroAsteriscos);
			System.out.println("            "+"|"+numeroNumerales);
			//System.out.println("---------------------------------------------------");
		}
		System.out.println("Cada * y # representa "+ max +" comparendos (o fraccion)");
	}





}
