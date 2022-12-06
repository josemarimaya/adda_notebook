package Ejercicios;

public record Trayectoria(String origen, String destino, 
		Double dinero, Double tiempo) {
	
	public static Trayectoria ofFormat(String v[]) {
		String[] d = v[2].split("e");
		Double dinero = Double.parseDouble(d[0]);
		String[] t = v[3].split("m");
		Double tiempo = Double.parseDouble(t[0]);
		return new Trayectoria(v[0], v[1], dinero, tiempo);
	}
	public String toString() {
		String nn = dinero + " euros\n" + tiempo + " minutos";
		return nn;
	}
}
