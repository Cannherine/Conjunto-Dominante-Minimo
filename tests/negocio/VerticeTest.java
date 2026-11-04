package negocio;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import negocio.Vertice;

public class VerticeTest {
	  @Test
	    public void dosVerticesConMismoNombreSonIguales() {
	        Vertice v1 = new Vertice("A");
	        Vertice v2 = new Vertice("A");

	        assertEquals(v1, v2);
	    }

	    @Test
	    public void dosVerticesConDistintoNombreSonDiferentes() {
	        Vertice v1 = new Vertice("A");
	        Vertice v2 = new Vertice("B");

	        assertNotEquals(v1, v2);
	    }

	    @Test
	    public void obtenerNombreCorrectamente() {
	        Vertice v = new Vertice("A");

	        assertEquals("A", v.getNombre());
	    }

@Test
public void verticesIgualesTienenMismoHashCode() {
    Vertice v1 = new Vertice("A");
    Vertice v2 = new Vertice("A");

    assertEquals(v1.hashCode(), v2.hashCode());
}

@Test
public void toStringDevuelveElNombre() {
    Vertice v = new Vertice("A");

    assertEquals("A", v.toString());
}

@Test
public void noPermiteNombreVacio() {
    assertThrows(IllegalArgumentException.class, () -> new Vertice(""));
}

@Test
public void noPermiteNombreNulo() {
    assertThrows(IllegalArgumentException.class, () -> new Vertice(null));
}

@Test
public void noPermiteNombreSoloConEspacios() {
    assertThrows(IllegalArgumentException.class, () -> new Vertice("   "));
}

@Test
public void verticesConEspaciosSonIguales() {
    Vertice a = new Vertice(" A ");
    Vertice b = new Vertice("A");

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
}

}
