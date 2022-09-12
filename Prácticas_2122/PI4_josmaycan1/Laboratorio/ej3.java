package laboratorio;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Shape;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;

public class ej3 {
	
	

	public static void main(String[] args) {
		
		/*Se desea ubicar un conjunto de n comensales en mesas, de forma que hay ciertos
		comensales que no se pueden sentar en la misma mesa por ser incompatibles entre
		ellos. Existe simetría en las incompatibilidades.*/
		
			/*Diseñe un algoritmo que minimice el número de mesas necesarias para sentar
		a todos los comensales teniendo en cuenta las incompatibilidades.*/
		
		//Modelado de grafo de incompatibilidad, vamos a usar el coloreado de grafo
		
		//Contruimos el grafo con vertice que tenga de contenido el nombre y el default es la arista ya que no tiene peso
		
		//Así tenemos un grafo con vertices de string y aristas default sin peso, ponemos al final el false para indicar que no es un grafo ponderado
		Graph<String,  DefaultEdge> grafo = Graphs2.simpleGraph(String::new, DefaultEdge::new, false);
		
		Files2.streamFromFile("ficheros/Comensales.txt")
		.forEach(linea->{
			//Hacemos el split de los valores que tienen incompatibilidades
			String[] separado = linea.split(",");
			//Le creamos los vertices al grafo que están separados
			grafo.addVertex(separado[0]);
			grafo.addVertex(separado[1]);
			//Creamos la arista entre vertices que tienen que estar separados
			grafo.addEdge(separado[0], separado[1]);
		});
		
		//Inicializamos el greedycoloring que vamos a usar para el grafo de incompatibilidades
		var alg = new GreedyColoring<>(grafo);
		
		//Cogemos el grafo que queremos colorear y le aplicamos el coloreado
		Coloring<String> coloring = alg.getColoring();
		
		/*Muestre el tamaño y la composición de cada una de las mesas.*/
		
		//Cogemos el numero de colores que necesitamos, es decir, las incompatibilidades
		System.out.println("Mesas necesarias: " + coloring.getNumberColors());
		
		//Nos devuelven los vertices que son compatibles entre sí, en este problema son los que pueden compartir mesa
		System.out.println("Organizacion de mesas: " + coloring.getColorClasses());
		
		//El String es el vertice y el integer es el color identificado, que según la teoría del color nunca va a ser mayor que 4
		Map<String, Integer> map = coloring.getColors();
		
		/*Muestre el grafo configurando su apariencia de forma que todos los
		comensales de la misma mesa se muestren del mismo color.*/
		
		GraphColors.toDot(grafo, "salida/Ejemplo3.gv",
				v-> v,
				a-> "",
				//Le añadimos forma a los vertices
				v-> GraphColors.all(GraphColors.color(map.get(v)), GraphColors.shape(Shape.box)),
				//Le añadimos estilo a las aristas
				a-> GraphColors.style(Style.dotted));
	}

}
