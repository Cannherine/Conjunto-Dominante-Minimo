package negocio;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo {
	/**
	 * Representa un grafo simple, no dirigido y sin pesos.
	 *
	 * Utiliza una lista de adyacencia para almacenar los vertices y sus conexiones.
	 *
	 * No permite vertices repetidos, lazos ni aristas duplicadas.
	 */
	private final Map<Vertice, Set<Vertice>> adyacencias;

	public Grafo() {
		adyacencias = new HashMap<>();
	}

	private boolean contieneVertice(Vertice v) {
		return v != null && adyacencias.containsKey(v);
	}

	private boolean verticesValidos(Vertice a, Vertice b) {
		return contieneVertice(a) && contieneVertice(b);
	}

	public boolean agregarVertice(Vertice v) {
		if (v == null || contieneVertice(v)) {
			return false;
		}

		adyacencias.put(v, new HashSet<>());// vamos a guardar los vertics vecinos
		return true;
	}

	public boolean agregarArista(Vertice a, Vertice b) {

		if (!verticesValidos(a, b) || a.equals(b) || existeArista(a, b)) {
			return false;
		}

		adyacencias.get(a).add(b);
		adyacencias.get(b).add(a);

		return true;
	}

	public Set<Vertice> obtenerVecinos(Vertice v) {
		if (!contieneVertice(v)) {
			throw new IllegalArgumentException("El vertice no existe en el grafo");
		}

		return new HashSet<>(adyacencias.get(v));
	}

	public Set<Vertice> obtenerVertices() {
		return new HashSet<>(adyacencias.keySet());
	}

	public boolean eliminarVertice(Vertice v) {
		if (!contieneVertice(v)) {
			return false;
		}

		for (Vertice vecino : adyacencias.get(v)) {
			adyacencias.get(vecino).remove(v);
		}

		adyacencias.remove(v);
		return true;
	}

	public boolean eliminarArista(Vertice a, Vertice b) {
		if (!existeArista(a, b)) {
			return false;
		}

		adyacencias.get(a).remove(b);
		adyacencias.get(b).remove(a);

		return true;
	}

	public boolean existeArista(Vertice a, Vertice b) {
		return verticesValidos(a, b) && adyacencias.get(a).contains(b);
	}

	public int cantidadVertices() {
		return adyacencias.size();
	}

	public int cantidadAristas() {
		int cantidad = 0;

		for (Set<Vertice> vecinos : adyacencias.values()) {
			cantidad += vecinos.size();
		}

		return cantidad / 2;
	}

}
