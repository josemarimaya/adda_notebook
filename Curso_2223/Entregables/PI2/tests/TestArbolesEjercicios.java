package tests;

import java.util.List;

import ejercicios.Ej3;
import ejercicios.Ej4;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestArbolesEjercicios {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//testEjercicio3();
		testEjercicio4();
	}
	
	// Ejercicio 3
	
	public static void testEjercicio3() {
		String file = "ficheros/Ejercicio3DatosEntradaBinario.txt";
		List<Pair<BinaryTree<Character>, Character>> input = Files2.streamFromFile(file).map(linea -> {
			String[] aux = linea.split("#");
			Preconditions.checkArgument(aux.length == 2);
			return Pair.of(BinaryTree.parse(aux[0], s->s.charAt(0)), aux[1].charAt(0));
		}).toList();
		
		String fileN = "ficheros/Ejercicio3DatosEntradaNario.txt";
		
		List<Pair<Tree<Character>, Character>> inputN = Files2.streamFromFile(fileN).map(linea ->{
			String[] aux = linea.split("#");
			Preconditions.checkArgument(aux.length == 2);
			return Pair.of(Tree.parse(aux[0], s->s.charAt(0)), aux[1].charAt(0));
		}).toList();
		
		System.out.println("\n -----------------------------------------------------------------------------------");
		System.out.println("EJERCICIO 3 BINARIO");
		System.out.println("\n -----------------------------------------------------------------------------------");

		input.stream().forEach(par -> {
			BinaryTree<Character> tree = par.first();
			Character charr = par.second();

			System.out.println("Solucion: \n");
			System.out.println("Arbol: " + tree + "\t Secuencia: " + charr + "\t["
					+ Ej3.solucionRecursivaB(tree, charr) + "] \n");

			System.out.println("-.-.-..-.-.-.-.-.-.-..-.-.-.-.-.-.-.-.-.-.-.-.-.-.-..-.-.- \n\n");

		});
		
		inputN.stream().forEach(par->{
			Tree<Character> tree = par.first();
			Character charr = par.second();
			
			System.out.println("Solucion N-aria: \n");
			System.out.println("Arbol: " + tree + "\t Secuencia: " + charr + "\t["
					+ Ej3.solucionRecursivaTN(tree, charr) + "] \n");

			System.out.println("-.-.-..-.-.-.-.-.-.-..-.-.-.-.-.-.-.-.-.-.-.-.-.-.-..-.-.- \n\n");

			
		});
	}

	// Ejercicio 4
	
	public static void testEjercicio4() {
		String fileB = "ficheros/Ejercicio4DatosEntradaBinario.txt";
		
		String fileN = "ficheros/Ejercicio4DatosEntradaNario.txt";
		
		List<BinaryTree<String>> inputB = Files2.streamFromFile(fileB).map(linea->
		BinaryTree.parse(linea)).toList();
		
		List<Tree<String>> inputT = Files2.streamFromFile(fileN).map(linea->
		Tree.parse(linea)).toList();
		
		System.out.println("\n -----------------------------------------------------------------------------------");
		System.out.println("EJERCICIO 4");
		System.out.println("\n -----------------------------------------------------------------------------------");
		
		System.out.println("\n SOLUCION RECURSIVA BINARIA: \n");
		
		inputB.stream().forEach(x->{
			System.out.println(x + ":" + Ej4.solucionRecursiva(x));
		});
		
		System.out.println("\n SOLUCION RECURSIVA N-ARIA: \n");
		
		inputT.stream().forEach(x->{
			System.out.println(x + ":" + Ej4.solucionRecursivaT(x));
		});
	}

}
