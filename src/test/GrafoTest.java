package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import negocio.Grafo;
import negocio.Vertice;

public class GrafoTest {
    @Test
    public void agregarVerticeNuevo() {
        Grafo grafo = new Grafo();
        Vertice a = new Vertice("A");

        assertTrue(grafo.agregarVertice(a));
    }

    @Test
    public void noAgregarVerticeRepetido() {
        Grafo grafo = new Grafo();

        grafo.agregarVertice(new Vertice("A"));

        assertFalse(grafo.agregarVertice(new Vertice("A")));
    }

    @Test
    public void noAgregarVerticeNulo() {
        Grafo grafo = new Grafo();

        assertFalse(grafo.agregarVertice(null));
    }

@Test
public void agregarAristaValida() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);

    assertTrue(grafo.agregarArista(a, b));
}

@Test
public void noAgregarAristaRepetida() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarArista(a, b);

    assertFalse(grafo.agregarArista(a, b));
    assertFalse(grafo.agregarArista(b, a));
}

@Test
public void noAgregarLazos() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");

    grafo.agregarVertice(a);

    assertFalse(grafo.agregarArista(a, a));
}

@Test
public void noAgregarAristaConVerticeInexistente() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);

    assertFalse(grafo.agregarArista(a, b));
}

@Test
public void noAgregarAristaConVerticeNulo() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");

    grafo.agregarVertice(a);

    assertFalse(grafo.agregarArista(a, null));
    assertFalse(grafo.agregarArista(null, a));
}

@Test
public void obtenerVecinosCorrectamente() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);
    grafo.agregarArista(b, c);

    assertTrue(grafo.obtenerVecinos(b).contains(a));
    assertTrue(grafo.obtenerVecinos(b).contains(c));
    assertEquals(2, grafo.obtenerVecinos(b).size());
}

@Test
public void aristaSeGuardaEnAmbosSentidos() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarArista(a, b);

    assertTrue(grafo.obtenerVecinos(a).contains(b));
    assertTrue(grafo.obtenerVecinos(b).contains(a));
}

@Test
public void verticeAisladoNoTieneVecinos() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");

    grafo.agregarVertice(a);

    assertTrue(grafo.obtenerVecinos(a).isEmpty());
}

@Test(expected = IllegalArgumentException.class)
public void obtenerVecinosDeVerticeInexistente() {
    Grafo grafo = new Grafo();

    grafo.obtenerVecinos(new Vertice("A"));
}

@Test
public void obtenerVerticesDeGrafoVacio() {
    Grafo grafo = new Grafo();

    assertTrue(grafo.obtenerVertices().isEmpty());
}

@Test
public void obtenerTodosLosVertices() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);

    assertEquals(2, grafo.obtenerVertices().size());
    assertTrue(grafo.obtenerVertices().contains(a));
    assertTrue(grafo.obtenerVertices().contains(b));
}

@Test
public void obtenerVerticesNoPermiteModificarGrafo() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");

    grafo.agregarVertice(a);

    grafo.obtenerVertices().clear();

    assertEquals(1, grafo.obtenerVertices().size());
}

@Test
public void eliminarVerticeExistente() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");

    grafo.agregarVertice(a);

    assertTrue(grafo.eliminarVertice(a));
    assertFalse(grafo.obtenerVertices().contains(a));
}

@Test
public void eliminarVerticeInexistente() {
    Grafo grafo = new Grafo();

    assertFalse(grafo.eliminarVertice(new Vertice("A")));
}

@Test
public void eliminarVerticeEliminaSusConexiones() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);
    grafo.agregarArista(b, c);

    assertTrue(grafo.eliminarVertice(b));

    assertTrue(grafo.obtenerVecinos(a).isEmpty());
    assertTrue(grafo.obtenerVecinos(c).isEmpty());
    assertEquals(2, grafo.obtenerVertices().size());
}

@Test
public void eliminarAristaExistente() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarArista(a, b);

    assertTrue(grafo.eliminarArista(a, b));

    assertFalse(grafo.obtenerVecinos(a).contains(b));
    assertFalse(grafo.obtenerVecinos(b).contains(a));
}

@Test
public void eliminarAristaInexistente() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);

    assertFalse(grafo.eliminarArista(a, b));
}

@Test
public void eliminarAristaNoEliminaVertices() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarArista(a, b);

    grafo.eliminarArista(a, b);

    assertEquals(2, grafo.obtenerVertices().size());
}

@Test
public void existeAristaEntreVerticesConectados() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarArista(a, b);

    assertTrue(grafo.existeArista(a, b));
    assertTrue(grafo.existeArista(b, a));
}

@Test
public void noExisteAristaEntreVerticesSinConexion() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);

    assertFalse(grafo.existeArista(a, b));
}

@Test
public void noExisteAristaConVerticeInexistente() {
    Grafo grafo = new Grafo();
    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");

    grafo.agregarVertice(a);

    assertFalse(grafo.existeArista(a, b));
}

@Test
public void cantidadVerticesGrafoVacio() {
    Grafo grafo = new Grafo();

    assertEquals(0, grafo.cantidadVertices());
}

@Test
public void cantidadVerticesCorrecta() {
    Grafo grafo = new Grafo();

    grafo.agregarVertice(new Vertice("A"));
    grafo.agregarVertice(new Vertice("B"));
    grafo.agregarVertice(new Vertice("C"));

    assertEquals(3, grafo.cantidadVertices());
}

@Test
public void cantidadAristasGrafoVacio() {
    Grafo grafo = new Grafo();

    assertEquals(0, grafo.cantidadAristas());
}

@Test
public void cantidadAristasCorrecta() {
    Grafo grafo = new Grafo();

    Vertice a = new Vertice("A");
    Vertice b = new Vertice("B");
    Vertice c = new Vertice("C");

    grafo.agregarVertice(a);
    grafo.agregarVertice(b);
    grafo.agregarVertice(c);

    grafo.agregarArista(a, b);
    grafo.agregarArista(b, c);

    assertEquals(2, grafo.cantidadAristas());
}

}
