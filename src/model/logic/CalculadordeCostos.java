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

		int tiempoMaximo4 = 0;
		int tiempoMaximo40 = 0;
		int tiempoMaximo400 = 0;

		int tiempoMinimo4 = 999999;
		int tiempoMinimo40 = 99999;

		int tiempoPromedio4 = 0;
		int tiempoPromedio40 = 0;
		int tiempoPromedio400 = 0;

		int totalComparendos400 = 0;
		int totalComparendos40 = 0;
		int totalComparendos4 = 0;

		int tiempoSinProcesar40 = 0;
		int tiempoSinProcesar400 = 0;
		int tiempoSinProcesar4 = 0;
		Iterator<String> it = lista.iterator();
		while (it.hasNext())
		{
			String[] cajita = it.next().split(Modelo.SEPARADOR);
			int comparendos400 = Integer.parseInt(cajita[0]);
			int comparendos40 = Integer.parseInt(cajita[1]);
			int comparendos4 = Integer.parseInt(cajita[2]);
			totalComparendos4 += comparendos4;
			totalComparendos40 += comparendos40;
			totalComparendos400 += comparendos400;
			int capacidad = 1500 - comparendos400;
			if (capacidad > 0)
			{
				if (tiempoSinProcesar400 < tiempoMinimo40)
					tiempoMinimo40 = tiempoSinProcesar400;
				tiempoSinProcesar400 = 0;
				capacidad -= comparendos40;

				if (capacidad > 0)
				{
					if (tiempoSinProcesar40 < tiempoMinimo4)
						tiempoMinimo4 = tiempoSinProcesar40;

					tiempoSinProcesar40 = 0;
					capacidad -= comparendos4;

					if (capacidad > 0)
					{
						tiempoSinProcesar4 = 0;
					}
					else
					{						
						tiempoSinProcesar4++;
						if (comparendos4 >= 1500)
							tiempoPromedio4 += (comparendos4) / 1500 - 1;
						tiempoMaximo4 = tiempoPromedio4 + tiempoSinProcesar4;

					}
				}
				else
				{

					tiempoSinProcesar40++;
					if (comparendos4 >= 1500)
						tiempoPromedio4 += (comparendos4) / 1500 - 1;
					if (comparendos40 > 1500)
						tiempoPromedio40 += (comparendos40) / 1500;
					tiempoMaximo40 = tiempoPromedio40 +tiempoSinProcesar40;

				}
			}
			else
			{

				tiempoSinProcesar400++;
				if (comparendos4 >= 1500)
					tiempoPromedio4 += (comparendos4) / 1500 - 1;
				if (comparendos40 >= 1500)
					tiempoPromedio40 += (comparendos40) / 1500;
				if (comparendos400 >= 1500)
					tiempoPromedio400 += (comparendos400) / 1500;
				tiempoMaximo400 = tiempoPromedio400 + tiempoSinProcesar400;

			}

		}
		if(tiempoMinimo4 == 999999)
			tiempoMinimo4 = 365;
		if(tiempoMinimo40 == 99999)
			tiempoMinimo40 = 365;
			
		String respuesta = 0 + Modelo.SEPARADOR + tiempoMaximo400 + Modelo.SEPARADOR + tiempoPromedio400
				+ Modelo.SEPARADOR + tiempoMinimo40 + Modelo.SEPARADOR + tiempoMaximo40 + Modelo.SEPARADOR
				+ tiempoPromedio40 + Modelo.SEPARADOR + tiempoMinimo4 + Modelo.SEPARADOR + tiempoMaximo4
				+ Modelo.SEPARADOR + tiempoPromedio4 +  Modelo.SEPARADOR +  totalComparendos400 + Modelo.SEPARADOR + totalComparendos40 + Modelo.SEPARADOR + totalComparendos4;
		return respuesta;
	}
}
