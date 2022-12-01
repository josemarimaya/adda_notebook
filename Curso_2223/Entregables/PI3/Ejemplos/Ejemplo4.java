package Ejemplos;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;

import datos.Pasillo;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Ejemplo4 {

	public static Set<String> apartadoA(Graph<String, Pasillo> grafo) {
		// TODO Auto-generated method stub
		GreedyVCImpl<String, Pasillo> algA = new GreedyVCImpl<>(grafo);
		Set<String> cruces = algA.getVertexCover();
		return cruces;
	}

	public static void apartadoByC(Graph<String, Pasillo> grafo, Set<String> cruces, String file) {
		// TODO Auto-generated method stub
		Predicate<String> pv = c-> cruces.contains(c);
		Predicate<Pasillo> pe = p-> cruces.contains(grafo.getEdgeSource(p)) && cruces.contains(grafo.getEdgeTarget(p));
		
		Graph<String, Pasillo> subgraph = SubGraphView.of(grafo, pv, pe);
		
		// Para saber cuantos equipos --> componentes conexas
		
		ConnectivityInspector<String, Pasillo> algB1 = new ConnectivityInspector<>(grafo);
		System.out.println("Número de equipos necesarios: " + algB1.connectedSets());
		
		// Para saber cuantos metros de cable
		
		KruskalMinimumSpanningTree<String, Pasillo> algB2 = new KruskalMinimumSpanningTree<>(grafo);
		SpanningTree<Pasillo> tree = algB2.getSpanningTree();
		System.out.println(String.format("Metros de cable necesarios: %.1f", tree.getWeight()));
		
		// Apartado C
		
		GraphColors.toDot(grafo, "resultados/ejemplo4/" + file + ".gv",
				c-> c.toString(),
				e-> e.mts().toString(),
				v-> GraphColors.colorIf(Color.blue, Color.black, cruces.contains(v)),
				e-> GraphColors.colorIf(Color.blue, Color.black, tree.getEdges().contains(e)));
	}

}
