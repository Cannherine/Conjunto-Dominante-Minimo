package interfaz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import negocio.Grafo;
import negocio.Vertice;

public class GrafoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

    private static final int DIAMETRO_VERTICE = 40;
    private static final int RADIO_VERTICE = DIAMETRO_VERTICE / 2;
    private static final int MARGEN = 20;
    private static final int ITERACIONES = 150;
    private static final double REPULSION = 6000;
    private static final double ATRACCION = 0.015;
    private static final double DISTANCIA_IDEAL = 110;

    private Grafo grafo;
    private final Map<Vertice, Point> posiciones = new HashMap<>();
    private Vertice verticeSeleccionado;
    private Set<Vertice> solucionActual;
    private Color colorSolucion = Color.GREEN;

    public GrafoPanel(Grafo grafo) {
        this.grafo = grafo;
        setBackground(Color.WHITE);
        configurarMouse();
    }

    private void configurarMouse() {
        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                verticeSeleccionado = buscarVertice(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (verticeSeleccionado == null) {
                    return;
                }
                Point posicion = posiciones.get(verticeSeleccionado);
                if (posicion != null) {
                    posicion.setLocation(e.getX() - RADIO_VERTICE, e.getY() - RADIO_VERTICE);
                    limitarPosicion(posicion);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                verticeSeleccionado = null;
            }
        };

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    public void setGrafo(Grafo nuevoGrafo) {
        this.grafo = nuevoGrafo;
        posiciones.clear();
        verticeSeleccionado = null;
        limpiarSolucion();
    }

    public void reorganizarGrafo() {
        posiciones.clear();
        distribuirEnCirculo();

        if (grafo.cantidadVertices() > 1) {
            aplicarFuerzas();
        }
        repaint();
    }

    private void distribuirEnCirculo() {
        int cantidad = grafo.cantidadVertices();
        int indice = 0;
        int centroX = Math.max(100, getWidth() / 2);
        int centroY = Math.max(100, getHeight() / 2);
        int radio = Math.max(80, Math.min(getWidth(), getHeight()) / 3);

        for (Vertice vertice : grafo.obtenerVertices()) {
            double angulo = 2 * Math.PI * indice / Math.max(1, cantidad);
            int x = centroX + (int) (radio * Math.cos(angulo)) - RADIO_VERTICE;
            int y = centroY + (int) (radio * Math.sin(angulo)) - RADIO_VERTICE;
            posiciones.put(vertice, new Point(x, y));
            indice++;
        }
    }

    private void aplicarFuerzas() {
        for (int iteracion = 0; iteracion < ITERACIONES; iteracion++) {
            Map<Vertice, Point> movimientos = calcularMovimientos();
            aplicarMovimientos(movimientos);
        }
    }

    private Map<Vertice, Point> calcularMovimientos() {
        Map<Vertice, Point> movimientos = new HashMap<>();

        for (Vertice vertice : grafo.obtenerVertices()) {
            Point posicion = posiciones.get(vertice);
            double fuerzaX = 0;
            double fuerzaY = 0;

            for (Vertice otro : grafo.obtenerVertices()) {
                if (vertice.equals(otro)) {
                    continue;
                }

                Point posicionOtro = posiciones.get(otro);
                double dx = posicion.x - posicionOtro.x;
                double dy = posicion.y - posicionOtro.y;
                double distancia = Math.max(1, Math.sqrt(dx * dx + dy * dy));
                double fuerza = REPULSION / (distancia * distancia);
                fuerzaX += (dx / distancia) * fuerza;
                fuerzaY += (dy / distancia) * fuerza;
            }

            for (Vertice vecino : grafo.obtenerVecinos(vertice)) {
                Point posicionVecino = posiciones.get(vecino);
                double dx = posicionVecino.x - posicion.x;
                double dy = posicionVecino.y - posicion.y;
                double distancia = Math.max(1, Math.sqrt(dx * dx + dy * dy));
                double fuerza = ATRACCION * (distancia - DISTANCIA_IDEAL);
                fuerzaX += (dx / distancia) * fuerza;
                fuerzaY += (dy / distancia) * fuerza;
            }

            int moverX = (int) Math.round(Math.max(-10, Math.min(10, fuerzaX)));
            int moverY = (int) Math.round(Math.max(-10, Math.min(10, fuerzaY)));
            movimientos.put(vertice, new Point(moverX, moverY));
        }
        return movimientos;
    }

    private void aplicarMovimientos(Map<Vertice, Point> movimientos) {
        for (Map.Entry<Vertice, Point> entrada : movimientos.entrySet()) {
            Point posicion = posiciones.get(entrada.getKey());
            Point movimiento = entrada.getValue();
            posicion.translate(movimiento.x, movimiento.y);
            limitarPosicion(posicion);
        }
    }

    private void limitarPosicion(Point posicion) {
        int maxX = Math.max(MARGEN, getWidth() - DIAMETRO_VERTICE - MARGEN);
        int maxY = Math.max(MARGEN, getHeight() - DIAMETRO_VERTICE - MARGEN);
        posicion.x = Math.max(MARGEN, Math.min(posicion.x, maxX));
        posicion.y = Math.max(MARGEN, Math.min(posicion.y, maxY));
    }

    public void mostrarSolucion(Set<Vertice> solucion, Color color) {
        solucionActual = solucion == null ? null : new HashSet<>(solucion);
        colorSolucion = color;
        repaint();
    }

    public void limpiarSolucion() {
        solucionActual = null;
        repaint();
    }

    public void actualizarPosiciones() {
        for (Vertice vertice : grafo.obtenerVertices()) {
            if (!posiciones.containsKey(vertice)) {
                int cantidad = posiciones.size();
                int columna = cantidad % 5;
                int fila = cantidad / 5;
                int x = 70 + columna * 80;
                int y = 90 + fila * 80;
                posiciones.put(vertice, new Point(x, y));
            }
        }
    }

    private Vertice buscarVertice(int x, int y) {
        for (Vertice vertice : grafo.obtenerVertices()) {
            Point posicion = posiciones.get(vertice);
            if (posicion != null) {
                int centroX = posicion.x + RADIO_VERTICE;
                int centroY = posicion.y + RADIO_VERTICE;
                if (Point.distance(x, y, centroX, centroY) <= RADIO_VERTICE) {
                    return vertice;
                }
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        actualizarPosiciones();
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            dibujarAristas(g2);
            dibujarVertices(g2);
        } finally {
            g2.dispose();
        }
    }

    private void dibujarAristas(Graphics2D g2) {
        g2.setColor(Color.GRAY);
        for (Vertice origen : grafo.obtenerVertices()) {
            Point p1 = posiciones.get(origen);
            for (Vertice destino : grafo.obtenerVecinos(origen)) {
                Point p2 = posiciones.get(destino);
                if (p1 != null && p2 != null) {
                    g2.drawLine(p1.x + RADIO_VERTICE, p1.y + RADIO_VERTICE, p2.x + RADIO_VERTICE, p2.y + RADIO_VERTICE);
                }
            }
        }
    }

    private void dibujarVertices(Graphics2D g2) {
        for (Vertice vertice : grafo.obtenerVertices()) {
            Point posicion = posiciones.get(vertice);
            if (posicion == null) {
                continue;
            }

            int x = posicion.x;
            int y = posicion.y;
            if (solucionActual != null && solucionActual.contains(vertice)) {
                g2.setColor(colorSolucion);
            } else {
                g2.setColor(new Color(173, 216, 230));
            }

            g2.fillOval(x, y, DIAMETRO_VERTICE, DIAMETRO_VERTICE);
            g2.setColor(Color.BLUE);
            g2.drawOval(x, y, DIAMETRO_VERTICE, DIAMETRO_VERTICE);
            g2.setColor(Color.BLACK);
            g2.drawString(vertice.getNombre(), x + 15, y + 25);
        }
    }

}
