package Ejemplos;

import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import datos.Carretera;
import datos.Ciudad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.graphs.Graphs2;

public class Ejemplo2 {

	// BRG: Para no usar el funcional abajo (es otra opción)

	private static Boolean someEdgeEqualsTo1000(Ciudad v, SimpleWeightedGraph<Ciudad, Carretera> gf) {
		boolean res = false;

		for (Carretera c : gf.edgesOf(v)) {
			if (c.km() == 1000.) {
				res = true;
				break;
			}
		}

		return res;
	}


	/*
	 * A) Obtener un nuevo grafo que contenga los mismos vértices y sea completo.
	 * Las nuevas aristas tendrán un peso muy grande. Muestre el grafo resultante
	 * configurando su apariencia de forma que se resalten las nuevas aristas y los
	 * vértices de dichas aristas.
	 */
	public static void apartadoA(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {

		Graph<Ciudad, Carretera> g = Graphs2.explicitCompleteGraph(gf, // Grafo del que parte
				1000., // Peso de las nuevas aristas
				Graphs2::simpleWeightedGraph, // Tipo de grafo
				() -> Carretera.of(1000.), // Generador de aristas
				Carretera::km); // Atributo que constituirá el peso

		GraphColors.toDot(g,
				"resultados/ejemplo2/" + file + "A.gv", 
				x -> x.nombre(), 
				x -> "",
				v -> GraphColors.colorIf(Color.blue, g.edgesOf(v).stream().anyMatch(e -> ((Carretera) e).km() == 1000.)),
				// v->GraphColors.colorIf(Color.blue, someEdgeEqualsTo1000(v, g)), BRG: Para no usar funcional y streams
				e -> GraphColors.colorIf(Color.blue, (g.getEdgeWeight(e) == 1000.)));

		System.out.println(file + "A.gv generado en " + "resultados/ejemplo2");
	}
	
	
	// ======================================================================================================================================================================================================

	
	public static Ciudad getVertexOfCiudad(Graph<Ciudad, Carretera> graph, String nombre) {
		return graph.vertexSet().stream().filter(c -> c.nombre().equals(nombre)).findFirst().get();
	}
	
	// B) A partir del grafo original, dados dos vértices v1 y v2 de dicho grafo
	// obtener
	// el camino mínimo para ir de v1 a v2. Muestre el grafo original configurando
	// su apariencia
	// de forma que se resalte el camino mínimo para ir de v1 a v2

	
	public static void apartadoB(SimpleWeightedGraph<Ciudad, Carretera> gf, String file, String c1, String c2) {

		//Camino minimo entre dos vertices --> Dijkstra
		var alg = new DijkstraShortestPath<>(gf);
		Ciudad origen = getVertexOfCiudad(gf, c1);
		Ciudad destino = getVertexOfCiudad(gf, c2);
		GraphPath<Ciudad, Carretera> path = alg.getPath(origen, destino);

		GraphColors.toDot(gf, 
				"resultados/ejemplo2/" + file + "B.gv", 
				x -> x.nombre(), 
				x -> x.nombre(),
				v -> GraphColors.styleIf(Style.bold, path.getVertexList().contains(v)),
				e -> GraphColors.styleIf(Style.bold, path.getEdgeList().contains(e)));

		System.out.println(file + "B.gv generado en " + "resultados/ejemplo2");

	}
	

	// ======================================================================================================================================================================================================

	

	// C) Obtener un nuevo grafo dirigido con los mismos vértices y que por cada
	// arista
	// original tenga dos dirigidas y de sentido opuesto con los mismos pesos

	public static void apartadoC(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {

		Graph<Ciudad, Carretera> g = Graphs2.toDirectedWeightedGraph(gf,
																	(Carretera x) -> Carretera.of(x.km(), x.nombre())); //Esta factoría hay que implementarla en Carretera

		GraphColors.toDot(g, 
						"resultados/ejemplo2/" + file + "C.gv", 
						x -> x.nombre(), 
						x -> x.nombre(),
						v -> GraphColors.color(Color.black), 
						e -> GraphColors.style(Style.solid));

		System.out.println(file + "C.gv generado en " + "resultados/ejemplo2");
	}
	
	
	// ======================================================================================================================================================================================================


	// D) Calcule las componentes conexas del grafo original. Muestre el grafo
	// original
	// configurando su apariencia de forma que se coloree cada componente conexa de
	// un color diferente

	public static void apartadoD(SimpleWeightedGraph<Ciudad, Carretera> gf, String file) {

		var alg = new ConnectivityInspector<>(gf);
		List<Set<Ciudad>> ls = alg.connectedSets();
		System.out.println("Hay " + ls.size() + " componentes conexas.");

		GraphColors.toDot(gf, 
				"resultados/ejemplo2/" + file + "D.gv", 
				x -> x.nombre(), 
				x -> x.nombre(),
				v -> GraphColors.color(asignaColor(v, ls, alg)),
				e -> GraphColors.color(asignaColor(gf.getEdgeSource(e), ls, alg))); //La arista tendrá el mismo color que su vertice origen

		System.out.println(file + "D.gv generado en " + "resultados/ejemplo2");
	}

	private static Color asignaColor(Ciudad v, List<Set<Ciudad>> ls, ConnectivityInspector<Ciudad, Carretera> alg) {
		Color[] vc = Color.values(); //Una lista de colores
		Set<Ciudad> s = alg.connectedSetOf(v); //La componente conexa a la que pertenece el vértice
		return vc[ls.indexOf(s)]; //Qué componente conexa es dentro de la lista
	}

}

