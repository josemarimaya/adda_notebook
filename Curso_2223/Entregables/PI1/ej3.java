package ejercicios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.geometria.Punto2D;
import us.lsi.geometria.Punto2D.Cuadrante;
import us.lsi.streams.Stream2;

public class ej3 {
	
	
	/*A partir de 2 ficheros ordenados de objetos de tipo Punto2D, obtener una lista ordenada
	de puntos que incluya sólo los puntos del primer y tercer cuadrante. Para realizar la fusión
	debe hacer uso de iteradores directamente sobre los ficheros de entrada, no permitiéndose
	almacenar los puntos en listas y hacer fusión de listas.*/
	
	public static List<Punto2D> ej3Iter(List<Punto2D> ls){
		List<Punto2D> res = new ArrayList<>();
		Integer i = 0;
		while(i<ls.size()) {
			
			Punto2D p = ls.get(i);
			Cuadrante c = p.getCuadrante();
			if( (c == Cuadrante.PRIMER_CUADRANTE) || (c == Cuadrante.TERCER_CUADRANTE) ) {
				res.add(p);
			}
			i++;
		}
		return res;
	}
	
	public static List<Punto2D> ej3RF(List<Punto2D> ls){
		
		List<Punto2D> res = new ArrayList<>();
		return ej3RF(ls, 0, res);
		
	}
	
	private static List<Punto2D> ej3RF(List<Punto2D> ls, Integer i, List<Punto2D> res){
		
		if(i<ls.size()) {
			Punto2D p = ls.get(i);
			Cuadrante c = p.getCuadrante();
			if( (c == Cuadrante.PRIMER_CUADRANTE) || (c == Cuadrante.TERCER_CUADRANTE) ) {
				res.add(p);
				res = ej3RF(ls, i+1, res);
			}else {
				res = ej3RF(ls, i+1, res);
			}
		}
		
		return res;
	}
	
	public static List<Punto2D> ej3Funcional(List<Punto2D> ls){
		return ls.stream().filter(p-> (p.getCuadrante() == Cuadrante.PRIMER_CUADRANTE) || (p.getCuadrante() == Cuadrante.TERCER_CUADRANTE) ).toList();
	}
	
	
	// Aquí usaremos como referencia fusionSecuenciasOrdenadas de clase
	public static List<Punto2D> algoritmoIterOrdenado(String s1, String s2){
		Iterator<String> it1 = Stream2.file(s1).iterator();
		Iterator<String> it2 = Stream2.file(s2).iterator();
		
		String i1 = it1.next();
		String i2 = it2.next();
		List<Punto2D> res = new ArrayList<>();
		while(it1.hasNext() && it2.hasNext()) {
			String line1[] = i1.split(",");
			String line2[] = i2.split(",");
			
			Punto2D p = Punto2D.of(0.0,0.0);
			
			Punto2D p1aux = Punto2D.of(Double.parseDouble(line1[0]), Double.parseDouble(line1[1]));
			Punto2D p2aux = Punto2D.of(Double.parseDouble(line2[0]), Double.parseDouble(line2[1]));
			
			if(p1aux.compareTo(p2aux) < 0) {
				p = p1aux;
				i1 = it1.next();
			} else {
				p = p2aux;
				i2 = it2.next();
			}
			
			if( (p.getCuadrante() == Cuadrante.PRIMER_CUADRANTE) || (p.getCuadrante() == Cuadrante.TERCER_CUADRANTE) ) {
				res.add(p);
			}
			
		}
		
		return res;
		
	}
	
	public static List<Punto2D> algoritmoROrdenado(String s1, String s2){
		Iterator<String> it1 = Stream2.file(s1).iterator();
		Iterator<String> it2 = Stream2.file(s2).iterator();
		String i1 = it1.next();
		String i2 = it2.next();
		List<Punto2D> res = new ArrayList<>();
		
		return algoritmoROrdenado(it1, it2, i1,i2, res);
	}
	
	public static List<Punto2D> algoritmoROrdenado(Iterator<String> it1, Iterator<String> it2, String i1, String i2, List<Punto2D> res){
		if((it1.hasNext() && it2.hasNext())) {
			String line1[] = i1.split(",");
			String line2[] = i2.split(",");
			
			Punto2D p = Punto2D.of(0.0,0.0);
			Punto2D p1aux = Punto2D.of(Double.parseDouble(line1[0]), Double.parseDouble(line1[1]));
			Punto2D p2aux = Punto2D.of(Double.parseDouble(line2[0]), Double.parseDouble(line2[1]));
			if(p1aux.compareTo(p2aux) < 0) {
				p = p1aux;
				i1 = it1.next();
			} else {
				p = p2aux;
				i2 = it2.next();
			}
			
			if( (p.getCuadrante() == Cuadrante.PRIMER_CUADRANTE) || (p.getCuadrante() == Cuadrante.TERCER_CUADRANTE) ) {
				res.add(p);
				res = algoritmoROrdenado(it1, it2, i1, i2, res);
			}else {
				res = algoritmoROrdenado(it1, it2, i1, i2, res);
			}
		}
		
		return res;
		
	}
	
