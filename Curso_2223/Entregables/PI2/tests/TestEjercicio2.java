package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import ejercicios.Ej2;
import tests.TestEjemplo3.TResultMedD;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.common.Trio;
import us.lsi.curvefitting.DataCurveFitting;
import utils.GraficosAjuste;
import utils.Resultados;
import utils.TipoAjuste;

public class TestEjercicio2 {
	
	/*public static void main(String[] args) {
		generaFicherosTiempoEjecucionImpresion();
		muestraGraficas();
	}*/
	

	private static Integer numMediciones = 2; // número de mediciones de tiempo de cada caso (número de experimentos)
	private static Integer numIter = 5; // número de iteraciones para cada medición de tiempo
	private static Integer numIterWarmup = 50; // número de iteraciones para warmup

	private static Integer numListas = 30; // Número de listas
	private static Integer sizeMin = 50; // Tamaño mínimo de listas
	private static Integer sizeMax = 50000; // Tamaño máximo de listas
	private static Integer umbral = 4; // Umbral fijo para análisis de complejidad
	
	private static Integer numUmbrales = 30; // número de umbrales para análisis de distintos umbrales
	private static Integer nMinUmbral = 1; // n mínimo umbral
	private static Integer nMaxUmbral = 100; // n máximo umbral
	
	record Problema(Integer tam, Integer numList, Integer numCase) {
		public static Problema of(Integer tam, Integer numList, Integer numCase){
			return new Problema(tam, numList, numCase);
		}
	
		 private static void actualizaTiempos(Map<Problema, Double> tiempos, Problema p, double d) {
				if (!tiempos.containsKey(p)) {
					tiempos.put(p, d);
				} else if (tiempos.get(p) > d) {
						tiempos.put(p, d);
				}
			}
	
	private static Random rr = new Random(System.nanoTime()); // para inicializarlo una sola vez y compartirlo con los m�todos que lo requieran

	private static String ficheroListaEntrada = "ficheros/ListasAlgSort.txt";

	private static List<Trio<BiConsumer<List<Integer>, Integer>, TipoAjuste, String>> metodosComplejidad = List.of(
			Trio.of(Ej2::sort, TipoAjuste.NLOGN_0, "Quicksort(complejidad)") // Análisis de complejidad del algoritmo de ordenación con listas de distintos tamaños y umbral fijo
			); 
	
	private static List<Trio<BiConsumer<List<Integer>, Integer>, TipoAjuste, String>> metodosUmbral = List.of(
			Trio.of(Ej2::sort, TipoAjuste.NLOGN_0, "Quicksort(analisisumbral)") // Análisis del tamaño umbral con una única lista de tamaño sizeMax
			); 
	
	public static void generaFicherosTiempoEjecucion(List<Trio<BiConsumer<List<Integer>, Integer>, TipoAjuste, String>> metodos) {
		for (int i=0; i<metodos.size(); i++) { 

			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",
					metodos.get(i).third());
			System.out.println(ficheroSalida);
			if(!ficheroSalida.contains("umbral")) {
				testTiemposEjecucionComplejidad(
						metodos.get(i).first(),
							ficheroSalida
							);
			}
			/*else {
				testTiemposEjecucionUmbral(
						metodos.get(i).first(),
							ficheroSalida
							);
			}*/

		}
	}
	
	public static void generaFicherosTiempoEjecucionImpresion() {
		generaFicherosTiempoEjecucion(metodosComplejidad);
		generaFicherosTiempoEjecucion(metodosUmbral);
	}
	
	public static <E> void muestraGraficasMetodos(List<Trio<BiConsumer<List<Integer>, Integer>, TipoAjuste, String>> metodosComplejidad2, List<String> ficherosSalida, List<String> labels) {
		for (int i=0; i<metodosComplejidad2.size(); i++) { 
			
			String ficheroSalida = String.format("ficheros/Tiempos%s.csv",
					metodosComplejidad2.get(i).third());
			ficherosSalida.add(ficheroSalida);
			String label = metodosComplejidad2.get(i).third();
			System.out.println(label);

			TipoAjuste tipoAjuste = metodosComplejidad2.get(i).second();
			GraficosAjuste.show(ficheroSalida, tipoAjuste, label);	
			
			// Obtener ajusteString para mostrarlo en gráfica combinada
			Pair<Function<Double, Double>, String> parCurve = GraficosAjuste.fitCurve(
					DataCurveFitting.points(ficheroSalida), tipoAjuste);
			String ajusteString = parCurve.second();
			labels.add(String.format("%s     %s", label, ajusteString));
		}
	}
	
