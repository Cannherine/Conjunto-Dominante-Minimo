package comparacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestStressTest {
	@Test
	public void ejecutarInstanciaConCincoVertices() {

		TestStress testStress = new TestStress();

		ResultadoAlgoritmo resultado = testStress.ejecutarInstancia(5);

		assertNotNull(resultado);
		assertNotNull(resultado.getSolucion());
		assertTrue(resultado.getTiempoNanosegundos() >= 0);
	}
	
	@Test
	public void ejecutarInformaUnResultadoPorCadaCantidad() {

		TestStress testStress = new TestStress();
		List<Integer> cantidades = new ArrayList<>();

		testStress.ejecutar(5, 8, (n, resultado) -> cantidades.add(n));

		assertEquals(List.of(5, 6, 7, 8), cantidades);
	}

	@Test
	public void ejecutarConRangoInvalido() {

		TestStress testStress = new TestStress();

		assertThrows(IllegalArgumentException.class, () -> testStress.ejecutar(10, 5, (n, resultado) -> {
		}));
	}

	@Test
	public void detenerCortaLaEjecucion() {

		TestStress testStress = new TestStress();
		List<Integer> cantidades = new ArrayList<>();

		testStress.ejecutar(5, 10, (n, resultado) -> {
			cantidades.add(n);
			testStress.detener();
		});

		assertEquals(1, cantidades.size());
		assertTrue(testStress.estaDetenido());

		Thread.interrupted();
	}
}
