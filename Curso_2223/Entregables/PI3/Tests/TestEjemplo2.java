package Tests;

import org.jgrapht.graph.SimpleWeightedGraph;
import datos.Carretera;
import datos.Ciudad;
import Ejemplos.Ejemplo2;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjemplo2 {

	private static String file = "Andalucia";

	private static SimpleWeightedGraph<Ciudad, Carretera> g = GraphsReader.newGraph("ficheros/" + file + ".txt",
																					Ciudad::ofFormat, 
																					Carretera::ofFormat, 
																					Graphs2::simpleWeightedGraph, 
																					Carretera::km);

	public static void main(String[] args) {

		System.out.println("Archivo de entrada " + file + ".txt \n" + "Datos de entrada: " + g);

		testEjemplo2A();
		testEjemplo2B("Sevilla", "Almeria");
		testEjemplo2C();
		testEjemplo2D();

	}

	public static void testEjemplo2A() {
		System.out.println("Apartado A):");
		Ejemplo2.apartadoA(g, file);
	}

	public static void testEjemplo2B(String origen, String destino) {
		System.out.println("Apartado B):");
		Ejemplo2.apartadoB(g, file, origen, destino);
	}

	public static void testEjemplo2C() {
		System.out.println("Apartado C):");
		Ejemplo2.apartadoC(g, file);
	}

	public static void testEjemplo2D() {
		System.out.println("Apartado D):");
		Ejemplo2.apartadoD(g, file);
	}

}
