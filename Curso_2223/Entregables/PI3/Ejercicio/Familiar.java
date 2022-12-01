package Ejercicios;


public record Familiar(Integer id, String nombre, String ciudad, Integer anyo) {
	
	public static Familiar ofFormat(String[] formato) {
		Integer id = Integer.parseInt(formato[0]);
		String nombre = formato[1];
		Integer anyo = Integer.parseInt(formato[2]);
		String ciudad = formato[3];
		return new Familiar(id, nombre,ciudad, anyo);
	}
	
	public static Familiar of(Integer id, String nombre, String ciudad, Integer anyo) {
		return new Familiar(id, nombre,ciudad, anyo);
	}
	
	@Override
	public String toString() {
		return this.nombre;
	}

}
