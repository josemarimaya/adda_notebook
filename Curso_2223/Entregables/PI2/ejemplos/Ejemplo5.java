package ejemplos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TEmpty;
import us.lsi.tiposrecursivos.Tree.TLeaf;
import us.lsi.tiposrecursivos.Tree.TNary;

public class Ejemplo5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static List<Boolean> solucionRecursiva(Tree<Integer> t, Predicate<Integer> p) {
		// TODO Auto-generated method stub
		return recursivo(t,p,0, new ArrayList<>());
	}

	private static List<Boolean> recursivo(Tree<Integer> tree, Predicate<Integer> p, int nivel, List<Boolean> ls) {
		// TODO Auto-generated method stub
		
		if(ls.size() <= nivel) {
			ls.add(true);
		}
		return switch(tree) {
		case TEmpty<Integer>  t-> ls;
		case TLeaf<Integer> t -> {
			Boolean r = p.test(t.label()) && ls.get(nivel);
			ls.set(nivel, r);
			yield ls;
		}
		case TNary<Integer> t-> {
			Boolean r = p.test(t.label()) && ls.get(nivel);
			ls.set(nivel, r);
			t.elements().forEach(tc ->{
				recursivo(tc, p, nivel+1, ls);
			});
			yield ls;
		}
		
		default -> ls;
		};
	}


}
