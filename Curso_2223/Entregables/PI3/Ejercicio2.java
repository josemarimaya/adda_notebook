package Ejercicios;

public class Ejercicio2 {
	
	/*Un grupo de amigos desea visitar una serie de ciudades haciendo uso de un tipo de
	transporte que sólo relaciona algunas de ellas. Se tiene un grafo no dirigido y ponderado
	cuyos vértices son dichas ciudades y cuyas aristas representan los posibles trayectos entre
	ellas. De cada Ciudad se conoce su puntuación (valor entero en [1,5]), basada en el interés
	que tienen dicho grupo de personas en visitarla, y de cada Trayecto se conoce su precio
	y duración*/
	
	private static record CiudadPuntua(String ciudad, Integer puntos) {
		public static CiudadPuntua ofFormat(String[] v) {
			String[] valueS = v[1].split("p");
			Integer value = Integer.parseInt(valueS[0]);
			return new CiudadPuntua(v[0], value);
		}
	}
	
	private static record Trayectoria(String origen, String destino, 
			Integer dinero, Integer tiempo) {
		public static Trayectoria ofFormat(String v[]) {
			String[] d = v[2].split("e");
			Integer dinero = Integer.parseInt(d[0]);
			String[] t = v[3].split("m");
			Integer tiempo = Integer.parseInt(t[0]);
			return new Trayectoria(v[0], v[1], dinero, tiempo);
		}
	}

}
