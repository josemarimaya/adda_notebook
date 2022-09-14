package Septiembre2122;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Ej1 {
	
	/* Diseñar un algoritmo que permita determinar si en una lista de
		valores enteros todos son iguales.
		public boolean todosIguales(List<Integer> lista): Devuelve
		verdadero si todos los elementos de la lista son iguales, y falso en
		caso contrario
		Ejemplo: Para la lista [4, 4, 4, 2, 4, 4, 6, 4] debería devolver falso, y
		con [4, 4, 4, 4, 4, 4] debería devolver verdadero 
	 * */
	
	// Solución del menda
	
	public static Boolean todosIgualesIter(List<Integer> lista) {
		Boolean res = true;
		Integer i = 0;
		while(i < lista.size()) {
			if(lista.get(0) != lista.get(i)) {
				res = false;
			}
			i++;
		}
		return res;
	}
	
	public static Boolean todosIgualesR(List<Integer> lista) {
		return todosIgualesR(lista, 0, lista.size(), true);
	}
	
	private static Boolean todosIgualesR(List<Integer> ls, Integer i, Integer n, Boolean valor_recursivo) {
		Boolean res = null;
		if(i<n) {
			if(ls.get(0) != ls.get(i)) {
				res = todosIgualesR(ls, i+1, n, false);
			}else{
				res = todosIgualesR(ls, i+1, n, valor_recursivo);
			}
		}else {
			res = valor_recursivo;
		}
		
		return res;
	}
	
	public static Boolean todosIgualesF(List<Integer> lista) {
		lista.stream();
		return Stream.iterate(0, i-> i+1).limit(lista.size()).allMatch(x-> lista.get(x).equals(lista.get(0)));
	}
	
	// Solución oficial
	
	public static Boolean todosIguales(List<Integer> lista) {
		Integer i = 0;
		Boolean b = true;
		while (i < (lista.size() - 1) && b) {
			b = lista.get(i).equals(lista.get(i+1));
			i = i + 1;
		}
		return b;
	}
	
	public static Boolean todosIgualesRF(List<Integer> lista) {
		return todosIgualesRF(lista, 0, true);
	}
	
	private static Boolean todosIgualesRF(List<Integer> lista, Integer i, Boolean b) {
		Boolean r;
		if (i < (lista.size() - 1) && b) {
			r = todosIgualesRF(lista, i + 1, lista.get(i).equals(lista.get(i+1)));
		}else {
			r = b;
		}
		return r;
	}
	
	public static Boolean todosIgualesFunc(List<Integer> lista) {
		Boolean b = true;
		if(lista.size()>=1){
			Integer first = lista.get(0);
			b = lista.stream().allMatch(x -> x.equals(first));
		}
			return b;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> ls1 = Arrays.asList(4, 4, 4, 2, 4, 4, 6, 4);
		List<Integer> ls2 = Arrays.asList(4, 4, 4, 4, 4, 4);
		
		System.out.println(todosIgualesIter(ls1));
		System.out.println(todosIgualesIter(ls2));
		
		System.out.println(todosIgualesR(ls1));
		System.out.println(todosIgualesR(ls2));
		
		System.out.println(todosIgualesF(ls1));
		System.out.println(todosIgualesF(ls2));
	}

}
