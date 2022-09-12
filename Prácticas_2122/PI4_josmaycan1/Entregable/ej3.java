package practica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import us.lsi.common.Files2;
import us.lsi.common.Pair;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BinaryType;


public class ej3 {
	
	/*Diseñe un algoritmo que dado un árbol binario de enteros, determine el camino del
	árbol desde la raíz a una hoja no vacía tal que el producto de sus etiquetas sea máximo.*/
	
	//¿Puede ser un ejercicio iterativo que tiene en cuenta el nivel?
	
	public static Integer ej3(BinaryTree<Integer> bt) {
		Integer res = 1;
		List<Integer> ls = new ArrayList<Integer>();
		BinaryType type = bt.getType();
		switch(type) {
			case Empty: return res; 
			case Leaf:
				if(!bt.isEmpty()) {
				//Una vez que se llega a una hoja no vacía se va iterando los valores guardados hasta que se obtiene el resultado
				for(int i = 0; i<ls.size(); i++) {
					res = res * ls.get(i);
				}
				return res;
			}
				break;
			case Binary:
				BinaryTree<Integer> derecha = bt.getRight();
				Integer vd = ej3(bt.getRight());
				BinaryTree<Integer> izquierda = bt.getLeft();
				Integer vi = ej3(bt.getLeft());
				if(!(derecha.isEmpty() || izquierda.isEmpty())) {
					if(vd > vi) {
						ls.add(derecha.getLabel());
					}else if(vi > vd) {
						ls.add(izquierda.getLabel());
					}
				}
				break;
		}
		return res;
	}
	public static Pair<List<Integer>, Integer> ej3_2(BinaryTree<Integer> bt){
		return ej3aux(bt,new ArrayList<Integer>());
	}
	
	//Sabemos que al estar ordenado los valores de la derecha siempre serán mayores
	public static Pair<List<Integer>, Integer> ej3aux(BinaryTree<Integer> bt,List<Integer> ls){
		//En la lista vamos a encontrarnos los valores más altos del grafo
		Pair<List<Integer>, Integer> res = Pair.of(ls, 1);
		
		switch(bt.getType()) {
		case Empty: return res;
		case Leaf:
			//Vamos a hacer postorder con lo cual en el caso hoja creamos el primer par con el valor que hay del primer label
			if(!(bt.isEmpty())) {
				ls.add(bt.getLabel());
				res = Pair.of(ls, bt.getLabel());
			}
			
			break;
		case Binary:
			//Todavía tenemos que rescatar el par en el caso en el que alguno de los dos valores sea empty
			Pair<List<Integer>, Integer> derecha = null;
			Pair<List<Integer>, Integer> izquierda = null;
			Boolean cond1 = bt.getRight().isEmpty();
			Boolean cond2 = bt.getLeft().isEmpty();
			if(cond1 == false) {
				derecha = ej3_2(bt.getRight());
			}
			if(cond2 == false) {
				izquierda = ej3_2(bt.getLeft());
			}
				
			if(derecha != null || izquierda == null) {
				if(izquierda == null || (derecha.second() > izquierda.second())) {
					List<Integer> aux = new ArrayList<Integer>();
					aux.addAll(derecha.first());
					//aux.add(derecha.second());
					aux.add(bt.getLabel());
					Integer multiplicacion = 1;
					for (int i = 0; i < aux.size(); i++) {
						multiplicacion = multiplicacion*aux.get(i);
					} 
					res = Pair.of(aux, multiplicacion);
				}
			}else if(izquierda != null || derecha == null) {
				if(derecha == null || (izquierda.second() > derecha.second())) {
					List<Integer> aux2 = new ArrayList<Integer>();
					aux2.addAll(izquierda.first());
					//aux2.add(izquierda.second());
					aux2.add(bt.getLabel());
					Integer multiplicacion = 1;
					for (int i = 0; i < aux2.size(); i++) {
						multiplicacion = multiplicacion*aux2.get(i);
					}
					res = Pair.of(aux2, multiplicacion);
				}
					
			} 
			/*if(!(bt.getRight().isEmpty())) {
				ls.add(bt.getRight().getLabel());
			}else if(!(bt.getLeft().isEmpty())){
				ls.add(bt.getLeft().getLabel());
			}*/
			//Pair<List<Integer>, Integer> aux = ej3_2(bt.getRight());
			//res = aux;
		}
		//return Pair.of(ls, mul);
		return res;
		
	}
	
	public static Pair<List<Integer>, Integer> ej3_3(BinaryTree<Integer> bt){
		return ej3aux_2(bt, new ArrayList<Integer>());
	}
	
