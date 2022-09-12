package practica;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;
import us.lsi.common.IntegerSet;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BinaryType;

public class ej2 {
	
	/*Dado un árbol binario ordenado de enteros, diseñe un algoritmo que devuelva un
	conjunto con todos los elementos mayores o iguales que uno dado.*/
	
	//Se puede usar de referencia el ejercicio de la pagina 20
	public static Set<Integer> ej2(BinaryTree<Integer> bt, Integer valor){
		return ej2aux(bt, valor, new HashSet<>());
	}
	public static Set<Integer> ej2aux (BinaryTree<Integer> bt , Integer valor, Set<Integer> res) {
		BinaryType type = bt.getType();
		switch(type) {
			case Empty: return res;
			case Leaf: 
			//Tenemos que hacer la comprobacion del filtro también en el nodo hoja ya que puede tener valor
			if(!(bt.isEmpty())) {
				if(bt.getLabel()>=valor) {
					res.add(bt.getLabel());
				}
			}
			break;
			case Binary:
				//El nodo en el que estamos ahora mismo tiene que pasar el filtro para ver si su etiqueta la metemos o no
				if(!(bt.isEmpty())) {
					if(bt.getLabel()>=valor) {
						res.add(bt.getLabel());
					}
				}
				//Llamadas recursivas una vez que hemos terminado de hacer las comprobaciones
				//Hacemos el recorrido de ambos arboles guardando el valor que pasa por el filtro en res
				ej2aux(bt.getRight(), valor, res);
				ej2aux(bt.getLeft(), valor, res);
				
		}
		return res;
	}
	
	/*public static Set<Integer> ej2_r2(BinaryTree<Integer> bt, Integer valor, Set<Integer> set){
		Set<Integer> res = Set.of();
		switch(bt.getType()) {
		case Empty: return set;
		case Leaf:
			if(!(bt.isEmpty())) {
				if(bt.getLabel()>=valor) {
					set.add(bt.getLabel());
				}
			}
		case Binary:
			BinaryTree<Integer> btr = bt.getRight();
			//Set<Integer> derecha = ej2_r2(btr, valor, set);
			BinaryTree<Integer> btl = bt.getLeft();
			//Set<Integer> izquierda = ej2_r2(btl, valor, set);
			if(!(btr.isEmpty() || btl.isEmpty())) {
				if(btr.getLabel()> valor && btl.getLabel()>valor) {
					if(btr.getLabel()>btl.getLabel()) {
						set.add(btr.getLabel());
					}else {
						set.add(btl.getLabel());
					}
				}else if(btl.getLabel()>valor && btr.getLabel()<valor){
					set.add(btl.getLabel());
				}else if(btr.getLabel()>valor && btl.getLabel()<valor) {
					set.add(btr.getLabel());
				}
				
			}
			//Set<Integer> s1 = ej2_r2(btl, valor, set);
			Set<Integer> s2 = ej2_r2(btr, valor, set);
			res = s2;
		}
		return res;
	}*/
	
	/*public static Set<Integer> ej2_iter(BinaryTree<Integer> bt , Integer valor) {
		Set<Integer> res = Set.of();
		Iterator<BinaryTree<Integer>> btn = bt.iterator();
		while(btn.hasNext()) {
			BinaryTree<Integer> subarbol = btn.next();
			//Condicionante para que sea binario la parte analizada
			if(!(subarbol.isLeaf() || subarbol.isEmpty())) {
				BinaryTree<Integer> sub_izq = subarbol.getLeft();
				BinaryTree<Integer> sub_der = subarbol.getRight();
				//Condicionante para no coger un nodo vacío
				if(!(sub_izq.isEmpty() || sub_der.isEmpty())) {
					if(sub_izq.getLabel()> valor) {
						res.add(sub_izq.getLabel());
					}else if(sub_der.getLabel() > valor) {
						res.add(sub_der.getLabel());
					}else if(sub_izq.getLabel()> valor && sub_der.getLabel() > valor) {
						res.add(sub_izq.getLabel());
						res.add(sub_der.getLabel());
					}
				}
			}
		}
		return res;
	}*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Pair<BinaryTree<Integer>, Integer>> datos = List2.empty();
		datos = Files2.streamFromFile("ficheros/PI3E2_DatosEntrada.txt")
				.map(linea -> {
					//Hago el spliteo por los dos valores con el corchete
				String[] elementos = linea.split("#");
				//Cojo como parametro de entrada lo primero que hay de valor
				BinaryTree<Integer> arbol = BinaryTree.parse(elementos[0], s-> Integer.parseInt(s));
				//A los valores del txt les quito los corchetes con el replace a nada
				Integer objetivo = Integer.parseInt(elementos[1]);
				//Hay que poner return porque sino el funcional no te devuelve el valor
				return Pair.of(arbol, objetivo);
				}).toList();
		//System.out.println(datos);
	
		for(int i=0; i<datos.size(); i++) {
			Pair<BinaryTree<Integer>, Integer> par = datos.get(i);
			System.out.println(ej2(par.first(), par.second()));
		}
	}

}
