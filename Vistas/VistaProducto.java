package Vistas;

import Modelo.Producto;
import Modelo.Proveedor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VistaProducto extends JDialog {
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JTextField txtFechaVencimiento;
    private JComboBox<String> cbProveedor; 
    private JTextField txtCategoria; // Campo de texto para la categoría
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

        // Convertir la lista de objetos Proveedor a una lista de nombres de proveedores
        String[] nombresProveedores = proveedores.stream()
        .map(Proveedor::getNombre) // Obtener solo el nombre de cada proveedor
        .toArray(String[]::new);


        add(new JLabel("Proveedor:"));
        // Agregar solo los nombres al ComboBox
        cbProveedor = new JComboBox<>(nombresProveedores);
        add(cbProveedor);

        add(new JLabel("Categoría:"));
        txtCategoria = new JTextField(); // Campo de texto para categoría
        add(txtCategoria);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener((ActionEvent _) -> guardarProducto());
        add(btnGuardar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(_ -> dispose());
        add(btnCancelar);

        if (producto != null) {
            txtNombre.setText(producto.getNombre());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtCantidad.setText(String.valueOf(producto.getCantidad()));
            txtFechaVencimiento.setText(producto.getFechaVencimiento().toString());
            cbProveedor.setSelectedItem(producto.getProveedor()); // Seleccionar el proveedor correspondiente
            txtCategoria.setText(producto.getCategoria());
        }

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void guardarProducto() {
        String nombre = txtNombre.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        int cantidad = Integer.parseInt(txtCantidad.getText());
        LocalDate fechaVencimiento = LocalDate.parse(txtFechaVencimiento.getText(), DateTimeFormatter.ISO_LOCAL_DATE);
        String proveedorNombre = (String) cbProveedor.getSelectedItem(); // Obtener proveedor seleccionado
        String categoria = txtCategoria.getText();

        if (producto == null) {
            producto = new Producto();
        }

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        producto.setFechaVencimiento(fechaVencimiento);
        producto.setProveedor(proveedorNombre); // Almacenar el nombre del proveedor
        producto.setCategoria(categoria);

        dispose();
    }

    public Producto getProducto() {
        return producto;
    }
}
