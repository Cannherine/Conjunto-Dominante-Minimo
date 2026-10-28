package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import algoritmo.ConjuntoDominante;
import negocio.Grafo;
import negocio.Vertice;

public class ConjuntoDominanteTest {
	  @Test(expected = IllegalArgumentException.class)
	    public void noAceptarGrafoNulo() {
	        ConjuntoDominante.esDominante(null, null);
	    }

@Test
public void conjuntoConVerticeInexistente() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);

    Set<Vertice> conjunto = new HashSet<>();
    conjunto.add(b);

    assertFalse(
        ConjuntoDominante.esDominante(grafo, conjunto)
    );
}

@Test
public void conjuntoDominanteValido() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);
    grafo.agregarArista(b, c);

    Set<Vertice> conjunto = new HashSet<>();
    conjunto.add(b);

    assertTrue(
        ConjuntoDominante.esDominante(grafo, conjunto)
    );
}

@Test
public void conjuntoNoDominante() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);
    grafo.agregarArista(b, c);

    Set<Vertice> conjunto = new HashSet<>();
    conjunto.add(a);

    assertFalse(
        ConjuntoDominante.esDominante(grafo, conjunto)
    );
}

@Test
public void verticeAisladoNoDominado() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);

    Set<Vertice> conjunto = new HashSet<>();
    conjunto.add(a);

    assertFalse(
        ConjuntoDominante.esDominante(grafo, conjunto)
    );
}

@Test
public void verticeAisladoIncluidoEnConjunto() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);

    Set<Vertice> conjunto = new HashSet<>();
    conjunto.add(a);
    conjunto.add(c);

    assertTrue(
        ConjuntoDominante.esDominante(grafo, conjunto)
    );
}

@Test
public void conjuntoVacioDominaGrafoVacio() {
    Grafo grafo = new Grafo();

    Set<Vertice> conjunto = new HashSet<>();

    assertTrue(
        ConjuntoDominante.esDominante(grafo, conjunto)
    );
}


}
