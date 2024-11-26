package Vistas;

import javax.swing.*;
import Modelo.Producto;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class VistaModificarCantidad extends JDialog {
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JButton btnAumentar;
    private JButton btnDisminuir;
    private JButton btnAplicar;
    private Producto producto;

    public VistaModificarCantidad(Frame owner, String title, Producto producto) {
        super(owner, title, true);
        this.producto = producto;

        setLayout(new GridLayout(7, 1));
        setSize(300, 200);

        txtNombre = new JTextField(producto.getNombre());
        txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
        txtCantidad = new JTextField(String.valueOf(producto.getCantidad()));

        txtNombre.setEditable(false);
        txtPrecio.setEditable(false);
        txtCantidad.setEditable(true);

        btnAumentar = new JButton("Aumentar");
        btnDisminuir = new JButton("Disminuir");
        btnAplicar = new JButton("Aplicar");

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Precio:"));
        add(txtPrecio);
        add(new JLabel("Cantidad:"));
        add(txtCantidad);
        add(btnAumentar);
        add(btnDisminuir);
        add(btnAplicar);

        btnAumentar.addActionListener(_ -> modificarCantidad(true));
        btnDisminuir.addActionListener(_ -> modificarCantidad(false));
        btnAplicar.addActionListener(_ -> aplicarCambio());

        setLocationRelativeTo(owner);
    }

    private void modificarCantidad(boolean aumentar) {
        int cantidadActual = Integer.parseInt(txtCantidad.getText());
        cantidadActual += aumentar ? 1 : -1;
        txtCantidad.setText(String.valueOf(cantidadActual));
    }

    private void aplicarCambio() {
        int cantidadModificada = Integer.parseInt(txtCantidad.getText());
        producto.setCantidad(cantidadModificada);
        escribirLog("Modificación de cantidad aplicada: " + cantidadModificada);
        dispose();
    }

    private void escribirLog(String mensaje) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transacciones.log", true))) {
            String logMessage = LocalDate.now() + " - " + mensaje + " - ID " + producto.getId();
            writer.write(logMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al escribir en el log.");
        }
    }
}
