package laboratorio;

import java.util.function.Predicate;

import org.jgrapht.Graph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.views.SubGraphView;

public class ej1Andalucia {

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
		
		
		//Dibujar grafo
		GraphColors.toDot(
				//Grafo
				andalucia,
				//Ruta donde se guarda el grafo que sea crea
				"salida/Andalucia.gv",
				//Las etiquetas
				v->v.getNombre(),
				a->a.getNombre() + "--" + a.getKm());
		//Ejecutar la salida en http://grafos.molivero.com/
		
		//Predicate que le queremos añadir a la función principal para que nos pille un grafo distinto
		
		Predicate<Ciudad> p1 = v-> andalucia.degreeOf(v) > 2;
		
		/*Obtener un subgrafo con los vértices que cumplen una propiedad y las
		aristas que cumplen otra propiedad dada. NO debe crear un grafo nuevo, sino
		obtener una vista del grafo original. Muestre el subgrafo resultante
		configurando su apariencia de forma que se muestren los vértices en los que
		inciden más de 2 aristas de un color diferente al resto de vértices*/
		
		GraphColors.toDot(
				andalucia,
				"salida/AndaluciaColor.gv",
				v->v.getNombre(),
				a->a.getNombre() + "--" + a.getKm(),
				//Primer color si se cumple la condicion, la segunda si no, y luego la condicion
				v-> GraphColors.colorIf(Color.orange, Color.blue, p1.test(v)),
				a->GraphColors.style(Style.solid));
		
		/*Ciudades que poseen menos de 500.000 habitantes, y carreteras cuya
		ciudad origen o destino tiene un nombre de más de 8 caracteres y
		poseen más de 150 km de distancia.*/
				//Necesitamos otro predicate que vamos a usar para el view
		//El conceto de view en grafos es el mismo que para backend en SQL
		//Predicado vertice
		Predicate<Ciudad> pv1 = c-> c.getHabitantes()<50000;
		//Predicado Aristas
		Predicate<Carretera> pa1 = c->c.getKm()>150;
		
		Predicate<Carretera> pa2 = c-> 
		//Cogemos la ciudad origen
		andalucia.getEdgeSource(c)
		.getNombre().length()>8
		//Concatenamos predicate con puerta logica
		|| 
		//Y havemos predicate tambien con el destino
		andalucia.getEdgeTarget(c)
		.getNombre().length()>8;
		
		
		//Otra forma de concatenar predicates
		Predicate<Carretera> pa3 = pa1.and(pa2);
		//Usamos la vista
		Graph<Ciudad, Carretera> andalucia_filtrado = SubGraphView.of(andalucia, pv1, pa3);
		
		GraphColors.toDot(
				andalucia_filtrado,
				"salida/AndaluciaColorFiltradoVista.gv",
				v->v.getNombre(),
				a->a.getNombre() + "--" + a.getKm(),
				//Primer color si se cumple la condicion, la segunda si no, y luego la condicion
				v-> GraphColors.colorIf(Color.orange, Color.blue, p1.test(v)),
				a->GraphColors.style(Style.solid));
		
		
	}

}
