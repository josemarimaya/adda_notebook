package pruebas;

import java.util.*;
import java.util.stream.IntStream;

public class Recursividad_clase {

	public static void main(String[] args) {
		int n = 5;
		int a = 125, b = 30;
		System.out.println("Factorial " + n + "! = " + factorial(n));
		System.out.println("MCD(" + a + "," + b + ") = " + mcd(a,b));
		System.out.println("Fibonacci(" + n + ") = " + fib(n));
		List<Double> lista = List.of(5., 10., 15., 20.);
		System.out.println("SumLista(lista) = " + sumLista(lista));
		System.out.println("SumListaRec(lista) = " + sumListaRec(lista));
		
		System.out.println(factorialJ8(5));
	}
	
	// Cositas del profesor
	
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
	
	static long factorial(int n) {
		long r;
		if(n == 0) {
			r = 1;
		}else {
			r = factorial(n-1) * n;
		}
		return r;
	}
	
	static int mcd(int a, int b) {
		int r;
		if(b==0) {
			r = a;
		}else {
			r = mcd(b, a%b);
		}
		return r;		
	}
	
	static long fib(int n) {
		long r;
		if(n>=0 && n<=1) {
			r = n;
		}else {
			r = fib(n-1) + fib(n-2);
		}
		return r;
	}
	
	// Cositas del menda
	
	public static Integer factorialJ8(Integer n) {
		// El rango cerrado es de [2,n]
		return IntStream.rangeClosed(2, n)
				// Con reduce hacemos operaciones con el IntStream usando x e y como dos valores
				// Tenemos en cuenta que inicializamos con 1 y que x va a ser el primer valor siempre e y el segundo
				// Asi vamos haciendo el factorial multiplicando recursivamente ...
				.reduce(1, (x,y)-> x*y);
	}
}

