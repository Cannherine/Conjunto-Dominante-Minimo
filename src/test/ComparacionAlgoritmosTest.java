package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import algoritmo.ConjuntoDominante;
import algoritmo.DominanteBacktracking;
import algoritmo.DominanteGoloso;
import negocio.Grafo;
import negocio.Vertice;
import comparacion.CompararAlgoritmos;
import comparacion.CreadorGrafo;
import comparacion.ResultadoAlgoritmo;

public class ComparacionAlgoritmosTest {

@Test
public void compararCaminoTresVertices() {

    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);
    grafo.agregarArista(b, c);

    CompararAlgoritmos comparador =new CompararAlgoritmos();

    ResultadoAlgoritmo resultadoBacktracking =comparador.medirBacktracking(grafo);

    ResultadoAlgoritmo resultadoGoloso =  comparador.medirGoloso(grafo);

    assertTrue(ConjuntoDominante.esDominante(grafo, resultadoBacktracking.getSolucion()));

    assertTrue(ConjuntoDominante.esDominante(grafo, resultadoGoloso.getSolucion()));
    assertEquals(1, resultadoBacktracking.getTamaño());
    assertEquals(1, resultadoGoloso.getTamaño());
    assertTrue(resultadoBacktracking.getTiempoNanosegundos() >= 0);
assertTrue(resultadoGoloso.getTiempoNanosegundos() >= 0);

System.out.println("BACKTRACKING");
System.out.println("Tamaño: " + resultadoBacktracking.getTamaño());
System.out.println("Tiempo (ns): " + resultadoBacktracking.getTiempoNanosegundos());

System.out.println("GOLOSO");
System.out.println("Tamaño: " + resultadoGoloso.getTamaño());
System.out.println("Tiempo (ns): " + resultadoGoloso.getTiempoNanosegundos());

}

@Test
public void compararCaminosDistintosTamaños() {

    CompararAlgoritmos comparador = new CompararAlgoritmos();

    DominanteBacktracking backtracking = new DominanteBacktracking();

    DominanteGoloso goloso = new DominanteGoloso();

    for (int n = 3; n <= 10; n++) {

        Grafo grafo = CreadorGrafo.generarCamino(n);

        ResultadoAlgoritmo resultadoBacktracking = comparador.medirBacktracking(grafo);

        ResultadoAlgoritmo resultadoGoloso = comparador.medirGoloso(grafo);

        assertTrue(ConjuntoDominante.esDominante( grafo, resultadoBacktracking.getSolucion()));

        assertTrue(ConjuntoDominante.esDominante( grafo, resultadoGoloso.getSolucion()));
        assertTrue(resultadoBacktracking.getTamaño()<= resultadoGoloso.getTamaño());
        long promedioBacktracking =comparador.calcularPromedio( grafo, backtracking::resolver, 10);

        long promedioGoloso =  comparador.calcularPromedio( grafo, goloso::resolver, 10);

        System.out.println("Vertices: " + n);

        System.out.println("Backtracking - Tamaño: " + resultadoBacktracking.getTamaño());

        System.out.println("Goloso - Tamaño: " + resultadoGoloso.getTamaño());

        System.out.println("Promedio Backtracking (ns): "  + promedioBacktracking);

        System.out.println("Promedio Goloso (ns): "  + promedioGoloso);

        System.out.println("------------------------");
    }
}

@Test
public void compararGrafosCompletos() {

    CompararAlgoritmos comparador =new CompararAlgoritmos();

    DominanteBacktracking backtracking = new DominanteBacktracking();

    DominanteGoloso goloso =  new DominanteGoloso();

    for (int n = 3; n <= 10; n++) {

        Grafo grafo = CreadorGrafo.generarCompleto(n);

        ResultadoAlgoritmo resultadoBacktracking = comparador.medirBacktracking(grafo);

        ResultadoAlgoritmo resultadoGoloso = comparador.medirGoloso(grafo);

        assertTrue(ConjuntoDominante.esDominante(grafo, resultadoBacktracking.getSolucion()));

        assertTrue(ConjuntoDominante.esDominante( grafo, resultadoGoloso.getSolucion()));

        assertEquals(1, resultadoBacktracking.getTamaño());
        assertEquals(1, resultadoGoloso.getTamaño());

        long promedioBacktracking = comparador.calcularPromedio( grafo, backtracking::resolver, 10);

        long promedioGoloso =  comparador.calcularPromedio( grafo, goloso::resolver, 10);

        System.out.println("GRAFO COMPLETO - Vertices: " + n);
        System.out.println("Backtracking (ns): "+ promedioBacktracking);
        System.out.println("Goloso (ns): "+ promedioGoloso);
        System.out.println("------------------------");
    }
}

@Test
public void compararGrafoConVerticeAislado() {

    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);

    CompararAlgoritmos comparador = new CompararAlgoritmos();

    ResultadoAlgoritmo backtracking =  comparador.medirBacktracking(grafo);

    ResultadoAlgoritmo goloso =comparador.medirGoloso(grafo);

    assertTrue(ConjuntoDominante.esDominante(grafo, backtracking.getSolucion()));

    assertTrue(ConjuntoDominante.esDominante(grafo, goloso.getSolucion()));

    assertTrue(backtracking.getSolucion().contains(c));
    assertTrue(goloso.getSolucion().contains(c));

    assertEquals(2, backtracking.getTamaño());
    assertEquals(2, goloso.getTamaño());
}

}
