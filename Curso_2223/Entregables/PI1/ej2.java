package ejercicios;

import java.util.List;
import java.util.stream.Stream;

import us.lsi.common.Files2;

public class ej2 {
	
	/*Dada la siguiente definición recursiva de la función f (que toma como entrada 2
	números enteros positivos y una cadena, y devuelve un número entero):
	𝑓(𝑎, 𝑏, 𝑠) =
	⎩ ⎪ ⎨
	⎪ ⎧
	𝑎 ∗ 𝑎 + 𝑏 ∗ 𝑏, 𝑠. 𝑙𝑒𝑛𝑔𝑡ℎ = 0
	𝑠. 𝑙𝑒𝑛𝑔𝑡ℎ + 𝑎 + 𝑏, 𝑎 < 2 || 𝑏 < 2
	𝑎 + 𝑏 + 𝑓(𝑎 − 1,
	𝑏
	2
	, 𝑠. 𝑠𝑢𝑏𝑠𝑡𝑟𝑖𝑛𝑔(𝑎%𝑠. 𝑙𝑒𝑛𝑔𝑡ℎ, 𝑏%𝑠. 𝑙𝑒𝑛𝑔𝑡ℎ)), 𝑎%𝑠. 𝑙𝑒𝑛𝑔𝑡ℎ < 𝑏%𝑠. 𝑙𝑒𝑛𝑔𝑡ℎ
	𝑎 ∗ 𝑏 + 𝑓(
	𝑎
	2
	, 𝑏 − 1, 𝑠. 𝑠𝑢𝑏𝑠𝑡𝑟𝑖𝑛𝑔(𝑏%𝑠. 𝑙𝑒𝑛𝑔𝑡ℎ, 𝑎%𝑠. 𝑙𝑒𝑛𝑔𝑡ℎ)), 𝑒𝑛 𝑜𝑡𝑟𝑜 𝑐𝑎𝑠𝑜
	 * */
	/*
	public static Integer ej2Iter(Integer a, Integer b, String s) {
		Integer res = 0;
		return res;
	}
	
	public static Integer ej2RF(Integer a, Integer b, String s) {
		Integer res = 0;
		
		return res;
	}*/
	
	// Hasta aquí lo comprobado
	
	public static Integer ej2RNF(Integer a, Integer b, String s) {
		Integer res = 0;
		if(s.length() == 0) {
			res = a * a + b * b;
		}else if(a < 2 || b < 2) {
			res = s.length() + a + b;
		}else if(a%s.length() < b%s.length()) {
			res = a + b + ej2RNF(a-1, b/2, s.substring(a%s.length(), b%s.length()));
		}else {
			res =  a * b + ej2RNF(a/2, b-1, s.substring(b%s.length(), a%s.length()));
		}
		
		return res;
	}
	
	public static Integer ej2RF(Integer a, Integer b, String s) {
		return ej2RF(a,b,s,0);
	}
	
	private static Integer ej2RF(Integer a, Integer b, String s, Integer res) {
		if(s.length() == 0) {
			res = a * a + b * b;
		}else if(a < 2 || b < 2) {
			res = s.length() + a + b;
		}else if(a%s.length() < b%s.length()) {
			Integer suma = a+b;
			res = suma + ej2RF(a-1, b/2, s.substring(a%s.length(), b%s.length()), res);
		}else {
			Integer mul = a*b;
			res = mul + ej2RF(a/2, b-1, s.substring(b%s.length(), a%s.length()), res);
		}
		return res;
	}
	
	public static Integer ej2Iter(Integer a, Integer b, String s) {
		Integer res = 0;
		
		while(s.length() != 0 && (a > 2 || b > 2)) {
			
			if(a%s.length() < b%s.length()) {
				s = s.substring(a%s.length(), b%s.length());
				res = a + b + res;
				a = a-1;
				b = b/2;
			}else {
				res = a*b + res;
				s = s.substring(b%s.length(), a%s.length());
				a = a/2;
				b = b-1;
			}
		}
		
		if(s.length() == 0) {
			res = a * a + b * b + res;
		}else if (a < 2 || b < 2) {
			res = s.length() + a + b + res;
		}
		return res;
	}
	
