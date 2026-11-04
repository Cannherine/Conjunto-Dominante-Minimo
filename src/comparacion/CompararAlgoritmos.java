package comparacion;

import java.util.Set;
import java.util.function.Function;

import algoritmo.DominanteBacktracking;
import algoritmo.DominanteGenetico;
import algoritmo.DominanteGoloso;
import negocio.Grafo;
import negocio.Vertice;

public class CompararAlgoritmos {
	private static final int repeticiones = 10;

	public ResultadoAlgoritmo medirBacktracking(Grafo grafo) {

		DominanteBacktracking algoritmo = new DominanteBacktracking();
		return medirTiempo(grafo, algoritmo::resolver);
	}

	public ResultadoAlgoritmo medirGoloso(Grafo grafo) {

		DominanteGoloso algoritmo = new DominanteGoloso();
		return medirTiempo(grafo, algoritmo::resolver);
	}

	public ResultadoAlgoritmo medirGenetico(Grafo grafo) {

		DominanteGenetico algoritmo = new DominanteGenetico();
		return medirTiempo(grafo, algoritmo::resolver);
	}

	public long medirPromedioGenetico(Grafo grafo) {

		DominanteGenetico algoritmo = new DominanteGenetico();
		return calcularPromedio(grafo, algoritmo::resolver, repeticiones);
	}

	public long medirPromedioBacktracking(Grafo grafo) {

		DominanteBacktracking algoritmo = new DominanteBacktracking();
		return calcularPromedio(grafo, algoritmo::resolver, repeticiones);
	}

	public long medirPromedioGoloso(Grafo grafo) {
		DominanteGoloso algoritmo = new DominanteGoloso();
		return calcularPromedio(grafo, algoritmo::resolver, repeticiones);
	}

	public long calcularPromedio(Grafo grafo, Function<Grafo, Set<Vertice>> resolver, int repeticiones) {

		validarArgumentos(grafo, resolver);

		if (repeticiones <= 0) {
			throw new IllegalArgumentException("Las repeticiones deben ser positivas");
		}

		for (int i = 0; i < 5; i++) {
			resolver.apply(grafo);
		}

		long tiempoTotal = 0;

		for (int i = 0; i < repeticiones; i++) {

			ResultadoAlgoritmo resultado = medirTiempo(grafo, resolver);

			tiempoTotal += resultado.getTiempoNanosegundos();
		}

		return tiempoTotal / repeticiones;
	}

	private ResultadoAlgoritmo medirTiempo(Grafo grafo, Function<Grafo, Set<Vertice>> resolver) {

		validarArgumentos(grafo, resolver);

		long inicio = System.nanoTime();
		Set<Vertice> solucion = resolver.apply(grafo);
		long fin = System.nanoTime();

		return new ResultadoAlgoritmo(solucion, fin - inicio);
	}

	private void validarArgumentos(Grafo grafo, Function<Grafo, Set<Vertice>> resolver) {

		if (grafo == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo");
		}

		if (resolver == null) {
			throw new IllegalArgumentException("El algoritmo no puede ser nulo");
		}
	}

}
