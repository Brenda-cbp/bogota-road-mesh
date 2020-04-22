package model.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	public String darTipoDetencion() {
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

	public String toString() {
		return "OBJECTID=" + objectId + "\n" + "FECHA_HORA=" + fecha_hora + "\n" + "DES_INFRAC=" + des_infrac
				+ "\n" + "MEDIO_DETE=" + medio_dete +  "\n" +"CLASE_VEHI=" + clase_vehi +  "\n" +"TIPO_SERVI=" + tipo_servi
				+  "\n" +"INFRACCION=" + infraccion + "\n" + "LOCALIDAD=" + localidad +  "\n" +"MUNICIPIO=" + municipio +  "\n" +"latitud="
				+ latitud +  "\n" +"longitud=" + longitud;
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
	public int IntentoGravedad(Comparendo o)
	{
		if(tipo_servi.compareToIgnoreCase(o.darTipoServicio())==0) 
			return compareCodigo(o);
		else return tipo_servi.compareToIgnoreCase(o.darTipoServicio());
	}
	@Override
	/**
	 * Compara el comparendo actual con el que llega por parametro se compara
	 * por fecha, si las fechas son iguales copmara por ID return >0 si el
	 * actual es mayor al parametro, 0 si son iguales <0 de lo contario
	 */
	public int compareTo(Comparendo o) {
		if(darLatitud() == o.darLatitud())
			return 0;
		else if(darLatitud() > o.darLatitud())
			return 1;
		return -1;
	}
}
