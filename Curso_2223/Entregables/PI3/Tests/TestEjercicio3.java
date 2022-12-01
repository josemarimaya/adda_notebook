package Tests;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import Ejemplos.Ejemplo3;
import Ejercicios.Ejercicio3;
import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.graphs.Graphs2;

public class TestEjercicio3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testEjercicio3("PI3E3A_DatosEntrada");
		testEjercicio3_Actividades("PI3E3A_DatosEntrada");
	}
	
	public static void testEjercicio3(String file) {
		
		Graph<String, DefaultEdge> g = Graphs2.simpleGraph(
				String::new, DefaultEdge::new, false);
		
		Files2.streamFromFile("ficheros/"+ file + ".txt").forEach(linea ->{
			// Separamos por coma los valores del ficheros
			String[] v = linea.split(": ");
			// Creamos los vértices para los vértices del fichero
			g.addVertex(v[0]);
			
			// La segunda parte de la linea son las actividades que separaremos por la ,/s
			String[] w = v[1].split(",");
			
			// En este for recorremos el número i de actividades que haya ya que no siempre son 2 o 3
			for(int i= 0; i < w.length; i++) {
				// Creamos el vértice de la actividad
				g.addVertex(w[i]);
				// Unimos el alumno con la actividad de su línea
				g.addEdge(v[0], w[i]);
			}
		});
		
		GraphColors.toDot(g, "resultados/ejercicio3/PI3E3_inicial.gv");
		Ejercicio3.apartadoA(g, file);
		
		
	}
	
	public static void testEjercicio3_Actividades(String file) {
		
		/*Hay que plantear el test de acuerdo a que lo que hay que 
		 * unir en aristas son cada Actividad, es decir, no crear
		 * los vértices de alumnos sino usarlos como apoyo para unir 
		 * dichos conjuntos
		*/
		
		Graph<String, DefaultEdge> g = Graphs2.simpleGraph(
				String::new, DefaultEdge::new, false);
		
		/*Podemos intentar hacer una "manualidad" para
		 * forzar a aquellos alumnos que tienen 2 o 3 actividades
		 * para unirlas*/
		
		Files2.streamFromFile("ficheros/"+ file + ".txt").forEach(linea ->{
			// Separamos por coma los valores del ficheros
			String[] v = linea.split(": ");
			// La segunda parte de la linea son las actividades que separaremos por la ,/s
			String[] w = v[1].split(",");
			
			// En este for recorremos el número i de actividades que haya ya que no siempre son 2 o 3
			for(int i= 0; i < w.length; i++) {
				// Creamos el vértice de la actividad
				g.addVertex(w[i]);
				/*Con el bucle uniremos cada actividad sobre si misma sin que
				 * se una consigo mismo*/
				for(int j = 0; j < w.length; j++) {
					if(!(w[i].equals(w[j]))) {
						g.addEdge(w[i], w[j]);
					}
				}
			}
		});
		
		GraphColors.toDot(g, "resultados/ejercicio3/PI3E3_actividades.gv");
		Ejercicio3.apartadoA(g, file);
		
	}

}
