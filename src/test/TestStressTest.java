package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comparacion.ResultadoAlgoritmo;
import comparacion.TestStress;

public class TestStressTest {
	 @Test
	    public void ejecutarInstanciaConCincoVertices() {

	        TestStress testStress = new TestStress();

	        ResultadoAlgoritmo resultado =   testStress.ejecutarInstancia(5);

	        assertNotNull(resultado);
	        assertNotNull(resultado.getSolucion());
	        assertTrue(resultado.getTiempoNanosegundos() >= 0);
	    }
}
