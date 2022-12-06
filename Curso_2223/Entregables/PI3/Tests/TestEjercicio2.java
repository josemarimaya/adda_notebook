package Tests;

import org.jgrapht.Graph;

import Ejemplos.Ejemplo2;
import Ejercicios.CiudadPuntua;
import Ejercicios.Ejercicio2;
import Ejercicios.Trayectoria;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;

public class TestEjercicio2 {
	
	private static String file = "PI3E2_DatosEntrada"; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testEjercicio2(file);
		//testEjercicio2A();
		//testEjercicio2C();
		testEjercicio2D();
	}
	
	// Test parecido al de los ejemplos 1 y 2
	
	public static void testEjercicio2(String file) {
		
		Graph<CiudadPuntua, Trayectoria> g = GraphsReader.newGraph("ficheros/"+file+".txt",
				CiudadPuntua::ofFormat, 
				Trayectoria::ofFormat, 
				Graphs2::simpleGraph);
		
		//Para mostrar el grafo original
		GraphColors.toDot(g,"resultados/ejercicio2/" + file + ".gv",
				// Hemos creados los métodos toString para que sean lo más parecido al enunciado
				CiudadPuntua::toString, 
				Trayectoria::toString, //Atributos del vertice y de la arista
				v->GraphColors.color(Color.black), //Propiedades del vertice
				e->GraphColors.color(Color.black)); //Propiedades de la arista
		
		System.out.println("Grafo de entrada para el ejercicio 2 creado");
		
		
	}
	
	public static void testEjercicio2A() {
		
		Graph<CiudadPuntua, Trayectoria> g = GraphsReader.newGraph("ficheros/"+file+".txt",
				CiudadPuntua::ofFormat, 
				Trayectoria::ofFormat, 
				Graphs2::simpleGraph);
		
		System.out.println("Apartado A):");
		Ejercicio2.apartadoA(g, file);
	}
	
	public static void testEjercicio2B() {
		Graph<CiudadPuntua, Trayectoria> g = GraphsReader.newGraph("ficheros/"+file+".txt",
				CiudadPuntua::ofFormat, 
				Trayectoria::ofFormat, 
				Graphs2::simpleGraph);
	}
	
	public static void testEjercicio2C() {
		Graph<CiudadPuntua, Trayectoria> g = GraphsReader.newGraph("ficheros/"+file+".txt",
				CiudadPuntua::ofFormat, 
				Trayectoria::ofFormat, 
				Graphs2::simpleWeightedGraph,
				/* Hemos cambiado dinero y tiempo a tipo Double porque sino
				 * el simpleWeightedGraph no lo lee si es Integer*/
				Trayectoria::dinero);
		
		System.out.println("Apartado C):");
		Ejercicio2.apartadoC(g, file);
	}
	
	public static void testEjercicio2D() {
		Graph<CiudadPuntua, Trayectoria> g = GraphsReader.newGraph("ficheros/"+file+".txt",
				CiudadPuntua::ofFormat, 
				Trayectoria::ofFormat, 
				Graphs2::simpleWeightedGraph,
				Trayectoria::tiempo);
		
		System.out.println("Apartado D):");
		Ejercicio2.apartadoD(g, file);
	}

}
