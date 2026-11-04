package archivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import negocio.Grafo;
import negocio.Vertice;

class LectorGrafoTest {

	@TempDir
	Path directorioTemporal;

	private File crearArchivo(String nombre, String contenido) throws IOException {
		Path archivo = directorioTemporal.resolve(nombre);
		Files.writeString(archivo, contenido);
		return archivo.toFile();
	}

	private String mensajeDeError(File archivo) {
		IOException excepcion = assertThrows(IOException.class, () -> new LectorGrafo().cargar(archivo));
		return excepcion.getMessage();
	}

	@Test
	void archivoNuloLanzaExcepcion() {
		IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class,
				() -> new LectorGrafo().cargar(null));

		assertEquals("El archivo no puede ser nulo", excepcion.getMessage());
	}

	@Test
	void archivoTxtInexistenteLanzaExcepcion() {
		File archivo = directorioTemporal.resolve("inexistente.txt").toFile();

		assertThrows(IOException.class, () -> new LectorGrafo().cargar(archivo));
	}

	@Test
	void archivoJsonInexistenteLanzaExcepcion() {
		File archivo = directorioTemporal.resolve("inexistente.json").toFile();

		assertThrows(IOException.class, () -> new LectorGrafo().cargar(archivo));
	}

	@Test
	void cargarJsonValido() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"A\", \"B\", \"C\", \"D\"],"
				+ " \"aristas\": [[\"A\", \"B\"], [\"B\", \"C\"], [\"C\", \"D\"]] }");

		Grafo grafo = new LectorGrafo().cargar(archivo);

		assertEquals(4, grafo.cantidadVertices());
		assertEquals(3, grafo.cantidadAristas());
		assertTrue(grafo.existeArista(new Vertice("A"), new Vertice("B")));
		assertTrue(grafo.existeArista(new Vertice("C"), new Vertice("D")));
	}

	@Test
	void jsonConMinusculasSeNormalizaAMayusculas() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"a\", \"b\"], \"aristas\": [[\"a\", \"b\"]] }");

		Grafo grafo = new LectorGrafo().cargar(archivo);

		assertTrue(grafo.existeArista(new Vertice("A"), new Vertice("B")));
	}

	@Test
	void jsonSinAristasEsValido() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"A\", \"B\"], \"aristas\": [] }");

		Grafo grafo = new LectorGrafo().cargar(archivo);

		assertEquals(2, grafo.cantidadVertices());
		assertEquals(0, grafo.cantidadAristas());
	}

	@Test
	void jsonVacioLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json", "");

		assertTrue(mensajeDeError(archivo).contains("debe tener los campos"));
	}

	@Test
	void jsonMalFormadoLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"A\", ");

		assertTrue(mensajeDeError(archivo).contains("JSON mal formado"));
	}

	@Test
	void jsonSinCampoAristasLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"A\", \"B\"] }");

		assertTrue(mensajeDeError(archivo).contains("debe tener los campos"));
	}

	@Test
	void jsonSinCampoVerticesLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"aristas\": [] }");

		assertTrue(mensajeDeError(archivo).contains("debe tener los campos"));
	}

	@Test
	void jsonConVerticeNuloLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"A\", null], \"aristas\": [] }");

		assertTrue(mensajeDeError(archivo).contains("vértice vacío"));
	}

	@Test
	void jsonConVerticeRepetidoLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"A\", \"A\"], \"aristas\": [] }");

		assertTrue(mensajeDeError(archivo).contains("Vértice repetido: A"));
	}

	@Test
	void jsonConAristaDeTresElementosLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json",
				"{ \"vertices\": [\"A\", \"B\", \"C\"], \"aristas\": [[\"A\", \"B\", \"C\"]] }");

		assertTrue(mensajeDeError(archivo).contains("exactamente dos vértices"));
	}

	@Test
	void jsonConAristaAVerticeInexistenteLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"A\", \"B\"], \"aristas\": [[\"A\", \"Z\"]] }");

		assertTrue(mensajeDeError(archivo).contains("No se pudo agregar la arista"));
	}

	@Test
	void jsonConLazoLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json", "{ \"vertices\": [\"A\"], \"aristas\": [[\"A\", \"A\"]] }");

		assertTrue(mensajeDeError(archivo).contains("No se pudo agregar la arista"));
	}

	@Test
	void jsonConAristaRepetidaLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.json",
				"{ \"vertices\": [\"A\", \"B\"], \"aristas\": [[\"A\", \"B\"], [\"B\", \"A\"]] }");

		assertTrue(mensajeDeError(archivo).contains("No se pudo agregar la arista"));
	}

	@Test
	void cargarTxtValido() throws IOException {
		File archivo = crearArchivo("grafo.txt", "VERTICES\nA\nB\nC\nARISTAS\nA B\nB C\n");

		Grafo grafo = new LectorGrafo().cargar(archivo);

		assertEquals(3, grafo.cantidadVertices());
		assertEquals(2, grafo.cantidadAristas());
		assertTrue(grafo.existeArista(new Vertice("A"), new Vertice("B")));
	}

	@Test
	void txtIgnoraLineasVacias() throws IOException {
		File archivo = crearArchivo("grafo.txt", "VERTICES\n\nA\n   \nB\nARISTAS\n\nA B\n");

		Grafo grafo = new LectorGrafo().cargar(archivo);

		assertEquals(2, grafo.cantidadVertices());
		assertEquals(1, grafo.cantidadAristas());
	}

	@Test
	void txtVacioLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.txt", "");

		assertTrue(mensajeDeError(archivo).contains("VERTICES y ARISTAS"));
	}

	@Test
	void txtSinSeccionVerticesLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.txt", "A\nB\n");

		assertTrue(mensajeDeError(archivo).contains("Falta la sección VERTICES"));
	}

	@Test
	void txtSinSeccionAristasLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.txt", "VERTICES\nA\nB\n");

		assertTrue(mensajeDeError(archivo).contains("VERTICES y ARISTAS"));
	}

	@Test
	void txtConVerticeRepetidoLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.txt", "VERTICES\nA\nA\nARISTAS\n");

		assertTrue(mensajeDeError(archivo).contains("Vértice repetido: A"));
	}

	@Test
	void txtConAristaInvalidaLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.txt", "VERTICES\nA\nB\nC\nARISTAS\nA B C\n");

		assertTrue(mensajeDeError(archivo).contains("Arista inválida"));
	}

	@Test
	void txtConAristaAVerticeInexistenteLanzaExcepcion() throws IOException {
		File archivo = crearArchivo("grafo.txt", "VERTICES\nA\nB\nARISTAS\nA Z\n");

		assertTrue(mensajeDeError(archivo).contains("No se pudo agregar la arista"));
	}
}