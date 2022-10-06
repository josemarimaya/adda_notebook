package tests;

import java.util.List;
import java.util.function.Function;

import us.lsi.common.IntPair;
import us.lsi.geometria.Punto2D;
import us.lsi.streams.Stream2;

import ejemplos.Ejemplo1;
import ejemplos.Ejemplo2;

public class TestEjemplos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ejemplo1("ficherosPractica/Ejemplo1DatosEntrada.txt");
		Ejemplo2("ficherosPractica/Ejemplo2DatosEntrada.txt");

	}
	
	
	public static void Ejemplo1(String fichero) {
		Function<String, Punto2D> parsePunto = s-> {
			String[] v = s.split(",");
			return Punto2D.of(Double.valueOf(v[0]), Double.valueOf(v[1]));
		};
		
		List<Punto2D> ls = Stream2.file(fichero).map(parsePunto).toList();
		
		System.out.println("1. Solución funcional: \n " + Ejemplo1.solucionFuncional(ls));
		System.out.println("2. Solución iterativa: \n " + Ejemplo1.solucionIterativa(ls));
		System.out.println("3. Solucion recursiva final: \n" + Ejemplo1.solucionRecursiva(ls));
		
		System.out.println("____________________________________________________________________");
	}
	
	public static void Ejemplo2(String fichero) {
		List<IntPair> ls = Stream2.file(fichero).map(IntPair::parse).toList();
		
		ls.forEach(par->{
			Integer a = par.first();
			Integer b = par.second();
			
			System.out.println("1. Solución Recursiva No Final: \n" +Ejemplo2.solucionRecursivaNoFinal(a,b));
			System.out.println("2. Solución Recursiva Final: \n" + Ejemplo2.solucionRecursivaFinal(a,b));
			System.out.println("3. Solución iterativa: \n" + Ejemplo2.solucionIterativa(a,b));
			System.out.println("4. Solución funcional: \n" + Ejemplo2.solucionFuncional(a,b));
			
			System.out.println("-----------------------");
		});
		
		System.out.println("____________________________________________________________________");
	}
	
}
