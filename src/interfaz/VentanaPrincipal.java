package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import algoritmo.DominanteBacktracking;
import algoritmo.DominanteGoloso;
import archivo.LectorGrafo;
import negocio.Grafo;
import negocio.Vertice;
import comparacion.CompararAlgoritmos;
import comparacion.ResultadoAlgoritmo;
import comparacion.TestEstres;



public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private Grafo grafo = new Grafo();
	private GrafoPanel grafoPanel;
	private final CompararAlgoritmos comparador = new CompararAlgoritmos();

	private JTextField txtVertice;
	private JComboBox<String> comboOrigen;
	private JComboBox<String> comboDestino;
	private DefaultListModel<String> modeloVertices;
	private DefaultListModel<String> modeloAristas;
	private JList<String> listaVertices;
	private JList<String> listaAristas;

	private JButton btnCargarGrafo;
	private JButton btnAgregarVertice;
	private JButton btnEliminarVertice;
	private JButton btnAgregarArista;
	private JButton btnEliminarArista;
	private JButton btnBacktracking;
	private JButton btnGoloso;

	private JLabel lblSolucionBacktracking;
	private JLabel lblTamBacktracking;
	private JLabel lblTiempoBacktracking;
	private JLabel lblSolucionGoloso;
	private JLabel lblTamGoloso;
	private JLabel lblTiempoGoloso;
	
	private JButton btnGenetico;
	private JLabel lblSolucionGenetico;
	private JLabel lblTamGenetico;
	private JLabel lblTiempoGenetico;
	
	private JButton btnIniciarEstres;
	private JButton btnDetenerEstres;
	private JTextArea areaResultadosEstres;
	
	private TestEstres testEstres;
	private SwingWorker<Void, String> trabajadorEstres;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal() {
		setTitle("Conjunto Dominante Mínimo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);

		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.add(crearPanelControles(), BorderLayout.WEST);

		grafoPanel = new GrafoPanel(grafo);
		grafoPanel.setAlHacerClickEnEspacioVacio(this::agregarVerticeEnPosicion);
		contentPane.add(grafoPanel, BorderLayout.CENTER);

		JScrollPane scrollResultados = new JScrollPane(crearPanelResultados());

		scrollResultados.setPreferredSize(new Dimension(280, 0));

		contentPane.add(scrollResultados, BorderLayout.EAST);
		configurarEventos();
	}

	private JPanel crearPanelControles() {
		JPanel panel = new JPanel(new GridLayout(0, 1, 5, 8));
		panel.setPreferredSize(new Dimension(230, 0));
		panel.setBackground(new Color(220, 235, 250));

		btnCargarGrafo = new JButton("Cargar grafo desde TXT");
		panel.add(btnCargarGrafo);

		panel.add(new JLabel("Nombre del vértice:"));
		txtVertice = new JTextField();
		panel.add(txtVertice);

		btnAgregarVertice = new JButton("Agregar vértice");
		panel.add(btnAgregarVertice);

		panel.add(new JLabel("Vértices agregados:"));
		modeloVertices = new DefaultListModel<>();
		listaVertices = new JList<>(modeloVertices);
		panel.add(new JScrollPane(listaVertices));

		btnEliminarVertice = new JButton("Eliminar vértice");
		panel.add(btnEliminarVertice);

		panel.add(new JLabel("Agregar una arista:"));
		panel.add(new JLabel("Vértice origen:"));
		comboOrigen = new JComboBox<>();
		panel.add(comboOrigen);

		panel.add(new JLabel("Vértice destino:"));
		comboDestino = new JComboBox<>();
		panel.add(comboDestino);

		btnAgregarArista = new JButton("Agregar arista");
		panel.add(btnAgregarArista);

		panel.add(new JLabel("Aristas agregadas:"));
		modeloAristas = new DefaultListModel<>();
		listaAristas = new JList<>(modeloAristas);
		panel.add(new JScrollPane(listaAristas));

		btnEliminarArista = new JButton("Eliminar arista");
		panel.add(btnEliminarArista);

		return panel;
	}

	private JPanel crearPanelResultados() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(250, 0));
		panel.setBackground(new Color(235, 225, 245));

		panel.add(new JLabel("RESULTADOS"));

		btnBacktracking = new JButton("Ejecutar Backtracking");
		panel.add(btnBacktracking);
		lblSolucionBacktracking = new JLabel("Solución: -");
		lblTamBacktracking = new JLabel("Tamaño: -");
		lblTiempoBacktracking = new JLabel("Tiempo: -");
		panel.add(lblSolucionBacktracking);
		panel.add(lblTamBacktracking);
		panel.add(lblTiempoBacktracking);

		btnGoloso = new JButton("Ejecutar Goloso");
		panel.add(btnGoloso);
		lblSolucionGoloso = new JLabel("Solución: -");
		lblTamGoloso = new JLabel("Tamaño: -");
		lblTiempoGoloso = new JLabel("Tiempo: -");
		panel.add(lblSolucionGoloso);
		panel.add(lblTamGoloso);
		panel.add(lblTiempoGoloso);
		
		btnGenetico = new JButton("Ejecutar Genético");
		panel.add(btnGenetico);
		lblSolucionGenetico = new JLabel("Solución: -");
		lblTamGenetico = new JLabel("Tamaño: -");
		lblTiempoGenetico = new JLabel("Tiempo: -");
		panel.add(lblSolucionGenetico);
		panel.add(lblTamGenetico);
		panel.add(lblTiempoGenetico);
		panel.add(new JLabel("TEST DE ESTRES"));

		btnIniciarEstres = new JButton("Iniciar test de estres");
		panel.add(btnIniciarEstres);

		btnDetenerEstres = new JButton("Detener test");
		btnDetenerEstres.setEnabled(false);
		panel.add(btnDetenerEstres);

		areaResultadosEstres = new JTextArea();
		areaResultadosEstres.setEditable(false);

		JScrollPane scrollEstres = new JScrollPane(areaResultadosEstres);
		panel.add(scrollEstres);
		return panel;
	}

	private void configurarEventos() {
		btnCargarGrafo.addActionListener(e -> cargarGrafo());
		btnAgregarVertice.addActionListener(e -> agregarVertice());
		btnEliminarVertice.addActionListener(e -> eliminarVertice());
		btnAgregarArista.addActionListener(e -> agregarArista());
		btnEliminarArista.addActionListener(e -> eliminarArista());
		btnBacktracking.addActionListener(e -> ejecutarBacktracking());
		btnGoloso.addActionListener(e -> ejecutarGoloso());
		btnGenetico.addActionListener(e -> ejecutarGenetico());
		btnIniciarEstres.addActionListener(e -> iniciarTestEstres());
		btnDetenerEstres.addActionListener(e -> detenerTestEstres());
	}

	private void cargarGrafo() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccionar archivo de grafo");
		selector.setFileFilter(new FileNameExtensionFilter("Archivos TXT", "txt"));

		if (selector.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File archivoSeleccionado = selector.getSelectedFile();
		try {
			LectorGrafo lector = new LectorGrafo();
			Grafo nuevoGrafo = lector.cargar(archivoSeleccionado);

			grafo = nuevoGrafo;
			grafoPanel.setGrafo(grafo);
			actualizarListas();
			actualizarVistaGrafo();
			mostrarMensaje("Grafo cargado correctamente.");
		} catch (IOException | IllegalArgumentException ex) {
			mostrarError("Error al cargar el archivo: " + ex.getMessage());
		}
	}

	private void agregarVertice() {
		String nombre = txtVertice.getText().trim().toUpperCase();
		try {
			Vertice vertice = new Vertice(nombre);
			if (!grafo.agregarVertice(vertice)) {
				mostrarMensaje("El vértice " + nombre + " ya existe.");
				return;
			}

			modeloVertices.addElement(vertice.getNombre());
			comboOrigen.addItem(vertice.getNombre());
			comboDestino.addItem(vertice.getNombre());
			txtVertice.setText("");
			actualizarVistaGrafo();
			mostrarMensaje("Vértice agregado: " + vertice.getNombre());
		} catch (IllegalArgumentException ex) {
			mostrarError(ex.getMessage());
		}
	}

	/**
	 * Se ejecuta al hacer click en un espacio vacio del grafo: pide el nombre y
	 * crea el vertice justo en el punto donde se hizo click.
	 */
	private void agregarVerticeEnPosicion(Point centro) {
		while (true) {
			String ingresado = JOptionPane.showInputDialog(this, "Nombre del nuevo vértice:", "Nuevo vértice",
					JOptionPane.QUESTION_MESSAGE);
			if (ingresado == null) {
				return; // el usuario canceló
			}

			String nombre = ingresado.trim().toUpperCase();
			if (nombre.isEmpty()) {
				mostrarError("El nombre no puede estar vacío.");
				continue;
			}

			Vertice vertice = new Vertice(nombre);
			if (!grafo.agregarVertice(vertice)) {
				mostrarMensaje("El vértice " + nombre + " ya existe.");
				continue;
			}

			modeloVertices.addElement(nombre);
			comboOrigen.addItem(nombre);
			comboDestino.addItem(nombre);

			// El grafo cambió, así que los resultados anteriores ya no valen.
			// No se llama a actualizarVistaGrafo() para no reubicar todos los vértices.
			limpiarResultados();
			grafoPanel.limpiarSolucion();
			grafoPanel.colocarVertice(vertice, centro);
			return;
		}
	}

	private void eliminarVertice() {
		String nombre = listaVertices.getSelectedValue();
		if (nombre == null) {
			mostrarMensaje("Seleccioná un vértice para eliminar.");
			return;
		}

		int respuesta = JOptionPane.showConfirmDialog(this, "¿Querés eliminar el vértice " + nombre + "?",
				"Confirmar eliminación", JOptionPane.YES_NO_OPTION);
		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}

		if (grafo.eliminarVertice(new Vertice(nombre))) {
			actualizarListas();
			actualizarVistaGrafo();
			mostrarMensaje("Vértice eliminado: " + nombre);
		}
	}

	private void agregarArista() {
		String origen = (String) comboOrigen.getSelectedItem();
		String destino = (String) comboDestino.getSelectedItem();
		if (origen == null || destino == null) {
			mostrarMensaje("Primero tenés que agregar vértices.");
			return;
		}

		try {
			boolean agregada = grafo.agregarArista(new Vertice(origen), new Vertice(destino));
			if (!agregada) {
				JOptionPane.showMessageDialog(this,
						"No se pudo agregar la arista. Puede estar repetida o ser un bucle.", "Arista inválida",
						JOptionPane.WARNING_MESSAGE);
				return;
			}

			actualizarListas();
			actualizarVistaGrafo();
			mostrarMensaje("Arista agregada: " + origen + " - " + destino);
		} catch (IllegalArgumentException ex) {
			mostrarError(ex.getMessage());
		}
	}

	private void eliminarArista() {
		String aristaSeleccionada = listaAristas.getSelectedValue();
		if (aristaSeleccionada == null) {
			mostrarMensaje("Seleccioná una arista para eliminar.");
			return;
		}

		int respuesta = JOptionPane.showConfirmDialog(this, "¿Querés eliminar la arista " + aristaSeleccionada + "?",
				"Confirmar eliminación", JOptionPane.YES_NO_OPTION);
		if (respuesta != JOptionPane.YES_OPTION) {
			return;
		}

		String[] extremos = aristaSeleccionada.split(" - ", 2);
		if (extremos.length != 2) {
			mostrarError("No se pudo identificar la arista seleccionada.");
			return;
		}

		if (grafo.eliminarArista(new Vertice(extremos[0]), new Vertice(extremos[1]))) {
			actualizarListas();
			actualizarVistaGrafo();
			mostrarMensaje("Arista eliminada: " + aristaSeleccionada);
		}
	}

	private void actualizarListas() {
		modeloVertices.clear();
		modeloAristas.clear();
		comboOrigen.removeAllItems();
		comboDestino.removeAllItems();

		List<String> nombres = new ArrayList<>();
		for (Vertice v : grafo.obtenerVertices()) {
			nombres.add(v.getNombre());
		}
		Collections.sort(nombres);
		for (String nombre : nombres) {
			modeloVertices.addElement(nombre);
			comboOrigen.addItem(nombre);
			comboDestino.addItem(nombre);
		}

		for (String nombreOrigen : nombres) {
			Vertice origen = new Vertice(nombreOrigen);
			List<String> vecinos = new ArrayList<>();

			for (Vertice vecino : grafo.obtenerVecinos(origen)) {
				vecinos.add(vecino.getNombre());
			}
			Collections.sort(vecinos);
			for (String nombreDestino : vecinos) {
				if (nombreOrigen.compareTo(nombreDestino) < 0) {
					modeloAristas.addElement(nombreOrigen + " - " + nombreDestino);
				}
			}
		}
	}

	private void actualizarVistaGrafo() {
		limpiarResultados();
		grafoPanel.limpiarSolucion();
		grafoPanel.reorganizarGrafo();
	}

	private void ejecutarBacktracking() {
		if (grafo.cantidadVertices() == 0) {
			mostrarMensaje("Primero tenés que agregar vértices.");
			return;
		}

		ResultadoAlgoritmo resultado = comparador.medirBacktracking(grafo);

		Set<Vertice> solucion = resultado.getSolucion();

		double tiempoMs = resultado.getTiempoNanosegundos() / 1_000_000.0;

		mostrarResultado(solucion, tiempoMs, lblSolucionBacktracking, lblTamBacktracking, lblTiempoBacktracking,Color.YELLOW);
	}

	private void ejecutarGoloso() {
		if (grafo.cantidadVertices() == 0) {
			mostrarMensaje("Primero tenés que agregar vértices.");
			return;
		}

		ResultadoAlgoritmo resultado = comparador.medirGoloso(grafo);

		Set<Vertice> solucion = resultado.getSolucion();

		double tiempoMs = resultado.getTiempoNanosegundos() / 1_000_000.0;	mostrarResultado(solucion, tiempoMs, lblSolucionGoloso, lblTamGoloso, lblTiempoGoloso, Color.GREEN);
	}
	
	private void ejecutarGenetico() {
		if (grafo.cantidadVertices() == 0) {
			mostrarMensaje("Primero tenés que agregar vértices.");
			return;
		}

		ResultadoAlgoritmo resultado = comparador.medirGenetico(grafo);

		Set<Vertice> solucion = resultado.getSolucion();

		double tiempoMs = resultado.getTiempoNanosegundos() / 1_000_000.0;
       
		mostrarResultado(solucion, tiempoMs, lblSolucionGenetico, lblTamGenetico, lblTiempoGenetico, Color.ORANGE);
	}
	private void iniciarTestEstres() {

	    TestEstres prueba = new TestEstres();
	    testEstres = prueba;

	    areaResultadosEstres.setText("Iniciando test de estres...\n");

	    btnIniciarEstres.setEnabled(false);
	    btnDetenerEstres.setEnabled(true);

	    trabajadorEstres = new SwingWorker<Void, String>() {

	        @Override
	        protected Void doInBackground() {

	            prueba.ejecutar(5, 100, (vertices, resultado) -> {

	                double tiempoMs =
	                        resultado.getTiempoNanosegundos() / 1_000_000.0;

	                String texto = String.format(
	                        "Vertices: %d - Tiempo: %.3f ms",
	                        vertices, tiempoMs);

	                publish(texto);
	            });

	            return null;
	        }

	        @Override
	        protected void process(List<String> resultados) {

	            for (String resultado : resultados) {
	                areaResultadosEstres.append(resultado + "\n");
	            }
	        }

	        @Override
	        protected void done() {

	            if (prueba.estaDetenido()) {
	                areaResultadosEstres.append("TEST DETENIDO POR EL USUARIO\n");
	            } else {
	                areaResultadosEstres.append( "TEST FINALIZADO\n");
	            }

	            btnIniciarEstres.setEnabled(true);
	            btnDetenerEstres.setEnabled(false);
	        }
	    };

	    trabajadorEstres.execute();
	}
	private void detenerTestEstres() {

	    if (testEstres != null && !testEstres.estaDetenido()) {

	        testEstres.detener();

	        areaResultadosEstres.append( "Solicitando detener el test...\n");

	        btnDetenerEstres.setEnabled(false);
	    }
	}
	private void mostrarResultado(Set<Vertice> solucion, double tiempoMs, JLabel lblSolucion, JLabel lblTam,
			JLabel lblTiempo, Color color) {
		lblSolucion.setText("Solución: " + formatearSolucion(solucion));
		lblTam.setText("Tamaño: " + solucion.size());
		lblTiempo.setText(String.format("Tiempo: %.3f ms", tiempoMs));
		grafoPanel.mostrarSolucion(solucion, color);
	}

	private String formatearSolucion(Set<Vertice> solucion) {
		List<String> nombres = new ArrayList<>();
		for (Vertice vertice : solucion) {
			nombres.add(vertice.getNombre());
		}
		Collections.sort(nombres);
		return "{" + String.join(", ", nombres) + "}";
	}

	private void limpiarResultados() {
		lblSolucionBacktracking.setText("Solución: -");
		lblTamBacktracking.setText("Tamaño: -");
		lblTiempoBacktracking.setText("Tiempo: -");
		
        lblSolucionGoloso.setText("Solución: -");
		lblTamGoloso.setText("Tamaño: -");
		lblTiempoGoloso.setText("Tiempo: -");
        
        
        //if(lblSolucionGenetico != null) {
            lblSolucionGenetico.setText("Solución: -");
            lblTamGenetico.setText("Tamaño: -");
            lblTiempoGenetico.setText("Tiempo: -");
       // }
	}
	private void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje);
	}

	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

}