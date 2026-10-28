package algoritmo;

import java.util.Set;

import negocio.Grafo;
import negocio.Vertice;

public class ConjuntoDominante {
	private ConjuntoDominante() {
	}

	public static boolean esDominante(Grafo grafo, Set<Vertice> conjunto) {
		if (grafo == null || conjunto == null) {
			throw new IllegalArgumentException("El grafo y el conjunto no pueden ser nulos");
		}
		if (!grafo.obtenerVertices().containsAll(conjunto)) {
			return false;
		}
		for (Vertice v : grafo.obtenerVertices()) {

			if (conjunto.contains(v)) {
				continue;
			}

			boolean dominado = false;

			for (Vertice vecino : grafo.obtenerVecinos(v)) {

				if (conjunto.contains(vecino)) {
					dominado = true;
					break;
				}
			}

			if (!dominado) {
				return false;
			}
		}
		return true;
	}
}
