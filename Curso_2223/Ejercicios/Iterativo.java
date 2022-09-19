package iterativo;
import java.util.*;
import java.util.stream.*;
import us.lsi.common.*;
import us.lsi.streams.*;

public class Iterativo {

	public static void main(String[] args) {
		// Transformar de iterativo a recursivo final
		List<Double> listaReales = List.of(1., 2., 3., 4., 5., 6., 7. );
		System.out.println("SumLista(lista) = " + sumLista(listaReales));
		System.out.println("SumListaRec(lista) = " + sumListaRec(listaReales));

		// Para ver takeWhile y dropWhile
		Integer arrayEnteros2[] = {1, 3, 5, 3, 10};
		List<Integer> listaEnteros2 = Arrays.asList(arrayEnteros2);
		System.out.println(hastaMayorN(listaEnteros2, 3));
		System.out.println(desdeMayorN(listaEnteros2, 3));

		// Concatenar: concat(s1,s2)
		
		System.out.println("Concatenar:" + Stream.concat(listaReales.stream(), listaReales.stream()).
				collect(Collectors.toList()));
		
		// Enumerate: enumerate(s), secuencia formada por los pares de
		// elementos construidos por los elementos de s y su posicion en la misma.
		Stream<Enumerate<Double>> enumerado = 
				Stream2.enumerate(listaReales.stream());
		System.out.println("Enumerados: " + enumerado.collect(Collectors.toList()));	
		
		// Pares consecutivos
		Stream<Pair<Double, Double>> pairs = 
				Stream2.consecutivePairs(listaReales.stream());
		System.out.println("ConsecutivePairs: " + pairs.collect(Collectors.toList()));	
		
		// Producto cartesiano
		Stream<Pair<Double, Double>> cartesian = 
				Stream2.cartesianProduct(listaReales.stream());
		System.out.println("CartesianProduct: " + cartesian.collect(Collectors.toList()));	
		
		// Zip		
		Stream<Pair<Double, Double>> zip = 
				Stream2.zip(listaReales.stream(), listaReales.stream(),
						(x,y) -> Pair.of(x, y));
		System.out.println("Zip: " + zip.collect(Collectors.toList()));	

		
		// Usando Collectors
		// AllMatch
		Integer arrayEnteros1[] = {4, 6, 10};
		List<Integer> listaEnteros1 = Arrays.asList(arrayEnteros1);
		System.out.println(todosMultiplos(listaEnteros1, 2));
		System.out.println(todosMultiplosWhile(listaEnteros1, 2));
		
		// Joining(sp, p, s) : separador, prefijo y sufijo, 
		// Concatena los objetos en forma de cadena
		System.out.println(listaEnteros1.stream()
				.map(e->e.toString())
				.collect(Collectors.joining(", ", "{", "}")));
		
		// Reduce(id, bo): valor inicial acumulador, funcion que recibe ac y valor y genera el nuevo ac
		// Reduce la secuencia a un unico elemento
		System.out.println(listaEnteros1.stream()
				.reduce(1, (subtotal, elemento) -> subtotal * elemento));
		
		// GroupingBy(fkey)
		List<String> listaCadenas = List.of("Uno", "Tres", "Cinco", "Tres", "Diez");
		System.out.println("Cadenas: " + listaCadenas);
		
		Map<Integer, Set<String>> cadenasPorLongitud = listaCadenas.stream()
				  .collect(Collectors.groupingBy(String::length, 
						  Collectors.toSet())); // <- sino ponemos nada agrupa en list (por defecto)
		System.out.println("Agrupadas por longitud: " + cadenasPorLongitud);
	}
	
	static Double sumLista(List<Double> list) {
		Integer i = 0;
		Double b = 0.0;
		while(i < list.size()) {
			b = b + list.get(i);
			i = i + 1;
		}
		return b;
	}
	
	static Double sumListaRec(List<Double> list) {
		Integer i = 0;
		Double b = 0.0;
		b = sumListaRecFinal(list, b, i);
		return b;
	}
	// Añadimos tantos parámetros como variables cambien en el bucle
	static Double sumListaRecFinal(List<Double> list, Double b, Integer i) {
		if(i < list.size()) {
			b = sumListaRecFinal(list, b + list.get(i), i + 1);
		}
		return b;
	}
	
	static List<Integer> hastaMayorN(List<Integer> lista, Integer n) {
		return lista.stream().takeWhile(e -> e <= n).collect(Collectors.toList());
	}

	static List<Integer> desdeMayorN(List<Integer> lista, Integer n) {
		return lista.stream().dropWhile(e -> e <= n).collect(Collectors.toList());
	}

	static Boolean todosMultiplos(List<Integer> ls, Integer m){  
		return ls.stream().allMatch(e->e%m==0); 
	}  
	
	static Boolean todosMultiplosWhile(List<Integer> ls, Integer m){ 
		Integer e = 0; 
		Boolean b = true; 
		while(e < ls.size() && b){  
			b = (ls.get(e) % m) == 0;  
			e = e + 1;  
		}  
		return b; 
	} 
	

}
