package Vistas;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;

import Modelo.Producto;
import Modelo.ProductoDuradero;
import Modelo.ProductoPerecedero;

public class VistaProducto extends JDialog {

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private Producto producto;
    private JTextField txtDuracionEnMeses;
    private JLabel lblFecha;
    private JLabel lblDiasParaCaducar;
    private JTextField txtFecha;
    private JComboBox<String> comboTipo;

    public VistaProducto(Frame owner, String title, Producto producto) {

        super(owner, title, true);
        this.producto = producto;
        setLayout(new GridLayout(7, 1));
        setSize(450, 350);

        txtNombre = new JTextField(producto != null ? producto.getNombre() : "");
        txtPrecio = new JTextField(producto != null ? String.valueOf(producto.getPrecio()) : "");
        txtCantidad = new JTextField(producto != null ? String.valueOf(producto.getCantidad()) : "");

        // Verificación de tipo para producto
        if (producto instanceof ProductoDuradero) {
            txtDuracionEnMeses = new JTextField(String.valueOf(((ProductoDuradero) producto).getDuracionEnMeses()));
            txtFecha = new JTextField(String.valueOf(((ProductoDuradero) producto).getFechaDeFabricacion()));
        } else if (producto instanceof ProductoPerecedero) {
            txtDuracionEnMeses = new JTextField("");  // No aplica para ProductoPerecedero
            txtFecha = new JTextField(String.valueOf(((ProductoPerecedero) producto).getFechaDeVencimiento()));
        } else {
            txtDuracionEnMeses = new JTextField("");
            txtFecha = new JTextField("");
        }

        lblFecha = new JLabel();
        lblDiasParaCaducar = new JLabel("Días para caducar:");

        JPanel panelBotones = new JPanel();

        comboTipo = new JComboBox<>(new String[] { "No Perecedero", "Perecedero" });
        if (producto instanceof ProductoPerecedero) {
            comboTipo.setSelectedItem("Perecedero");
            lblFecha.setText("Fecha de Vencimiento");
        } else {
            comboTipo.setSelectedItem("No Perecedero");
            lblFecha.setText("Fecha de Fabricación");
        }

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Precio:"));
        add(txtPrecio);
        add(new JLabel("Cantidad:"));
        add(txtCantidad);
        add(new JLabel("Tipo:"));
        add(comboTipo);
        add(lblDiasParaCaducar);
        add(txtDuracionEnMeses);
        add(lblFecha);
        add(txtFecha);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            if (validarCampos()) {
                if (producto == null) {
                    // Crear nuevo producto
                    if (comboTipo.getSelectedItem().equals("Perecedero")) {
                        this.producto = new ProductoPerecedero(
                                txtNombre.getText(),
                                Double.parseDouble(txtPrecio.getText()),
                                Integer.parseInt(txtCantidad.getText()),
                                LocalDate.parse(txtFecha.getText()));
                    } else {
                        this.producto = new ProductoDuradero(
                                txtNombre.getText(),
                                Double.parseDouble(txtPrecio.getText()),
                                Integer.parseInt(txtCantidad.getText()),
                                Integer.parseInt(txtDuracionEnMeses.getText()),
                                LocalDate.parse(txtFecha.getText()));
                    }
                } else {
                    // Actualizar producto existente
                    producto.setNombre(txtNombre.getText());
                    producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
                    producto.setCantidad(Integer.parseInt(txtCantidad.getText()));

                    if (producto instanceof ProductoPerecedero) {
                        ((ProductoPerecedero) producto).setFechaDeVencimiento(LocalDate.parse(txtFecha.getText()));
                    } else if (producto instanceof ProductoDuradero) {
                        ((ProductoDuradero) producto).setDuracionEnMeses(Integer.parseInt(txtDuracionEnMeses.getText()));
                        ((ProductoDuradero) producto).setFechaDeFabricacion(LocalDate.parse(txtFecha.getText()));
                    }
                }
                dispose();
            }
        });
        panelBotones.add(btnAceptar);
        add(panelBotones, BorderLayout.CENTER);

        // Manejo de cambio en el comboBox
        comboTipo.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String selectedOption = (String) comboTipo.getSelectedItem();
                if ("Perecedero".equals(selectedOption)) {
                    lblFecha.setText("Fecha de Vencimiento");
                    lblDiasParaCaducar.setVisible(false);
                    txtDuracionEnMeses.setVisible(false);
                } else {
                    lblFecha.setText("Fecha de Fabricación");
                    lblDiasParaCaducar.setVisible(true);
                    txtDuracionEnMeses.setVisible(true);
                }
            }
        });
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
