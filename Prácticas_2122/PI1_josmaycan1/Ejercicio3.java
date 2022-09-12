package ejercicios;
import us.lsi.common.Files2;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import ejercicios.Ejercicio2.T;

public class Ejercicio3 {
	
	public static String ejercicio3(Integer a, Integer limit) {
		return Stream.iterate(Par.of(0, a), t -> t.v1 < limit,
		//Se empieza creando un par (0,a) que es con el que trabajamos
		 //* v1 va a ser 0 y lo vamos a usar para iterar hasta el limite
		 //* v2 sera la a
		 //* Con lo cual en cada iteracion del par se suma 1
		 //* Si el valor de v1 en ese momento NO es multiplo de 3
		 //* a se actualiza con el valor de v2
		 //* sino v2 se queda con la suma del actual v1 junto a a
		t -> Par.of(t.v1+1, t.v1 % 3 == 1 ? t.v2 : t.v1+t.v2))
		//Par debe de ser el record que tenemos que implementar
		.collect(Collectors.toList())//Esta linea hay que reescribirla a mano
		.toString();
		}
	
	//El operador ? coge una expresion previa y si es true hace la primera accion y si es false hace la segunda
	//Dichas acciones están separadas por el :
	
	public static record Par(Integer v1, Integer v2) {
		
		public static Par of(Integer v1, Integer v2) {
			//Creacion de metodo para la llamada de un nuevo par
			return new Par(v1, v2);
		}
	}
	
	/*public static String ej3Iter(Integer a, Integer limit) {
		Integer v1 = 0;
		Integer v2 = a;
		
		//List<List<Integer>> res = new ArrayList<List<Integer>>();
		List<Integer> temp = new ArrayList<Integer>();
		
		//Presuponemos que v1 es el contador que hay hasta el límite
		//Además de que están las dos propiedades tanto v1 como v2
		while(v1<limit) {
			
			if(v1%3==1) {
				//Cuando v1 no sea multiplo de 3 el valor res pasará a ser v2
				a = v2; 
			}else {
				v2 = v1 + a;
			}
			temp.add(v1);
			temp.add(v2);
			//res.add(temp);
			v1++;
		}
		//return res.toString();
		return temp.toString();
	}*/
	
	public static String ej3Iter(Integer a, Integer limit) {
		Integer v1 = 0;
		Integer v2 = a;
		List<Par> temp = new ArrayList<Par>();
		
		//Presuponemos que v1 es el contador que hay hasta el límite
		//Además de que están las dos propiedades tanto v1 como v2
		while(v1<limit) {
			//Inicializando por el par inicial
			Par w = Par.of(v1, v2);
			temp.add(w);
			if(v1%3==1) {
				//Cuando v1 no sea multiplo de 3 el valor res pasará a ser v2
				v2 = a; 
			}else {
				//Cuando v2 es multiplo de 3 sumamos el contador junto al valor de v2 actualmente
				v2 = v1 + v2;
			}
			
			v1++;
		}
		//return res.toString();
		return temp.toString();
	}
	
	public static String ej3RF(Integer a, Integer limit) {
		return ej3RFAux(0, a, limit,  new ArrayList<Par>(), "");
	}
	
	public static String ej3RFAux(Integer v1, Integer v2, Integer limit, List<Par> temp, String res) {
		if(v1<=limit) {
			Par w = Par.of(v1, v2);
			temp.add(w);
			if(v1%3==1) {
				res = ej3RFAux(v1 + 1, v2, limit,temp, res);
			}else {
				res = ej3RFAux(v1 + 1, v1 + v2, limit, temp, res);
			}	
		}
		return res = temp.toString();
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//System.out.println(ej3Iter(86, 87));
		
		List<String> ls = Files2.linesFromFile("tests/PI1E3_DatosEntrada.txt");
		
		for (String linea : ls) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length;i++) {
	            String s1 = line[i];
	            Integer a1 = Integer.parseInt(s1);
	            String s2 = line[i+1];
	            Integer a2 = Integer.parseInt(s2);
	            i++;
	            System.out.println("\nParámetros de entrada: "+ a1+", "+a2 + "\nSolucion iterativa: \n ");
	            System.out.println(ej3Iter(a1, a2));
	            System.out.println("\nSolucion recursiva:");
	            System.out.println(ej3RF(a1, a2));
	            System.out.println("\nSolucion funcional:");
	            System.out.println(ejercicio3(a1, a2));
	           
	        }
		}

	}

}
