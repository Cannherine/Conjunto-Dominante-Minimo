package comparacion;

import java.util.HashSet;
import java.util.Set;

import negocio.Vertice;

public class ResultadoAlgoritmo {
	private final Set<Vertice> solucion;
	private final long tiempoNanosegundos;

	public ResultadoAlgoritmo(Set<Vertice> solucion, long tiempoNanosegundos) {

		if (solucion == null || tiempoNanosegundos < 0) {
			throw new IllegalArgumentException("Resultado de ejecucion invalido");
		}

		this.solucion = new HashSet<>(solucion);
		this.tiempoNanosegundos = tiempoNanosegundos;
	}

	public Set<Vertice> getSolucion() {
		return new HashSet<>(solucion);
	}

	public int getTamaño() {
		return solucion.size();
	}

	public long getTiempoNanosegundos() {
		return tiempoNanosegundos;
	}
}
