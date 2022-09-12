package practica;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;



import us.lsi.common.Files2;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TreeLevel;
import us.lsi.tiposrecursivos.Tree.TreeType;

public class ej1 {
	
	/*Diseñe un algoritmo que dado un árbol n-ario de tipo genérico y un predicado sobre
	dicho tipo, devuelva un conjunto que contenga las etiquetas de las hojas de dicho árbol
	que cumplan el predicado.*/
	
	
	//Para los ejercicios intentaremos usar el esquema recursivo a la hora de plantear los problemas
	public static <E> Set<E> ej1(Tree<E> t, Predicate<E> p){
		Set<E> res = new HashSet<E>();
		return ej1aux(t, p, res);
	}
	
	public static <E> Set<E> ej1aux(Tree<E> tree, Predicate<E> p, Set<E> res) {
		TreeType type = tree.getType();
		switch(type) {
			case Empty: return res;
			case Leaf:
				if(p.test(tree.getLabel())) {
					res.add(tree.getLabel());
				}
				break;
			case Nary:
			//Es el test que se tiene que pasa para que añadir el valor al conjunto
			if(p.test(tree.getLabel())) {
				res.add(tree.getLabel());
			}
			//Bucle para hacer llamada la llamada recursiva del arbol en el cual se recorre cada hijo del arbol nario
			for(int i=0; i<tree.getChildren().size(); i++) {
				ej1aux(tree.getChild(i), p, res);
			}
			
			//tree.getChildren().stream().forEach(subarbol->ej1aux(subarbol, p, res));
			break;
		}
		return res;
	}
	
	
	//En principio va a ser lo mismo que el ejercicio dado en clase pero usando lo de este ejercicio
	public static <E> Set<E> ej_iter(Tree<E> t, Predicate<E> p) {
		Set<E> res =  new HashSet<>();
		//Lo necesitaremos para iterar con el hasnext
		Iterator<TreeLevel<E>>btn = t.byLevel();
		while(btn.hasNext()) {
			//Cogemos el subarbol que estamos iterando con el has.next
			TreeLevel<E> subarbol = btn.next();
			//Vamos recorriendo cada hijo del subarbol haciendo el test que se pasa
			if(!(subarbol.tree().isEmpty())) {
				if(p.test(subarbol.tree().getLabel())) {
					res.add(subarbol.tree().getLabel());
				}
			}
			
		}
		//En principio al recorrer el arbol de manera iterativa hijo a hijo y pasarle el test debería devolver el resultado en res
		return res;
		
	}
	

	public static <E> void main(String[] args) {		
		Predicate<Integer> par = n-> n%2==0;
		Predicate<Integer> menorCinco = n-> n<5;
		
		List<Tree<Integer>> input2 = Files2.streamFromFile("ficheros/Ejemplo3_DatosEntrada.txt")
				.map(linea -> Tree.parse(linea, s-> Integer.parseInt(s)))
				//Dado una linea hacemos una transformacion de la linea a binarytree y los valores de string los pasa a integer
				.toList();
				//Pasa todo a la lista
		//System.out.println(input2);
		input2.stream().forEach(a->{
			System.out.println("Solución recursiva con primer predicado: " +ej1(a, par));
			System.out.println("Solución iterativa con segundo predicado: " +ej_iter(a, par));
			System.out.println("Solución recursiva con primer predicado: " +ej1(a, menorCinco));
			System.out.println("Solución iterativa con segundo predicado: " +ej_iter(a, menorCinco));
			
		});
	}

}
