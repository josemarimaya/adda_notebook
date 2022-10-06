package ejemplos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Punto2D.Cuadrante;

public class Ejemplo1 {
	
	public static Map<Punto2D.Cuadrante,Double> solucionFuncional(List<Punto2D> l){
		return l.stream()
		.collect(Collectors.groupingBy(Punto2D::getCuadrante,
				// Agrupamos por las coordenadas x por cuadrante
		Collectors.<Punto2D,Double>reducing(0.,x->x.x(),
				// Se suma los valores del eje x de manera subsiguiente x, x2, x3... xn que haya en el cuadrante
				(x,y)->x+y)));
	}
	
	/*
	 * Binary Operator recibe como entrada forzosamente dos parámetros del mismo tipo
	 * y forzosamente devuelve un resultado con el tipo de los valores de entrada
	 * */

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public static Map<Punto2D.Cuadrante, Double>  solucionIterativa(List<Punto2D> ls) {
		// TODO Auto-generated method stub
		// Inicializamos la secuencia
		// Inicializamos el acumulador
		
		// Abrimos bucle while
			// Función de acumulación
			// next(elemento)
		
		// return
		
		Integer e = 0;
		Map<Punto2D.Cuadrante, Double> res = new HashMap<>();
		while(e<ls.size()) {
			
			Punto2D p = ls.get(e);
			Cuadrante key = p.getCuadrante();
			
			if(res.containsKey(key)) {
				res.put(key, res.get(key) + p.x());
			}else {
				res.put(key, p.x());
			}
			
			e++;
		}
		return res;
	}

	public static Map<Punto2D.Cuadrante, Double>  solucionRecursiva(List<Punto2D> ls) {
		
		// Inicializamos la secuencia
		// Inicializamos el acumulador
		// acumulador = llamadarecursiva()
		// return acumulador
		
		Integer e = 0;
		Map<Punto2D.Cuadrante, Double> res = new HashMap<>();
		
		res = solucionRecursiva(e, res, ls);
		
		return res;
	}

	private static Map<Cuadrante, Double> solucionRecursiva(Integer e, Map<Cuadrante, Double> res, List<Punto2D> ls) {
		// if hay siguiente
		// llamadarecursiva(next(e), funcionAcumulacion, ls)
		// return acumulador
		
		if(e < ls.size()) {
			Punto2D p = ls.get(e);
			Cuadrante key = p.getCuadrante();
			
			if(res.containsKey(key)) {
				res.put(key, res.get(key) + p.x());
			}else {
				res.put(key, p.x());
			}
			
			res = solucionRecursiva(e+1, res, ls);
		}
		
		return res;
	}

}
