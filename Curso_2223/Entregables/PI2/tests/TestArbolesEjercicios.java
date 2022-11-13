package tests;

import java.util.ArrayList;
import java.util.List;

import ejemplos.Ejemplo4;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.BinaryTree;

public class TestArbolesEjercicios {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	// Ejercicio 3
	
	// Hemos cogido literalmente el test del ejemplo 4 como referencia, volverlo a hacer
	public static void testsEjemplo4() {
		String file = "ficheros/Ejemplo4DatosEntrada.txt";

		List<Pair<BinaryTree<Character>, Character>> inpunts = Files2.streamFromFile(file).map(linea -> {
			String[] aux = linea.split("#");
			Preconditions.checkArgument(aux.length == 2);
			return Pair.of(BinaryTree.parse(aux[0], s -> s.charAt(0)), stringListToCharList(aux[1]));

		}).toList();

		System.out.println("\n -----------------------------------------------------------------------------------");
		System.out.println("EJEMPLO 4");
		System.out.println("\n -----------------------------------------------------------------------------------");

		inpunts.stream().forEach(par -> {
			BinaryTree<Character> tree = par.first();
			Character chars = par.second();

			System.out.println("Solucion 1: \n");
			System.out.println("Arbol: " + tree + "\t Secuencia: " + chars + "\t["
					+ Ejemplo4.solucionRecursiva(tree, chars) + "] \n");

			System.out.println("Solucion 1: \n");
			System.out.println("Arbol: " + tree + "\t Secuencia: " + chars + "\t["
					+ Ejemplo4.solucionRecursiva2(tree, chars) + "] \n");

			System.out.println("-.-.-..-.-.-.-.-.-.-..-.-.-.-.-.-.-.-.-.-.-.-.-.-.-..-.-.- \n\n");

		});

	}
	
	// Ejercicio 4

}
