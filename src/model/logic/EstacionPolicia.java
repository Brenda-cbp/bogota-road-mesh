package model.logic;

public class EstacionPolicia
{
	private String  EPODESCRIP, EPODIR_SITIO, EPOSERVICIO, EPOHORARIO;
	private double EPOLATITUD, EPOLONGITU;
	private int OBJECTID, EPOTELEFON, EPOIULOCAL;
	public EstacionPolicia(int OBJECTID, String EPODESCRIP,String EPODIR_SITIO,String EPOSERVICIO,String EPOHORARIO,
			double EPOLATITUD, double EPOLONGITU, int EPOTELEFON, int EPOIULOCAL)
	{
		this.EPODESCRIP = EPODESCRIP;
		this.EPODIR_SITIO = EPODIR_SITIO;
		this.EPOHORARIO = EPOHORARIO;
		this.EPOIULOCAL = EPOIULOCAL;
		this.EPOLATITUD = EPOLATITUD;
		this.EPOLONGITU = EPOLONGITU;
		this.EPOSERVICIO = EPOSERVICIO;
		this.EPOTELEFON = EPOTELEFON;
		this.OBJECTID = OBJECTID;
	}
	public String getEPODESCRIP() {
		return EPODESCRIP;
	}
	public void setEPODESCRIP(String ePODESCRIP) {
		EPODESCRIP = ePODESCRIP;
	}
	public String getEPODIR_SITIO() {
		return EPODIR_SITIO;
	}
	public void setEPODIR_SITIO(String ePODIR_SITIO) {
		EPODIR_SITIO = ePODIR_SITIO;
	}
	public String getEPOSERVICIO() {
		return EPOSERVICIO;
	}
	public void setEPOSERVICIO(String ePOSERVICIO) {
		EPOSERVICIO = ePOSERVICIO;
	}
	public String getEPOHORARIO() {
		return EPOHORARIO;
	}
	public void setEPOHORARIO(String ePOHORARIO) {
		EPOHORARIO = ePOHORARIO;
	}
	public double getEPOLATITUD() {
		return EPOLATITUD;
	}
	public void setEPOLATITUD(double ePOLATITUD) {
		EPOLATITUD = ePOLATITUD;
	}
	public double getEPOLONGITU() {
		return EPOLONGITU;
	}
	public void setEPOLONGITU(double ePOLONGITU) {
		EPOLONGITU = ePOLONGITU;
	}
	public int getOBJECTID() {
		return OBJECTID;
	}
	public void setOBJECTID(int oBJECTID) {
		OBJECTID = oBJECTID;
	}
	public int getEPOTELEFON() {
		return EPOTELEFON;
	}
	public void setEPOTELEFON(int ePOTELEFON) {
		EPOTELEFON = ePOTELEFON;
	}
	public int getEPOIULOCAL() {
		return EPOIULOCAL;
	}
	public void setEPOIULOCAL(int ePOIULOCAL) {
		EPOIULOCAL = ePOIULOCAL;
	}
	@Override
	public String toString() {
		return "EstacionPolicia" + "\n" + "EPODESCRIP=" + EPODESCRIP +"\n"+ "EPODIR_SITIO=" + EPODIR_SITIO+"\n" + "EPOSERVICIO="
				+ EPOSERVICIO +"\n"+ "EPOHORARIO=" + EPOHORARIO +"\n"+ "EPOLATITUD=" + EPOLATITUD +"\n"+ "EPOLONGITU="
				+ EPOLONGITU +"\n"+ "OBJECTID=" + OBJECTID +"\n"+ "EPOTELEFON=" + EPOTELEFON +"\n"+ "EPOIULOCAL=" + EPOIULOCAL
				+ "";
	}
	
}
