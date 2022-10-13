package ejemplos;

import java.util.HashMap;
import java.util.Map;

import us.lsi.common.IntPair;

public class Ejemplo3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static Integer solucionRecursivaSinMemoria(Integer a, Integer b) {
		// TODO Auto-generated method stub
		Integer ac = null;
		if(a<2 || b < 2) {
			ac = a*a+b;
		}else {
			ac = solucionRecursivaSinMemoria(a/2, b-1) + solucionRecursivaSinMemoria(a/3, b-2);
		}
		return ac;
	}

	public static Integer solucionRecursivaConMemoria(Integer a, Integer b) {
		// TODO Auto-generated method stub
		Map<IntPair, Integer> m = new HashMap<>();
		return solucionRecursivaAux(a,b,m);
	}
	
	public static Integer solucionRecursivaAux(Integer a,Integer b, Map<IntPair, Integer>m) {
		Integer ac = null;
		IntPair key = IntPair.of(a,b);
		// Compruebo el valor que hay en memoria
		if(m.containsKey(key)) {
			// Si está en memoria el valor el acumulador es ese valor
			ac = m.get(key);
		}else {
			if(a<2 || b < 2) {
				ac = a*a+b;
			}else {
				ac = solucionRecursivaAux(a/2,b-1,m) + solucionRecursivaAux(a/3,b-2,m);
			}
			// Como no estaba en memoria guado la solución en la memoria para este caso
			m.put(IntPair.of(a,b), ac);
		}
		return ac;
	}

	public static Integer solucionIterativa(Integer a, Integer b) {
		// TODO Auto-generated method stub
		Map<IntPair, Integer> m = new HashMap<>();
		Integer ac = null;
		
		// Vamos iterando de 0 hacia a y b usando tantos bucles como datos de entrada haya
		for (int i = 0; i <= a ; i++) {
			for (int j = 0; j <= b; j++) {
				// Usamos los iteradores como punteros para calcular los casos base
				if(i < 2 || j < 2) {
					ac = i*i+j;
				}else {
					ac = m.get(IntPair.of(i/2, j-1)) + m.get(IntPair.of(i/3, j-2));
				}
				// Guardamos el resultado de la iteracion en el acumulador
				m.put(IntPair.of(i,j), ac);
			}
		}
		
		// Cogemos como resultado la iteración final que son a,b
		return m.get(IntPair.of(a,b));
	}

}
