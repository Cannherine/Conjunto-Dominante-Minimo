package archivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import negocio.Grafo;
import negocio.Vertice;

public class LectorGrafo {
	public Grafo cargar(File archivo) throws IOException {

        if (archivo == null) {
            throw new IllegalArgumentException("El archivo no puede ser nulo");
        }

        Grafo grafo = new Grafo();

        try (BufferedReader lector =new BufferedReader(new FileReader(archivo))) {

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
                        throw new IOException( "Sección VERTICES repetida o fuera de orden");
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
                throw new IOException( "El archivo debe contener VERTICES y ARISTAS");
            }
        }

        return grafo;
    }

    private void agregarVertice(Grafo grafo, String nombre)
            throws IOException {

        nombre = nombre.toUpperCase(Locale.ROOT);

        Vertice vertice = new Vertice(nombre);

        if (!grafo.agregarVertice(vertice)) {
            throw new IOException("Vértice repetido: " + nombre);
        }
    }

    private void agregarArista(Grafo grafo, String linea)throws IOException {

        String[] extremos = linea.split("\\s+");

        if (extremos.length != 2) {
            throw new IOException( "Arista inválida: " + linea);
        }

        Vertice origen = new Vertice(extremos[0].toUpperCase(Locale.ROOT));

        Vertice destino = new Vertice(extremos[1].toUpperCase(Locale.ROOT));

        if (!grafo.agregarArista(origen, destino)) {
            throw new IOException( "No se pudo agregar la arista: " + linea);
        }
    }
}
