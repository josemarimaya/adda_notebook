package Ejemplos;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import datos.Carretera;
import datos.Ciudad;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.views.SubGraphView;

public class Ejemplo1 {
	
	// Crea vista del grafo
		public static void crearVista(String file, Graph<Ciudad,Carretera> g, Predicate<Ciudad> pv, 
				Predicate<Carretera> pa, String nombreVista) {
			
			Graph<Ciudad, Carretera> vista = SubGraphView.of(g, pv, pa);

			GraphColors.toDot(vista,"resultados/ejemplo1/" + file + nombreVista + ".gv",
					x->x.nombre(), x->x.nombre(),
					v->GraphColors.colorIf(Color.red, vista.edgesOf(v).size() > 1), //vértices en los que inciden más de 1 arista --> color diferente al resto de vértices
					e->GraphColors.color(Color.black));
			System.out.println(file + nombreVista + ".gv generado en " + "resultados/ejemplo1");
		}

}
