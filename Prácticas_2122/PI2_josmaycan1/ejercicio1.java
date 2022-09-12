package PI2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import us.lsi.common.Files2;
import us.lsi.common.IntTrio;

public class ejercicio1 {
	
	/*Dada la siguiente definición recursiva de la función f (que toma como entrada 3
		números enteros positivos y devuelve una cadena):
		𝑓(𝑎, 𝑏, 𝑐) = *
		"(" + 𝑡𝑜𝑆𝑡𝑟𝑖𝑛𝑔(𝑎 ∗ 𝑏 ∗ 𝑐) + ")", 𝑎 < 3 ⋀ 𝑏 < 3 ⋀ 𝑐 < 3
		"(" + 𝑡𝑜𝑆𝑡𝑟𝑖𝑛𝑔(𝑎 + 𝑏 + 𝑐) + ")", 𝑎 < 5 ⋁ 𝑏 < 5 ⋁ 𝑐 < 5
		𝑡𝑜𝑆𝑡𝑟𝑖𝑛𝑔(𝑎 ∗ 𝑏 ∗ 𝑐) + 𝑓(𝑎/2, 𝑏 − 2, 𝑐/2), 𝑎 𝑒𝑠 𝑝𝑎𝑟 ⋀ 𝑏 𝑒𝑠 𝑝𝑎𝑟 ⋀ 𝑐 𝑒𝑠 𝑝𝑎𝑟
		𝑡𝑜𝑆𝑡𝑟𝑖𝑛𝑔(𝑎 + 𝑏 + 𝑐) + 𝑓(𝑎/3, 𝑏 − 3, 𝑐/3), 𝑒𝑛 𝑜𝑡𝑟𝑜 𝑐𝑎𝑠𝑜
		siendo + un operador que representa la concatenación de cadenas, y toString(i) un método
		que devuelve una cadena a partir de un entero. Al llevar a cabo la implementación, para
		el tratamiento de cadenas se recomienda hacer uso de String.format.*/
	
	/*public static String ej1Iter(List<Integer> ls) {
		String res = "";
		Integer i = 0;
		while(i< ls.size()) {
			Integer a = ls.get(i);
			Integer b = ls.get(i+1);
			Integer c = ls.get(i+2);
			if(a<3 & b<3 & c<3) {
				Integer mul = a*b*c;
				res = "(" + mul + ")";
			}else if(a<5 | b <5 | c<5) {
				Integer sum = a+b+c;
				res = "(" + sum + ")";
			} else if(a%2==0 & b%2==0 & c%2==0){
				a = a/2;
				b = b-2;
				c = c/2;
				Integer mul = a*b*c;
				res = "(" + mul + ")";
			}else {
				a = a/3;
				b = b-3;
				c = c/3;
				Integer sum = a+b+c;
				res = "(" + sum + ")";
			}
			i = i+3;
		}
		
		return res;
	}*/
	
	public static String ej1RF(Integer a, Integer b, Integer c) {
		return ej1RFAUX(a,b,c);
	}
	
	public static String ej1RFAUX(Integer a, Integer b, Integer c) {
		String res = "";
		if(a<3 & b<3 & c<3) {
			Integer mul = a*b*c;
			res = "(" + mul.toString() + ")";
		}else if(a<5 | b <5 | c<5) {
			Integer sum = a+b+c;
			res = "(" + sum.toString() + ")";
		}else if(a%2==0 & b%2==0 & c%2==0) {
			Integer mul = a*b*c;
			String s = mul.toString();
			res = s +ej1RFAUX(a/2, b-2, c/2);
		}else {
			Integer sum = a+b+c;
			 String s = sum.toString();
			res = s + ej1RFAUX(a/3, b-3, c/3);
			
		}
		return res;
	}
	
	public static String ej1RNF2(Integer a, Integer b, Integer c) {
		return ej1RNF2AUX("", a,b,c);
	}
	
	public static String ej1RNF2AUX(String res,
			Integer a, Integer b, Integer c) {
		//Conseguimos que sea recursiva no final haciendo que en cada llamada recursiva haga la operacion de transformacion
		//Ya que la recursion no final consiste en hacer una operacion junto a la llamada recursiva
		if(a<3 & b<3 & c<3) {
			Integer mul = a*b*c;
			res = "(" + mul.toString() + ")";
		}else if(a<5 | b <5 | c<5) {
			Integer sum = a+b+c;
			res = "(" + sum.toString() + ")";
		}else if(a%2==0 & b%2==0 & c%2==0) {
			Integer mul = a*b*c;
			res = mul.toString() + ej1RNF2AUX(res, a/2, b-2, c/2);
		}else {
			Integer sum = a+b+c;
			res = sum.toString() + ej1RNF2AUX(res, a/3, b-3, c/3);
			
		}
		return res;
	}
	
