package Vistas;

import javax.swing.*;
import Modelo.Producto;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VistaModificarCantidad extends JDialog {
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JButton btnAumentar;
    private JButton btnDisminuir;
    private JButton btnAplicar; // Botón para aplicar la cantidad manualmente
    private Producto producto;

    public VistaModificarCantidad(Frame owner, String title, Producto producto) {
        super(owner, title, true);
        this.producto = producto;

        setLayout(new GridLayout(7, 1)); // Aumentamos las filas para el nuevo botón
        setSize(300, 200);

        txtNombre = new JTextField(producto.getNombre());
        txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
        txtCantidad = new JTextField(String.valueOf(producto.getCantidad()));

        txtNombre.setEditable(false);
        txtPrecio.setEditable(false);
        txtCantidad.setEditable(true); // Permitir editar la cantidad

        btnAumentar = new JButton("Aumentar");
        btnDisminuir = new JButton("Disminuir");
        btnAplicar = new JButton("Aplicar"); // Botón para aplicar cambios manualmente

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Precio:"));
        add(txtPrecio);
        add(new JLabel("Cantidad:"));
        add(txtCantidad);
        add(btnAumentar);
        add(btnDisminuir);
        add(btnAplicar); // Agregar el botón para aplicar cambios

        btnAumentar.addActionListener(e -> modificarCantidad(1)); // Aumentar cantidad
        btnDisminuir.addActionListener(e -> modificarCantidad(-1)); // Disminuir cantidad
        btnAplicar.addActionListener(e -> aplicarCantidad()); // Aplicar cantidad manualmente
    }

    private void modificarCantidad(int cantidad) {
        // Solo modifica la cantidad en la interfaz sin guardar en el log
        int cantidadActual = Integer.parseInt(txtCantidad.getText());
        int nuevaCantidad = cantidadActual + cantidad;
        
        if (nuevaCantidad >= 0) {
            txtCantidad.setText(String.valueOf(nuevaCantidad));
        } else {
            JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.");
        }
    }

    private void aplicarCantidad() {
        String motivo = JOptionPane.showInputDialog(this, "Ingresa el motivo de la modificación:");
        
        if (motivo != null && !motivo.trim().isEmpty()) {
            try {
                int cantidadActual = producto.getCantidad(); // Cantidad antes del cambio
                int nuevaCantidad = Integer.parseInt(txtCantidad.getText());
                int cambioCantidad = nuevaCantidad - cantidadActual; // Diferencia entre las cantidades

                if (nuevaCantidad >= 0) {
                    producto.setCantidad(nuevaCantidad); // Actualizar la cantidad en el producto
                    escribirLog("Cambio manualmente aplicado: " + producto.getNombre() + 
                                ", cambio: " + (cambioCantidad > 0 ? "+" : "") + cambioCantidad + 
                                ", cantidad final: " + nuevaCantidad + 
                                ". Motivo: " + motivo);
                    dispose(); // Cerrar la ventana al aplicar
                } else {
                    JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un número válido para la cantidad.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El motivo no puede estar vacío.");
        }
    }

    private void escribirLog(String mensaje) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transacciones.log", true))) {
            writer.write(mensaje + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al escribir en el log.");
        }
    }
}
