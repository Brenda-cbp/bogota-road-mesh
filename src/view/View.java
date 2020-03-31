package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import model.logic.Modelo;

public class View {
	private final String CANCELAR = "CANCELAR";

	/**
	 * Metodo constructor
	 */
	public View() {

	}

	public void printMenu() {
		System.out.println("1. Crear Arreglo Dinamico de Strings");
		System.out.println("2. Agregar String");
		System.out.println("3. Buscar String");
		System.out.println("4. Eliminar String");
		System.out.println("5. Imprimir el Arreglo");
		System.out.println("6. Exit");
		System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
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
		System.out.println("Favor inserte " + parametro + " buscada.");
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
}
