package ejercicios;
import java.util.List;
import java.util.Optional;
/*import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;*/

import java.util.stream.Stream;
import us.lsi.common.Files2;

public class Ejercicio4 {
	
	/*Hay que hacerlo con la filosofía de la busqueda binaria (bandera holandesa)*/
	
	/*Diseñe un algoritmo que dados dos números n y e (con n real positivo mayor que 1 y
	e real en el intervalo [0,1)), devuelva un número real que se corresponda con la raíz cúbica
	de n con un error menor que e.*/
	

	
		// Devuelve el valor absoluto de a-mid^3
		//Mid es el valor de la raiz cúbica
		/*public static Double diferencia(Double a,Double medio)
		{
			if ((a > (medio*medio*medio))) {
				return (a-(medio*medio*medio));
			}else {
				return ((medio*medio*medio) - a);
			}
				
		}*/
		public static Double ej4Iter(Double a, Double e) {
			
			Double i = 0.;
			Double fin = a;
			//El cortocircuito se hace cuando i es menor que el fin
			while (i<fin){
				//Empezamos con un valor que sea realmente el valor a+i/2;
				//Al final acabaremos devolviendo el valor pivote (mid) que nos dará el resultado de la raíz cúbica
				//Nos da el resultado de la raíz cúbica iterando hasta que error que tengamos sea menor que el del enunciado
				Double medio = (i + fin)/2;
				//Double error = diferencia(a, medio);
				Double err = Math.abs((medio*medio*medio) - a);
				if (err <= e) {
					return medio;
				} else if ((medio*medio*medio) > a) {
					//Si el cubo de mid es mayor a a quiere decir que acercamos el nuevo fin es mid
					fin = medio;
				} else {
					//Si el cubo de mid es mas pequeño que a quiere decir que la solucion no está ahí y que lo ponemos como valor i para tenerlo como inicio
					i = medio;
				}
			}
			//Devolvemos fin ya que es el valor que nos asegura el error
			return fin;
		}
		public static Double ej4RF(Double a, Double e) {
			Double i = 0.;
			Double fin = a;
			return ej4RFAux(a, e, i, fin, (i + fin)/2);
		}
		
		public static Double ej4RFAux(Double a, Double e, Double i, Double fin, Double medio) {
			
			if (i<fin) {
				//Double error = diferencia(a, medio);
				Double err = Math.abs((medio*medio*medio) - a);
				if (err <= e) {
					return medio;
				}else if ((medio*medio*medio) > a) {
					medio = ej4RFAux(a, e, i, medio, (i + fin)/2);
				} else {
					medio = ej4RFAux(a, e, medio, fin, (i + fin)/2);
				}
			} 
			
			return medio;
		}
		
		public static record RangoDouble(Double a, Double e,Double medio, Double i, Double fin, Double err) {
			
			public static RangoDouble of(Double a, Double e, Double i, Double fin) {
				//No invocamos a medio porque va a ser una propiedad derivada
				Double medio = (i + fin)/2;
				return new RangoDouble(a, e, medio, i, fin, Math.abs((medio*medio*medio) - a));
				
			}

			
			public RangoDouble next() {
				//Double err = Math.abs((medio*medio*medio) - a);
				RangoDouble r = RangoDouble.of(a, e, i, fin);
				//if (err <= e) return r;
				if ((medio*medio*medio) > a) RangoDouble.of(a, e, i, medio);
				else RangoDouble.of(a, e, medio,fin);
				return r;
			}
		}
		
		public static Double ej4Func(Double a, Double e) {
			//Tengo que crear un record(i, fin) con of y next como el del ejemplo3 
			//En el next tenemos que hacer todos los if de la iteracion y dentro hacer los cambios dentro del record
			
		
			RangoDouble rd = RangoDouble.of(a, e, 0.0, a);
			Stream<RangoDouble> stream = Stream.iterate(rd,  r->r.next());
			Optional<RangoDouble> res = stream.
					filter(r->r.err<= r.e).findFirst();
			return res.isPresent() ? res.get().i() : -1;
		}


	public static void main(String[] args) {
		List<String> ls = Files2.linesFromFile("tests/PI1E4_DatosEntrada.txt");
		
		for (String linea : ls) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length;i++) {
	            String s1 = line[i];
	            Double a1 = Double.parseDouble(s1);
	            String s2 = line[i+1];
	            Double a2 = Double.parseDouble(s2);
	            i++;
	            System.out.println("\nParámetros de entrada: "+ a1+", "+a2);
	            System.out.println("1. Iterativa (while): "+ej4Iter(a1, a2));
	            System.out.println("2. Recursiva final: "+ej4RF(a1, a2));
	            //System.out.println("3. Funcional: "+ ej4Func(a1, a2));
	            //Comentamos el funcional porque no sabemos porqué no itera
	            System.out.println("");
	        }
		}
	}

}
