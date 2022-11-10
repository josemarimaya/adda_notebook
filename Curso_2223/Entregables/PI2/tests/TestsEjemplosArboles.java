package tests;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import ejemplos.Ejemplo4;
import ejemplos.Ejemplo5;
import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.common.Preconditions;
import us.lsi.math.Math2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;

public class TestsEjemplosArboles {
	
	private static final Predicate<Integer> PAR = x-> x%2 == 0;
	private static final Predicate<Integer> PRIMO = x-> Math2.esPrimo(x);

	public static void main(String[] args) {
		testsEjemplo4();
		testsEjemplo5();
	}

	// Ejemplo4

	private static List<Character> stringListToCharList(String s) {
		String letras = s.replace(",", "").replace("[", "").replace("]", "");
		List<Character> ls = new ArrayList<>(letras.length());
		for (int i = 0; i < letras.length(); i++) {
			ls.add(letras.charAt(i));
		}

		return ls;
	}

	public static void testsEjemplo4() {
		String file = "ficheros/Ejemplo4DatosEntrada.txt";

		List<Pair<BinaryTree<Character>, List<Character>>> inpunts = Files2.streamFromFile(file).map(linea -> {
			String[] aux = linea.split("#");
			Preconditions.checkArgument(aux.length == 2);
			return Pair.of(BinaryTree.parse(aux[0], s -> s.charAt(0)), stringListToCharList(aux[1]));

		}).toList();

		System.out.println("\n -----------------------------------------------------------------------------------");
		System.out.println("EJEMPLO 4");
		System.out.println("\n -----------------------------------------------------------------------------------");

		inpunts.stream().forEach(par -> {
			BinaryTree<Character> tree = par.first();
			List<Character> chars = par.second();

			System.out.println("Solucion 1: \n");
			System.out.println("Arbol: " + tree + "\t Secuencia: " + chars + "\t["
					+ Ejemplo4.solucionRecursiva(tree, chars) + "] \n");

			System.out.println("Solucion 1: \n");
			System.out.println("Arbol: " + tree + "\t Secuencia: " + chars + "\t["
					+ Ejemplo4.solucionRecursiva2(tree, chars) + "] \n");

			System.out.println("-.-.-..-.-.-.-.-.-.-..-.-.-.-.-.-.-.-.-.-.-.-.-.-.-..-.-.- \n\n");

		});

	}

	// Ejemplo5
	
	public static void testsEjemplo5() {
		String file = "ficheros/Ejemplo5DatosEntrada.txt";
		
		List<Tree<Integer>> inputs = Files2.streamFromFile(file).map(linea->
				Tree.parse(linea, s-> Integer.parseInt(s))).toList();
		
		System.out.println("\n -----------------------------------------------------------------------------------");
		System.out.println("EJEMPLO 5");
		System.out.println("\n -----------------------------------------------------------------------------------");
		
		System.out.println("\n SOLUCION RECURSIVA PAR: \n");
		
		inputs.stream().forEach(x->{
			System.out.println(x + ":" + Ejemplo5.solucionRecursiva(x, PAR));
		});
		
		System.out.println("\n SOLUCION RECURSIVA PRIMO: \n");
		
		inputs.stream().forEach(x->{
			System.out.println(x + ":" + Ejemplo5.solucionRecursiva(x, PRIMO));
		});


	}
}
