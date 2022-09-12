package PI2;

import java.util.ArrayList;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.IntegerSet;

public class ejercicio3 {
	
	/*Dada una lista de enteros ordenada de mayor a menor, diseñar un algoritmo que
	devuelva un conjunto que incluya los elementos de dicha lista cuyo valor se encuentre en
	un rango [a, b) dado (siendo a y b de tipo entero). Para trabajar con conjuntos, haga uso
	del tipo IntegerSet del repositorio.*/
	
	public static IntegerSet ej3RF(List<Integer> ls, Integer a, Integer b) {
		Integer cotaInf = 0;
		Integer cotaSup = ls.size();
		return ej3RFAUX(ls, a, b, cotaInf, cotaSup);
	}
	
	//Podemos intentar aplicar el MergeSort para este método
	
	//También podemos intentar usar el método del k-esimo
	
	public static IntegerSet ej3RFAUX(List<Integer> ls, Integer a, Integer b, Integer cotaInf, Integer cotaSup) {
		//Antes de hacer el ejercicio intentar hacer el ejercicio a papel usando la bandera holandesa
		//Así inicializamos el Set
		IntegerSet res = IntegerSet.empty();
		//Deberíamos poner dos centros en vez de solo uno ya que las lista luego se puede subdividir
		if(cotaSup-cotaInf > 1) {
			//El pivote usado va a ser (cotaSup+cotaInf)/2 porque queremos ver la mitad de los índices del integer set, no el tamaño de la propia lsita por la mitad
			IntegerSet izquierda = ej3RFAUX(ls, a, b, cotaInf, (cotaSup+cotaInf)/2);
			IntegerSet derecha = ej3RFAUX(ls, a, b, (cotaSup+cotaInf)/2, cotaSup);
			//Una vez que tanto la izquierda como la derecha tienen tamaño 1 es cuando los añadimos al IntegerSet, esté vacío o no
			res.addAll(izquierda);
			res.addAll(derecha);
			return res;
			/*if() {
				
			}else if() {
				
			}*/
		} else {
			if(ls.get((cotaSup+cotaInf)/2) >= a && ls.get((cotaSup+cotaInf)/2) < b) {
				res.add(ls.get((cotaSup+cotaInf)/2));
			}
			//Devolvemos el IntegerSet con los valores comprobados
			return res;
		}
		/*if(iteraciones>= ls.size()/2) {
			//Hay que usar la bandera holandesa para hacer este ejercicio pero con el set
			if(ls.get(centro) >= a && ls.get(centro) < b) {
				//Si el valor de la lista que hace de centro está en el intervalo lo añadimos
				//Así es como se añade un valor en el Set
				res.add(centro);
				ej3RFAUX(ls, a, b, cotaInf+1, cotaSup);
			}else if (ls.get(centro) < a) {
				//Si el valor del centro es menor que a debe de estar en la parte de la izquierda ya que está ordenador de mayor a menor
				ej3RFAUX(ls, a, b, cotaInf, centro);
			}else if (ls.get(centro) > b){
				//Si el valor es mayor a b el valor a añadir debe de estar 
				ej3RFAUX(ls, a, b, centro+1, cotaSup);
			}
			iteraciones++;
		}*/
		
	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*List<Integer> ls = List.of(10,9,8,7,6,5,4,3,2,1,0);
		
		System.out.println(ej3RF(ls, 2, 5));*/
		
		List<String> fichero = Files2.linesFromFile("tests/PI2Ej3DatosEntrada.txt");
		
		for (String linea : fichero) {
	        String[] line = linea.split("#"); 
	        for(int i=0;i<line.length-1;i++) {
	        	
	        	List<Integer> valores = new ArrayList<Integer>();
	        	List<Integer> intervalos = new ArrayList<Integer>();
	        	String[] linV = line[0].split(",");
	        	String[] linInter = line[1].split(",");
	        	
	        	
	        	for (int j = 0; j < linV.length; j++) {
					String sV = linV[j];
					Integer v = Integer.parseInt(sV);
					valores.add(v);
				}
	        	for(int k = 0; k<linInter.length; k++) {
	        		String sI = linInter[k];
	        		Integer inter = Integer.parseInt(sI);
	        		intervalos.add(inter);
	        
	        	}
	        	
	        	/*System.out.println(valores);
	        	System.out.println(intervalos);*/
	        	
	        	System.out.println("El resultado es " + ej3RF(valores, intervalos.get(0), intervalos.get(1)));
	        	
	        }
		}

	}

}
