package comparacion;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
