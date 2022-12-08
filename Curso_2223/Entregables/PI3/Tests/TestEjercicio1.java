package Tests;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.vertexcover.GreedyVCImpl;
import org.jgrapht.graph.DefaultEdge;

import Ejercicios.Ejercicio1;
import Ejercicios.Familiar;
import Ejercicios.RelacionSanguinea;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testEjercicio1A("PI3E1A_DatosEntrada");
		testEjercicio1B("PI3E1B_DatosEntrada", Familiar.of(14, "Julia", "Jaen", 1996));
		testEjercicio1C("PI3E1B_DatosEntrada", Familiar.of(13, "Raquel", "Sevilla", 1993), Familiar.of(6, "Angela", "Sevilla", 1997));
		testEjercicio1C("PI3E1B_DatosEntrada", Familiar.of(13, "Raquel", "Sevilla", 1993), Familiar.of(15, "Alvaro", "Sevilla", 2000));
		testEjercicio1D("PI3E1B_DatosEntrada");
		testEjercicio1E("PI3E1B_DatosEntrada");
	}
	
	public static void testEjercicio1A(String file) {
		
		Graph<Familiar, DefaultEdge> g = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				Familiar::ofFormat, 
				s-> new DefaultEdge(), Graphs2::simpleDirectedGraph);
		
		Predicate<Familiar> pv1 = v-> mismaCiudadAnyo(g, g.incomingEdgesOf(v));
		
		Ejercicio1.crearVistaA_2(file, g, pv1, "ApartadoA");
		
	}
	
	private static Boolean mismaCiudadAnyo(Graph<Familiar, DefaultEdge> g, Set<DefaultEdge> sa) {
		
		Set<String> ciudades = new HashSet<>();
		Set<Integer> anyos = new HashSet<>();
		
		Boolean res = null;
		/*// Comprobamos que en la lista de vértices haya solo dos padres 
		 * ya que nos los dice en plural y no deducimos que un padre
		 * cuente para la condición*/
		
		if(sa.size()==2) {
			for (DefaultEdge arista : sa) {
				ciudades.add(g.getEdgeSource(arista).ciudad());
				anyos.add(g.getEdgeSource(arista).anyo());
			}
		}
		
		/* Si tienen tamaño uno quiere decir que solo se ha añadido un dato,
		 * con lo cual los vértices padres tienen el mismo año de nacimiento
		 * y la misma ciudad
		 * */
		res = ciudades.size() == 1 && anyos.size() == 1;
		
		return res;
		
	}
	
	public static void testEjercicio1B(String file, Familiar f) {
		
		Graph<Familiar, DefaultEdge> g = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				Familiar::ofFormat, 
				s-> new DefaultEdge(), Graphs2::simpleDirectedGraph);
		
		Predicate<Familiar> pv = v-> descendientes(g, v).contains(f);
		
		//Predicate<Familiar> pv2 = v-> Graphs.predecessorListOf(g, v).contains(f);
		
		Ejercicio1.apartadoB(file, g, pv, f, "ApartadoB");
		
		//Ejercicio1.apartadoB(file, g, pv2, f, "ApartadoB_Alternativo");
		
		
	}
	
	private static Set<Familiar> descendientes(Graph<Familiar, DefaultEdge> g,
			Familiar f) {
		Set<Familiar> res = new HashSet<>();
		if(g.outDegreeOf(f)>0) {
			// Recorremos las aristas mirando a la salida que es familiar objetivo
			for (DefaultEdge arista : g.outgoingEdgesOf(f)) {
				/*
				 * El familiar actual es el que tiene como objetivo la arista que es el descendiente del que observamos
				 * */
				Familiar f_actual = g.getEdgeTarget(arista);
				// Lo guardamos en el set
				res.add(f_actual);
				// Así como guardamos todos los subsiguientes hasta que se acaben las aristas
				res.addAll(descendientes(g, f_actual));
			}
		}
		res.add(f);
		return res;
		
	}
	
	/*
	 *  A partir de aquí no usamos la clase de Ejercicio 1 sino que ejecutamos directamente sobre los test
	 */
	
	
	/*c. Implemente un algoritmo que dadas dos personas devuelva un valor entre los
	posibles del enumerado {Hermanos, Primos, Otros} en función de si son
	hermanos, primos hermanos, o ninguna de las dos cosas. Tenga en cuenta que 2
	personas son hermanas en caso de que tengan al padre o a la madre en común, y
	primas en caso de tener al menos un abuelo/a en común. */
	
	public static void testEjercicio1C(String file, Familiar f1, Familiar f2) {
		
		Graph<Familiar, DefaultEdge> g = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				Familiar::ofFormat, 
				s-> new DefaultEdge(), 
				// Si es directed no podemos calcular la lejanía entre vértices del grafo
				Graphs2::simpleGraph);
		
		DijkstraShortestPath<Familiar, DefaultEdge> dijkstra = new DijkstraShortestPath<>(g);
		
		// La lejanía consanguínea que tienen hermanos, primos y otros
		Integer lejania = dijkstra.getPath(f1, f2).getLength();
		
		if(lejania == 2) {
			System.out.println(f1.nombre() + " y "  + f2.nombre() + " son: " + RelacionSanguinea.Hermanos);
		} else if (lejania == 4) {
			System.out.println(f1.nombre() + " y "  + f2.nombre() + " son: " + RelacionSanguinea.Primos);
		} else {
			System.out.println(f1.nombre() + " y "  + f2.nombre() + " son: " + RelacionSanguinea.Otros);
		}
	}
	
	/*
	 * d. Implemente un algoritmo que devuelva un conjunto con todas las personas que
		tienen hijos/as con distintas personas. Muestre el grafo configurando su apariencia
		de forma que se resalten las personas de dicho conjunto. 
	 * */
	
	public static void testEjercicio1D(String file) {
		
		Graph<Familiar, DefaultEdge> g = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				Familiar::ofFormat, 
				s-> new DefaultEdge(), Graphs2::simpleDirectedGraph);
		
		Predicate<Familiar> pv = v-> hijastros(g, v);
		
		Ejercicio1.crearVistaA_2(file, g, pv, "ApartadoD");
		
		
	}
	
	public static Boolean hijastros(Graph<Familiar, DefaultEdge> g, Familiar f) {
		Boolean res = null;
		
		Set<Familiar> padreEhijo = new HashSet<>();
		
		if(g.outDegreeOf(f)>0) {
			for (DefaultEdge arista : g.outgoingEdgesOf(f)) {
				Familiar hijo = g.getEdgeTarget(arista);
				for (DefaultEdge aristaPadre : g.incomingEdgesOf(hijo)) {
					padreEhijo.add(g.getEdgeSource(aristaPadre));
				}
			}
		}
		
		res = padreEhijo.size()>2;
		
		return res;
		
	}
	
	/*
	 * e. Se desea seleccionar el conjunto mínimo de personas para que se cubran todas
		las relaciones existentes. Implemente un método que devuelva dicho conjunto.
		Muestre el grafo configurando su apariencia de forma que se resalten las personas
		de dicho conjunto.
	 * */
	
	public static void testEjercicio1E(String file) {
		
		Graph<Familiar, DefaultEdge> g = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				Familiar::ofFormat, 
				s-> new DefaultEdge(), 
				// Esta vez para el uso del VertexCover
				Graphs2::simpleGraph);
		
		GreedyVCImpl<Familiar, DefaultEdge> greedy = new GreedyVCImpl<>(g);
		
		// Nos devuelve un recubrimiento mínimo en Set sin colorear
		Set<Familiar> res = greedy.getVertexCover();
		
		// Lo pintamos nosotros con la vista y usando en el predicate que contenga a los vértices del recubrimiento y los pinte de rojo
		
		Predicate<Familiar> pv = v-> res.contains(v);
		
		// Haciendo uso de nuestro coloreado
		
		Ejercicio1.crearVistaA_2(file, g, pv, "ApartadoE");
	}
	

}
