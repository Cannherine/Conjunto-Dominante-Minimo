package interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicButtonUI;

public final class EstiloInterfaz {

	public static final Color FONDO = new Color(241, 245, 249);
	public static final Color TARJETA = Color.WHITE;
	public static final Color PRIMARIO = new Color(37, 99, 235);
	public static final Color PELIGRO = new Color(220, 38, 38);
	public static final Color TEXTO = new Color(30, 41, 59);
	public static final Color BORDE = new Color(203, 213, 225);
	public static final Color DESHABILITADO = new Color(148, 163, 184);
	public static final Color LIENZO = Color.WHITE;
	public static final Color PUNTOS_LIENZO = new Color(226, 232, 240);
	public static final Color ARISTA = new Color(148, 163, 184);
	public static final Color VERTICE_RELLENO = new Color(219, 234, 254);
	public static final Color VERTICE_BORDE = new Color(37, 99, 235);
	public static final Color SELECCION = new Color(239, 68, 68);
	public static final Color BACKTRACKING = new Color(217, 119, 6);
	public static final Color GOLOSO = new Color(5, 150, 105);
	public static final Color GENETICO = new Color(124, 58, 237);
	public static final Font FUENTE = new Font("Segoe UI Symbol", Font.PLAIN, 13);
	public static final Font FUENTE_NEGRITA = new Font("Segoe UI Symbol", Font.BOLD, 13);
	public static final Font FUENTE_VERTICE = new Font("Segoe UI Symbol", Font.BOLD, 13);
	private static final int SEPARACION = 6;

	private EstiloInterfaz() {
	}

	public static void aplicarGlobal() {
		String[] componentes = { "Label", "Button", "ToggleButton", "TextField", "TextArea", "List", "ComboBox" };
		for (String componente : componentes) {
			UIManager.put(componente + ".font", new FontUIResource(FUENTE));
		}
	}

	public static JButton crearBoton(String texto, Color fondo) {
		JButton boton = new JButton(texto) {
			private static final long serialVersionUID = 1L;

			@Override
			public void setEnabled(boolean habilitado) {
				super.setEnabled(habilitado);
				setBackground(habilitado ? fondo : DESHABILITADO);
			}
		};
		boton.setUI(new BasicButtonUI());
		boton.setBackground(fondo);
		boton.setForeground(Color.WHITE);
		boton.setFont(FUENTE_NEGRITA);
		boton.setFocusPainted(false);
		boton.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));
		boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return boton;
	}

	public static JPanel crearSeccion(String titulo) {
		JPanel seccion = new JPanel();
		seccion.setLayout(new BoxLayout(seccion, BoxLayout.Y_AXIS));
		seccion.setBackground(TARJETA);

		TitledBorder borde = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(BORDE), titulo);
		borde.setTitleFont(FUENTE_NEGRITA);
		borde.setTitleColor(PRIMARIO);
		seccion.setBorder(BorderFactory.createCompoundBorder(borde, BorderFactory.createEmptyBorder(2, 6, 6, 6)));
		seccion.setAlignmentX(Component.LEFT_ALIGNMENT);
		return seccion;
	}

	public static void agregarFila(JPanel seccion, JComponent componente) {
		componente.setAlignmentX(Component.LEFT_ALIGNMENT);
		componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, componente.getPreferredSize().height));
		seccion.add(componente);
		seccion.add(Box.createVerticalStrut(SEPARACION));
	}
}