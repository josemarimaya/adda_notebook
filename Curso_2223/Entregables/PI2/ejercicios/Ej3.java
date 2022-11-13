package ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import us.lsi.common.Set2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ej3 {
	
	/*
	 * Dados un árbol binario de caracteres y un carácter, diseñe un algoritmo que devuelva
		una lista con todas las cadenas que se forman desde la raíz a una hoja no vacía, excluyendo
		aquellas cadenas que contengan dicho carácter. Proporcione una solución también para
		árboles n-arios*/
	
	public static List<String> solucionRecursivaB(BinaryTree<Character> tree, Character c) {
		return recursivaB(tree, c, new ArrayList<>(), "");
	}

	private static List<String> recursivaB(BinaryTree<Character> tree, Character c, List<String> ls, String s) {
		
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
			Character c_actual = t.label();
			s = s+c_actual;
			 recursivaB(t.right(), c, ls, s);
			 recursivaB(t.left(), c, ls,s);
			yield ls;
		}
		};
	}
	
	public static List<String> solucionRecursivaTN(Tree<Character> tree, Character c) {
		return recursivaTN(tree, c, new ArrayList<>(), "");
	}
	

	private static List<String> recursivaTN(Tree<Character> tree, Character c, List<String> ls, String s) {
		// TODO Auto-generated method stub
		return switch(tree) {
		case TEmpty<Character> t-> ls;
		case TLeaf<Character> t-> {
			Character c_actual = t.label();
			s = s+c_actual;
			if(!(s.contains(String.valueOf(c)))) {
				ls.add(s);
			}
			yield ls;
		}
		case TNary<Character> t-> {
			Character c_actual = t.label();
			String s1 = s+c_actual;
			t.elements().forEach(tc ->{
				recursivaTN(tc, c, ls, s1);
			});
			yield ls;
		}
		};
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
