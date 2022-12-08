package Ejercicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;
import us.lsi.graphs.Graphs2;

public class Ejercicio1 {
	
	// https://jgrapht.org/javadoc/org.jgrapht.core/org/jgrapht/Graphs.html
	
	/*
	 * Se tiene un grafo dirigido no ponderado que representa relaciones familiares entre
		distintas personas. En concreto, en dicho grafo hay una arista de a hacia b si b es hijo/a
		de a. De cada persona se conoce un identificador numérico único, su nombre, año y
		ciudad de nacimiento*/
	
	
	
	/*a.Obtenga una vista del grafo que sólo incluya las personas cuyos padres aparecen
	en el grafo, y ambos han nacido en la misma ciudad y en el mismo año. Muestre
	el grafo configurando su apariencia de forma que se resalten los vértices y las
	aristas de la vista. */

	
	public static void crearVistaA_2(String file, Graph<Familiar, DefaultEdge> g, 
			Predicate<Familiar> pv, String vista) {
		
		// Hemos llegado a la conclusión que al ser no ponderado es DefaultEdge y con la vista hacemos SubGraphView
		Graph<Familiar, DefaultEdge> view = SubGraphView.of(g, pv, null);
		
		GraphColors.toDot(g, "resultados/ejercicio1/ " + file + vista +".gv",
				v-> v.nombre(),
				a->"",
				v-> GraphColors.colorIf(Color.red, view.containsVertex(v)),
				a-> GraphColors.colorIf(Color.red, view.containsEdge(a)));
		
		System.out.println("Se ha creado el grafo: " + vista);
	}
	
	
	
	
	/*b.Implemente un algoritmo que dada una persona devuelva un conjunto con todos
		sus ancestros que aparecen en el grafo. Muestre el grafo configurando su
		apariencia de forma que se resalte la persona de un color y sus ancestros de otro*/
	
	public static void apartadoB(String file, Graph<Familiar, DefaultEdge> g, 
			Predicate<Familiar> pv, Familiar f,String vista){
		
		Graph<Familiar, DefaultEdge> view = SubGraphView.of(g, pv, null);
		
		Map<Familiar, Color> m = new HashMap<>();
		
		// Recorremos los vertices del grafo
		for (Familiar fg : g.vertexSet()) {
			// Si el vértice es la persona observada le ponemos un color...
			if(fg.equals(f)) {
				m.put(fg, Color.red);
				// ... y si observamos a sus descendientes de otra ...
			}else if(view.containsVertex(fg)) {
				m.put(fg, Color.blue);
				// ... sino lo dejamos igual ...
			}else {
				m.put(fg, Color.black);
			}
		}
		
		GraphColors.toDot(g, "resultados/ejercicio1/ " + file + vista +".gv",
				v-> v.nombre(),
				a->"",
				/*
				 *  Pintamos el vértice del color que tenga asignado 
				 *  en el map al recorrer los vértices del grafo
				 */
				v-> GraphColors.color(m.get(v)),
				a-> GraphColors.colorIf(Color.red, view.containsEdge(a)));
		
		System.out.println("Se ha creado el grafo: " + vista);
		
	}
	
	// --------------------- Metodos de prueba sin buen resultado ------------------
	
	public static void crearVistaA(Graph<Familiar, Relacion> g,
			Predicate<Familiar> pv,
			Predicate<Relacion> pa, String nombreVista,
			String file) {
		
		Map<Familiar, List<Familiar>> predecesors = predecesores(g);
		
		Predicate<Familiar> pv1 = v->{
			List<Familiar> f = predecesors.get(v);
			Set<Familiar> sv = g.vertexSet();
			return contieneFamiliarYPadre(f, sv, v, g);
		};
	
		GraphColors.toDot(g, "resultados/ejercicio1/A.gv", 
				x->x.nombre(), x->"",
				v-> GraphColors.colorIf(Color.blue, pv.test(v))
				,a-> GraphColors.color(Color.black));
				// ,a-> GraphColors.colorIf(Color.blue, pa.test(a)));
	}
	
	public static Graph<Familiar, Relacion> grafo(String apartado){
		return GraphsReader.newGraph("ficheros/PI3E1" + apartado +"_DatosEntrada.txt", 
				Familiar::ofFormat, Relacion::ofFormat, 
				Graphs2::simpleGraph);
	}
	
	public static void main(String[] args) {
		Graph<Familiar, Relacion> g = grafo("A");
		System.out.println(g);
		predecesores(g);
		
	}
	
	public static Map<Familiar, List<Familiar>> predecesores(Graph<Familiar, Relacion> g){
		// Iteramos el set que contiene los vertices
		Set<Familiar> sf = g.vertexSet();
		Iterator<Familiar> itv = sf.iterator();
		// Creamos un map que contenga los vértices y su lista de predecesores
		Map<Familiar, List<Familiar>> mlf_padres = new HashMap<>();
		while(itv.hasNext()){
			Familiar f_actual = itv.next();
			// Método de graphs para los predecesores
			List<Familiar> lf = Graphs.predecessorListOf(g, f_actual);
			// Si existe una arista que vaya al predecesor al hijo
			// Quiere decir que es padre
			List<Familiar> padres = new ArrayList<>();
			for (Familiar familiar : lf) {
				if(g.containsEdge(familiar, f_actual)) {
					// Los guardamos en el map
					padres.add(familiar);
					mlf_padres.put(f_actual, padres);
				}
			}
			
			
		}
		
		System.out.println(mlf_padres);
		
		return mlf_padres;
	}
	
	// Método auxiliar para comprobar que los predecesores del vertice están en el grafo
	public static Boolean contieneFamiliarYPadre(List<Familiar> lf, Set<Familiar> sv,
			Familiar hijo, Graph<Familiar, Relacion> g) {
		Boolean res = null;
		for (Familiar f : lf) {
			if(sv.contains(f) 
					// También comprobamos teniendo al hijo como destino y al padre como origen si es relación directa padre e hijo
					&& g.containsEdge(f, hijo)) {
				res = true;
			}else {
				res = false;
			}
		}
		return res;
	}


}