	public static String ej1Iterv2(Integer a, Integer b, Integer c) {
		String res = "";
		Map<IntTrio, String> m = new HashMap<>();
		IntTrio clave = IntTrio.of(a, b, c);
		Integer a_it = 0;
		Integer b_it = 0;
		Integer c_it = 0;
		
		while(a_it <= a) {
			while(b_it <= b) {
				while(c_it <= c) {
					IntTrio clave_actual = IntTrio.of(a_it, b_it, c_it);
					if(a_it<3 && b_it<3 && c_it<3) {
						Integer mul = a_it*b_it*c_it;
						res = "(" + mul.toString() + ")";
						m.put(clave_actual, res);
					}else if(a_it<5 || b_it <5 || c_it<5) {
						Integer sum = a_it+b_it+c_it;
						res = "(" + sum.toString() + ")";
						m.put(clave_actual, res);
					}else if(a_it%2==0 && b_it%2==0 && c_it%2==0){
						IntTrio clave1 = IntTrio.of(a_it/2, b_it-2, c_it/2);
						Integer mul = a_it*b_it*c_it;
						res = mul.toString() + m.get(clave1);
						m.put(clave_actual, res);
					}else {
						IntTrio clave1 = IntTrio.of(a_it/3, b_it-3, c_it/3);
						Integer sum = a_it+b_it+c_it;
						res = sum.toString() + m.get(clave1);
						m.put(clave_actual, res);
					}
					c_it++;
				}
				c_it = 0;
				b_it++;
			}
			b_it=0;
			a_it++;
		}
		return m.get(clave);
	}
	
	//Funcional parecido al ejercicio 1 del entregable anterior, usar un record of con los tres valores y el next que use las llamadas recursivas de la funcion, una vez ahí usar stream para llamar a of para invocarlo y a next para hacer la llamadas recursivas
	
	public static record Funcion(Integer a, Integer b, Integer c, String res) {
		public static Funcion of(Integer a, Integer b, Integer c, String res) {
			return new Funcion(a, b, c, res);
		}
		
		public Funcion next() {
			Funcion res = Funcion.of(a,b,c, "");
			if(a<3 & b<3 & c<3) {
				Integer mul = a*b*c;
				String cadena = "(" + mul.toString() + ")";
				res = Funcion.of(a,b,c, cadena);
			}else if(a<5 | b <5 | c<5) {
				Integer sum = a+b+c;
				String cadena = "(" + sum.toString() + ")";
				res = Funcion.of(a,b,c, cadena);
			}else if(a%2==0 & b%2==0 & c%2==0) {
				Integer mul = a*b*c;
				String s = mul.toString();
				res = Funcion.of(a/2, b-2, c/2, s);
			}else {
				Integer sum = a+b+c;
				String s = sum.toString();
				res = Funcion.of(a/3, b-3, c/3, s);
			}
			return res;
		}
	}
	
	/*public static String ej4Funcional(Integer a, Integer b, Integer c) {
		Funcion f = Funcion.of(a, b, c, "");
		Stream<Funcion> stream = Stream.iterate(f, r->r.next());
		Optional<Funcion> res = stream.filter(r->r.a == r.b).findFirst();
		return res.isPresent() ? res.get().a : -1;
	}*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Integer[] pruebaArray = {20,40,80};
		List<Integer> prueba = new ArrayList<Integer>();
		prueba = Arrays.asList(pruebaArray);
		System.out.println(ej1RNF2(20, 40, 80));
		System.out.println(ej1RF(20, 40, 80));
		System.out.println(ej1Iter(prueba));*/
		
		List<String> ls = Files2.linesFromFile("tests/PI2Ej1DatosEntrada.txt");
		
		for (String linea : ls) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length-1;i++) {
	            String s1 = line[i];
	            Integer a1 = Integer.parseInt(s1);
	            String s2 = line[i+1];
	            Integer a2 = Integer.parseInt(s2);
	            String s3 = line[i+2];
	            Integer a3 = Integer.parseInt(s3);
	            i++;
	            System.out.println("\nParámetros de entrada: "+ a1+", "+a2+", "+a3);
	            System.out.println("1. Recursivo final: "+ej1RF(a1, a2, a3));
	            System.out.println("2. Recursiva no final: "+ej1RNF2(a1, a2, a3));
	            System.out.println("3. Iterativo: "+ ej1Iterv2(a1, a2, a3));
	            //System.out.println("4. Funcional: "+ ej4Func(a1, a2));
	            //Comentamos el funcional porque no sabemos porqué no itera
	            System.out.println("");
	        }
		}
		
	}

}
