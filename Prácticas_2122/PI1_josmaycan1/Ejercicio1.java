package ejercicios;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import us.lsi.common.Files2;

public class Ejercicio1 {
	
	public static boolean ejercicio1(List<String> ls, Predicate<String> pS,
			Predicate<Integer> pI, Function<String,Integer> f){
			return ls.stream()//usamos a una lista de string que pasamos a stream
			.filter(pS)//segun si se cumple el predicado de string ps se hace lo siguiente
			.map(f)//hacemos una funcion de integer con strings
			.anyMatch(pI);//devuelve true o false dependiendo de los valores que pasen el filtro pI
			}
	
	public static Predicate<Integer> pI = x->{
		return x%2==0;
	};
	
	public static Predicate<String> pS = x->{
		return x.contains("a") || x.contains("e") || x.contains("o");
	};
	
	public static Function<String, Integer> f = x->{
		return x.length();
	};
	
	public static Boolean ejercicio1Iter(List<String> ls,  Predicate<String> pS,
			Predicate<Integer> pI, Function<String,Integer> f) {
		
		Boolean res = false;
		//List<Boolean> res = new ArrayList<Boolean>();
		Integer i = 0;
		while(i<ls.size()) {
			// El predicado sobre String devuelve cierto si dicho String contiene alguna vocal abierta (es decir, a, e ó o)
			String a = ls.get(i);
			if(pS.test(a)) {
				// La función String -> Integer devuelve la longitud de la cadena 
				Integer a1 = f.apply(a);
				// El predicado sobre Integer devuelve cierto si ese entero es par
				if(pI.test(a1)) {
					res = true;
				}	
			}
			i++;
		}
		
		return res;
	}
	
	public static Boolean ejercicio1RecurFinal(List<String> ls,
			Predicate<String> pS,
			Predicate<Integer> pI, Function<String,Integer> f) {
		Integer i = 0;
		return ejercicio1AuxRecurFinal(i, false, ls, pS, pI, f);
	}
	
	public static Boolean ejercicio1AuxRecurFinal(Integer i,Boolean res, List<String> ls,
			Predicate<String> pS,
			Predicate<Integer> pI, Function<String,Integer> f) {
		
		if(res == true) {
			return res;
		}else {
			if(ls.size()>i) {
				String a = ls.get(i);
				if(pS.test(a)) {
					Integer ai = f.apply(a);
					if(pI.test(ai)) {
						res = ejercicio1AuxRecurFinal(i+1, true, ls,pS, pI, f);
					}else {
						res = ejercicio1AuxRecurFinal(i+1, res, ls,pS, pI, f);
					}
				}else {
					res = ejercicio1AuxRecurFinal(i+1, res, ls,pS, pI, f);
				}
			}
		}
		
		return res;
	}
	
	
	
	public static List<List<String>> lecturaFichero(String s){
        List<String> lineas = Files2.linesFromFile(s);
        List<List<String>> fileComplete = new ArrayList<List<String>>();
        for(String linea : lineas) {
            List<String> temp = new ArrayList<String>();
            if(!linea.isEmpty()) {
                String[] valores = linea.split(" ");
                for(Integer j=0; j<=valores.length-1;j++) {
                    temp.add(String.valueOf(valores[j].replace(",", " ")));
                    //Aquí hacemos que por cada coma del valor haya la coma con el espacio
                    //listaTemporal.add(String.valueOf(valores[j].replace(",, ", ", ")));
                    
                }
            }
            fileComplete.add(temp);
        }
        return fileComplete;
    }
	
	


	public static void main(String[] args) {
		
		List<List<String>> ls2 =lecturaFichero("tests/PI1E1_DatosEntrada.txt");
		for(Integer i=0; i<ls2.size(); i++) {
			List<String> lista = ls2.get(i);
			List<Boolean> res1 = new ArrayList<Boolean>();
			List<Boolean> res2 = new ArrayList<Boolean>();
			res1.add(ejercicio1Iter(lista,pS,pI, f));
			System.out.println("\nLa solución iterativa de "+ lista +" es: ");
			System.out.println(res1 + "\n");
			System.out.println("La solución recursiva de "+lista +" es:");
			res2.add(ejercicio1RecurFinal(lista, pS,pI, f));
			System.out.println(res2);
		}
		

	}

}
