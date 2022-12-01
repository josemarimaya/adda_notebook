package Tests;

import java.util.Set;

import org.jgrapht.Graph;

import Ejemplos.Ejemplo4;
import datos.Pasillo;
import us.lsi.colors.GraphColors;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testEjemplo4("Supermercado1");
		testEjemplo4("Supermercado2");
		testEjemplo4("Supermercado3");

	}
	
	public static void testEjemplo4(String file) {
		System.out.println("\n ============================ Soluciones para: " + file + "===================");
		
		// 1º Implementar tipo Pasillo
		
		Graph<String, Pasillo> grafo = GraphsReader.newGraph("ficheros/" + file + ".txt", 
				v->v[0], 
				Pasillo::ofFormat, 
				Graphs2::simpleWeightedGraph,
				Pasillo::mts);
		
		GraphColors.toDot(grafo, "resultados/ejemplo4/" + file + "_original.gv");
		
		System.out.println("\nArchivo " + file + ".txt \nDatos de entrada: "+ grafo);
		
		// Apartado a
		
		Set<String> solApartadoA = Ejemplo4.apartadoA(grafo);
		System.out.println("\nApartado A): ");
		System.out.println("Las cámaras deben colocarse en los siguientes cruces: "+ solApartadoA.size() );
		solApartadoA.forEach(c-> System.out.println("\t -Cruce" + c));
		
		// Apartado b
		
		
		System.out.println("\nApartado B): ");
		Ejemplo4.apartadoByC(grafo, solApartadoA, file);
		
		
		// Apartado c
		
		System.out.println("\nApartado C): ");
		System.out.println(file + "C.gv generado en resultados/ejemplo4/");
	}

}
