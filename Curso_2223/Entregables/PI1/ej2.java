package pruebas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ej2 {
	
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
	
	public static record Trio(Integer a, Integer b, String s) {
		public static Trio of(Integer a, Integer b, String s) {
			return new Trio(a,b,s);
		}
	}
	
	/*public static Integer ej2Iterv2(Integer a, Integer b, String s) {
		Integer res= 0;
		Map<Trio, Integer> mem = new HashMap<Trio, Integer>();
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				for (int k = 0; k < s.length(); k++) {
					Trio temp = Trio.of(a, b, s);
					if(s.length() == 0) {
						mem.put(temp, a*a + b*b);
					}else if(i < 2 || j < 2) {
						mem.put(temp, s.length()+a+b);
					}else if(i%s.length() < j%s.length()) {
						Integer mul = i*j;
						Integer res_t1 = mul + mem.get(Trio.of(i-1, j/2, s.substring(i%s.length(), j%s.length())));
						mem.put(temp, res_t1);
					}else {
						Integer sum = i+j;
						Integer res_t2 = sum +mem.get(Trio.of(i/2, j-1, s.substring(j%s.length(), i%s.length())));
						mem.put(temp, res_t2);
					}
				}
			}
		}
		return res = mem.get(Trio.of(a, b, s));
	}*/
	
	public static void lectura(String s) {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(ej2RNF(10, 20, "adda"));
		System.out.println(ej2RF(10, 20, "adda"));
		System.out.println(ej2Iter(10, 20, "adda"));
		//System.out.println(ej2Iterv2(10, 20, "adda"));

	}

}
