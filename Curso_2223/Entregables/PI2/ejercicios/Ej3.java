package ejercicios;

import java.util.List;
import java.util.Set;

import us.lsi.common.Set2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ej3 {
	
	public static List<String> ej3BiT(Character a, BinaryTree<Character> t){
		return null;
		
		
	}
	
	
	public static void ej3(Character a, BinaryTree<Character> tree){
		
		switch(tree) {
			case BEmpty<Character> t: BinaryTree.empty();
			
		}
	}
	
	/*
	
	public static Set<String> palindromas(Tree<Character> tree) {
		Set<String> st = Set2.of();
		palindromas(tree, "", st);
		return st;
	}
	
	public static void palindromas(Tree<Character> tree, String camino, Set<String> st) {
		switch(tree) {	
		case TEmpty<Character> t: break;
		case TLeaf<Character> t: 
			Character label = t.label();
			camino = camino+label;
			if(IterativosyRecursivosSimples.esPalindromo1(camino)) st.add(camino);
			break;
		case TNary<Character> t: 
			label = t.label();
			camino = camino+label;
			for(Tree<Character> tt: t.elements()) 
				palindromas(tt,camino,st);
			break;
		}
	}
	
	*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
