package Vistas;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.*;
import java.time.LocalDate;
import Modelo.Producto;

public class VistaProducto extends JDialog {

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private Producto producto; // Variable de instancia
    private JLabel lblFecha;
    private JTextField txtFecha;


    public VistaProducto(Frame owner, String title, Producto producto) {
        super(owner, title, true);
        this.producto = producto;
        setLayout(new GridLayout(7, 1));
        setSize(450, 350);

        txtNombre = new JTextField(producto != null ? producto.getNombre() : "");
        txtPrecio = new JTextField(producto != null ? String.valueOf(producto.getPrecio()) : "");
        txtCantidad = new JTextField(producto != null ? String.valueOf(producto.getCantidad()) : "");
        txtFecha = new JTextField(producto != null ? producto.getFechaVencimiento().toString() : LocalDate.now().toString());

        lblFecha = new JLabel("Fecha de Vencimiento");

        JPanel panelBotones = new JPanel();

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Precio:"));
        add(txtPrecio);
        add(new JLabel("Cantidad:"));
        add(txtCantidad);
        add(lblFecha);
        add(txtFecha);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            if (validarCampos()) {
                Producto nuevoProducto = producto != null ? producto : new Producto(); // Crea una nueva variable
                nuevoProducto.setNombre(txtNombre.getText());
                nuevoProducto.setPrecio(Double.parseDouble(txtPrecio.getText()));
                nuevoProducto.setCantidad(Integer.parseInt(txtCantidad.getText()));
                nuevoProducto.setFechaVencimiento(LocalDate.parse(txtFecha.getText()));

                if (producto == null) {
                    this.producto = nuevoProducto; // Asigna el nuevo producto a la variable de instancia
                }

                dispose();
            }
        });

        panelBotones.add(btnAceptar);
        add(panelBotones, BorderLayout.CENTER);
    }

    private boolean validarCampos() {
        // Validaciones básicas
        try {
            Double.parseDouble(txtPrecio.getText());
            Integer.parseInt(txtCantidad.getText());
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio y cantidad deben ser numéricos.");
            return false;
        }
    }

    public Producto getProducto() {
        return producto;
    }
}
