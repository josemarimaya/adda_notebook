package PI2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Files2;
import us.lsi.common.IntTrio;

public class ejercicio5 {
	
	/*Diseñar un algoritmo recursivo, con y sin memoria, y posteriormente encontrar un
		algoritmo iterativo para la siguiente definición:
		𝑔(𝑎, 𝑏, 𝑐) = +
		𝑎 + 𝑏! + 2 ∗ 𝑐, 𝑎 < 3 ∨ 𝑏 < 3 ∨ 𝑐 < 3
		𝑔(𝑎 − 1, 𝑏/2, 𝑐/2) + 𝑔(𝑎 − 3, 𝑏/3, 𝑐/3), 𝑎 𝑒𝑠 𝑚ú𝑙𝑡𝑖𝑝𝑙𝑜 𝑑𝑒 𝑏
		𝑔 1
		𝑎
		3
		, 𝑏 − 3, 𝑐 − 34 + 𝑔 1
		𝑎
		2
		, 𝑏 − 2, 𝑐 − 24 , 𝑒𝑛 𝑜𝑡𝑟𝑜 𝑐𝑎𝑠𝑜
		siendo a, b y c números enteros positivos.*/
	
	public static Integer ej5FRSM(Integer a, Integer b, Integer c) {
		return ej5FRSMAUX(a, b, c);
	}
	
	public static Integer ej5FRSMAUX(Integer a, Integer b, Integer c) {
		Integer res = 0;
		if(a<3 || b<3 || c<3) {
			res = a + b*b + 2*c;
		}else if(a%b==0) {
			res = ej5FRSMAUX(a-1, b/2, c/2) 
					+ ej5FRSMAUX(a-3, b/3, c/3);
		}else {
			res = ej5FRSMAUX(a/3, b-3, c-3) 
					+ ej5FRSMAUX(a/2, b-2, c-2);
		}
		return res;
	}
	
	public static Integer ej5FRM(Integer a, Integer b, Integer c) {
		return ej5FRMAUX(a, b, c, new HashMap<>());
	}
	
	public static Integer ej5FRMAUX(Integer a, Integer b,
			Integer c, Map<Integer, Integer> m) {
		Integer res = 0;
		if(m.containsKey(a)) {
			res = m.get(a);
		}else if (a<3 || b<3 || c<3) {
			res = a + b*b + 2*c;
		}else if (a%b==0) {
			res = ej5FRSMAUX(a-1, b/2, c/2) 
					+ ej5FRSMAUX(a-3, b/3, c/3);
		}else {
			res = ej5FRSMAUX(a/3, b-3, c-3) 
					+ ej5FRSMAUX(a/2, b-2, c-2);
		}
		return res;
	}
	
	public static Long ej5Iter(Integer a, Integer b, Integer c) {
		Long res = 0L;
		Integer a_it = 0;
		Integer b_it = 0;
		Integer c_it = 0;
		IntTrio clave = IntTrio.of(a, b, c);
		//Tenemos que usar la misma manera de trabajar que en el ejercicio 3 del laboratorio pero con 3 bucles, usando a,b,c en ese orden
		Map<IntTrio, Long> m = new HashMap<>();
		while(a_it <= a) {
			while(b_it <= b) {
				while(c_it <= c) {
					IntTrio clave_actual = IntTrio.of(a_it, b_it, c_it);
					if(a_it<3 || b_it<3 || c_it<3) {
						res = (long) a_it + b_it*b_it + 2*c_it;
						m.put(clave_actual, res);
					}else if(a%b==0) {
						IntTrio clave1 = IntTrio.of(a_it-1, b_it/2, c_it/2);
						IntTrio clave2 = IntTrio.of(a_it-3, b_it/3, c_it/3);
						res = m.get(clave1)+m.get(clave2);
						m.put(clave_actual, res);
					}else {
						IntTrio clave1 = IntTrio.of(a_it/3, b_it-3, c_it-3);
						IntTrio clave2 = IntTrio.of(a_it/2, b_it-2, c_it-2);
						res = m.get(clave1)+m.get(clave2);
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(ej5FRSM(20,10,5));
		//System.out.println(ej5FRM(20, 10, 5));
		
		//System.out.println(ej5Iter(20, 10, 5));
		List<String> ls = Files2.linesFromFile("tests/PI2Ej5DatosEntrada.txt");
		
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
	            System.out.println("1. Recursivo sin memoria: "+ej5FRSM(a1, a2, a3));
	            System.out.println("2. Recursivo con memoria: "+ej5FRM(a1, a2, a3));
	            System.out.println("3. Iterativo: "+ej5Iter(a1, a2, a3));
	            System.out.println("");
	        }
		}

	}

}
