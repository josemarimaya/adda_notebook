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
				return of(a/2, b-1, s.substring(b%s.length(), a%s.length()), res + a + b);
			}else {
				return of(a/2, b-1, s.substring(b%s.length(), a%s.length()), res + (a*b));
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
				.filter(elem->elem.isCaseBase())
				.findFirst().get();
		
		Integer res = trio.res();
		if(trio.s().length() == 0) {
			res = trio.a() * trio.a() + trio.b() * trio.b() + res;
		}else {
			res = trio.s().length() + trio.a() + trio.b() + res;
		}
		return res;
	}
	
	public static void lectura(String s){
		List<String> ls = Files2.linesFromFile(s);
		for (String linea : ls) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length-1;i++) {
	            String s1 = line[i];
	            Integer a1 = Integer.parseInt(s1);
	            String s2 = line[i+1];
	            Integer a2 = Integer.parseInt(s2);
	            String s3 = line[i+2];
	            i++;
	            System.out.println("\nParámetros de entrada: "+ a1+", "+a2+", "+s3);
	            System.out.println("1. Recursivo final: "+ ej2RF(a1, a2, s3));
	            System.out.println("2. Recursivo no final: "+ej2RNF(a1, a2, s3));
	            System.out.println("3. Iterativo: "+ ej2Iter(a1, a2, s3));
	            //System.out.println("4. Funcional: "+ ej2Funcional(a1, a2, s));
	        }
		}
		
	}
	
	public static void lecturav2(String s) {
		List<String> ls = Files2.linesFromFile(s);
		for (String linea : ls) {
	        String[] line = linea.split(",");
	        
	        Integer a = Integer.parseInt(line[0]);
	        Integer b = Integer.parseInt(line[1]);
	        String s1 = line[2];
	        System.out.println("\nParámetros de entrada: "+ a+", "+b+", "+s);
            System.out.println("1. Recursivo final: "+ ej2RF(a,b,s));
            System.out.println("2. Recursivo no final: "+ej2RNF(a,b,s));
            System.out.println("3. Iterativo: "+ ej2Iter(a,b,s));
            System.out.println("4. Funcional: "+ ej2Funcional(a,b, s));
            System.out.println("__________________________________________ \n");
		}
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//lectura("tests/PI1Ej2DatosEntrada.txt");
		
		lectura("ficherosBuenos/PI1Ej2DatosEntrada.txt");

	}

}
