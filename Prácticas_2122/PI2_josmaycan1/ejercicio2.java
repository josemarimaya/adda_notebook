package PI2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Matrix;

public class ejercicio2 {
	
	/*Dada una matriz de 𝑛 𝑥 𝑛 cadena de caracteres (con 𝑛=2m; siendo m un número entero
		mayor que 0), devolver una lista de cadenas de caracteres que incluya las cadenas de
		longitud 4 que se forman uniendo las 4 cadenas de las 4 esquinas de la matriz principal,
		y de cada una de sus 4 submatrices, y así sucesivamente hasta llegar a una matriz de 2x2.
		El orden en el que se unen las cadenas de las esquinas es: superior izquierda, superior
		derecha, inferior izquierda, e inferior derecha.*/
	
	
	public static List<String> ej2RF(Matrix<String> m) {
		List<String> res = new ArrayList<String>();
		//Calculamos el numero de filas y numero de columnas de la matriz principal que usaremos para operar
		Integer nf = m.nf();
		Integer nc = m.nc();
		String primeraCadena = m.get(0,0) + m.get(nf-1, 0) +m.get(0,nc-1) +  m.get(nf-1, nc-1) ;
		res.add(primeraCadena);
		//Cuando la matriz sea una 2x2 se devuelve el resultado
		if(nf>2 && nc >2) {
			//Aquí hay que unir las 4 cadenas de las 4 esquinas de la matriz principal
			//List<String> ls = CreaSubmatrix(m);
			
			//Dividir la matriz en 4 submatrices de 4
			/*List<String> m0 = ej2RF(m.view(0));
			List<String> m1 = ej2RF(m.view(1));
			List<String> m2 = ej2RF(m.view(2));
			List<String> m3 = ej2RF(m.view(3));*/
			
			res.addAll(ej2RF(m.view(0)));
			res.addAll(ej2RF(m.view(1)));
			res.addAll(ej2RF(m.view(2)));
			res.addAll(ej2RF(m.view(3)));
			//¿Cómo unimos las cadenas de caracteres ?
			//A esas submatrices hay que unirles las cuatro cadenas de caracteres de las cuatro esquinas
		}
		return res;
	}
	
	//Con este metodo creamos una Lista con la submatriz y sus cuatro esquinas encadenadas
	public static String CreaSubmatrix(Matrix<String> m){
		Integer nf = m.nf();
		Integer nc = m.nc();
		/*List<String> res = List.of(m.get(0,0), m.get(nf-1, 0), 
				m.get(0,nc-1), m.get(nf-1, nc-1));*/
		String res = m.get(0,0) + m.get(nf-1, 0) +m.get(0,nc-1) +  m.get(nf-1, nc-1) ;
		return res; 
	}
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Lectura 
		String file = "tests/PI2Ej2DatosEntrada1.txt";
		Matrix<String> r;
		List<String> datos = Files2.streamFromFile(file)
		//Pasamos a stream el fichero y convertimos cada valor separado por espacio en integer
		.map(l->List2.parse(l, " ", String::toString))//Para hacer lectura tenemos que cambiarlo a toString
		//Aplanamos la lista para luego poder crear el array bidimensional
		.flatMap(l->l.stream())
		.collect(Collectors.toList());
		
		//Hacemos casting de int porque el sqrt lo que devuelve en general son double
		//Hacemos la raiz cuadrada para saber el tamaño de la matriz nxn
		Integer n = (int) Math.sqrt(datos.size());
		//Pasamos la matriz a array y ya podemos trabajar con ella n,n es el tamaño porque es igual a nxn
		r = Matrix.of(datos.toArray(String[]::new), n, n);
		
		
		
		System.out.println(ej2RF(r));

	}

}
