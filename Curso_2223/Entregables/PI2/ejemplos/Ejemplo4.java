package ejemplos;

import java.util.List;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class Ejemplo4 {

	/*
	 * PI2 - Ejemplo 4
	 * 
	 * Implemente una función booleana que, dados un árbol binario de caracteres y
	 * una lista de caracteres, determine si existe un camino en el árbol de la raíz
	 * a una hoja que sea igual a la lista.
	 * 
	 * Resolver de forma recursiva
	 */

	public static Boolean solucionRecursiva(BinaryTree<Character> tree, List<Character> chars) {
		return recursivo(tree, chars, 0);
	}

	private static Boolean recursivo(BinaryTree<Character> tree, List<Character> chars, int i) {
		Integer n = chars.size();
		return switch (tree) {
		case BEmpty<Character> t -> false;
		case BLeaf<Character> t -> n - i == 1 && chars.get(i).equals(t.label());
		case BTree<Character> t -> n - i > 0 && chars.get(i).equals(t.label()) && (recursivo(t.left(), chars, i + 1)
				|| recursivo(t.right(), chars, i + 1));
		};
	}

	public static Boolean solucionRecursiva2(BinaryTree<Character> tree, List<Character> chars) {
		return recursivo2(tree, chars, 0);
	}

	public static Boolean recursivo2(BinaryTree<Character> tree, List<Character> chars, int i) {
		Integer n = chars.size();
		return switch (tree) {
		case BEmpty<Character> t -> false;
		case BLeaf<Character> t -> n - i == 1 && chars.get(i).equals(t.label());
		case BTree<Character> t -> {
			BinaryTree<Character> left = t.left();
			BinaryTree<Character> right = t.right();
			yield (t.label().equals(chars.get(i)) && recursivo2(left, chars, i + 1) || recursivo2(right, chars, i + 1));
		}
		};
	}

}
