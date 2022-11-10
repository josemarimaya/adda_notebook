package ejercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import us.lsi.common.IntPair;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.math.Math2;
import utils.Resultados;

public class Ej2 {
	
	/*Analizar el tiempo de ejecución del algoritmo Quicksort usando la bandera holandesa.
	Analizar la influencia del tamaño del umbral del caso base, en el que se usará la
	ordenación por inserción*/
	
	// Bandera holandesa 
	
	/*
	 * bh(ls, p){
		n = |ls|;
		(a,b,c) = (0,0,n);
			while(c-b>0){
			if(ls[b] < p ){
				it(a,b,ls);
				a++;
				b++;
			}else if(ls[b] == p){
				b++;
			}else{
				it(b,c-1,ls);
				c--;
			}
			}
		return (a,b);
		}
		
		it(a,b,ls){
			(ls[a],ls[b]) = (ls[b],ls[a]);
		}
	 * */
	
	// Bandera holandesa versión recursiva
	
	/*
	 * (Integer, Integer) bh(ls, p){
			n = |ls|;
			(a,b,c) = (0,0,n);
			(a,b,c) = bh(a,b,c,ls,p);
			return (a,b);
		}
		
		(Integer, Integer, Integer) bh(a,b,c,ls,p){
			if(c-b>0){
				if(ls[b] < p ){
					it(a,b,ls);
					a++;
					b++;
				}else if(ls[b] == p){
					b++;
				}else{
					it(b,c-1,ls);
					c--;
				}
				
				(a,b,c) = bh(a,b,c,ls,p);
			}
			return (a,b,c);
		}
	 * 
	 * */
	
	// Quicksort
	
	/*
	 * (Integer, Integer, Integer) bh(a,b,c,ls,p){
		if(c-b>0){
		if(ls[b] < p ){
		it(a,b,ls);
		a++;
		b++;
		}else if(ls[b] == p){
		b++;
		}else{
		it(b,c-1,ls);
		c--;
		}
		
		(a,b,c) = bh(a,b,c,ls,p);
		}
		return (a,b,c);
		}
	 * */
	
	
	/*
	public static <E> void quickSort(List<E> lista, int i, int j,
			Comparator<? super E> ord){
		Preconditions.checkArgument(j>=i);
		if(j-i <= 4){
		ordenaBase(lista, i, j, ord);
		}else{
		E pivote = escogePivote(lista, i, j);
		Tuple2<Integer,Integer> p =
		banderaHolandesa(lista, pivote, i, j, ord);
		quickSort(lista,i,p.v1,ord);
		quickSort(lista,p.v2,j,ord);
		}
	}*/
	
	public static <E> E escogePivote(List<E> lista, Integer i, Integer j) {
		E res = lista.get(i/j);
		return res;
	}
	
	
	



// Quicksort
	
	public static <E extends Comparable<? super E>> void sort(List<E> lista, Integer umbral){
		Comparator<? super E> ord = Comparator.naturalOrder();
		quickSort(lista,0,lista.size(),ord, umbral);	
	}
	
	private static <E> void quickSort(List<E> lista, int i, int j, Comparator<? super E> ord, Integer umbral){
		Preconditions.checkArgument(j>=i);
		if(j-i <= umbral){
			ordenaBase(lista, i, j, ord);
		}else{
			E pivote = escogePivote(lista, i, j);
			IntPair p = banderaHolandesa(lista, pivote, i, j, ord);
			quickSort(lista,i,p.first(),ord, umbral);
			quickSort(lista,p.second(),j,ord, umbral);			
		}
	}
	
	public static <T> void ordenaBase(List<T> lista, Integer inf, Integer sup, Comparator<? super T> ord) {		
		for (int i = inf; i < sup; i++) {
		      for(int j = i+1; j < sup; j++){
		    	  if(ord.compare(lista.get(i),lista.get(j))>0){
		    		  List2.intercambia(lista, i, j);
		    	  }
		      }
		}
	}
	
	private static <E> E escogePivote(List<E> lista, int i, int j) {
		E pivote = lista.get(Math2.getEnteroAleatorio(i, j));
		return pivote;
	}
	
	
	public static <E> IntPair banderaHolandesa(List<E> ls, E pivote, Integer i, Integer j,  Comparator<? super E> cmp){
		Integer a=i, b=i, c=j;
		while (c-b>0) {
		    E elem =  ls.get(b);
		    if (cmp.compare(elem, pivote)<0) {
		    	List2.intercambia(ls,a,b);
				a++;
				b++;
		    } else if (cmp.compare(elem, pivote)>0) {
		    	List2.intercambia(ls,b,c-1);
				c--;	
		    } else {
		    	b++;
		    }
		}
		return IntPair.of(a, b);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
