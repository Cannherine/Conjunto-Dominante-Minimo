package comparacion;

import java.util.concurrent.CancellationException;
import java.util.function.BiConsumer;

import negocio.Grafo;

public class TestStress {
	private final CompararAlgoritmos comparador;
	private volatile boolean detenido;
	private volatile Thread hiloEjecucion;
	
    public TestStress() {
        comparador = new CompararAlgoritmos();
    }
    public void detener() {
        detenido = true;
        Thread hilo = hiloEjecucion;

        if (hilo != null) {
            hilo.interrupt();
        }
    }
    public boolean estaDetenido() {
        return detenido;
    }
    public ResultadoAlgoritmo ejecutarInstancia(int cantidadVertices) {

        Grafo grafo = CreadorGrafo.generarAleatorio(cantidadVertices);

        return comparador.medirBacktracking(grafo);
    }
    public void ejecutar(int cantidadInicial, int cantidadFinal,  BiConsumer<Integer, ResultadoAlgoritmo> informarResultado) {
    	if (informarResultado == null) {
    	    throw new IllegalArgumentException(
    	            "El receptor de resultados no puede ser nulo");
    	}
    	if (cantidadInicial < 0 || cantidadFinal < cantidadInicial) {
            throw new IllegalArgumentException("Rango de vertices invalido");
        }

        hiloEjecucion = Thread.currentThread();
        if (estaDetenido()) {
            hiloEjecucion.interrupt();
        }
        try {

            for (int n = cantidadInicial;
                    n <= cantidadFinal && !estaDetenido();
                    n++) {

                ResultadoAlgoritmo resultado = ejecutarInstancia(n);

                if (estaDetenido()) {
                    break;
                }

                informarResultado.accept(n, resultado);
                }

        } catch (CancellationException e) {

            System.out.println("Test de estres detenido por el usuario.");

        } finally {

            hiloEjecucion = null;
        }
    }
}
