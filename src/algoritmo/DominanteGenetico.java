package algoritmo;

import java.util.*;
import negocio.*;

public class DominanteGenetico {
	private final int poblacion = 100, generaciones = 500;
	private final double probMutacion = 0.05;
	private final Random rand = new Random();

	public Set<Vertice> resolver(Grafo grafo) {
		if (grafo == null || grafo.cantidadVertices() == 0)
			return new HashSet<>();
		List<Vertice> vertices = new ArrayList<>(grafo.obtenerVertices());
		List<Individuo> pop = new ArrayList<>();

		for (int i = 0; i < poblacion; i++)
			pop.add(new Individuo(vertices, grafo));
		Individuo mejorGlobal = pop.get(0);

		for (int gen = 0; gen < generaciones; gen++) {
			List<Individuo> nuevaPop = new ArrayList<>();
			nuevaPop.add(obtenerMejor(pop));

			if (nuevaPop.get(0).fitness < mejorGlobal.fitness)
				mejorGlobal = nuevaPop.get(0);

			while (nuevaPop.size() < poblacion) {
				Individuo p1 = seleccion(pop), p2 = seleccion(pop);
				nuevaPop.add(new Individuo(p1, p2, vertices, grafo)); // Cruce y mutación
			}
			pop = nuevaPop;
		}

		Individuo mejorFinal = obtenerMejor(pop);

		if (mejorFinal.fitness < mejorGlobal.fitness) {
			mejorGlobal = mejorFinal;
		}

		Set<Vertice> solucion = extraerSolucion(mejorGlobal, vertices);

		repararSolucion(grafo, solucion);

		return solucion;
	}

	private void repararSolucion(Grafo grafo, Set<Vertice> solucion) {
		for (Vertice vertice : grafo.obtenerVertices()) {

			if (solucion.contains(vertice)) {
				continue;
			}

			boolean dominado = false;

			for (Vertice vecino : grafo.obtenerVecinos(vertice)) {
				if (solucion.contains(vecino)) {
					dominado = true;
					break;
				}
			}

			if (!dominado) {
				solucion.add(vertice);
			}
		}
	}

	private Individuo seleccion(List<Individuo> pop) {
		Individuo mejor = pop.get(rand.nextInt(poblacion));
		for (int i = 1; i < 3; i++) { // Torneo de tamaño 3
			Individuo competidor = pop.get(rand.nextInt(poblacion));
			if (competidor.fitness < mejor.fitness)
				mejor = competidor;
		}
		return mejor;
	}

	private Individuo obtenerMejor(List<Individuo> pop) {
		Individuo mejor = pop.get(0);
		for (Individuo ind : pop)
			if (ind.fitness < mejor.fitness)
				mejor = ind;
		return mejor;
	}

	private Set<Vertice> extraerSolucion(Individuo ind, List<Vertice> v) {
		Set<Vertice> sol = new HashSet<>();
		for (int i = 0; i < ind.cromosoma.length; i++)
			if (ind.cromosoma[i])
				sol.add(v.get(i));
		return sol;
	}

	private class Individuo {
		private final boolean[] cromosoma;
		private int fitness;

		Individuo(List<Vertice> v, Grafo g) {
			cromosoma = new boolean[v.size()];
			for (int i = 0; i < cromosoma.length; i++)
				cromosoma[i] = rand.nextBoolean();
			calcularFitness(v, g);
		}

		Individuo(Individuo p1, Individuo p2, List<Vertice> v, Grafo g) {
			cromosoma = new boolean[v.size()];
			for (int i = 0; i < cromosoma.length; i++) {
				cromosoma[i] = rand.nextBoolean() ? p1.cromosoma[i] : p2.cromosoma[i];
				if (rand.nextDouble() < probMutacion)
					cromosoma[i] = !cromosoma[i];
			}
			calcularFitness(v, g);
		}

		void calcularFitness(List<Vertice> vertices, Grafo grafo) {
			Set<Vertice> dominados = new HashSet<>();
			int tamanoConjunto = 0;
			for (int i = 0; i < cromosoma.length; i++) {
				if (cromosoma[i]) {
					tamanoConjunto++;
					dominados.add(vertices.get(i));
					dominados.addAll(grafo.obtenerVecinos(vertices.get(i)));
				}
			}
			int noDominados = grafo.cantidadVertices() - dominados.size();

			fitness = tamanoConjunto + noDominados * (grafo.cantidadVertices() + 1);
		}
	}
}