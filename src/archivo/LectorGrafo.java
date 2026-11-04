package archivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import negocio.Grafo;
import negocio.Vertice;

public class LectorGrafo {

	private static class GrafoJson {
		List<String> vertices;
		List<List<String>> aristas;
	}

	public Grafo cargar(File archivo) throws IOException {
		if (archivo == null) {
			throw new IllegalArgumentException("El archivo no puede ser nulo");
		}

		if (archivo.getName().toLowerCase(Locale.ROOT).endsWith(".json")) {
			return cargarJson(archivo);
		}
		return cargarTxt(archivo);
	}

	private Grafo cargarTxt(File archivo) throws IOException {

		Grafo grafo = new Grafo();

		try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {

			String linea;
			boolean leyendoAristas = false;
			boolean encontroVertices = false;
			boolean encontroAristas = false;

			while ((linea = lector.readLine()) != null) {

				linea = linea.trim();

				if (linea.isEmpty()) {
					continue;
				}

				if (linea.equalsIgnoreCase("VERTICES")) {
					if (encontroVertices || encontroAristas) {
						throw new IOException("Sección VERTICES repetida o fuera de orden");
					}

					encontroVertices = true;
					continue;
				}

				if (linea.equalsIgnoreCase("ARISTAS")) {
					if (!encontroVertices || encontroAristas) {
						throw new IOException("Sección ARISTAS inválida");
					}

					encontroAristas = true;
					leyendoAristas = true;
					continue;
				}

				if (!encontroVertices) {
					throw new IOException("Falta la sección VERTICES");
				}

				if (!leyendoAristas) {
					agregarVertice(grafo, linea);
				} else {
					agregarArista(grafo, linea);
				}
			}

			if (!encontroVertices || !encontroAristas) {
				throw new IOException("El archivo debe contener VERTICES y ARISTAS");
			}
		}

		return grafo;
	}

	private Grafo cargarJson(File archivo) throws IOException {
		GrafoJson datos;
		try (Reader lector = new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8)) {
			datos = new Gson().fromJson(lector, GrafoJson.class);
		} catch (JsonParseException ex) {
			throw new IOException("JSON mal formado: " + ex.getMessage());
		}

		if (datos == null || datos.vertices == null || datos.aristas == null) {
			throw new IOException("El JSON debe tener los campos \"vertices\" y \"aristas\"");
		}

		Grafo grafo = new Grafo();

		for (String nombre : datos.vertices) {
			if (nombre == null) {
				throw new IOException("Hay un vértice vacío en la lista");
			}
			agregarVertice(grafo, nombre);
		}

		for (List<String> par : datos.aristas) {
			if (par == null || par.size() != 2 || par.get(0) == null || par.get(1) == null) {
				throw new IOException("Cada arista debe tener exactamente dos vértices: " + par);
			}
			agregarArista(grafo, par.get(0), par.get(1));
		}

		return grafo;
	}

	private void agregarVertice(Grafo grafo, String nombre) throws IOException {
		nombre = nombre.toUpperCase(Locale.ROOT);

		Vertice vertice = new Vertice(nombre);

		if (!grafo.agregarVertice(vertice)) {
			throw new IOException("Vértice repetido: " + nombre);
		}
	}

	private void agregarArista(Grafo grafo, String linea) throws IOException {
		String[] extremos = linea.split("\\s+");

		if (extremos.length != 2) {
			throw new IOException("Arista inválida: " + linea);
		}

		agregarArista(grafo, extremos[0], extremos[1]);
	}

	private void agregarArista(Grafo grafo, String a, String b) throws IOException {
		Vertice origen = new Vertice(a.toUpperCase(Locale.ROOT));
		Vertice destino = new Vertice(b.toUpperCase(Locale.ROOT));

		if (!grafo.agregarArista(origen, destino)) {
			throw new IOException("No se pudo agregar la arista: " + origen + " - " + destino);
		}
	}
}