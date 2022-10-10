package ejercicios;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import us.lsi.common.Files2;

public class ej1 {
	
	public static Map<Integer,List<String>> ejercicioA (Integer varA, String varB, Integer varC, String
			varD, Integer varE) {
			UnaryOperator<EnteroCadena> nx = elem ->
			// Cogemos como valor inicial el entero sumándolo dos
			{ return EnteroCadena.of(elem.a()+2,
					// Comprobamos si es múltiplo de 3
					elem.a()%3==0
					// Si lo es añadimos el valor entero a una cadena junto al valor inicial de EnteroCadena que es la cadena
					? elem.s()+elem.a().toString():
					// Sino	nos devuelve el substring de la cadena desde el valor resto de la división del (entero / tamanyo cadena)
					elem.s().substring(elem.a()%elem.s().length()));
			};
			// Iteramos sobre varA y varB como valores de EnteroCadena siendo a < c
			return Stream.iterate(EnteroCadena.of(varA,varB), elem -> elem.a() < varC, nx)
			// Transformamos elem en un string de s junto a varD
			.map(elem -> elem.s()+varD)
			// Filtramos por aquellos cuyo tamaño sea menor que varE
			.filter(nom -> nom.length() < varE)
			// Agrupamos por tamaño de String
			.collect(Collectors.groupingBy(String::length));
	}
	
	/* Pasar el ejercicio a iterativo y recursivo final teniendo en cuenta que:
	 * donde EnteroCadena es una clase con una propiedad entera a y otra de tipo cadena s, la
		cual debe implementar como un record. */
	
	public static record EnteroCadena(Integer a, String s) {
		public static EnteroCadena of(Integer a, String s) {
			return new EnteroCadena(a, s);
		}
		
		// Función auxiliar nx del funcional pero esta vez dentro del record al ser UnaryOperator
		public EnteroCadena nx(Integer a, String s) {
			Integer a2 = a+2;
			String s2 = "";
			if(a%3==0) {
				// Usamos a no a2 porque es la que usa la función en funcional
				s2 = s+a.toString();
			}else {
				s2 = s.substring(a%s.length());
			}
			
			return EnteroCadena.of(a2, s2);
		}

	}
	 /*
	public static Map<Integer, List<String>> ej1Iter(Integer varA, String varB, Integer varC, String
			varD, Integer varE){
		// Usaremos este valor como iterador para hacer nx
		EnteroCadena i = EnteroCadena.of(varA, varB);
		Map<Integer, List<String>> res = new HashMap<>();
		List<String> ls = new ArrayList<>();
		while(i.a < varC) {
			
			String nom = i.s() + varD;
	
			if(nom.length() < varE) {
				if(res.containsKey(nom.length())) {
					// ls.add(nom);
					res.get(nom.length()).add(nom);
				}else {
					res.put(nom.length(), new ArrayList<>());
				}
			}
			
			// Valor a iterar 
			i.nx(i.a, i.s);
			
		}
		
		return res;
	}*/
	
	// Falta ordenar los valores de cada map
	
	public static Map<Integer, List<String>> ej1Iterv2(Integer varA, String varB, Integer varC, String
		varD, Integer varE){
		Map<Integer, List<String>> res = new HashMap<>();
		while(varA <= varC) {
			
			String nom = varB + varD;
			
			if(nom.length() < varE) {
				if(res.containsKey(nom.length())) {
					// ls.add(nom);
					res.get(nom.length()).add(nom);
				}else {
					List<String> ls = new ArrayList<>();
					ls.add(nom);
					res.put(nom.length(), ls);
				}
			} 
			
			if(varA%3==0) {
				// Usamos a no a2 porque es la que usa la función en funcional
				varB = varB+varA.toString();
			}else {
				varB = varB.substring(varA%varB.length());
			}
			
			varA = varA + 2;
		}
		
		return res;
	}
	
	
	public static Map<Integer, List<String>> ej1RF(Integer varA, String varB, Integer varC, String
			varD, Integer varE){
		return ej1RFAUX(varA, varB, varC, varD, varE, new HashMap<>());
	}
	public static Map<Integer, List<String>> ej1RFAUX(Integer varA, String varB, Integer varC, String
			varD, Integer varE, Map<Integer, List<String>> res){
		if(varA <= varC) {
			String nom = varB + varD;
			if(nom.length() < varE) {
				if(res.containsKey(nom.length())) {
					res.get(nom.length()).add(nom);
				}else {
					List<String> ls = new ArrayList<>();
					ls.add(nom);
					res.put(nom.length(), ls);
				}
			}
			
			if(varA%3==0) {
				res = ej1RFAUX(varA + 2, varB+varA.toString(), varC, varD, varE, res);
			}else {
				res = ej1RFAUX(varA + 2, varB.substring(varA%varB.length()), varC, varD, varE, res);
			}
		}
		
		return res;
	}
	
	
	
	public static void lectura(String s){
		List<String> ls = Files2.linesFromFile(s);
		for (String linea : ls) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length-1;i++) {
	            String s1 = line[i];
	            Integer i1 = Integer.parseInt(s1);
	            String s2 = line[i+1];
	            String s3 = line[i+2];
	            Integer i3 = Integer.parseInt(s3);
	            String s4 = line[i+3];
	            Integer i5 = Integer.parseInt(line[i+4]);
	            i++;
	            System.out.println("\nParámetros de entrada: "+ i1 +", "+ s2 +", "+ i3 + ", " + s4 + ", " +i5 );
	            System.out.println("1. Iterativa: "+ ej1Iterv2(i1, s2, i3, s4, i5));
	            //System.out.println("2. Recursivo" +);
	        }
		}
		
	}
	
	// Lectura con Java File: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
	
	public static void lecturav2(String s) {
		
		try {
			File f = new File(s);
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(f);
			while(sc.hasNextLine()) {
				String s2 = sc.nextLine();
				System.out.println(s2);
			}
		} catch (FileNotFoundException e) {
		      System.out.println("Fallaste amigo");
		      e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// WIP
		// System.out.println(ej1Iter(5,"pera",10, "pi�a" ,20));
		//lectura("testsAlumnos/PI1Ej1DatosEntrada.txt");
		/*System.out.println(ej1Iterv2(5,"pera",10, "pi�a" ,20));
		System.out.println(ej1Iterv2(15,"zumo",25,"frutas",50));
		
		System.out.println(ej1RF(5,"pera",10, "pi�a" ,20));
		System.out.println(ej1RF(15,"zumo",25,"frutas",50));*/
		
		lecturav2("ficherosBuenos/PI1Ej1DatosEntrada.txt");
		
		lectura("ficherosBuenos/PI1Ej1DatosEntrada.txt");
		
		
	}

}
