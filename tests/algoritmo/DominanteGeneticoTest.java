package algoritmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import algoritmo.ConjuntoDominante;
import algoritmo.DominanteGenetico;
import negocio.Grafo;
import negocio.Vertice;

public class DominanteGeneticoTest {

    private DominanteGenetico genetico;

    @Before
    public void setUp() {
        genetico = new DominanteGenetico();
    }

    @Test
    public void testGrafoNulo() {
        
        Set<Vertice> solucion = genetico.resolver(null);
        assertNotNull(solucion);
        assertTrue(solucion.isEmpty());
    }

    @Test
    public void testGrafoVacio() {
        Grafo grafo = new Grafo();
        
        Set<Vertice> solucion = genetico.resolver(grafo);
        assertTrue(solucion.isEmpty());
    }

    @Test
    public void testGrafoUnVertice() {
        Grafo grafo = new Grafo();
        Vertice v1 = new Vertice("V1");
        grafo.agregarVertice(v1);

        Set<Vertice> solucion = genetico.resolver(grafo);

        assertEquals("Debe contener exactamente 1 vértice", 1, solucion.size());
        assertTrue(solucion.contains(v1));
        assertTrue("Debe ser un conjunto dominante", ConjuntoDominante.esDominante(grafo, solucion));
    }

    @Test
    public void testGrafoDesconectado() {
        
        Grafo grafo = new Grafo();
        Vertice v1 = new Vertice("V1");
        Vertice v2 = new Vertice("V2");
        Vertice v3 = new Vertice("V3");

        grafo.agregarVertice(v1);
        grafo.agregarVertice(v2);
        grafo.agregarVertice(v3);

        Set<Vertice> solucion = genetico.resolver(grafo);

        assertEquals("Al no haber aristas, debe seleccionar todos los vértices", 3, solucion.size());
        assertTrue(ConjuntoDominante.esDominante(grafo, solucion));
    }

    @Test
    public void testGrafoEstrella() {
       
        Grafo grafo = new Grafo();
        Vertice centro = new Vertice("Centro");
        Vertice h1 = new Vertice("H1");
        Vertice h2 = new Vertice("H2");
        Vertice h3 = new Vertice("H3");
        Vertice h4 = new Vertice("H4");

        grafo.agregarVertice(centro);
        grafo.agregarVertice(h1);
        grafo.agregarVertice(h2);
        grafo.agregarVertice(h3);
        grafo.agregarVertice(h4);

        grafo.agregarArista(centro, h1);
        grafo.agregarArista(centro, h2);
        grafo.agregarArista(centro, h3);
        grafo.agregarArista(centro, h4);

        Set<Vertice> solucion = genetico.resolver(grafo);

        
        assertTrue("La solución debe ser un conjunto válido", ConjuntoDominante.esDominante(grafo, solucion));
        assertEquals("El tamaño óptimo es 1 (solo el centro)", 1, solucion.size());
        assertTrue(solucion.contains(centro));
    }

    @Test
    public void testGrafoCompleto() {
        
        Grafo grafo = new Grafo();
        Vertice v1 = new Vertice("V1");
        Vertice v2 = new Vertice("V2");
        Vertice v3 = new Vertice("V3");
        Vertice v4 = new Vertice("V4");

        grafo.agregarVertice(v1);
        grafo.agregarVertice(v2);
        grafo.agregarVertice(v3);
        grafo.agregarVertice(v4);

        grafo.agregarArista(v1, v2);
        grafo.agregarArista(v1, v3);
        grafo.agregarArista(v1, v4);
        grafo.agregarArista(v2, v3);
        grafo.agregarArista(v2, v4);
        grafo.agregarArista(v3, v4);

        Set<Vertice> solucion = genetico.resolver(grafo);

        assertTrue(ConjuntoDominante.esDominante(grafo, solucion));
        assertEquals("Cualquier vértice domina el grafo, el tamaño debe ser 1", 1, solucion.size());
    }
    @Test
    public void testSolucionSiempreDominante() {
        Grafo grafo = new Grafo();

        Vertice a = new Vertice("A");
        Vertice b = new Vertice("B");
        Vertice c = new Vertice("C");
        Vertice d = new Vertice("D");
        Vertice e = new Vertice("E");

        grafo.agregarVertice(a);
        grafo.agregarVertice(b);
        grafo.agregarVertice(c);
        grafo.agregarVertice(d);
        grafo.agregarVertice(e);

        grafo.agregarArista(a, b);
        grafo.agregarArista(b, c);
        grafo.agregarArista(c, d);

        for (int i = 0; i < 20; i++) {
            Set<Vertice> solucion = genetico.resolver(grafo);

            assertTrue( "El genetico debe devolver un conjunto dominante",
                ConjuntoDominante.esDominante(grafo, solucion));
        }
    }
}