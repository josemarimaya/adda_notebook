package ejemplos;

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

	public static String solucionFuncional(Integer a, Integer b) {
		// TODO Auto-generated method stub
		return null;
	}

}
