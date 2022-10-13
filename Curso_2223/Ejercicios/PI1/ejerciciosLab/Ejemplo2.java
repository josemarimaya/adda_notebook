package ejemplos;

import java.util.stream.Stream;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Punto2D.Cuadrante;

public class Ejemplo2 {

	public static String solucionRecursivaNoFinal(Integer a, Integer b) {
		// TODO Auto-generated method stub
		
		String res = "";
		if(a<5 || b<5) { // Caso base
			res = String.format("(%d)", a*b);
		}else { // Caso recursivo
			res = String.format("%d", a+b) + solucionRecursivaNoFinal(a/2, b-2);
		}
		return res;
	}

	public static String solucionIterativa(Integer a, Integer b) {
		// TODO Auto-generated method stub
				// Inicializamos la secuencia
				// Inicializamos el acumulador
				
				// Abrimos bucle while
					// Función de acumulación
					// next(elemento)
				
				// return
		String res = "";
		while(!(a<5 || b<5)) {
			res = String.format("%s%d",res, a+b);
			a = a/2;
			b = b-2;
		}
		// Devolvemos el acumulador con el caso base ya que es la última iteración
		
		return res + String.format("(%d)", a*b);
	}

	public static String solucionRecursivaFinal(Integer a, Integer b) {
		// TODO Auto-generated method stub
		
		// Inicializamos la secuencia
		// Inicializamos el acumulador
		// acumulador = llamadarecursiva()
		// return acumulador
		
		String res = "";
		res = solucionRecursivaFinal(a,b,res);
		return res;
	}

	private static String solucionRecursivaFinal(Integer a, Integer b, String res) {
		// if hay siguiente
		// else --> llamadarecursiva(next(e), funcionAcumulacion, ls)
		// return acumulador
				
		if(a<5 || b<5) {
			res = res + String.format("(%d)", a*b);
		}else{
			res =  solucionRecursivaFinal(a/2, b-2,String.format("%s%d",res, a+b));
		}
		return res;
	}
	
	public static record Tupla(Integer a, Integer b, String ac ) {
		public static Tupla of(Integer a, Integer b, String ac ) {
			return new Tupla(a,b,ac);
		}
		
		public static Tupla first(Integer a, Integer b) {
			return of(a,b, ""); 
		}
		
		public Tupla next() {
			//return of(a/2,b-2, ac + String.format("%d", a+b));
			return of(a/2, b-2, ac+String.format("%d", a+b));
		}
		
		public Boolean isCaseBase() {
			return a < 5 || b < 5;
		}
	}
	
	public static String solucionFuncional(Integer a, Integer b) {
		// TODO Auto-generated method stub
		Tupla elementoFinal = Stream.iterate(Tupla.first(a, b), elem -> elem.next())
				// Se queda con los elementos de caso base ya que así tengo los valores de toda las llamadas anteriores para luego añadirlo al caso base
				.filter(elem-> elem.isCaseBase())
				// Cogemos el primer valor porque no tenemos criterio de parada
				.findFirst().get();
		/*
		Tupla elementoFinal2 = Stream.iterate(Tupla.first(a, b), elem -> elem.next())
				// DropWhile nos descartará todos aquellos valores que no cumplen el boolean que hay, en este caso descarta aquellos que NO cumplen el caso base
				.dropWhile(elem-> !elem.isCaseBase())
				.findFirst().get();
		*/
		
		return elementoFinal.ac() + String.format("(%d)", elementoFinal.a * elementoFinal.b);
		
	}

}
