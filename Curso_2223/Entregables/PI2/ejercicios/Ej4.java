package ejercicios;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class Ej4 {
	/*Dado un árbol binario de cadena de caracteres, diseñe un algoritmo que devuelva cierto
	si se cumple que, para todo nodo, el número total de vocales contenidas en el subárbol
	izquierdo es igual al del subárbol derecho. Proporcione una solución también para árboles
	n-arios.
	 * */
	
	// Binarios: https://github.com/migueltoro/ada_v2_18/blob/master/java/EjemplosParteComun/src/us/lsi/trees/BinaryTreesTest.java
	
	public static Boolean ej4(BinaryTree<String> tree) {
		Boolean res = true;
		
		switch(tree) {
		case BEmpty<String> t: return res;
		
		// Hay que hacer las comprobacioens anteriores
		case BLeaf<String> t: return res;
		
		case BTree<String> t:
			{
				BinaryTree<String> ramaIzquierda = t.left();
				String s = t.label();
			}
	}
		
		return res;
	}
	
	// N-arios: https://github.com/migueltoro/ada_v2_18/blob/master/java/EjemplosParteComun/src/us/lsi/trees/TreesTest.java
	
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(cuentaVocales("pepe"));
		
		System.out.println(mismasVocales("pepe", "pepa"));
	}

}
