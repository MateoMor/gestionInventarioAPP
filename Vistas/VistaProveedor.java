package Vistas;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import javax.swing.*;
import Modelo.Proveedor;

public class VistaProveedor extends JDialog {

    private JTextField txtNombre;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private Proveedor proveedor;

    public VistaProveedor(Frame owner, String title, Proveedor proveedor) {
        super(owner, title, true);
        this.proveedor = proveedor;
        setLayout(new GridLayout(5, 1));
        setSize(450, 350);

        txtNombre = new JTextField(proveedor != null ? proveedor.getNombre() : "");
        txtDireccion = new JTextField(proveedor != null ? proveedor.getDireccion() : ""); 
        txtTelefono = new JTextField(proveedor != null ? proveedor.getTelefono() : "");
        txtCorreo = new JTextField(proveedor != null ? proveedor.getCorreo() : "");

        JPanel panelBotones = new JPanel();

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(_ -> {
            // Comprobar si el proveedor es nulo y crear uno nuevo
            if (this.proveedor == null) {
                this.proveedor = new Proveedor(); // Asignar a la variable de instancia
            }
            // Asignar los valores de los campos de texto al proveedor
            /* this.proveedor.setId(); */
            this.proveedor.setNombre(txtNombre.getText());
            this.proveedor.setDireccion(txtDireccion.getText());
            this.proveedor.setTelefono(txtTelefono.getText());
            this.proveedor.setCorreo(txtCorreo.getText());
            dispose();
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(_ -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Dirección:"));
        add(txtDireccion);
        add(new JLabel("Teléfono:"));
        add(txtTelefono);
        add(new JLabel("Correo:"));
        add(txtCorreo);
        add(panelBotones, BorderLayout.SOUTH);

        setLocationRelativeTo(owner); 
    }
}
