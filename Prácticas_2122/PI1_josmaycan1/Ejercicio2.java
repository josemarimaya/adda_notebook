package ejercicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Ejercicio2 {
	
	public static Map<Integer,List<String>> ejercicio2 (List<List<String>> listas) {
		return listas.stream()
		.flatMap(lista -> lista.stream())
		.collect(Collectors.groupingBy(String::length));
		//.collect(Collectors.groupingBy(String::length));
		}
	
	public static Map<Integer, List<String>> ejercicio2Iter (List<String> listas){
		
		Map<Integer, List<String>> res = new HashMap<>();
		Integer i = 0;
		
		while(i<listas.size()) {
			
			List<String> l = new ArrayList<String>();
			String a = listas.get(i);
			Integer tam = a.length();
			l.add(a);
			/*//¿Como actualizo la lista de Strings dentro del map?
			if(res.containsKey(tam)) {
				//Esto funciona pero unicamente con los Strings
				res.put(tam, res.get(tam)+l);
			}else {
				res.put(tam, a);
				
			}*/
			
			if(res.containsKey(tam)) {
				res.get(tam).add(a);
			}else {
				res.put(tam, l);
				
			}
			
			i++;
		}
		return res;
	}
	
	
	public static Map<Integer, List<String>> ejercicio2RecurF(List<String> listas){
		Map<Integer, List<String>> res = new HashMap<Integer, List<String>>();
		List<String> ls = new ArrayList<String>();
		String a = listas.get(0);
		Integer tam = a.length();
		return ejercicio2RecurFAux(listas, 0, a , tam,ls, res);
	}
	
	public static Map<Integer, List<String>> ejercicio2RecurFAux
	(List<String> listas, Integer i, String a, Integer tam, List<String> ls, Map<Integer, List<String>> res){
		
		if(i<listas.size()) {
			if(res.containsKey(tam)) {
				res.get(tam).add(a);
				res = ejercicio2RecurFAux(listas, i+1, listas.get(i), tam,ls, res);
				
			}else {
				res.put(tam, ls);
				res = ejercicio2RecurFAux(listas, i+1, listas.get(i), tam ,ls, res);
			}
		}/* else {
			return ejercicio2RecurFAux(listas, i+1, a, tam, ls, res);
		}*/
		
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
		// TODO Auto-generated method stub
		
		/*System.out.println(lecturaFichero("tests/PI1E2_DatosEntrada1.txt"));
		System.out.println(lecturaFichero("tests/PI1E2_DatosEntrada2.txt"));
		
		List<List<String>> ls2 =lecturaFichero("tests/PI1E2_DatosEntrada2.txt");
		
		System.out.println(ej2RecursivoFinal(ls2));*/
		
		
		List<String> file1 = Files2.linesFromFile("tests/PI1E2_DatosEntrada1.txt");
		List<String> file2 = Files2.linesFromFile("tests/PI1E2_DatosEntrada2.txt");
		
		List<List<String>> fileComplete = new ArrayList<List<String>>();
		
		fileComplete.add(file1);
		fileComplete.add(file2);
		
		List<String> ls1 = new ArrayList<String>();
		List<String> ls2 = new ArrayList<String>();
		for(String linea: file1) {
			
			String[] line = linea.split(",");
			
			for(String chain: line) {
				
				ls1.add(chain);
			}
		}
		
		for(String linea: file2) {
			
			String[] line = linea.split(",");
			
			for(String chain: line) {
				
				ls2.add(chain);
			}
		}
		
		List<List<String>> ls1f =lecturaFichero("tests/PI1E2_DatosEntrada1.txt");
		List<List<String>> ls2f =lecturaFichero("tests/PI1E2_DatosEntrada2.txt");
		
		System.out.println("Datos de entrada 1 \nIterativo:");
		System.out.println(ejercicio2Iter(ls1));
		System.out.println("Recursivo: ");
		System.out.println(ejercicio2RecurF(ls1));
		System.out.println("Funcional:");
		System.out.println(ejercicio2(ls1f));
		System.out.println("###################");
		System.out.println("Datos de entrada 2 \nIterativo:");
		System.out.println(ejercicio2Iter(ls2));
		System.out.println("Recursivo: ");
		System.out.println(ejercicio2RecurF(ls2));
		System.out.println("Funcional:");
		System.out.println(ejercicio2(ls2f));
		

	}

}
