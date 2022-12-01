package Ejercicios;

import java.util.HashSet;
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
		
		//Graph<Familiar, Relacion> vista = SubGraphView.of(g, pv, pa);
		
		Graphs.predecessorListOf(null, null);
		
		GraphColors.toDot(g, "resultados/ejercicio1/A.gv", 
				x->x.nombre(), x->"",
				v-> GraphColors.colorIf(Color.blue, pv.test(v))
				,a-> GraphColors.color(Color.black));
				// ,a-> GraphColors.colorIf(Color.blue, pa.test(a)));
	}
	
	/*b.Implemente un algoritmo que dada una persona devuelva un conjunto con todos
		sus ancestros que aparecen en el grafo. Muestre el grafo configurando su
		apariencia de forma que se resalte la persona de un color y sus ancestros de otro*/
	
	public static Set<Familiar> apartadoB(Familiar f, Graph<Familiar, Relacion> g){
		Set<Familiar> res = new HashSet<>();
		res = g.incomingEdgesOf(f);
		return res;
		
	}


}
