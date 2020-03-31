package model.logic;

//Solucion tomada del libro guia en:https://algs4.cs.princeton.edu/14analysis/Stopwatch.java.html
public class Cronometro { 

    private long start;
    private double tiempo;

    /**
     * Crea un nuevo cronometro
     * Recuerde iniciar toma de tiempo antes de usarlo
     */
    public Cronometro() 
    {
       tiempo = 0;
    } 
    /**
     * Registra el momento en que inicia la operacion;
     */
    public void iniciar()
    {
    	 start = System.currentTimeMillis();
    }

    /**
     * Retorna el tiempo en segundos que paso desde la creacion del cronometro
     * @return tiempo que paso desde la cracion del cronometro en segundos
     */
    public double registrarTiempo() {
        long now = System.currentTimeMillis();
        tiempo = (now - start) / 1000.0;
        return tiempo;
    }
    /**
     * Retorna el tiempo regitrado en el momento
     * @return tiempo registrado
     */
    public double darTiempo()
    {
    	return tiempo;
    }
}
