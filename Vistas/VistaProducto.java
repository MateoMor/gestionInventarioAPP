package Vistas;

import Modelo.Producto;
import Modelo.Proveedor; // Asegúrate de importar tu clase Proveedor

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VistaProducto extends JDialog {
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JTextField txtFechaVencimiento;
    private JComboBox<Proveedor> cbProveedor; // Cambiado a Proveedor
    private JTextField txtCategoria; // Cambiado a JTextField
    private Producto producto;

    public VistaProducto(Frame parent, String title, Producto producto, List<Proveedor> proveedores) {
        super(parent, title, true);
        this.producto = producto;

        setLayout(new GridLayout(7, 2));

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        add(txtPrecio);

        add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        add(txtCantidad);

        add(new JLabel("Fecha de Vencimiento (YYYY-MM-DD):"));
        txtFechaVencimiento = new JTextField();
        add(txtFechaVencimiento);

        add(new JLabel("Proveedor:"));
        cbProveedor = new JComboBox<>(proveedores.toArray(new Proveedor[0])); // Llenar el combo con proveedores
        add(cbProveedor);

        add(new JLabel("Categoría:"));
        txtCategoria = new JTextField(); // Campo de texto para categoría
        add(txtCategoria);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtCantidad.setText(String.valueOf(producto.getCantidad()));
            txtFechaVencimiento.setText(producto.getFechaVencimiento().toString()); // Suponiendo que el formato es YYYY-MM-DD
            cbProveedor.setSelectedItem(producto.getProveedor()); // Se selecciona el proveedor correspondiente
            txtCategoria.setText(producto.getCategoria()); // Se establece la categoría correspondiente
        }

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void guardarProducto() {
        String nombre = txtNombre.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        int cantidad = Integer.parseInt(txtCantidad.getText());
        LocalDate fechaVencimiento = LocalDate.parse(txtFechaVencimiento.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
        Proveedor proveedor = (Proveedor) cbProveedor.getSelectedItem(); // Cambiado a Proveedor
        String categoria = txtCategoria.getText(); // Obtener categoría desde el campo de texto

        if (producto == null) {
            producto = new Producto();
        }

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        producto.setFechaVencimiento(fechaVencimiento);
        producto.setProveedor(proveedor.getNombre());
        producto.setCategoria(categoria);

        dispose();
    }

    public Producto getProducto() {
        return producto;
    }
}