	public static Pair<List<Integer>, Integer> ej3aux_2(BinaryTree<Integer> bt,List<Integer> ls){
		//En la lista vamos a encontrarnos los valores más altos del grafo
		Pair<List<Integer>, Integer> res = Pair.of(ls, 1);
		
		switch(bt.getType()) {
		case Empty: return res;
		case Leaf:
			//Vamos a hacer postorder con lo cual en el caso hoja creamos el primer par con el valor que hay del primer label
			if(!(bt.isEmpty())) {
				ls.add(bt.getLabel());
				res = Pair.of(ls, bt.getLabel());
			}
			
			break;
		case Binary:
			
			Pair<List<Integer>, Integer> derecha = ej3_3(bt.getRight());
			Pair<List<Integer>, Integer> izquierda = ej3_3(bt.getLeft());
			
			//Si la rama derecha no está vacía y la izquierda sí no tenemos que comprobar los valores ya que siempre es mayor la de la derecha
			if(!(bt.getRight().isEmpty())&& bt.getLeft().isEmpty()) {
				List<Integer> aux = new ArrayList<Integer>();
				//Añadimos a la lista los valores que tenemos ahora mismo
				aux.addAll(derecha.first());
				//Añadimos el de la etiqueta actual
				aux.add(bt.getLabel());
				Integer multiplicacion = 1;
				//El bucle que hace la multiplicacion de los valores que tenemos hasta ahora
				for (int i = 0; i < aux.size(); i++) {
					multiplicacion = multiplicacion*aux.get(i);
				} 
				//Añadimos la lista y la multiplicacion al resultado
				res = Pair.of(aux, multiplicacion);
			
			//Lo mismo que el anterior pero siendo la izquierda una rama no vacía y la derecha sí, no se hacen comprobaciones y se añade la rama izquierda como mayor
			}else if(!(bt.getLeft().isEmpty()) && bt.getRight().isEmpty()) {
					List<Integer> aux2 = new ArrayList<Integer>();
					aux2.addAll(izquierda.first());
					aux2.add(bt.getLabel());
					Integer multiplicacion = 1;
					for (int i = 0; i < aux2.size(); i++) {
						multiplicacion = multiplicacion*aux2.get(i);
					}
					res = Pair.of(aux2, multiplicacion);
			
			//Una vez que tenemos que ninguno de las ramas están vacías hacemos la comprobacion que se nos pide
			}else if(!(bt.getLeft().isEmpty()) && !(bt.getRight().isEmpty())) {
				//La comprobacion es que ambos sean mayores, primero vemos si el de la derecha es mayor que el otro
				if(derecha.second()*bt.getLabel() > izquierda.second()*bt.getLabel()) {
					//Al ser mayor agregamos la rama de la derecha
					List<Integer> aux = new ArrayList<Integer>();
					//El metodo es igual que cuando la rama de la izquierda estaba vacia
					aux.addAll(derecha.first());
					aux.add(bt.getLabel());
					Integer multiplicacion = 1;
					for (int i = 0; i < aux.size(); i++) {
						multiplicacion = multiplicacion*aux.get(i);
					} 
					res = Pair.of(aux, multiplicacion);
				//Lo que hace la condición es igual que la anterior pero teniendo como referencia a añadir la rama de la izquierda
				//Tenemos que hacer la multiplicacion por el label actual porque sino va a haber casos en los que coja un conjunto que da valores negativos como mayor cuando el que tenemos que estar comprobando es si el actual es el mayor
				}else if (izquierda.second()*bt.getLabel() > derecha.second()*bt.getLabel()) {
					List<Integer> aux2 = new ArrayList<Integer>();
					aux2.addAll(izquierda.first());
					aux2.add(bt.getLabel());
					Integer multiplicacion = 1;
					for (int i = 0; i < aux2.size(); i++) {
						multiplicacion = multiplicacion*aux2.get(i);
					}
					res = Pair.of(aux2, multiplicacion);
					
				}
				
			}/*else if(bt.getRight().isEmpty() && bt.getLeft().isEmpty()) {
				List<Integer> aux = new ArrayList<Integer>();
				aux.add(bt.getLabel());
				Integer multiplicacion = 1;
				for (int i = 0; i < aux.size(); i++) {
					multiplicacion = multiplicacion*aux.get(i);
				} 
				res = Pair.of(aux, multiplicacion);
			}*/
			
		}
		
		return res;
		
	}

	public static void main(String[] args) {
		
		List<BinaryTree<Integer>> input2 = Files2.streamFromFile("ficheros/PI3E3_DatosEntrada.txt")
				.map(linea -> BinaryTree.parse(linea, s-> Integer.parseInt(s)))
				//Dado una linea hacemos una transformacion de la linea a binarytree y los valores de string los pasa a integer
				.toList();
				//Pasa todo a la lista
		//System.out.println(input2);
		//input2.stream().forEach(a->System.out.println(ej3_2(a)));
		input2.stream().forEach(a->System.out.println(ej3_3(a)));
	}

}
