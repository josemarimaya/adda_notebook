package PI2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.common.Files2;

public class ejercicio4 {
	
	/*Diseñar un algoritmo recursivo, con y sin memoria, y posteriormente encontrar un
	algoritmo iterativo que calcule los valores de la recurrencia fn = 2*fn−1 + 4*fn−2 + 6*fn−3,
	f2 = 6, f1 = 4, f0 = 2.*/
	
	public static Long ej4FRSM(Integer n) {
		return ej4FRSMAUX(n);
		
	}
	
	public static Long ej4FRSMAUX(Integer n) {
		Long res = 0L;
		if(n==0) {
			res = 2L;
		}else if(n==1) {
			res = 4L;
		}else if(n==2) {
			res = 6L;
		}else {
			res = 2*ej4FRSMAUX(n-1)+4*ej4FRSMAUX(n-2)+6*ej4FRSMAUX(n-3);
		}
		
		return res;
		
	}
	
	public static Long ej4FRM(Integer n) {
		return ej4FRMAUX(n, 0L, new HashMap<>());
	}
	
	public static Long ej4FRMAUX(Integer n, Long r, Map<Integer, Long> m) {
		if(m.containsKey(n)) {
			r = m.get(n);
		}else if(n.equals(0)) {
			r = 2L;
			m.put(n, r);
		}else if(n.equals(1)) {
			r = 4L;
			m.put(n, r);
		}else if(n.equals(2)) {
			r = 6L;
			m.put(n, r);
		}else {
			r = 2*ej4FRMAUX(n-1, r, m)+4*ej4FRMAUX(n-2, r, m)+6*ej4FRMAUX(n-3, r, m); 
			m.put(n, r);
		}
		return r;
	}
	
	public static Long ej4Iter(Integer n) {
		Long res = 0L;
		Map<Integer, Long> m = new HashMap<>();
		//No creo que ninguna clave más porque n es lo que nos servirá de clave para el map
		Integer n_it = 0;
		//Va a ser la variable con la actualizaremos el map dentro del bucle
		while(n_it<=n) {
			if(n_it.equals(0)) {
				res = 2L;
				m.put(n_it, res);
			}else if (n_it.equals(1)) {
				res = 4L;
				m.put(n_it, res);
			}else if (n_it.equals(2)) {
				res = 6L;
				m.put(n_it, res);
			}else {
				//Recordamos que hacemos tres claves ya que vamos a hacer 3 llamadas recursivas cada una con su operacion
				Integer clave1 = n_it-1;
				Integer clave2 = n_it-2;
				Integer clave3 = n_it-3;
				//Obtenemos el resultado de las operaciones de los valores que hay en dichas claves con su llamada recursiva
				res = 2*m.get(clave1)+4*m.get(clave2)+6*m.get(clave3);
				//Literalmente tenemos que hacer las mismas funciones recursivas que cuando estamos trabajando en bucles recursivos
				m.put(n_it, res);
			}
			n_it++;
		}
		
		return m.get(n);
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println(ej4Iter(5));
		
		List<String> ls = Files2.linesFromFile("tests/PI2Ej4DatosEntrada.txt");
		for (String linea : ls) {
			//Al hacer el split no quitamos el n= sino que lo sustituimos por nada, así que igualmente tenemos que invocar el segundo valor que es el que necesitamos
	        String[] line = linea.split("n=");
	        //Es un 1 porque es donde se encuentra el Integer dentro del array spliteado
	        //System.out.println("Este es el valor de line 0" +line[0]);
	        //Ejemplo de lo que tenemos cuando hacemos el split del valor spliteado: nada
	        String s1 = line[1];
            Integer a1 = Integer.parseInt(s1);
	        System.out.println("\nParámetros de entrada: "+a1+ "\nSolucion sin memoria: " + ej4FRSM(a1));
            System.out.println("\nSolucion con memoria:" + ej4FRM(a1));
            System.out.println("\nSolucion iterativa:" + ej4Iter(a1));
	       
	        
	       
		}

	}

}
