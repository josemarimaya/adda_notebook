package Tests;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import datos.Carretera;
import datos.Ciudad;
import Ejemplos.Ejemplo1;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo1 {

	public static void main(String[] args) {
		testsEjemplo1("Andalucia");
		testsEjemplo1("Castilla La Mancha");
		
	}
	
	public static void testsEjemplo1(String file) {
		
		//Los tipos Ciudad y Carretera están en DatosCompartidos -> src -> us.lsi.grafos.datos
		
		Graph<Ciudad, Carretera> g = GraphsReader
					.newGraph("ficheros/" + file + ".txt",
							Ciudad::ofFormat, 
							Carretera::ofFormat, 
							Graphs2::simpleGraph);
		
		System.out.println("\nArchivo " + file + ".txt \n" + "Datos de entrada: " + g);
		
		//Para mostrar el grafo original
		GraphColors.toDot(g,"resultados/ejemplo1/" + file + ".gv",
				x->x.nombre(), x->x.nombre(), //Atributos del vertice y de la arista
				v->GraphColors.color(Color.black), //Propiedades del vertice
				e->GraphColors.color(Color.black)); //Propiedades de la arista
		
		// PrimerPredicado: Ciudades cuyo nombre contiene la letra “e”, y carreteras con menos de 200 km de distancia 
		Predicate<Ciudad> pv1 = c -> c.nombre().contains("e");
		Predicate<Carretera> pa1 = ca -> ca.km() < 200;
		
		Ejemplo1.crearVista(file, g,pv1,pa1," Primer predicado");
		
		
		/* SegundoPredicado: 
		 * Ciudades que poseen menos de 500.000 habitantes
		 * Carreteras cuya ciudad origen o destino tiene un nombre de más de 5 caracteres 
		 * Poseen más de 100 km de distancia */
		
		Predicate<Ciudad> pv2 = c -> c.habitantes() < 500000;
		Predicate<Carretera> pa2 = ca -> ca.km() > 100 &&		
			(g.getEdgeSource(ca).nombre().length() > 5 || g.getEdgeTarget(ca).nombre().length() > 5);
		
		Ejemplo1.crearVista(file, g,pv2,pa2," Segundo predicado");
	}

}


