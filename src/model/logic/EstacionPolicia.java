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
	public int darId()
	{
		return OBJECTID;
	}
	public int darTelefono()
	{
		return EPOTELEFON;
	}
}
