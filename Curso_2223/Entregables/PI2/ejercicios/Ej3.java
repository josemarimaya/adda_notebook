package ejercicios;

import java.util.ArrayList;
import java.util.List;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class Ejercicio3 {
	
	/*
	 * Dados un árbol binario de caracteres y un carácter, diseñe un algoritmo que devuelva
		una lista con todas las cadenas que se forman desde la raíz a una hoja no vacía, excluyendo
		aquellas cadenas que contengan dicho carácter. Proporcione una solución también para
		árboles n-arios*/
	
	public static List<String> solucionRecursivaB(BinaryTree<Character> tree, Character c) {
		return recursivaB(tree, c, new ArrayList<>());
	}

	private static List<String> recursivaB(BinaryTree<Character> tree, Character c, List<String> ls) {
		// TODO Auto-generated method stub
		String s = "";
		return switch(tree) {
		case BEmpty<Character> t-> ls;
		case BLeaf<Character> t->{
			Character c_actual = t.label();
			s = s+c_actual;
			if(!(s.contains(String.valueOf(c)))) {
				ls.add(s);
			}
			yield ls;
		}
		case BTree<Character> t->{
			 recursivaB(t.right(), c, ls);
			 recursivaB(t.left(), c, ls);
			yield ls;
		}
		default -> ls;
		};
	}

}