	// Usamos la referencia de la lectura iterativa del ejercicio de Miguel Toro de la página 256

	public static List<Punto2D> algoritmoIterOrdenado(List<Punto2D> ls1, List<Punto2D> ls2){
		List<Punto2D> res = new ArrayList<>();
		Integer i1 = 0;
		Integer i2 = 0;
		Integer n1 = ls1.size();
		Integer n2 = ls2.size();
		
		while(n1 + n2 - i1 - i2 > 0) {
			Punto2D p = Punto2D.of(0.0, 0.0);
			if(i2 >= n2 || i1 < n1 && 
					// Uso de compareTo para ver que el valor de ls1 en ese momento es menor que el de ls2
					ls1.get(i1).compareTo(ls2.get(i2)) < 0) {
				p = ls1.get(i1);
				i1 = i1 + 1;
			}else {
				p = ls2.get(i2);
				i2 = i2 +1;
			}
			
			if( (p.getCuadrante() == Cuadrante.PRIMER_CUADRANTE) || (p.getCuadrante() == Cuadrante.TERCER_CUADRANTE) ) {
				res.add(p);
			}
			
		}
		
		return res;
	}
	
	public static List<Punto2D> algoritmoRFOrdenado(List<Punto2D> ls1, List<Punto2D> ls2){
		List<Punto2D> res = new ArrayList<>();
		Integer i1 = 0;
		Integer i2 = 0;
		Integer n1 = ls1.size();
		Integer n2 = ls2.size();
		Punto2D p = Punto2D.of(0.0, 0.0);
		return algoritmoRFOrdenado(ls1, ls2, res, i1, i2, n1, n2, p);
	}
	
	private static List<Punto2D> algoritmoRFOrdenado(List<Punto2D> ls1, List<Punto2D> ls2, List<Punto2D> res,
			Integer i1, Integer i2, Integer n1, Integer n2, Punto2D p){
		
		if(n1 + n2 - i1 - i2 > 0) {
			if(i2 >= n2 || i1 < n1 && ls1.get(i1).compareTo(ls2.get(i2)) < 0) {
				if( (p.getCuadrante() == Cuadrante.PRIMER_CUADRANTE) || (p.getCuadrante() == Cuadrante.TERCER_CUADRANTE) ) {
					p = ls1.get(i1);
					res.add(p);
					res = algoritmoRFOrdenado(ls1, ls2, res, i1 + 1, i2, n1, n2, p);
				}else {
					res = algoritmoRFOrdenado(ls1, ls2, res, i1 + 1, i2, n1, n2, p);
				}
			}else {
				if( (p.getCuadrante() == Cuadrante.PRIMER_CUADRANTE) || (p.getCuadrante() == Cuadrante.TERCER_CUADRANTE) ) {
					p = ls2.get(i2);
					res.add(p);
					res = algoritmoRFOrdenado(ls1, ls2, res, i1, i2 +1, n1, n2, p);
				}else {
					res = algoritmoRFOrdenado(ls1, ls2, res, i1, i2 +1, n1, n2, p);
				}
			}
			
		}
		
		return res;
	}
	
