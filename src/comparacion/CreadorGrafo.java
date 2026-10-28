package comparacion;

import negocio.Grafo;
import negocio.Vertice;

public class CreadorGrafo {
	private CreadorGrafo() {
	}

	public static Grafo generarCamino(int cantidadVertices) {

		validarCantidad(cantidadVertices);

		Grafo grafo = new Grafo();
		Vertice anterior = null;

		for (int i = 0; i < cantidadVertices; i++) {

			Vertice actual = new Vertice("V" + i);
			grafo.agregarVertice(actual);

			if (anterior != null) {
				grafo.agregarArista(anterior, actual);
			}

			anterior = actual;
		}

		return grafo;
	}

	public static Grafo generarCompleto(int cantidadVertices) {

		validarCantidad(cantidadVertices);

		Grafo grafo = new Grafo();
		Vertice[] vertices = new Vertice[cantidadVertices];

		for (int i = 0; i < cantidadVertices; i++) {

			vertices[i] = new Vertice("V" + i);
			grafo.agregarVertice(vertices[i]);
		}

		for (int i = 0; i < cantidadVertices; i++) {

			for (int j = i + 1; j < cantidadVertices; j++) {

				grafo.agregarArista(vertices[i], vertices[j]);
			}
		}

		return grafo;
	}

	private static void validarCantidad(int cantidadVertices) {

		if (cantidadVertices < 0) {
			throw new IllegalArgumentException("La cantidad de vertices no puede ser negativa");
		}
	}

}
