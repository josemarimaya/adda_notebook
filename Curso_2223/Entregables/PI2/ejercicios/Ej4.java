package ejercicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ej4 {
	
	/*
	 * Dado un árbol binario de cadena de caracteres, diseñe un algoritmo que devuelva cierto
		si se cumple que, para todo nodo, el número total de vocales contenidas en el subárbol
		izquierdo es igual al del subárbol derecho. Proporcione una solución también para árboles
		n-arios
	 * */
	
	public static Boolean solucionRecursiva(BinaryTree<String> tree) {
		return recursivoB(tree, true, 0);
	}
	
	// N-arios: https://github.com/migueltoro/ada_v2_18/blob/master/java/EjemplosParteComun/src/us/lsi/trees/TreesTest.java
	
	private static Boolean recursivoB(BinaryTree<String> tree, Boolean b, Integer acumVocal) {
		return switch(tree) {
		case BEmpty<String> t-> false;
		case BLeaf<String> t-> {
			Integer vocales_actuales = cuentaVocales(t.label());
			yield acumVocal == vocales_actuales;
		}
		case BTree<String> t-> {
			acumVocal = cuentaVocales(t.label());
			BinaryTree<String> subarbolIzquierdo = t.left();
			BinaryTree<String> subarbolDerecho = t.right();
			yield (recursivoB(subarbolIzquierdo, b, acumVocal) == recursivoB(subarbolDerecho, b, acumVocal));
		}
		default-> b;
		};
	}
	
	public static Boolean solucionRecursivaT(Tree<String> tree) {
		return recursivoT(tree, true, 0);
	}
	
	// N-arios: https://github.com/migueltoro/ada_v2_18/blob/master/java/EjemplosParteComun/src/us/lsi/trees/TreesTest.java
	
	private static Boolean recursivoT(Tree<String> tree, Boolean b, Integer acumVocal) {
		// TODO Auto-generated method stub
		return switch(tree) {
		case TEmpty<String> t-> false;
		case TLeaf<String> t->{
			Integer vocales_actuales = cuentaVocales(t.label());
			yield acumVocal == vocales_actuales;
		}
		case TNary<String> t->{
			Integer acum = cuentaVocales(t.label());
			Boolean br = acumVocal == acum;
			t.elements().forEach(tc->{
				recursivoT(tc, br, acum);
			});
			yield b;
		}
		};
	}
	
	public static Boolean solucionRecursivaT2(Tree<String> tree) {
		return recursivoT2(tree, true);
	}
	
	private static Boolean recursivoT2(Tree<String> tree, Boolean b) {
		// TODO Auto-generated method stub
		return switch(tree) {
		case TEmpty<String> t-> false;
		case TLeaf<String> t->{
			yield b;
		}
		case TNary<String> t->{
			//Boolean br = acumVocal == acum;
			/*t.elements().forEach(tc->{
				recursivoT2(tc, br, nivel+1 , acum, ls);
			});*/
			/*t.byDepth().forEach(tl ->{
				tl.
			});
			for(int i=0; i< t.height(); i++) {
				List<Tree<String>> tl = t.level(i);
				for(int j=0; j< tl.size(); j++) {
					Integer vocales_actuales = cuentaVocales(tl.get(i).);
					recursivoT2(tree, b, acumVocal);
				}
			}*/
			if(b) {
				List<Tree<String>> hijos = t.elements();
				Boolean res = null; 
				if(!hijos.contains(Tree.empty())) {
					res = cuentaVocalesN(hijos);
					for(Tree<String> hijo : hijos) {
						if(res) {
							res = recursivoT2(hijo, res);
						}else {
							res = false;
						}
					}
					
				}
				yield res;
			}else {
				yield b;
			}
			
		}	
		};
	}
	
	public static Boolean cuentaVocalesN(List<Tree<String>> arboles) {
		Boolean res = true;
		// Cogemos las vocales del primer arbol
		Integer sumaVocales = cuentaVocales(arboles.get(0).toString());
		
		Integer i = 0;
		// Cuando sea falso lo devolvemos inmediatamente
		while(i<arboles.size() && res) {
			
			String arbol = arboles.get(i).toString();
			Integer suma_vocales_actuales = cuentaVocales(arbol);
			
			// Comparamos lo dos valores de suma de vocales
			res = sumaVocales == suma_vocales_actuales;
			i++;
		}
		
		return res;
		
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