	public static void muestraGraficas() {
		List<String> ficherosSalida = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		
		muestraGraficasMetodos(metodosComplejidad, ficherosSalida, labels);
		muestraGraficasMetodos(metodosUmbral, ficherosSalida, labels);
		
		GraficosAjuste.showCombined("Cálculo del factorial", ficherosSalida, labels);
	}

	
	public static void generaFicheroListasEnteros(String fichero) {
		Resultados.cleanFile(fichero);
		for (int i=0; i<numListas; i++) {
			int div = numListas<2? 1:(numListas-1);
			int tam = sizeMin + i*(sizeMax-sizeMin)/div;
				List<Integer> ls = generaListaEnteros(tam);
				String sls = ls.stream().map(x->x.toString()).collect(Collectors.joining(",")); 
				Resultados.toFile(String.format("%d#%s",tam,sls), fichero, false);
		}
	}
  
  
  public static List<Integer> generaListaEnteros(Integer sizeList) {
		List <Integer> ls = new ArrayList<Integer>();
		for (int i=0;i<sizeList;i++) {
			ls.add(0+rr.nextInt(1000000-0));
		}
		return ls;
	}
	
	
  public static void testTiemposEjecucionComplejidad(
			BiConsumer<List<Integer>, Integer> funcion,
			String ficheroTiempos
			) {
		
		Map<Problema, Double> tiempos = new HashMap<Problema,Double>();
		Map<Integer, Double> tiemposMedios; // tiempos medios por cada tamaño

		List<String> lineasListas = Files2.linesFromFile(ficheroListaEntrada);
		
		Integer nMed = numMediciones; 
		for (int iter=0; iter<nMed; iter++) {
			for (int i=0; i<lineasListas.size(); i++) { 
				System.out.println(iter + " " + i);
				String lineaLista = lineasListas.get(i);
				List<String> ls = List2.parse(lineaLista, "#", Function.identity());
				Integer tamLista = Integer.parseInt(ls.get(0)); 
				List<Integer> le = List2.parse(ls.get(1), ",", Integer::parseInt);
				
					Problema p = Problema.of(tamLista,i,tamLista);
					// Lo cogemos del ejercicio 1 del ejemplo1
					warmup(funcion, le, 4);
					
					Integer nIter = numIter;
					Long t0 = System.nanoTime();
					for (int z=0; z<nIter; z++) {
						funcion.accept(le, umbral);
					}
					Long t1 = System.nanoTime();
					actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter);
				}
			
		}
		
		
		tiemposMedios = tiempos.entrySet().stream()
				.collect(Collectors.groupingBy(x->x.getKey().tam(),
							Collectors.averagingDouble(x->x.getValue())
							)
						);
		
		Resultados.toFile(tiemposMedios.entrySet().stream()
				.map(x->TResultMedD.of(x.getKey(),x.getValue()))
				.map(TResultMedD::toString),
			ficheroTiempos,
			true);
		
	}
	

public static void testTiemposEjecucionUmbral(
			BiConsumer<List<Integer>, Integer> funcion,
			String ficheroTiempos
			) {
		
		Map<Problema, Double> tiempos = new HashMap<Problema,Double>();
		Map<Integer, Double> tiemposMedios; // tiempos medios por cada tamaño

		List<String> lineasListas = Files2.linesFromFile(ficheroListaEntrada);
		
		Integer nMed = numMediciones; 
		for (int iter=0; iter<nMed; iter++) {
				String lineaLista = lineasListas.get(lineasListas.size()-1);
				List<String> ls = List2.parse(lineaLista, "#", Function.identity());
				Integer tamLista = Integer.parseInt(ls.get(0)); 
				List<Integer> le = List2.parse(ls.get(1), ",", Integer::parseInt);
				for (int j=0; j<numUmbrales; j++) {
					System.out.println(j);
					Double r = Double.valueOf(nMaxUmbral-nMinUmbral)/(numUmbrales-1);
					Integer tam = (Integer.MAX_VALUE/nMaxUmbral > j) 
							? nMinUmbral + j*(nMaxUmbral-nMinUmbral)/(numUmbrales-1)
							: nMinUmbral + (int) (r*j) ;
					
					Problema p = Problema.of(tam,0,tamLista);
					warmup(funcion, le, 4);
					
					Integer nIter = numIter;
					Long t0 = System.nanoTime();
					for (int z=0; z<nIter; z++) {
						funcion.accept(le, tam);
					}
					Long t1 = System.nanoTime();
					actualizaTiempos(tiempos, p, Double.valueOf(t1-t0)/nIter);
				
			}
			
		}
		
		
		tiemposMedios = tiempos.entrySet().stream()
				.collect(Collectors.groupingBy(x->x.getKey().tam(),
							Collectors.averagingDouble(x->x.getValue())
							)
						);
		
//		Resultados.toFile(tiempos.entrySet().stream()
//		.map(x->TResultD.of(x.getKey().tam(), 
//							x.getKey().numList(), 
//							x.getKey().numCase(), 
//							x.getValue()))
//		.map(TResultD::toString),
//		ficheroTiempos,
//		true);
		
		Resultados.toFile(tiemposMedios.entrySet().stream()
				.map(x->TResultMedD.of(x.getKey(),x.getValue()))
				.map(TResultMedD::toString),
			ficheroTiempos,
			true);
		
	}
	
	}
	
	private static void warmup(BiConsumer<List<Integer>,Integer> f, List<Integer> ls, Integer umbral) {
		for (int i=0; i<numIterWarmup; i++) {
			f.accept(ls,umbral);
		}
	}    
	
	
}
