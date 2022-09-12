package laboratorio;

import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.alg.spanning.KruskalMinimumSpanningTree;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;

public class ej4Supermercado {
	
	//Creamos un record que son los vertices que son los cruces del pasillo junto a los metros entre cruces
			public static record Pasillo(String c1, String c2, Double metros) {
				//	Creamos los pasillos, recordamos que hay que convertir el parametro si no necesitamos un String y tenemos un array
				//Hacemos el ofFormat de esta manera ya que el archivo Supermercado está ordenado de esta manera al crear los edges
				//Si estuviese escrito de otra manera probablemente tendríamos que ordenarlo de otra manera
				public static Pasillo ofFormat(String[] v) {
					return new Pasillo(v[0], v[1], Double.parseDouble(v[2]));
				}
				
			}

	public static void main(String[] args) {
		
		/*Se desean ubicar cámaras de seguridad en un supermercado de forma que todos los
		pasillos estén vigilados. Se podrá poner una cámara en cada uno de los cruces entre
		pasillos. Una cámara situada en un cruce puede vigilar todos los pasillos adyacentes*/
		
		//Aplicar recubrimiento de vértices
		
		Graph<String, Pasillo> grafo =
				GraphsReader.newGraph("ficheros/Supermercado1.txt", 
						//Señalamos el vertice en el fichero, en su lista
						v-> v[0], 
						//Aplicamos el ofFormat en los edges
						Pasillo::ofFormat, 
						//Le marcamos que es un grafo ponderado simple
						Graphs2::simpleWeightedGraph,
						//Y marcamos que los metros son el peso
						Pasillo::metros);
		/*Determine cuántas cámaras poner y dónde ponerlas de forma que se minimice
		el coste total (es decir, el número de cámaras).*/
		
		//Con GreedyVC obtenemos los vertices que están conectados a todos los demás vértices
		//Si quitamos uno de esos vertices se genera otro vertice que también está conectado a todos los demás
		var alg = new GreedyVCImpl<>(grafo);
		Set<String> cruces_criticos = alg.getVertexCover();
		System.out.println("Cruces que necesitan cámaras: " + cruces_criticos);
		
		
		/*Sólo se podrán cablear pasillos que tengan
		cámaras a ambos extremos. ¿Cuántos equipos son necesarios? ¿Cuántos
		metros de cable son necesarios?*/
		//Saber cual es un vertice critico
		Predicate<String> pv = c-> cruces_criticos.contains(c);
		//Dada una arista ver cual es la arista que comunica con los vertices criticos
		Predicate<Pasillo> pa = p-> cruces_criticos.contains(grafo.getEdgeSource(p)) && cruces_criticos.contains(grafo.getEdgeTarget(p));
		
		//Cojo el subgrafo que los vertices criticos y los cruces criticos
		Graph<String, Pasillo> subgrafo = SubGraphView.of(grafo, pv,pa);
		
		//Ahora aplicaremos Kruzskal para conectar el camino minimo entre los vertices criticos, no necesariamente tiene que ser el camino minimo del grafo
		var kru = new KruskalMinimumSpanningTree<>(subgrafo);
		
		//Hacemos la llamada al kruskal para obtener el arbol de camino minimo del subgrafo
		var camino_minimo_critico = kru.getSpanningTree();
		//Predicates para ver si los vertices y aristas estan dentro del camino minimo critico
		Predicate<String> v_critico = v-> cruces_criticos.contains(v);
		//Si no le metemos el getedges no se crea la lista y no podemos usar el contains
		Predicate<Pasillo> arista_critica = a -> camino_minimo_critico.getEdges().contains(a);
		
		/*Muestre el grafo que representa el problema configurando su apariencia de
		forma que se resalten los cruces en los que hay cámara y los pasillos
		cableados.*/
		
		GraphColors.toDot(grafo, "salida/Ejemplo4.gv",
				//Los vertices 
				v-> v,
				//Cogemos las aristas que son los metros y les damos nombre en las aristas ponderadas
				a-> a.metros + " metros",
				//Si pasa el test de ser un camino criticos se pinta de naranja sino es gris
				v2-> GraphColors.colorIf(Color.orange, Color.gray, v_critico.test(v2)),
				//Si la arista esta dentro de la lista del camino critico se pinta de naranja y sino es gris
				a2-> GraphColors.colorIf(Color.orange, Color.gray, arista_critica.test(a2))
				);
	}

}
