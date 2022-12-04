package Tests;

import org.jgrapht.Graph;

import Ejercicios.CiudadPuntua;
import Ejercicios.Trayectoria;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Test parecido al de los ejemplos 1 y 2
	
	public static void testEjercicio2(String file) {
		
		Graph<CiudadPuntua, Trayectoria> g = GraphsReader.newGraph("ficheros/"+file+".txt",
				CiudadPuntua::ofFormat, 
				Trayectoria::ofFormat, 
				Graphs2::simpleGraph);
	}

}
