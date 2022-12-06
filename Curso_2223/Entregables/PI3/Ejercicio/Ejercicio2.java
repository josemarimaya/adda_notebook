package Ejercicios;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.color.GreedyColoring;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.interfaces.SpanningTreeAlgorithm.SpanningTree;
import org.jgrapht.alg.interfaces.VertexColoringAlgorithm.Coloring;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import datos.Carretera;
import datos.Ciudad;
import datos.Pasillo;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;

public class Ejercicio2 {
	
	/*Un grupo de amigos desea visitar una serie de ciudades haciendo uso de un tipo de
		transporte que sólo relaciona algunas de ellas. Se tiene un grafo no dirigido y ponderado
		cuyos vértices son dichas ciudades y cuyas aristas representan los posibles trayectos entre
		ellas. De cada Ciudad se conoce su puntuación (valor entero en [1,5]), basada en el interés
		que tienen dicho grupo de personas en visitarla, y de cada Trayecto se conoce su precio
		y duración*/
	
	/*a. Determine cuántos grupos de ciudades hay y cuál es su composición. Dos
	ciudades pertenecen al mismo grupo si están relacionadas directamente entre sí o
	si existen algunas ciudades intermedias que las relacionan. Muestre el grafo
	configurando su apariencia de forma que se coloree cada grupo de un color
	diferente*/
	
	public static void apartadoA(Graph<CiudadPuntua, Trayectoria> g, String file) {
		
		var alg = new ConnectivityInspector<>(g);
		List<Set<CiudadPuntua>> ls = alg.connectedSets();
		System.out.println("Hay " + ls.size() + " componentes conexas.");
		
		GraphColors.toDot(g, 
				"resultados/ejercicio2/" + file + "A.gv", 
				x -> x.ciudad(), 
				x -> "",
				v -> GraphColors.color(asignaColor(v, ls, alg)),
				e -> GraphColors.color(asignaColor(g.getEdgeSource(e), ls, alg))); //La arista tendrá el mismo color que su vertice origen

		System.out.println(file + "A.gv generado en " + "resultados/ejercicio2");
		
		
	}
	
	/*b. Determine cuál es el grupo de ciudades a visitar si se deben elegir las ciudades
	conectadas entre sí que maximice la suma total de las puntuaciones. Muestre el
	grafo configurando su apariencia de forma que se resalten dichas ciudades.*/
	
	public static void apartadoB(Graph<CiudadPuntua, Trayectoria> g, String file) {
	
	}
	
	/*c. Determine cuál es el grupo de ciudades a visitar si se deben elegir las ciudades
		conectadas entre sí que den lugar al camino cerrado de menor precio que pase por
		todas ellas. Muestre el grafo configurando su apariencia de forma que se resalte
		dicho camino*/
	
	// Usamos el Ejemplo4 como referencia para hacer este apartado
	
	public static void apartadoC(Graph<CiudadPuntua, Trayectoria> g, String file) {
		GreedyVCImpl<CiudadPuntua, Trayectoria> colores = new GreedyVCImpl<>(g);
		Set<CiudadPuntua> ciudades = colores.getVertexCover();
		
		// Predicados para que se coloreen los vértices y aristas del camino mínimo
		
		Predicate<CiudadPuntua> pv = c-> ciudades.contains(c);
		Predicate<Trayectoria> pe = p-> ciudades.contains(g.getEdgeSource(p)) && ciudades.contains(g.getEdgeTarget(p));
		
		KruskalMinimumSpanningTree<CiudadPuntua, Trayectoria> caminoMinimo = new KruskalMinimumSpanningTree<>(g);
		SpanningTree<Trayectoria> tree = caminoMinimo.getSpanningTree();
		ConnectivityInspector<CiudadPuntua, Trayectoria> conjuntoCiudades = new ConnectivityInspector<>(g);
		System.out.println(String.format("Grupo de ciudades con menor precio:" + 
		// Mostramos la lista de las ciudades que entra
		conjuntoCiudades.connectedSets()+ 
				"y su precio: %.1f", tree.getWeight()));
		
		GraphColors.toDot(g, "resultados/ejercicio2/" + file + "C.gv",
				c-> c.toString(),
				e-> e.dinero().toString(),
				v-> GraphColors.colorIf(Color.blue, Color.black, ciudades.contains(v)),
				e-> GraphColors.colorIf(Color.blue, Color.black, tree.getEdges().contains(e)));
		
		System.out.println("Grafo del apartado C creado");
	}
	
	/*d. De cada grupo de ciudades, determinar cuáles son las 2 ciudades (no conectadas
	directamente entre sí) entre las que se puede viajar en un menor tiempo. Muestre
	el grafo configurando su apariencia de forma que se resalten las ciudades y el
	camino entre ellas.*/
	
	public static void apartadoD(Graph<CiudadPuntua, Trayectoria> g, String file) {
		
		ConnectivityInspector<CiudadPuntua, Trayectoria> conjuntoCiudades = new ConnectivityInspector<>(g);
		List<Set<CiudadPuntua>> ciudadesConectadas = conjuntoCiudades.connectedSets();
		var alg = new DijkstraShortestPath<>(g);
		// Creamos un map que guarde los caminos y los pesos del recorrido analizado
		Map<GraphPath<CiudadPuntua, Trayectoria>, Double> m = new HashMap<>();
		// Recorremos los dos set que hay de cada conjunto de ciudades
		for (Set<CiudadPuntua> sc : ciudadesConectadas) {
			// Recorremos cada una de las ciudades
			Iterator<CiudadPuntua> iterator = sc.iterator();
			while(iterator.hasNext()) {
				// Cogemos el set con todas las aristas que tiene la ciudad
				Set<Trayectoria> st = g.edgesOf(iterator.next());
				CiudadPuntua c = iterator.next();
				// Vamos a crear un segundo iterador de las ciudades conectadas para ver cuál coincide que no está entre sí
				Iterator<CiudadPuntua> iterator2 = sc.iterator();
				while(iterator2.hasNext()) {
					CiudadPuntua c2 = iterator2.next();
					// Si no estamos observando la misma ciudad
					if(!(c2.equals(c)) 
							// Y la ciudad observada no está en la lista de aristas de la ciudad
							&& !(st.contains(c2))) {
						// Hacemos el camino entre ambos vértices
						GraphPath<CiudadPuntua, Trayectoria> gp = alg.getPath(c, c2);
						// Cogemos el valor del camino entre los dos vértices
						Double pesoCamino = alg.getPathWeight(c, c2);
						m.put(gp, pesoCamino);
					}
				}
			}
			/*for (CiudadPuntua cp : sc) {
				g.vertexSet().stream().anyMatch(e-> !(g.containsEdge(e, cp))).findFirst().get();
			}*/
		}
		
		System.out.println(m);
	}
	
	
	// Es el mismo método auxiliar del ejemplo 2 pero con nuestros records para el ejercicio
	private static Color asignaColor(CiudadPuntua v, List<Set<CiudadPuntua>> ls, ConnectivityInspector<CiudadPuntua, Trayectoria> alg) {
		Color[] vc = Color.values(); //Una lista de colores
		Set<CiudadPuntua> s = alg.connectedSetOf(v); //La componente conexa a la que pertenece el vértice
		return vc[ls.indexOf(s)]; //Qué componente conexa es dentro de la lista
	}

}
