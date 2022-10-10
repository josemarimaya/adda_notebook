package ejercicios;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Files2;
import us.lsi.common.IntTrio;

public class ej4 {
	
	public static String ej4Iter(Integer a, Integer b, Integer c) {
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
					if(a_it<2 && b_it<2 || c_it < 2) {
						res = "(" + a_it.toString() + "+" + b_it.toString() + "+" + c_it.toString() + ")";
						m.put(clave_actual, res);
					}else if(a_it < 3 || b_it < 3 && c_it < 3) {
						res = "(" + c_it.toString() + "-" + b_it.toString() + "-" + a_it.toString() + ")";
						m.put(clave_actual, res);
					}else if(b_it%a_it==0 && (a_it%2==0 || b_it%2==0)){
						IntTrio clave1 = IntTrio.of(a_it-1, b_it/a_it, c_it-1);
						IntTrio clave2 = IntTrio.of(a_it-2, b_it/2, c_it/2);
						res = "(" + m.get(clave1) + "*" + m.get(clave2) + ")";
						m.put(clave_actual, res);
					}else {
						IntTrio clave1 = IntTrio.of(a_it/2, b_it-2, c_it/2);
						IntTrio clave2 = IntTrio.of(a_it/3, b_it-1, c_it/3);
						res = "(" + m.get(clave1) + "/" + m.get(clave2) + ")";
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
	
	public static String ej4RF(Integer a, Integer b, Integer c) {
		String res = "";
		if(a < 2 && b < 2 || c < 2) {
			res = "(" + a.toString() + "+" + b.toString() + "+" + c.toString() + ")"; 
		}else if (a < 3 || b < 3 && c < 3) {
			res = "(" + c.toString() + "-" + b.toString() + "-" + a.toString() + ")";
		}else if(b % a == 0 && (a % 2 == 0 || b % 2 == 0)) {
			res = "(" + ej4RF(a-1, b/a, c-1)+ "*" + ej4RF(a-2, b/2, c/2) + ")";
		}else {
			res = "(" + ej4RF(a/2, b-2, c/2) + "/" + ej4RF(a/3, b-1, c/3) + ")";
		}
		return res;
	}
	
	
	public static String ej4RFM(Integer a, Integer b, Integer c) {
		return ej4RFMAUX(a, b, c, new HashMap<>(), "", IntTrio.of(a, b, c));
	}
	
	public static String ej4RFMAUX(Integer a, Integer b, Integer c, Map<IntTrio, String> acum, String res,
			IntTrio i) {
		if(acum.containsKey(i)) {
			res = acum.get(i);
		}else {
			if(i.first() < 2 && i.second() < 2 || i.third() < 2) {
				res = "(" + i.first().toString() + "+" + i.second().toString() + "+"
						+ i.third().toString() + ")"; 
				acum.put(i, res);
			}else if (a < 3 || b < 3 && c < 3) {
				res = "(" + i.third().toString() + "-" + i.second().toString() + 
						"-" + i.first().toString() + ")";
				acum.put(i, res);
			}else if(b % a == 0 && (a % 2 == 0 || b % 2 == 0)) {
				
				res = "(" + ej4RFMAUX(i.first()-1, i.second()/i.first(), i.third()-1, acum, res, i) 
				+ "*" + ej4RF(i.first()-2, i.second()/2, i.third()/2) + ")";
				acum.put(i, res);
			}else {
				res = "(" + ej4RF(i.first()/2, i.second()-2, i.third()/2) + "/" 
						+ ej4RF(i.first()/3, i.second()-1, i.third()/3) + ")";
				acum.put(i, res);
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
	            Integer a1 = Integer.parseInt(s1);
	            String s2 = line[i+1];
	            Integer a2 = Integer.parseInt(s2);
	            String s3 = line[i+2];
	            Integer a3 = Integer.parseInt(s3);
	            i++;
	            System.out.println("\nParámetros de entrada: "+ a1+", "+a2+", "+a3);
	            System.out.println("1. Recursivo final: "+ej4RF(a1, a2, a3));
	            System.out.println("2. Recursiva con memoria: "+ej4RFM(a1, a2, a3));
	            System.out.println("3. Iterativo: "+ ej4Iter(a1, a2, a3));
	        }
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		lectura("tests/PI1Ej4DatosEntrada.txt");
		
	}

}
