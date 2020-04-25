package model.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Comparendo implements Comparable<Comparendo> {
	private int objectId;
	private Date fecha_hora;
	private String des_infrac;
	private String medio_dete;
	private String clase_vehi;
	private String tipo_servi;
	private String infraccion;
	private String localidad;
	private String municipio;

	private double latitud;
	private double longitud;

	public Comparendo(int OBJECTID, Date FECHA_HORA, String DES_INFRAC, String MEDIO_DETE, String CLASE_VEHI,
			String TIPO_SERVI, String INFRACCION, String LOCALIDAD, String MUNICIPIO, double longitudd,
			double latitudd) {

		objectId = OBJECTID;
		fecha_hora = FECHA_HORA;
		des_infrac = DES_INFRAC;
		medio_dete = MEDIO_DETE;
		clase_vehi = CLASE_VEHI;
		tipo_servi = TIPO_SERVI;
		infraccion = INFRACCION;
		localidad = LOCALIDAD;
		municipio = MUNICIPIO;
		longitud = longitudd;
		latitud = latitudd;
	}

	public double darLatitud() {
		return latitud;
	}

	public double darLongitud() {
		return longitud;
	}

	public int darID() {
		return objectId;
	}

	public Date darfecha() {
		return fecha_hora;
	}

	public String darMedioDete() {
		return medio_dete;
	}

	public String darclaseVehiculo() {
		return clase_vehi;
	}

	public String darTipoServicio() {
		return tipo_servi;
	}

	public String darInfraccion() {
		return infraccion;
	}

	public String darLocalidad() {
		return localidad;
	}

	public String darDescripcion() {
		return des_infrac;
	}
	
	public int darNumeroMes() {
		Calendar calendario= Calendar.getInstance();
		calendario.setTime(fecha_hora);
		return calendario.get(Calendar.MONTH);
	}
	public String darNombreMes(int mes) {
		Date fecha= new Date(120,mes,11);
		Calendar calendario= Calendar.getInstance();
		calendario.setTime(fecha);
		return calendario.getDisplayName(Calendar.MONTH,Calendar.LONG_FORMAT,Locale.ENGLISH);
	}
	public String darInicialSemana() {
		Calendar calendario= Calendar.getInstance();
		calendario.setTime(fecha_hora);
		Locale espanol = new Locale("es", "ES");
		String dia=calendario.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT_FORMAT, espanol);
		if (dia.equalsIgnoreCase("mié.")) 
			return "I";
		return dia.substring(0, 1).toUpperCase();
	}
	public String toString() {
		return "OBJECTID=" + objectId + "\n" + "FECHA_HORA=" + fecha_hora + "\n" + "DES_INFRAC=" + des_infrac
				+ "\n" + "MEDIO_DETE=" + medio_dete +  "\n" +"CLASE_VEHI=" + clase_vehi +  "\n" +"TIPO_SERVI=" + tipo_servi
				+  "\n" +"INFRACCION=" + infraccion + "\n" + "LOCALIDAD=" + localidad +  "\n" +"MUNICIPIO=" + municipio +  "\n" +"latitud="
				+ latitud +  "\n" +"longitud=" + longitud ;
	}

	/**
	 * Compara por localidad el comparendo
	 * 
	 * @param o
	 *            Comparendo que se desea comparar
	 * @return >0 si el actual es mayor al parametro, 0 si son iguales <0 de lo
	 *         contario
	 */
	public int compareLocalidad(Comparendo o) {
		return darLocalidad().compareTo(o.darLocalidad());
	}

	/**
	 * Compara por fecha SOLO fecha
	 */
	public int compareFecha(Comparendo o) {
		return darfecha().compareTo(o.darfecha());
	}

	/**
	 * Compara por Id del comparendo
	 * 
	 * @param o
	 *            Comparendo que se desea comparar
	 * @return >0 si el actual es mayor al parametro, 0 si son iguales <0 de lo
	 *         contario
	 */
	public int compareCodigo(Comparendo o) {
		return darInfraccion().compareTo(o.darInfraccion());
	}
	public int compararID(Comparendo o)
	{
		if(objectId == o.darID())
			return 0;
		else if(objectId > o.darID())
			return 1;
		return -1;
	}
	@Override
	/**
	 * Compara el comparendo actual con el que llega por parametro se compara
	 * por fecha, si las fechas son iguales copmara por ID return >0 si el
	 * actual es mayor al parametro, 0 si son iguales <0 de lo contario
	 */
	public int compareTo(Comparendo o) {
		return fecha_hora.compareTo(o.darfecha());
	}
	public static class ComparatorGravedad implements Comparator<Comparendo>
	{
		public int compare(Comparendo o1, Comparendo o2) {
			if(o1.darTipoServicio().compareToIgnoreCase(o2.darTipoServicio())==0) 
				return o1.compareCodigo(o2);
			else return o1.darTipoServicio().compareToIgnoreCase(o2.darTipoServicio());
				
		}
	}
	public static class ComparatorFecha implements Comparator<Comparendo>{
		public int compare(Comparendo o1, Comparendo o2) {
			return o1.darfecha().compareTo(o2.darfecha());
		}
	}
	/**
	 * Compara dos comparendos por su cercania a la estacion de policia
	 */
	public static class ComparatorDistanciaPolicia implements Comparator<Comparendo>{
		public int compare(Comparendo o1, Comparendo o2) {
			double d1 = DistanciaHaversiana.distance(Modelo.LAT_POLICIA, Modelo.LONG_POLICIA, o1.darLatitud(), o1.darLongitud());
			double d2 = DistanciaHaversiana.distance(Modelo.LAT_POLICIA, Modelo.LONG_POLICIA, o2.darLatitud(), o2.darLongitud());
			if(d1 > d2)
				return -1;
			if(d2 > d1)
				return 1;
			return 0;
		}
	}
//	/**
//	 * Compara dos comparendos segun su Latitud, SOLO LATITUD
//	 * 
//	 */
//	public static class ComparatorLatitud implements Comparator<Comparendo>
//	{
//		public int compare(Comparendo o1, Comparendo o2)
//		{
//			double l1 = o1.darLatitud();
//			double l2 =o2.darLatitud();
//			if(l1 == l2)
//				return 0;
//			if(l1 > l2)
//				return 1;
//			return -1;
//		}
//	}
}
