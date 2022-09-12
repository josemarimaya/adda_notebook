package laboratorio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class ej2 {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph<Ciudad,Carretera> andalucia = GraphsReader.newGraph(
				//Ruta del archivo
				"ficheros/Andalucia.txt",
				//Creacion del vertice
				Ciudad::ofFormat,
				//Creacion de arista
				Carretera::ofFormat,
				//Tipo de grafo
				Graphs2::simpleWeightedGraph,
				//Peso del grafo
				Carretera::getKm);
		
		/*Obtener un nuevo grafo que contenga los mismos vértices y sea completo. Las
		nuevas aristas tendrán un peso muy grande. Muestre el grafo resultante
		configurando su apariencia de forma que se resalten las nuevas aristas y los
		vértices de dichas aristas.*/
		
		Graph<Ciudad,Carretera> andalucia_completo =
				//Creacion de un grafo completo
				Graphs2.explicitCompleteGraph(
						//El grafo que le pasamos por parametro
						andalucia,
						//Parametro cuando se nos pide un valor MUY GRANDE
						Double.MAX_VALUE, 
						//Creamos un grafo nuevo
						Graphs2::simpleWeightedGraph,
						//Pasamos el peso como parametro porque funciona así el explicit
						Carretera::ofWeight, 
						//Las aristas por parametro
						Carretera::getKm);
		
		/*A partir del grafo original, dados dos vértices v1 y v2 de dicho grafo obtener
		el camino mínimo para ir de v1 a v2. Muestre el grafo original configurando
		su apariencia de forma que se resalte el camino mínimo para ir de v1 a v2.*/
		
		//Filtro para ver las carreteras que tienen peso maximo
		Predicate<Carretera> pa1 = c-> andalucia_completo.getEdgeWeight(c) == Double.MAX_VALUE;
		
		//Recordamos que el andalucia completo con el edgeof nos devuelve un set y podemos usar stream
		Predicate<Ciudad> pv1 = c-> andalucia_completo.edgesOf(c).stream()
				//Queremos la ciudad que tenga cualquiera de sus aristas que cumpla el test
				.anyMatch(e->pa1.test(e));
		
		GraphColors.toDot(
				andalucia_completo,
				"salida/AndaluciaCompleto.gv",
				v->v.getNombre(),
				a->a.getNombre() + "--" + a.getKm(),
				//Primer color si se cumple la condicion, la segunda si no, y luego la condicion
				v-> GraphColors.colorIf(Color.orange, Color.gray, pv1.test(v)),
				a-> GraphColors.colorIf(Color.orange, Color.gray, pa1.test(a)));
		
		Ciudad origen = andalucia.vertexSet().stream().filter(c-> c.getNombre().equals("Sevilla"))
				.findFirst().orElse(null);
		
		Ciudad destino = andalucia.vertexSet().stream().filter(c-> c.getNombre().equals("Almeria"))
				.findFirst().orElse(null); 
		//Usar el var cuando no sepamos que tipo de variable usamos, pero no es recomendable
		var alg = new DijkstraShortestPath<>(andalucia);
		
		GraphPath<Ciudad, Carretera> camino_minimo = alg.getPath(origen, destino);
		
		//Predicate del vertice
		Predicate<Ciudad> pv2 = c-> camino_minimo.getVertexList().contains(c);
		//Predicate de las aristas
		Predicate<Carretera> pa2 = c-> camino_minimo.getEdgeList().contains(c);
		
		GraphColors.toDot(
				andalucia_completo,
				"salida/AndaluciaCompletoDijkstra.gv",
				v->v.getNombre(),
				a->a.getNombre() + "--" + a.getKm(),
				//Primer color si se cumple la condicion, la segunda si no, y luego la condicion
				v-> GraphColors.colorIf(Color.orange, Color.gray, pv1.test(v)),
				a-> GraphColors.colorIf(Color.orange, Color.gray, pa1.test(a)));
		
		/*Obtener un nuevo grafo dirigido con los mismos vértices y que por cada arista
		original tenga dos dirigidas y de sentido opuesto con los mismos pesos.*/
		
		//Así SÍ creamos un grafo con peso
		SimpleWeightedGraph<Ciudad,Carretera> andalucia_simple =
				GraphsReader.newGraph(
						//Ruta del archivo
						"ficheros/Andalucia.txt",
						//Creacion del vertice
						Ciudad::ofFormat,
						//Creacion de arista
						Carretera::ofFormat,
						//Tipo de grafo
						Graphs2::simpleWeightedGraph,
						//Peso del grafo
						Carretera::getKm);
		
		//Para haberlo pasado a dirigido teníamos que hacer la llamada a SimpleWeightedGraph
		Graph<Ciudad, Carretera> andalucia_dirigido = Graphs2.toDirectedWeightedGraph(andalucia_simple, Carretera::reverse);
		
		//Creamos las componentes conexas
		var alg2 = new ConnectivityInspector<>(andalucia);
		
		List<Set<Ciudad>> componentes = alg2.connectedSets();
		
		Map<Color, Set<Ciudad>> mapa = new HashMap<Color,Set<Ciudad>>();
		
		for (int i = 0; i < componentes.size(); i++) {
			mapa.put(Color.values()[i], componentes.get(i));
		}
		GraphColors.toDot(
				andalucia,
				"salida/Andalucia 2-D.gv",
				v->v.getNombre(),
				a->a.getNombre() + "--" + a.getKm(),
				//Primer color si se cumple la condicion, la segunda si no, y luego la condicion
				v-> GraphColors.color(getColor(mapa, v)),
				//Sacamos el color segun la ciudad de origen
				a-> GraphColors.color(getColor(mapa, andalucia.getEdgeSource(a))));
	}
	
	public static Color getColor(Map<Color,Set<Ciudad>> mapa, Ciudad c) {
		Color res = null;
		for (Map.Entry<Color, Set<Ciudad>> par: mapa.entrySet()) {
			if(par.getValue().contains(c)) {
				res = par.getKey();
				break;
			}
			
		}
		
		return res;
	}

}
