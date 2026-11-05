package comparacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import negocio.Vertice;

public class ResultadoAlgoritmoTest {

	@Test
	public void crearResultadoValido() {

		Set<Vertice> solucion = new HashSet<>();
		solucion.add(new Vertice("A"));

		ResultadoAlgoritmo resultado = new ResultadoAlgoritmo(solucion, 100);

		assertEquals(1, resultado.getTamanio());
		assertEquals(100, resultado.getTiempoNanosegundos());
	}

	@Test
	public void resultadoConSolucionNula() {

		assertThrows(IllegalArgumentException.class, () -> new ResultadoAlgoritmo(null, 100));
	}

	@Test
	public void modificarSolucionOriginalNoAfectaAlResultado() {

		Set<Vertice> solucion = new HashSet<>();
		solucion.add(new Vertice("A"));

		ResultadoAlgoritmo resultado = new ResultadoAlgoritmo(solucion, 100);
		solucion.add(new Vertice("B"));

		assertEquals(1, resultado.getTamanio());
	}
}
