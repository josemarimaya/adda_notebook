package Ejemplos;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Style;

public class Ejemplo3 {

	public static void todosLosApartados(Graph<String, DefaultEdge> g, String file) {
		// TODO Auto-generated method stub
		GreedyColoring<String, DefaultEdge> alg = new GreedyColoring<>(g);
		
		Coloring<String> coloring = alg.getColoring();
		
		System.out.println("Mesas necesarias: " + coloring.getNumberColors());
		
		System.out.println("Composición de las mesas: ");
		
		List<Set<String>> composicion = coloring.getColorClasses();
		for (int i = 0; i < composicion.size(); i++) {
			// Indicamos quién está en cada mesa
			System.out.println("Mesa número" + (i+1) + ": " + composicion.get(i));
		}
		
		// Creamos el grafo pintado
		
		Map<String, Integer> map = coloring.getColors();
		
		GraphColors.toDot(g, "resultados/ejemplo3/" + file + "_coloreado.gv",
				v-> v.toString(),
				// No queremos que escriba nada en las aristas
				e-> "",
				v-> GraphColors.color(map.get(v)),
				e-> GraphColors.style(Style.solid));
		
		// Usamos graphviz para ver el resultado
		System.out.println(file + "coloreado_C.gv generado en resultados/ejemplo3");
		
	}

}
