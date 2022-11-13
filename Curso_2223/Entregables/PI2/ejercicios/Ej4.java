package ejercicios;

import java.util.Arrays;
import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class Ejercicio4 {
	
	/*
	 * Dado un árbol binario de cadena de caracteres, diseñe un algoritmo que devuelva cierto
		si se cumple que, para todo nodo, el número total de vocales contenidas en el subárbol
		izquierdo es igual al del subárbol derecho. Proporcione una solución también para árboles
		n-arios
	 * */
	
	public static Boolean solucionRecursiva(BinaryTree<String> tree) {
		return recursivoB(tree, true);
	}
	
	// N-arios: https://github.com/migueltoro/ada_v2_18/blob/master/java/EjemplosParteComun/src/us/lsi/trees/TreesTest.java
	
	private static Boolean recursivoB(BinaryTree<String> tree, Boolean b) {
		// TODO Auto-generated method stub
		Integer acumVocal = 0;
		return switch(tree) {
		case BEmpty<String> t-> b;
		case BLeaf<String> t-> {
			Integer s_actual = cuentaVocales(t.label());
			acumVocal = acumVocal + s_actual;
		}
		case BTree<String> t-> {
			Integer s_actual = cuentaVocales(t.label());
			acumVocal = acumVocal + s_actual;
			BinaryTree<String> subarbolIzquierdo = t.left();
			BinaryTree<String> subarbolDerecho = t.right();
			yield (recursivoB(subarbolIzquierdo, b) == recursivoB(subarbolDerecho, b));
		}
		default-> b;
		};
	}

	public static Integer cuentaVocales(String s) {
		List<Character> ls_vocales = Arrays.asList('a', 'e', 'i', 'o', 'u');
		Integer res = 0;
		for (int i = 0; i < s.length(); i++) {
			Character c = s.charAt(i);
			if(ls_vocales.contains(c)) {
				res++;
			}
		}
		return res;
	}
	
	public static Boolean mismasVocales(String s1, String s2) {
		Boolean res = null;
		Integer v1 = cuentaVocales(s1);
		Integer v2 = cuentaVocales(s2);
		if(v1 == v2) {
			res = true;
		}else {
			res = false;
		}
		return res;
	}

}
