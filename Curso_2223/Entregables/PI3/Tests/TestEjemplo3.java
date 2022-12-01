package Tests;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import Ejemplos.Ejemplo3;
import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;

public class TestEjemplo3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testEjemplo3("Comensales");
	}
	
	public static void testEjemplo3(String file) {
		Graph<String, DefaultEdge> g = Graphs2.simpleGraph(
				String::new, DefaultEdge::new, false);
		
		Files2.streamFromFile("ficheros/"+ file + ".txt").forEach(linea ->{
			// Separamos por coma los valores del ficheros
			String[] v = linea.split(",");
			// Creamos los vértices para los vértices del fichero
			g.addVertex(v[0]);
			g.addVertex(v[1]);
			// Creamos las aristas entre vértices
			g.addEdge(v[0], v[1]);
		});
		
		GraphColors.toDot(g, "resultados/ejemplo3/" + file + "_inicial.gv");
		Ejemplo3.todosLosApartados(g, file);
	}

}