	public static record Trio(Integer a, Integer b, String s, Integer res) {
		public static Trio of(Integer a, Integer b, String s, Integer res) {
			return new Trio(a,b,s, res);
		}
		public static Trio first(Integer a, Integer b, String s) {
			return of(a,b,s,0);
		}
		public Trio next() {
			if(a%s.length() < b%s.length()) {
				return of(a-1, b/2, s.substring(a%s.length(), b%s.length()), res + a + b);
			}else {
				return of(a/2, b-1, s.substring(b%s.length(), a%s.length()), res + a*b);
			}
		}
		
		public Boolean isCaseBase() {
			return (s.length() == 0) || (a < 2 || b < 2);
		}
		
		/*public Integer caseBase() {
			if(s.length() == 0) {
				res = a * a + b * b + res;
			}else if (a < 2 || b < 2) {
				res =  s.length() + a + b + res;
			}
			return res;
		}*/
	}
	
	/*
	 *  Intentar usar Function para hacer las funciones auxiliares que se creen para el caso recursivo y 
	 *  luego otra Function pero con el caso base*/
	public static Integer ej2Funcional(Integer a, Integer b, String s) {
		
		Trio trio = Stream.iterate(Trio.first(a, b, s), elem-> elem.next())
				.filter(elem->elem.s().length()==0 || elem.a() < 2 || elem.b() < 2)
				.findFirst().get();
	
		if(trio.s().length() == 0) {
			return trio.a() * trio.a() + trio.b() * trio.b() + trio.res();
		}else {
			return trio.s().length() + trio.a() + trio.b() + trio.res();
		}
	
	}
	
	// Funcional con las tuplas hechas en clase
	
	public static record Tupla(Integer a, Integer b, String s, Integer ac) {
		
		public static Tupla of(Integer a, Integer b, String s, Integer ac) {
			return new Tupla(a,b,s,ac);
		}
		
		public static Tupla first(Integer a, Integer b, String s) {
			return of(a,b,s,0);
		}
		
		public Tupla next() {
			if(a%s.length() < b%s.length()) {
				return of(a-1,b/2,s.substring(a%s.length(), b%s.length()), ac+a+b);
			}else {
				return of(a/2, b-1, s.substring(b%s.length(), a%s.length()), ac+a*b);
			}
		}
	}
	
	public static Integer ej2Fv2(Integer a, Integer b, String s) {
		Tupla t = Stream.iterate(Tupla.first(a, b, s), elem->elem.next())
				.filter(elem-> elem.s().length() == 0 || elem.a() < 2 || elem.b() < 2)
				.findFirst().get();
		
		if(t.s().length()==0) {
			return t.a() * t.a() + t.b() * t.b() + t.ac();
		}else {
			return t.s().length() + t.a() + t.b() + t.ac();
		}
	}
	
	
	public static void lectura(String s){
		List<String> ls = Files2.linesFromFile(s);
		for (String linea : ls) {
	        String[] line = linea.split(",");

	        String s1 = line[0];
            Integer a1 = Integer.parseInt(s1);
            String s2 = line[1];
            Integer a2 = Integer.parseInt(s2);
            String s3 = line[2];
     
            System.out.println("\nParámetros de entrada: "+ a1+", "+a2+", "+s3);
            System.out.println("1. Recursivo final: "+ ej2RF(a1, a2, s3));
            System.out.println("2. Recursivo no final: "+ej2RNF(a1, a2, s3));
            System.out.println("3. Iterativo: "+ ej2Iter(a1, a2, s3));
            //System.out.println("4. Funcional: "+ ej2Funcional(a1, a2, s)); No funciona xd
		}
		
	}
	
	public static void lecturav2(String s) {
		List<String> ls = Files2.linesFromFile(s);
		for (String linea : ls) {
	        String[] line = linea.split(",");
	        
	        Integer a = Integer.parseInt(line[0]);
	        Integer b = Integer.parseInt(line[1]);
	        String s1 = line[2];
	        System.out.println("Parametros de entrada: "+ a+", "+b+", "+s1);
            System.out.println("1. Recursivo final: "+ ej2RF(a,b,s1));
            System.out.println("2. Recursivo no final: "+ej2RNF(a,b,s1));
            System.out.println("3. Iterativo: "+ ej2Iter(a,b,s1));
            System.out.println("4. Funcional: "+ ej2Funcional(a,b, s));
            System.out.println("__________________________________________ \n");
		}
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//lectura("tests/PI1Ej2DatosEntrada.txt");
		
		lecturav2("ficherosBuenos/PI1Ej2DatosEntrada.txt");

	}

}
