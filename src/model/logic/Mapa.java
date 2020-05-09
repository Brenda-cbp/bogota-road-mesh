package model.logic;

import java.awt.BorderLayout;
import java.util.Iterator;

import javax.swing.JFrame;

import com.teamdev.jxmaps.swing.MapView;

import model.data_structures.Edges;
import model.data_structures.Graph;
import model.data_structures.Lista;

import com.teamdev.jxmaps.*;

// basado en https://www.youtube.com/watch?v=-ofmjUWQEAI
public class Mapa extends MapView{

	public final double MINLONGITUD= -74.094723;
	public final double MAXLONGITUD= -74.062707;
	public final double MINLATITUD=4.597714;
	public final double MAXLATITUD= 4.621360;
	private Map map;	
	public Mapa(String nombre )
	{
		JFrame frame= 	new JFrame(nombre);
		setOnMapReadyHandler(new MapReadyHandler() {

			@Override
			public void onMapReady(MapStatus status) {
				if (status== MapStatus.MAP_STATUS_OK) {
					map=getMap();

					MapOptions mapOptions = new MapOptions(); 
					MapTypeControlOptions controlOptions = new MapTypeControlOptions();
					mapOptions.setMapTypeControlOptions(controlOptions);

					map.setOptions(mapOptions);
					map.setCenter(new LatLng(4.7110, -74.0721));
					map.setZoom(11.0);
				}
			}
		});
		frame.add(this,BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setVisible(true);
	}
	public void pintarGrafo(Graph<Esquina, Integer> grafoAPintar) {

		Iterator<Edges> listaArcos =grafoAPintar.darArcos().iterator();
	while(listaArcos.hasNext()) {

			Edges arco= listaArcos.next();
			Esquina E1= grafoAPintar.getInfoVertex((Integer)arco.darOrigen());
			Esquina E2= grafoAPintar.getInfoVertex((Integer)arco.darDestino());
			if ( E1.darLatitud()<MAXLATITUD && E2.darLatitud()<MAXLATITUD && E1.darLongitud()<MAXLONGITUD && E2.darLongitud()<MAXLONGITUD && 
					E1.darLongitud()>MINLONGITUD && E2.darLongitud()>MINLONGITUD && E1.darLatitud()>MINLATITUD && E2.darLatitud()>MINLATITUD) 
			{
				LatLng[] arcs= new LatLng[2];
				LatLng paraEsquina1 = new LatLng (E1.darLatitud(),E1.darLongitud());
				LatLng paraEsquina2 = new LatLng (E2.darLatitud(),E2.darLongitud());
				arcs[0]= paraEsquina1;
				arcs[1]= paraEsquina2;
				
				/*
				Marker mark = new Marker(map);
				mark.setPosition(paraEsquina1);
				mark.setVisible(true);
				Marker mark2 = new Marker(map);
				mark2.setPosition(paraEsquina1);
				mark2.setVisible(true);
				 */
				
				Circle vertice1 = new Circle (map);
				vertice1.setCenter(paraEsquina1);
				vertice1.setRadius(1);
				CircleOptions co1 = new  CircleOptions();
				co1.setFillColor("#FF0000");
				co1.setFillOpacity(0.35);
				vertice1.setOptions(co1);
				vertice1.setVisible(true);

				Circle vertice2 = new Circle (map);
				vertice2.setCenter(paraEsquina2);
				vertice2.setRadius(1);
				CircleOptions co2 = new  CircleOptions();
				co2.setFillColor("#FF00");
				co2.setFillOpacity(0.35);
				vertice2.setOptions(co2);
				vertice2.setVisible(true);

				Polygon w = new Polygon(map);
				w.setPath(arcs);
				w.setVisible(true);
			}
		}
	}
}


