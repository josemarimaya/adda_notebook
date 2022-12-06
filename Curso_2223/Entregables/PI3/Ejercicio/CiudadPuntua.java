package Ejercicios;

public record CiudadPuntua(String ciudad, Integer puntos) {
	public static CiudadPuntua ofFormat(String[] v) {
		String[] valueS = v[1].split("p");
		Integer value = Integer.parseInt(valueS[0]);
		return new CiudadPuntua(v[0], value);
	}
	
	public Integer getPuntos() {
		return puntos;
	}
	
	public String toString() {
		String nn = ciudad + "\n" + puntos + "puntos";
		return nn;
	}
}
