package Vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VistaReporteInventario extends JFrame {

    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;

    public VistaReporteInventario() {
        setTitle("Reporte de Inventario");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 400);
        setLocationRelativeTo(null);

        // Crear modelo de tabla
        modeloTabla = new DefaultTableModel(new String[]{"Producto", "Saldo", "Costo Por Unidad", "Costo Total"}, 0);
        tablaInventario = new JTable(modeloTabla);
        tablaInventario.setAutoCreateRowSorter(true);

        // Agregar tabla al panel principal
        add(new JScrollPane(tablaInventario), BorderLayout.CENTER);

        // Cargar datos automáticamente al abrir la vista
        cargarDatosDesdeCSV();
    }

    private void cargarDatosDesdeCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("reporte_inventario.csv"))) {
            String linea;
            reader.readLine(); // Leer y descartar la cabecera
            modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar datos
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) { // Verificar que haya 4 columnas
                    modeloTabla.addRow(new Object[]{
                        datos[0].trim(),
                        datos[1].trim(),
                        datos[2].trim(),
                        datos[3].trim()
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VistaReporteInventario vista = new VistaReporteInventario();
            vista.setVisible(true);
        });
    }
}
