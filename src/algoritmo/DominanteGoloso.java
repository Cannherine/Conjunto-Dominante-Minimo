package algoritmo;

import java.util.HashSet;
import java.util.Set;

import negocio.Grafo;
import negocio.Vertice;

public class DominanteGoloso {
	public Set<Vertice> resolver(Grafo grafo) {

		if (grafo == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo");
		}

		Set<Vertice> solucion = new HashSet<>();
		Set<Vertice> dominados = new HashSet<>();

		while (dominados.size() < grafo.cantidadVertices()) {

			Vertice mejor = buscarMejorVertice(grafo, dominados);

			solucion.add(mejor);

			dominados.add(mejor);
			dominados.addAll(grafo.obtenerVecinos(mejor));
		}

		return solucion;
	}

	private Vertice buscarMejorVertice(Grafo grafo, Set<Vertice> dominados) {

		Vertice mejor = null;
		int mayorCantidad = -1;

		for (Vertice v : grafo.obtenerVertices()) {

			Set<Vertice> nuevos = new HashSet<>(grafo.obtenerVecinos(v));

			nuevos.add(v);
			nuevos.removeAll(dominados);

			if (nuevos.size() > mayorCantidad) {
				mayorCantidad = nuevos.size();
				mejor = v;
			}
		}

		return mejor;
	}
}
