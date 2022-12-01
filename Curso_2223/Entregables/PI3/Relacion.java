package Ejercicios;

public record Relacion(Integer id_origen, Integer id_destino) {
	
	public static Relacion ofFormat(String[] formato) {
		
		Integer origen = Integer.parseInt(formato[0]);
		Integer destino = Integer.parseInt(formato[1]);
		return new Relacion(origen, destino);
	}

}
