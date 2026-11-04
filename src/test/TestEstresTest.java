package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comparacion.ResultadoAlgoritmo;
import comparacion.TestEstres;

public class TestEstresTest {
	 @Test
	    public void ejecutarInstanciaConCincoVertices() {

	        TestEstres testEstres = new TestEstres();

	        ResultadoAlgoritmo resultado =   testEstres.ejecutarInstancia(5);

	        assertNotNull(resultado);
	        assertNotNull(resultado.getSolucion());
	        assertTrue(resultado.getTiempoNanosegundos() >= 0);
	    }
}
