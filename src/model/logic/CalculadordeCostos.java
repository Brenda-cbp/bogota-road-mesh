package model.logic;

import java.util.Iterator;

import model.data_structures.Lista;

public class CalculadordeCostos
{
	public static String darCostos(Lista<String> lista)
	{
	
		int tiempoTotal4 = 0;
		int tiempoTotal40 = 0;
		int tiempoTotal400 = 0;
		
		int tiempoMaximo4= 0;
		int tiempoMaximo40 = 0;
		int tiempoMaximo400 = 0;
		
		int tiempoMinimo4 = 900000;
		int tiempoMinimo40 = 900000;
		
		int tiempoPromedio4 = 0;
		int tiempoPromedio40 = 0;
		int tiempoPromedio400 = 0;
		
		int totalComparendos400 = 0;
		int totalComparendos40 = 0;
		int totalComparendos4 = 0;
		
		int comparendosSinProcesar4 = 0;
		int comparendosSinProcesar40 = 0;
		int comparendosSinProcesar400 = 0;
		
		int tiempoSinProcesar40 = 0;
		int tiempoSinProcesar400 = 0;
		
		Iterator<String> it = lista.iterator();
		while(it.hasNext())
		{
			String[] cajita = it.next().split(Modelo.SEPARADOR);
			int comparendos400 = Integer.parseInt(cajita[0]) + comparendosSinProcesar400;
			int comparendos40 = Integer.parseInt(cajita[1]) + comparendosSinProcesar40;
			int comparendos4 = Integer.parseInt(cajita[2]) + comparendosSinProcesar4;
			totalComparendos4 += comparendos4;
			totalComparendos40 += comparendos40;
			totalComparendos400 += comparendos400;
			int capacidad = 1500 - comparendos400;
			if(capacidad > 0)
			{
				if(tiempoSinProcesar400 < tiempoMinimo40)
					tiempoMinimo40 = tiempoSinProcesar40;
				
				comparendosSinProcesar400 = 0;
				tiempoSinProcesar400 = 0;					
				capacidad -= comparendos40;
				
				if(capacidad > 0)
				{
					if(tiempoSinProcesar40 < tiempoMinimo4)
						tiempoMinimo4 = tiempoSinProcesar40;
					
					comparendosSinProcesar40 = 0;
					tiempoSinProcesar40 = 0;
					capacidad -= comparendos4;
					
					if(capacidad > 0)
					{
						comparendosSinProcesar4 = 0;
					}		
					else
					{
						comparendosSinProcesar4 -= capacidad;
						tiempoTotal4 += comparendosSinProcesar4;
					}
				}
				else
				{
					comparendosSinProcesar40 -= capacidad;
					comparendosSinProcesar4 += comparendos4;
					
					tiempoTotal40 += comparendosSinProcesar40;
					tiempoTotal4 += comparendosSinProcesar4;
					
				}
			}
			else
			{
				
				comparendosSinProcesar400 -= capacidad;
				comparendosSinProcesar40 += comparendos40;
				comparendosSinProcesar4 += comparendos4;
				
				tiempoTotal400 += comparendosSinProcesar400;
				tiempoTotal40 += comparendosSinProcesar40;
				tiempoTotal4 += comparendosSinProcesar4;
				
			}	
		}
		tiempoTotal400 += (int)(comparendosSinProcesar400/1500);
		tiempoTotal40 += ((int)(comparendosSinProcesar400/1500) + (int)(comparendosSinProcesar40 / 1500) );
		tiempoTotal4 += (comparendosSinProcesar400/1500 + (int)(comparendosSinProcesar40 / 1500) + (int)(comparendosSinProcesar4 / 1500));
		tiempoPromedio4 = totalComparendos4 / tiempoTotal4;
		tiempoPromedio40 = totalComparendos40 / tiempoTotal40;
		tiempoPromedio400 = totalComparendos400 / tiempoTotal400;
		String respuesta = 0 + Modelo.SEPARADOR + tiempoMaximo400 + Modelo.SEPARADOR +  tiempoPromedio400 + Modelo.SEPARADOR +
						tiempoMinimo40 + Modelo.SEPARADOR + tiempoMaximo40 + Modelo.SEPARADOR +  tiempoPromedio40 + Modelo.SEPARADOR +
						tiempoMinimo4 + Modelo.SEPARADOR + tiempoMaximo4 + Modelo.SEPARADOR +  tiempoPromedio4 + Modelo.SEPARADOR;
	return respuesta;
	}
}
