package practica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.common.Files2;
import us.lsi.common.Set2;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.Tree;
import us.lsi.tiposrecursivos.Tree.TreeType;

public class ej4 {
	
	/*Diseñe un algoritmo que dado un árbol n-ario de caracteres devuelva un conjunto de
	cadenas de caracteres que contenga todas las cadenas palíndromas que se formen desde
	la raíz a una hoja no vacía.*/
	
	/*Recordamos la definicion de palindromo que es una palabra que se escribe igual
	 * de adelante hacia atras, con lo cual el primer valor 0 es igual al valor n
	 * 1 al de n-1 y así sucesivamente*/
	
	//Creación de palíndromo: https://stackoverflow.com/questions/28001448/converting-a-string-into-a-palindrome-in-java/28001528
	
	
	/*public static Set<String> ej4 (Tree<Character> tree){
		Set<String> res = Set2.empty();
		TreeType type = tree.getType();
		switch(type) {
		case Empty: return res;
		case Leaf: 
		//Condicionante de que los dos primeros valores sean iguales y no vacíos
		if(tree.getFather().getLabel().equals(tree.getLabel()) && !(tree.isEmpty() && tree.getFather().isEmpty())) {
			//https://stackoverflow.com/questions/16282368/concatenate-chars-to-form-string-in-java
			//Concatenar characters para hacer un string
			String pal = "";
			//Creamos la cadena que es la concatenacion de dos caracteres
			StringBuilder palin = new StringBuilder();
			String palinf = palin.append(tree.getLabel()+tree.getLabel()).toString();
			//Al String que ya tenemos le añado la cadena de caracteres que hay
			pal = pal.charAt(0) + palinf + pal.charAt(pal.length()-1);
			res.add(palinf);
			//Tenemos que añadir cada caracter al string que se crea en el set
		}
		break;
		case Nary:
			//Iteramos segun el numero de hijo que tiene el arbol
		
			Integer i = 0;
			Tree<Character> hijo = tree.getChild(i);
			if(i<tree.getChildren().size()) {
				if(hijo.getFather().getLabel().equals(hijo.getLabel()) && !(hijo.isEmpty() && hijo.getFather().isEmpty())) {
					//¿La forma de crear el palíndromo es como en el Leaf?
					res.add(hijo.getLabel());
					res = ej4(tree.getChild(i));
					//Al cumplir el condicionante va haciendo la llamada recursiva hasta que no quedan más hijos
				}
			i++;
			}
			tree.getChildren().forEach(subarbol-> ej4(tree));
		}
		return res;
	}*/
	public static Set<String> ej4(Tree<Character> tree){
		return ej4aux(tree, "", new HashSet<String>());
	}
	
	public static Set<String> ej4aux (Tree<Character> tree, String acumulador, Set<String> res){
		//List<String> ls = new ArrayList<String>();
		switch(tree.getType()) {
		case Empty: return res;
		case Leaf:
			//Tenemos que analizar la lista de string y cada string para saber si es palindromo
				//Si el string es pakindromo lo añadimos
			acumulador = acumulador + tree.getLabel();
			//Usamos la funcion que hemos creado para comprobar si lo que tenemos ahora mismo en el String es un palindromo o no
			if(checkPalindrome(acumulador)) {
				//Añadimos el string si es un palíndromo
				res.add(acumulador);
			}
			break;
		case Nary:
			//Recorremos la los hijos del arbol que son caracteres
			for(int i=0; i<tree.getChildren().size(); i++) {
				//Y vamos metiendo los caracteres en el String que los acumula
				ej4aux(tree.getChild(i), acumulador+tree.getLabel(), res);
			}
		}
		return res;
	}
	
	//Funcion para comprobar si ahora mismo tenemos un palíndromo
	public static boolean checkPalindrome(String s){

        for (int i = 0; i < s.length()/2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - i - 1)) return false;
        }

        return true;

    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Tree<String>> input2 = Files2.streamFromFile("ficheros/Ejemplo4_DatosEntrada.txt")
				.map(linea -> Tree.parse(linea, s-> s.toString()))
				//Dado una linea hacemos una transformacion de la linea a binarytree y los valores de string los pasa a integer
				.toList();
		List<Tree<Character>> input3 = Files2.streamFromFile("ficheros/PI3E4_DatosEntrada.txt")
				.map(linea->{
					//Mirar ej2 de lab
					Tree<Character> arbol = Tree.parse(linea, s->s.charAt(0));
					return arbol;
				}).toList();
		//System.out.println(input3);
		input3.stream().forEach(a->{
			System.out.println(ej4(a));
		});
		
	}

}
