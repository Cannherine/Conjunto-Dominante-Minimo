package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import algoritmo.ConjuntoDominante;
import algoritmo.DominanteBacktracking;
import negocio.Grafo;
import negocio.Vertice;

public class DominanteBacktrackingTest {
	   private void agregarVertices(Grafo grafo, Vertice... vertices) {
	        for (Vertice v : vertices) {
	            grafo.agregarVertice(v);
	        }
	    }
	   private Set<Vertice> verificarSolucion( Grafo grafo, int tamañoEsperado) {

	        DominanteBacktracking algoritmo =   new DominanteBacktracking();

	        Set<Vertice> resultado = algoritmo.resolver(grafo);

	        assertNotNull(resultado);
	        assertEquals(tamañoEsperado, resultado.size());
	        assertTrue(ConjuntoDominante.esDominante(grafo, resultado));

	        return resultado;
	    } 
	   
    @Test
    public void grafoConUnSoloVertice() {

        Grafo grafo = new Grafo();
        Vertice a = new Vertice("A");

        grafo.agregarVertice(a);

        Set<Vertice> resultado = verificarSolucion(grafo, 1);
        assertTrue(resultado.contains(a));
    }

@Test
public void grafoCaminoTresVertices() {

    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    agregarVertices(grafo, a, b, c);

    grafo.agregarArista(a, b);
    grafo.agregarArista(b, c);

    Set<Vertice> resultado = verificarSolucion(grafo, 1);

    assertTrue(resultado.contains(b));
}

@Test
public void grafoConVerticeAislado() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    agregarVertices(grafo, a, b, c);

    grafo.agregarArista(a, b);

    Set<Vertice> resultado = verificarSolucion(grafo, 2);

    assertTrue(resultado.contains(c));
}

@Test
public void grafoCompleto() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    agregarVertices(grafo, a, b, c);

    grafo.agregarArista(a, b);
    grafo.agregarArista(a, c);
    grafo.agregarArista(b, c);

    verificarSolucion(grafo, 1);
}

@Test
public void grafoVacio() {

    Grafo grafo = new Grafo();

    Set<Vertice> resultado = verificarSolucion(grafo, 0);

    assertTrue(resultado.isEmpty());
}

@Test
public void grafoDesconectado() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");
    Vertice d = new Vertice("D");

    agregarVertices(grafo, a, b, c, d);

    grafo.agregarArista(a, b);
    grafo.agregarArista(c, d);

    verificarSolucion(grafo, 2);
}

}


