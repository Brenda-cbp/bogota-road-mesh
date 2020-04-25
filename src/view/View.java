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

	public int printMenu() {
		System.out.println();
		int rta=0;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("1. Obtener los M comparendos con mayor gravedad");
		System.out.println("2. Buscar los comparendos por mes y dia de la semana");
		System.out.println("3. Buscar los comparendos que tienen una fecha-hora en un rango y que son de una localidad dada");
		System.out.println("4. Dar los m comparendos mas cercanos a la estacion de policia del Campin ");
		System.out.println("5. Dar los comparendos por: Medio deteccion, Tipo de vehiculo y Localidad");
		System.out.println("6. Dar comparendos en rango de Latituddes.");
		System.out.println("7. Dar el número de comparendos en un rango de D dias. ");
		System.out.println("8. Mostrar el costo de los tiempos de espera hoy en día sin implementar el nuevo sistema");
		System.out.println("9. Mostrar el costo de los tiempos de espera usando el nuevo sistema"); 
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
}
