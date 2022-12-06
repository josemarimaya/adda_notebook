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
	
	/*a.Obtenga una vista del grafo que sólo incluya las personas cuyos padres aparecen
	en el grafo, y ambos han nacido en la misma ciudad y en el mismo año. Muestre
	el grafo configurando su apariencia de forma que se resalten los vértices y las
	aristas de la vista. */

	
	
	/*public static void apartadoA(Graph<Familiar, Relacion> g, String file) {
		
		GraphColors.toDot(g, "resultados/ejercicio1/ " + file + "A.gv",
				x->x.nombre(),x->"",
				v-> GraphColors.colorIf(Color.blue, Color.black, 
						g.edgesOf(v).stream().allMatch()));
		
	}*/
	
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
	
	
	/*b.Implemente un algoritmo que dada una persona devuelva un conjunto con todos
		sus ancestros que aparecen en el grafo. Muestre el grafo configurando su
		apariencia de forma que se resalte la persona de un color y sus ancestros de otro*/
	/*
	public static Set<Familiar> apartadoB(Familiar f, Graph<Familiar, Relacion> g){
		Set<Relacion> res = new HashSet<>();
		res = g.incomingEdgesOf(f);
		return res;
		
	}*/


}