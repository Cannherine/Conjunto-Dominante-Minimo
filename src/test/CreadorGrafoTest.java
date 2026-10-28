package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import algoritmo.ConjuntoDominante;
import comparacion.CompararAlgoritmos;
import comparacion.CreadorGrafo;
import comparacion.ResultadoAlgoritmo;
import negocio.Grafo;
import negocio.Vertice;

public class CreadorGrafoTest {

    @Test
    public void generarCaminoDeTresVertices() {

        Grafo grafo = CreadorGrafo.generarCamino(3);

        Vertice v0 = new Vertice("V0");
        Vertice v1 = new Vertice("V1");
        Vertice v2 = new Vertice("V2");

        assertEquals(3, grafo.cantidadVertices());
        assertEquals(2, grafo.cantidadAristas());

        assertTrue(grafo.existeArista(v0, v1));
        assertTrue(grafo.existeArista(v1, v2));
        assertFalse(grafo.existeArista(v0, v2));
    }

@Test
public void generarCaminoVacio() {

    Grafo grafo = CreadorGrafo.generarCamino(0);

    assertEquals(0, grafo.cantidadVertices());
    assertEquals(0, grafo.cantidadAristas());
}

@Test
public void generarCaminoUnVertice() {

    Grafo grafo = CreadorGrafo.generarCamino(1);

    assertEquals(1, grafo.cantidadVertices());
    assertEquals(0, grafo.cantidadAristas());
}

@Test(expected = IllegalArgumentException.class)
public void generarCaminoCantidadNegativa() {

	CreadorGrafo.generarCamino(-1);
}

@Test
public void compararCaminosDistintosTamaños() {

    CompararAlgoritmos comparador = new CompararAlgoritmos();

    for (int n = 3; n <= 10; n++) {

        Grafo grafo = CreadorGrafo.generarCamino(n);

        ResultadoAlgoritmo backtracking =  comparador.medirBacktracking(grafo);

        ResultadoAlgoritmo goloso = comparador.medirGoloso(grafo);

        assertTrue(ConjuntoDominante.esDominante(
                grafo, backtracking.getSolucion()));

        assertTrue(ConjuntoDominante.esDominante(
                grafo, goloso.getSolucion()));

        System.out.println("Vertices: " + n);
        System.out.println("Backtracking: "
                + backtracking.getTamaño() + " vertices, "
                + backtracking.getTiempoNanosegundos() + " ns");

        System.out.println("Goloso: "
                + goloso.getTamaño() + " vertices, "
                + goloso.getTiempoNanosegundos() + " ns");

        System.out.println("----------------------");
    }
}


@Test
public void generarGrafoCompletoCuatroVertices() {

    Grafo grafo = CreadorGrafo.generarCompleto(4);

    Vertice v0 = new Vertice("V0");
    Vertice v1 = new Vertice("V1");
    Vertice v2 = new Vertice("V2");
    Vertice v3 = new Vertice("V3");

    assertEquals(4, grafo.cantidadVertices());
    assertEquals(6, grafo.cantidadAristas());

    assertTrue(grafo.existeArista(v0, v1));
    assertTrue(grafo.existeArista(v0, v2));
    assertTrue(grafo.existeArista(v0, v3));
    assertTrue(grafo.existeArista(v1, v2));
    assertTrue(grafo.existeArista(v1, v3));
    assertTrue(grafo.existeArista(v2, v3));
}

}
