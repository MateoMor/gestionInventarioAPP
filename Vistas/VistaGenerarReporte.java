package Vistas;

import Modelo.Producto;
import Servicios.IProductoServicio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class VistaGenerarReporte extends JFrame {

    private IProductoServicio productoServicio;

    public VistaGenerarReporte(IProductoServicio productoServicio) {
        this.productoServicio = productoServicio;

        setTitle("Generar Reporte de Inventario");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        JLabel labelProducto = new JLabel("Nombre del producto (dejar vacío para todos):");
        JTextField campoProducto = new JTextField(15);
        JButton botonGenerar = new JButton("Generar Reporte");

        // Panel para diseño
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(labelProducto);
        panel.add(campoProducto);
        panel.add(botonGenerar);

        add(panel);

        // Acción del botón
        botonGenerar.addActionListener((ActionEvent _) -> {
            String nombreProducto = campoProducto.getText().trim();
            generarReporte(nombreProducto.isEmpty() ? null : nombreProducto);
        });
    }

    private void generarReporte(String nombreProducto) {
        try {
            // Determinar archivo de salida
            String rutaArchivo = "reporte_inventario.csv";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
                // Encabezado del archivo CSV
                writer.write("Producto,Saldo,CostoPromedio,CostoTotal\n");

                // Obtener productos
                List<Producto> productos = nombreProducto == null
                        ? productoServicio.obtenerTodos()
                        : productoServicio.obtenerTodos().stream()
                            .filter(p -> p.getNombre().equalsIgnoreCase(nombreProducto))
                            .toList();

                if (productos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron productos con ese nombre.");
                    return;
                }

                // Escribir información de productos en el CSV
                for (Producto producto : productos) {
                    double saldo = producto.getCantidad();
                    double costoPromedio = producto.getPrecio(); // Se asume que el precio es el costo promedio
                    double costoTotal = saldo * costoPromedio;

                    writer.write(String.format("%s,%d,%.2f,%.2f\n",
                            producto.getNombre(),
                            (int) saldo,
                            costoPromedio,
                            costoTotal));
                }
            }

            JOptionPane.showMessageDialog(this, "Reporte generado exitosamente: " + "reporte_inventario.csv");

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al generar el reporte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
