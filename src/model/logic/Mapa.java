package model.logic;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.teamdev.jxmaps.swing.MapView;

import model.data_structures.Graph;

import com.teamdev.jxmaps.*;

// basado en https://www.youtube.com/watch?v=-ofmjUWQEAI
public class Mapa extends MapView{

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
					map.setCenter(new LatLng(4.7110, 74.0721));
					map.setZoom(11.0);
				}
			}
		});
		frame.add(this,BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setVisible(true);
	}
	public void pintarGrafo(Graph<Esquina, Integer> grafoAPintar) {
		
	}
}

