package Ejercicios;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

public class Ejercicio3 {

	/*En un taller de extraescolares se ofertan una serie de actividades. Se conocen los
		alumnos que están interesados en acudir, de forma que cada alumno ha indicado las
		actividades a las que asistirá. Para minimizar el tiempo en el que el taller estará abierto,
		se van a impartir actividades en paralelo de forma que se minimicen las franjas horarias
		con actividades.
		a. Determine qué actividades deberían impartirse en paralelo y cuántas franjas
		horarias son necesarias, teniendo en cuenta que no se pueden poner en paralelo
		actividades que tengan alumnos en común.
		b. Muestre el problema como un grafo en el que los vértices son las actividades, y
		configure su apariencia de forma que se muestren los vértices coloreados en
		función de la franja horaria en la que se impartan*/
	
	/*a. Determine qué actividades deberían impartirse en paralelo y cuántas franjas
		horarias son necesarias, teniendo en cuenta que no se pueden poner en paralelo
		actividades que tengan alumnos en común.*/
	
	// Es un problema de coloreado de grafos por incompatibilidades, mirar ejemplo3
	
	public static void apartadoA(Graph<String, DefaultEdge> g, String file) {
		
		GreedyColoring<String, DefaultEdge> alg = new GreedyColoring<>(g);
		
		Coloring<String> coloring = alg.getColoring();
		
		System.out.println("Franjas horarias necesarias: " + coloring.getNumberColors());
		
		//System.out.println("Composición de las mesas: ");
		
	}
	
	/*b. Muestre el problema como un grafo en el que los vértices son las actividades, y
		configure su apariencia de forma que se muestren los vértices coloreados en
		función de la franja horaria en la que se impartan*/
	
	
}
