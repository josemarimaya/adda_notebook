package practica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BinaryType;

public class ej5 {
	
	/*Diseñe un algoritmo que dado un árbol binario de enteros devuelva un
	Map<Paridad,List<Integer>> que incluya las etiquetas de los nodos que tengan 2 hijos
	no vacíos, y que cumplan que dicha etiqueta sea mayor que la etiqueta de su hijo izquierdo
	y menor que la de su hijo derecho, agrupados teniendo en cuenta si son pares o no. Paridad
	es un enumerado con los valores Par e Impar.*/
	
	public static Map<Paridad, List<Integer>> ej5 (BinaryTree<Integer> tree, Map<Paridad, List<Integer>> m){
		//List<Integer> ls = new ArrayList<Integer>();
		Map<Paridad, List<Integer>> res = new HashMap<Paridad, List<Integer>>();
		BinaryType type = tree.getType();
		switch(type) {
		//Devolvemos m en el caso empty porque es el map que estamos actualizando y que tiene valores
		case Empty: return m;
		case Leaf: 
			if(!tree.isEmpty()) {
				if(tree.getLabel()%2==0) {
				//Si es para el resto de la division es 0
				List<Integer> lsPar = m.get(Paridad.PAR); 
				lsPar.add(tree.getLabel());
				//Con el put mirar el entregable con el uso que le dimos al map para actualizar valores
				m.put(Paridad.PAR, lsPar);
				}else if(tree.getLabel()%2==1) {
				//Si el resto de la division entre 2 no es 0 es porque es impar	
				//Cogemos el valor del map get que había ya con la clave IMPAR
				List<Integer> lsImpar = m.get(Paridad.IMPAR);
				//Rellenamos la lista de impares con el valor
				lsImpar.add(tree.getLabel());
				//Actua
				m.put(Paridad.IMPAR, lsImpar);
				}
				//Al final del programa lo que hacemos es meter los valores que hemos acumulado en el map de entrada en res
				res = m;
			}
			break;
		case Binary:
			//Implementar la llamada recursiva del metodo para que no sea solo el caso de la hoja
			//Hacemos las comprobaciones pertinentes para que el arbol no sea empty
			if(!(tree.getRight().isEmpty() || tree.getLeft().isEmpty())) {
				//Seguimos comprobando con los if los valores de padre junto al de los hijos izquierdo y derecho que no haciamos antes porque era tipo hoja
				if(tree.getLabel()> tree.getLeft().getLabel() && tree.getLabel() < tree.getRight().getLabel()) {
					//Las mismas comprobaciones que en el leaf
					if(tree.getLabel()%2 == 0) {
						List<Integer> lsPar = m.get(Paridad.PAR);
						lsPar.add(tree.getLabel());
						m.put(Paridad.PAR, lsPar);
					}else if(tree.getLabel()%2 == 1) {
						List<Integer> lsImpar = m.get(Paridad.IMPAR);
						lsImpar.add(tree.getLabel());
						m.put(Paridad.IMPAR, lsImpar);
					}
				}
			}
			//Primero recogemos la rama izquierda y eso hacemos la llamada con el en m	
			Map<Paridad, List<Integer>> en = ej5(tree.getLeft(), m);
			//Después de recorrer la rama izquierda y de guardar sus valores en la llamda recursiva, recorremos la rama de la izquierda
			Map<Paridad, List<Integer>> en2 = ej5(tree.getRight(), en);
			//Finalmente tras recorrer ambas ramas y guardarlo todo en en2 le hacemos la equiparacion en res, que devolveremos luego
			res = en2;
		}
		return res;
		
	}
	
	public static Map<Paridad, List<Integer>> ej5_iter (BinaryTree<Integer> tree, Map<Paridad, List<Integer>> m){
		Iterator<BinaryTree<Integer>> bti = tree.iterator();
		while(bti.hasNext()) {
			BinaryTree<Integer> bt = bti.next();
			switch(bt.getType()) {
			case Empty:
				break;
			case Leaf:
				if(bt.getLabel()%2 == 0) {
					List<Integer> lsPar = m.get(Paridad.PAR);
					lsPar.add(bt.getLabel());
					m.put(Paridad.PAR, lsPar);
				}else if(bt.getLabel()%2 == 1) {
					List<Integer> lsImpar = m.get(Paridad.IMPAR);
					lsImpar.add(bt.getLabel());
					m.put(Paridad.IMPAR, lsImpar);
				}
				break;
			case Binary:
				Boolean cond1 = bt.getRight().isEmpty();
				Boolean cond2 = bt.getLeft().isEmpty();
				Boolean pasa = cond1 == false && cond2 == false;
				if((pasa)) {
					if(bt.getLabel()> bt.getLeft().getLabel() && bt.getLabel() < bt.getRight().getLabel()) {
						//Las mismas comprobaciones que en el leaf
						if(bt.getLabel()%2 == 0) {
							List<Integer> lsPar = m.get(Paridad.PAR);
							lsPar.add(bt.getLabel());
							m.put(Paridad.PAR, lsPar);
						}else if(bt.getLabel()%2 == 1) {
							List<Integer> lsImpar = m.get(Paridad.IMPAR);
							lsImpar.add(bt.getLabel());
							m.put(Paridad.IMPAR, lsImpar);
						
						}
						
					}
				}
				
				
			}
			
		}
		return m;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<BinaryTree<Integer>> input2 = Files2.streamFromFile("ficheros/PI3E5_DatosEntrada.txt")
				.map(linea -> BinaryTree.parse(linea, s-> Integer.parseInt(s)))
				.toList();
		input2.stream().forEach(a->{
			//Creamos las variables de las listas en recorrido funcional
			Map<Paridad, List<Integer>> res = new HashMap<Paridad, List<Integer>>();
			List<Integer> lsPar = List2.empty();
			List<Integer> lsImpar = List2.empty();
			res.put(Paridad.IMPAR, lsImpar);
			res.put(Paridad.PAR, lsPar);
			System.out.println("Solucion recursiva: " + ej5(a, res));
			List<Integer> lsPar2 = List2.empty();
			List<Integer> lsImpar2 = List2.empty();
			Map<Paridad, List<Integer>> res2 = new HashMap<Paridad, List<Integer>>();
			res2.put(Paridad.IMPAR, lsImpar2);
			res2.put(Paridad.PAR, lsPar2);
			System.out.println("Solucion iterativa: " + ej5_iter(a, res2));
			});

	}

}
