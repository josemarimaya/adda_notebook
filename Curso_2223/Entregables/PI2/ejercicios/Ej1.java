package ejercicios;

import java.math.BigInteger;

public class Ej1 {

	/*. Analizar los tiempos de ejecución de las versiones recursiva e iterativa para el cálculo
	del factorial. Comparar según los resultados sean de tipo Double o BigInteger.*/
	
	
	public static Double factorialD(Integer n) {
		Double res = 0.0;
		if(n==1) {
			return 1.0;
		}else {
			res = factorialD(n-1)*n;
		}
		
		return res;
	}
	
	public static Double factorialD_Iter(Integer n) {
		Double res = 0.0;
		while(n<1) {
			res = res * n;
			n--;
		}
		
		return res;
	}
	
	
	public static BigInteger factorialBI(Integer n) {
		BigInteger res = null;
		if(n==1) {
			return BigInteger.ONE;
		}else {
			BigInteger nBI = BigInteger.valueOf(n.longValue());
			res = factorialBI(n-1).multiply(nBI);
		}
		
		return res;
	}
	
	public static BigInteger factorialBI_Iter(Integer n) {
		BigInteger res = null;
		while(n<1) {
			BigInteger nBI = BigInteger.valueOf(n.longValue());
			res = res.multiply(nBI);
			n--;
		}
		
		return res;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(factorialD(3));
		
		System.out.println(factorialBI(3));

	}

}
