package ListaTareas;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Metodos extends JFrame {

    private static final Path ARCHIVO =
            Paths.get("tareas.txt");

    private final JPanel panelTareas = new JPanel();
    private final JTextField campoTarea = new JTextField(22);
    private final JLabel etiquetaProgreso = new JLabel();

    private final List<Tarea> tareas = new ArrayList<>();

    private LocalDate fechaActual = LocalDate.now();

    // Clase interna para representar cada tarea
    private static class Tarea {

        String nombre;
        boolean completada;

        Tarea(String nombre, boolean completada) {
            this.nombre = nombre;
            this.completada = completada;
        }
    }

    // Constructor: crea la ventana
    public Metodos() {

        setTitle("COSAS QUE HACER EN EL DÍA");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cargarTareas();

        JPanel principal = new JPanel(new BorderLayout(15, 15));

        principal.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25));

        principal.setBackground(new Color(245, 247, 250));

        // Título
        JLabel titulo = new JLabel(
                "COSAS QUE HACER EN EL DÍA",
                SwingConstants.CENTER);

        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setForeground(new Color(40, 55, 80));

        JLabel fecha = new JLabel(
                "Fecha: " + fechaActual,
                SwingConstants.CENTER);

        JPanel cabecera = new JPanel(new GridLayout(2, 1, 5, 5));

        cabecera.setOpaque(false);
        cabecera.add(titulo);
        cabecera.add(fecha);

        principal.add(cabecera, BorderLayout.NORTH);

        // Panel donde aparecen las tareas
        panelTareas.setLayout(
                new BoxLayout(panelTareas, BoxLayout.Y_AXIS));

        panelTareas.setBackground(Color.WHITE);

        JScrollPane desplazamiento = new JScrollPane(panelTareas);

        principal.add(desplazamiento, BorderLayout.CENTER);

        // Zona para añadir tareas
        JButton botonAnadir = new JButton("Añadir tarea");

        JButton botonEliminar = new JButton("Eliminar tareas terminadas");
        
        JButton botonEditar = new JButton("Editar tarea");

        JPanel entrada = new JPanel(new FlowLayout());

        entrada.setOpaque(false);
        entrada.add(campoTarea);
        entrada.add(botonAnadir);

        JPanel inferior = new JPanel(new BorderLayout(5, 5));

        inferior.setOpaque(false);

        inferior.add(entrada, BorderLayout.NORTH);
        inferior.add(etiquetaProgreso, BorderLayout.CENTER);

        // Panel para los botones inferiores
        JPanel panelBotones = new JPanel(new FlowLayout());

        panelBotones.setOpaque(false);

        panelBotones.add(botonEditar);
        panelBotones.add(botonEliminar);

        inferior.add(panelBotones, BorderLayout.SOUTH);

        principal.add(inferior, BorderLayout.SOUTH);

        setContentPane(principal);
       

        // Eventos de los botones
        botonAnadir.addActionListener(e -> anadirTarea());

        campoTarea.addActionListener(e -> anadirTarea());

        botonEliminar.addActionListener(e -> {
            tareas.removeIf(t -> t.completada);
            guardarTareas();
            actualizarInterfaz();
        });
        
        botonEditar.addActionListener(e -> editarTarea());

        actualizarInterfaz();
    }

    // Añadir una tarea
    private void anadirTarea() {

        String nombre = campoTarea.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Escribe una tarea antes de añadirla.");

            return;
        }

        tareas.add(new Tarea(nombre, false));

        campoTarea.setText("");

        guardarTareas();

        actualizarInterfaz();
    }
    
 // Editar una tarea existente
    private void editarTarea() {

        // Comprobar si hay tareas
        if (tareas.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No hay tareas para editar.");

            return;
        }

        // Crear las opciones con las tareas existentes
        String[] opciones = new String[tareas.size()];

        for (int i = 0; i < tareas.size(); i++) {

            opciones[i] = (i + 1) + ". " + tareas.get(i).nombre;
        }

        // Crear una lista desplegable para seleccionar la tarea
        JComboBox<String> selector = new JComboBox<>(opciones);

        int resultado = JOptionPane.showConfirmDialog(
                this,
                selector,
                "Seleccionar tarea para editar",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Si se pulsa Cancelar, no hacer nada
        if (resultado != JOptionPane.OK_OPTION) {
            return;
        }

        // Obtener la tarea seleccionada
        int indice = selector.getSelectedIndex();

        Tarea tareaSeleccionada = tareas.get(indice);

        // Pedir el nuevo nombre
        String nuevoNombre = JOptionPane.showInputDialog(
                this,
                "Introduce el nuevo nombre de la tarea:",
                tareaSeleccionada.nombre);

        // Si se pulsa Cancelar, no hacer nada
        if (nuevoNombre == null) {
            return;
        }

        nuevoNombre = nuevoNombre.trim();

        // Comprobar que el nombre no está vacío
        if (nuevoNombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre de la tarea no puede estar vacío.");

            return;
        }

        // Modificar la tarea
        tareaSeleccionada.nombre = nuevoNombre;

        // Guardar y actualizar
        guardarTareas();

        actualizarInterfaz();
    }

    // Mostrar las tareas en pantalla
    private void actualizarInterfaz() {

        panelTareas.removeAll();

        for (Tarea tarea : tareas) {

            JCheckBox casilla = new JCheckBox(tarea.nombre);

            casilla.setSelected(tarea.completada);

            casilla.setFont(
                    new Font("Arial", Font.PLAIN, 16));

            casilla.setAlignmentX(Component.LEFT_ALIGNMENT);

            casilla.setBackground(Color.WHITE);

            casilla.setBorder(
                    BorderFactory.createEmptyBorder(
                            10, 12, 10, 12));

            casilla.addActionListener(e -> {

                tarea.completada = casilla.isSelected();

                guardarTareas();

                actualizarInterfaz();

            });

            panelTareas.add(casilla);
        }

        // Calcular el progreso
        int completadas = 0;

        for (Tarea tarea : tareas) {

            if (tarea.completada) {
                completadas++;
            }
        }

        etiquetaProgreso.setText(
                "Progreso: " + completadas + " de "
                + tareas.size() + " tareas completadas");

        panelTareas.revalidate();

        panelTareas.repaint();
    }

    // Cargar las tareas guardadas
    private void cargarTareas() {

        if (!Files.exists(ARCHIVO)) {
            return;
        }

        try {

            List<String> lineas = Files.readAllLines(
                    ARCHIVO,
                    StandardCharsets.UTF_8);

            if (lineas.isEmpty()) {
                return;
            }

            fechaActual = LocalDate.parse(
                    lineas.get(0).substring("FECHA=".length()));

            for (int i = 1; i < lineas.size(); i++) {

                String[] partes =
                        lineas.get(i).split("\\t", 2);

                if (partes.length != 2) {
                    continue;
                }

                boolean completada =
                        Boolean.parseBoolean(partes[0]);

                String nombre = new String(
                        Base64.getDecoder().decode(partes[1]),
                        StandardCharsets.UTF_8);

                tareas.add(new Tarea(nombre, completada));
            }

            LocalDate hoy = LocalDate.now();

            if (fechaActual.isBefore(hoy)) {

                // Mantener únicamente las tareas pendientes
                tareas.removeIf(t -> t.completada);

                fechaActual = hoy;

                guardarTareas();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar las tareas: "
                    + e.getMessage());
        }
    }

    // Guardar las tareas en el archivo
    private void guardarTareas() {

        List<String> lineas = new ArrayList<>();

        lineas.add("FECHA=" + fechaActual);

        for (Tarea tarea : tareas) {

            String nombreCodificado =
                    Base64.getEncoder().encodeToString(
                            tarea.nombre.getBytes(
                                    StandardCharsets.UTF_8));

            lineas.add(
                    tarea.completada + "\t" + nombreCodificado);
        }

        try {

            Files.write(
                    ARCHIVO,
                    lineas,
                    StandardCharsets.UTF_8);

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al guardar las tareas: "
                    + e.getMessage());
        }
    }

}