	public static void lecturaPuntoOrdenado(String fichero1, String fichero2) {
		
		List<String> ls1 = Files2.linesFromFile(fichero1);
		List<String> ls2 = Files2.linesFromFile(fichero2);
		List<Punto2D> res1 = new ArrayList<>();
		List<Punto2D> res2 = new ArrayList<>();
		
		for (String linea : ls1) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length-1;i++) {
	            String sx = line[i];
	            Double dx = Double.parseDouble(sx);
	            String sy = line[i+1];
	            Double dy = Double.parseDouble(sy);
	            Punto2D p = Punto2D.of(dx, dy);
	            res1.add(p);
	            i++;
	        }
		}
		
		for (String linea : ls2) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length-1;i++) {
	            String sx = line[i];
	            Double dx = Double.parseDouble(sx);
	            String sy = line[i+1];
	            Double dy = Double.parseDouble(sy);
	            Punto2D p = Punto2D.of(dx, dy);
	            res2.add(p);
	            i++;
	        }
		}
		
		System.out.println("La solución iterativa ordenada es: " + algoritmoIterOrdenado(res1, res2));
		System.out.println("La solución recursiva ordenada es: " + algoritmoRFOrdenado(res1, res2));
		
	}
	
	public static void lecturaPunto(String fichero1, String fichero2) {
		List<String> ls1 = Files2.linesFromFile(fichero1);
		List<String> ls2 = Files2.linesFromFile(fichero2);
		List<Punto2D> res = new ArrayList<>();
		for (String linea : ls1) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length-1;i++) {
	            String sx = line[i];
	            Double dx = Double.parseDouble(sx);
	            String sy = line[i+1];
	            Double dy = Double.parseDouble(sy);
	            Punto2D p = Punto2D.of(dx, dy);
	            res.add(p);
	            i++;
	        }
		}
		
		for (String linea : ls2) {
	        String[] line = linea.split(",");

	        for(int i=0;i<line.length-1;i++) {
	            String sx = line[i];
	            Double dx = Double.parseDouble(sx);
	            String sy = line[i+1];
	            Double dy = Double.parseDouble(sy);
	            Punto2D p = Punto2D.of(dx, dy);
	            res.add(p);
	            i++;
	        }
		}
		
		// System.out.println("La lista de argumentos es: " + res);
		
		System.out.println("1. Solución iterativa: \n " + ej3Iter(res) + "\n");
		System.out.println("2. Solución recursiva final: \n" + ej3RF(res) + "\n");
		System.out.println("3. Solución funcional: \n" + ej3Funcional(res) + "\n");
		
		
		System.out.println("________________________________________________" + "\n");
	}
	
	
	/*
	 * 	El concepto de lectura es que cogeremos los dos datos de entrada y 
	 * almacenaremos los datos en la MISMA lista de cuadrantes
	 */
	
	/*
	public static record Puntos(Punto2D p1, Punto2D p2) {
		public static Puntos of(String s1, String s2) {
			String line1[] = s1.split(",");
			String line2[] = s2.split(",");
			return new Puntos(Punto2D.of(Double.parseDouble(line1[0]), Double.parseDouble(line1[1])),
					Punto2D.of(Double.parseDouble(line2[0]), Double.parseDouble(line2[1])));
		}
		
		public static Puntos next() {
			if() {
				
			}
		}
	}
	
	public static List<Punto2D> ej3F(String s1, String s2){
		Iterator<String> it1 = Stream2.file(s1).iterator();
		Iterator<String> it2 = Stream2.file(s2).iterator();
	}
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Punto2D> ejemplo = new ArrayList<>();
		Punto2D p0 = Punto2D.of(-93.90,-6.76);
		ejemplo.add(p0);
		
		//System.out.println(ej3Iter(ejemplo));
		/*
		lecturaPunto("testsAlumnos/PI1EJ3DatosEntrada1A.txt", "testsAlumnos/PI1EJ3DatosEntrada1B.txt");
		lecturaPunto("testsAlumnos/PI1EJ3DatosEntrada2A.txt", "testsAlumnos/PI1EJ3DatosEntrada2B.txt");
		lecturaPunto("testsAlumnos/PI1EJ3DatosEntrada3A.txt", "testsAlumnos/PI1EJ3DatosEntrada3B.txt");
		*/
		
		//lecturaPuntoOrdenado("testsAlumnos/PI1EJ3DatosEntrada1A.txt", "testsAlumnos/PI1EJ3DatosEntrada1B.txt");
		//lecturaPuntoOrdenado("testsAlumnos/PI1EJ3DatosEntrada2A.txt", "testsAlumnos/PI1EJ3DatosEntrada2B.txt");
		//lecturaPuntoOrdenado("testsAlumnos/PI1EJ3DatosEntrada3A.txt", "testsAlumnos/PI1EJ3DatosEntrada3B.txt");
		System.out.println("La primera solución en iterativa es: " + algoritmoIterOrdenado("testsAlumnos/PI1EJ3DatosEntrada1A.txt", "testsAlumnos/PI1EJ3DatosEntrada1B.txt"));
		System.out.println("La segunda solución en iterativa es: " + algoritmoIterOrdenado("testsAlumnos/PI1EJ3DatosEntrada2A.txt", "testsAlumnos/PI1EJ3DatosEntrada2B.txt"));
		System.out.println("La tercera solución en iterativa es: " + algoritmoIterOrdenado("testsAlumnos/PI1EJ3DatosEntrada3A.txt", "testsAlumnos/PI1EJ3DatosEntrada3B.txt") + "\n");
		
		System.out.println("La primera solución recursiva es: " + algoritmoROrdenado("testsAlumnos/PI1EJ3DatosEntrada1A.txt", "testsAlumnos/PI1EJ3DatosEntrada1B.txt"));
		System.out.println("La segunda solución recursiva es: " + algoritmoROrdenado("testsAlumnos/PI1EJ3DatosEntrada2A.txt", "testsAlumnos/PI1EJ3DatosEntrada2B.txt"));
		System.out.println("La tercera solución recursiva es: " + algoritmoROrdenado("testsAlumnos/PI1EJ3DatosEntrada3A.txt", "testsAlumnos/PI1EJ3DatosEntrada3B.txt"));
	}

}
