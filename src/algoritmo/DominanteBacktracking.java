package algoritmo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import negocio.Grafo;
import negocio.Vertice;

//Backtracking es una técnica que explora distintas 
//soluciones posibles y descarta caminos cuando determina 
//que no conviene seguir por ellos
public class DominanteBacktracking {
	public Set<Vertice> resolver(Grafo grafo) {
		if (grafo == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo");
		}

		List<Vertice> vertices = new ArrayList<>(grafo.obtenerVertices());

		Set<Vertice> actual = new HashSet<>();
		Set<Vertice> mejor = new HashSet<>(vertices);

		buscar(grafo, vertices, 0, actual, mejor);

		return mejor;
	}

	private void buscar(Grafo grafo, List<Vertice> vertices, int indice, Set<Vertice> actual, Set<Vertice> mejor) {

		if (actual.size() >= mejor.size()) {
			return;
		}

		if (indice == vertices.size()) {// CASO BASE :cuando no quedan más vértices por analizar
			if (ConjuntoDominante.esDominante(grafo, actual)) {
				mejor.clear();
				mejor.addAll(actual);
			}
			return;
		}
		Vertice v = vertices.get(indice);
		actual.add(v);
		buscar(grafo, vertices, indice + 1, actual, mejor);

		actual.remove(v); // LO MAS IMPORTANTE

		buscar(grafo, vertices, indice + 1, actual, mejor);
	}
}
