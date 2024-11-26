package Vistas;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Modelo.Producto;
import Modelo.Proveedor;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import Repositorio.IProductoRepositorio;
import Repositorio.IProveedorRepositorio;
import Repositorio.ProveedorRepositorio;
import Repositorio.ProductoRepositorio;
import Servicios.IProductoServicio;
import Servicios.ProductoServicio;
import Servicios.ProveedorServicio;

public class GestionInventarioAPP extends JFrame {

    private JTable tablaDeProductos;
    private DefaultTableModel modeloDeTabla;
    private IProductoServicio productoServicio;
    private ProveedorServicio proveedorServicio;
    private JLabel lblValorInventario;

    public GestionInventarioAPP(Usuario usuarioLogueado) {
        IProductoRepositorio productoRepository = new ProductoRepositorio();
        IProveedorRepositorio proveedorRepository = new ProveedorRepositorio();
        productoServicio = new ProductoServicio(productoRepository);
        proveedorServicio = new ProveedorServicio(proveedorRepository);
    
        // Cargar proveedores desde el archivo CSV al abrir la aplicación
        proveedorServicio.cargarProveedoresDesdeCSV("proveedores.csv");
    
        // Configuración de la tabla para mostrar productos
        modeloDeTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Cantidad", "Fecha de Vencimiento", "Proveedor", "Categoría"}, 0);
        tablaDeProductos = new JTable(modeloDeTabla);
    
        // Configuración de ordenamiento
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloDeTabla);
        tablaDeProductos.setRowSorter(sorter);
    
        setTitle("Gestión de Inventario - Usuario: " + usuarioLogueado.getRol());
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 400);
    
        JPanel panelBotones = new JPanel();
        JButton btnProveedores = new JButton("Proveedores");
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLog = new JButton("Log");
        JButton btnModificarCantidad = new JButton("Modificar Cantidad");
    
        JPanel panelInventario = new JPanel();
        lblValorInventario = new JLabel("Valor de Inventario: $0.0");
        panelInventario.add(lblValorInventario);
        add(panelInventario, BorderLayout.NORTH);
    
        leerCSV();
        actualizarValor();
    
        panelBotones.add(btnModificarCantidad);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnProveedores);
        panelBotones.add(btnLog);
    
        add(new JScrollPane(tablaDeProductos), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    
        // Configuración de permisos según el rol
        configurarPermisos(usuarioLogueado, btnAgregar, btnEditar, btnEliminar, btnProveedores, btnLog, btnModificarCantidad);
    
        btnProveedores.addActionListener(_ -> mostrarProveedores());
        btnAgregar.addActionListener(_ -> agregarProducto());
        btnEditar.addActionListener(_ -> editarProducto());
        btnEliminar.addActionListener(_ -> eliminarProducto());
        btnLog.addActionListener(_ -> mostrarLog());
        btnModificarCantidad.addActionListener(_ -> modificarCantidadProducto());
    }

    private void configurarPermisos(Usuario usuario, JButton btnAgregar, JButton btnEditar, JButton btnEliminar, 
                                 JButton btnProveedores, JButton btnLog, JButton btnModificarCantidad) {
    String rol = usuario.getRol();

    switch (rol) {
        case "Administrador":
            // El administrador tiene acceso completo, no se necesita hacer nada
            break;
        case "Auxiliar":
            // Deshabilitar botones según los permisos para auxiliares
            btnAgregar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnProveedores.setEnabled(false);
            btnLog.setEnabled(true);
            btnModificarCantidad.setEnabled(true);
            break;
        default:
            // Si el rol no es reconocido, deshabilitar todo
            btnAgregar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnEliminar.setEnabled(false);
            btnProveedores.setEnabled(false);
            btnLog.setEnabled(false);
            btnModificarCantidad.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Rol no reconocido. Contacte al administrador.");
    }
}

    private void mostrarProveedores() {
        VistaTablaDeProveedores vistaProveedores = new VistaTablaDeProveedores();
        vistaProveedores.setVisible(true);
    }

    private void agregarProducto() {
        List<Producto> productosExistentes = productoServicio.obtenerTodos();
        int maxId = productosExistentes.stream().mapToInt(Producto::getId).max().orElse(0);

        VistaProducto dialog = new VistaProducto(this, "Agregar Producto", null, obtenerProveedores());
        dialog.setVisible(true);

        Producto producto = dialog.getProducto();

        if (producto != null) {
            producto.setId(maxId + 1);
            productoServicio.agregarProducto(producto);
            escribirLog("Producto agregado: ID " + producto.getId() + ", Nombre: " + producto.getNombre());
            actualizarTabla();
            actualizarCSV();
        }
    }

    private void editarProducto() {
        int selectedRow = tablaDeProductos.getSelectedRow();
        if (selectedRow != -1) {
            Producto producto = productoServicio.obtenerTodos().get(selectedRow);
            VistaProducto dialog = new VistaProducto(this, "Editar Producto", producto, obtenerProveedores());
            dialog.setVisible(true);
            if (dialog.getProducto() != null) {
                productoServicio.actualizarProducto(producto.getId(), dialog.getProducto());
                escribirLog("Producto editado: ID " + producto.getId() + ", Nombre: " + producto.getNombre());
                actualizarTabla();
                actualizarCSV();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para editar.");
        }
    }

    private void eliminarProducto() {
        int selectedRow = tablaDeProductos.getSelectedRow();
        if (selectedRow != -1) {
            Producto producto = productoServicio.obtenerTodos().get(selectedRow);
            productoServicio.eliminarProducto(producto);
            escribirLog("Producto eliminado: ID " + producto.getId() + ", Nombre: " + producto.getNombre());
            actualizarTabla();
            actualizarCSV();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
        }
    }

    private void exportarCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.csv"))) {
            writer.write("ID,Nombre,Precio,Cantidad,Fecha De Vencimiento,Proveedor,Categoría\n");
            for (Producto p : productoServicio.obtenerTodos()) {
                writer.write(String.format("%d,%s,%.2f,%d,%s,%s,%s\n",
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getCantidad(),
                    p.getFechaVencimiento(),
                    p.getProveedor() != null ? p.getProveedor() : "",
                    p.getCategoria()
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al exportar el archivo.");
        }
    }

    private void actualizarTabla() {
        modeloDeTabla.setRowCount(0);
        List<Producto> productos = productoServicio.obtenerTodos();
        for (Producto p : productos) {
            modeloDeTabla.addRow(new Object[]{
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                p.getCantidad(),
                p.getFechaVencimiento(),
                p.getProveedor() != null ? p.getProveedor() : "",
                p.getCategoria()
            });
        }
        actualizarValor();
    }

    private void actualizarValor() {
        double valorInv = productoServicio.obtenerTodos().stream().mapToDouble(Producto::calcularValorInventario).sum();
        lblValorInventario.setText("Valor de Inventario: $" + valorInv);
    }

    private void leerCSV() {
        try {
            productoServicio.leerProductosCSV("productos.csv");
            actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al leer el archivo.");
        }
    }

    private void actualizarCSV() {
        exportarCSV();
    }

    private void mostrarLog() {
        VistaLog vistaLog = new VistaLog();
        vistaLog.setVisible(true);
    }

    private void escribirLog(String mensaje) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transacciones.log", true))) {
            writer.write(mensaje + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al escribir en el log.");
        }
    }

    private void modificarCantidadProducto() {
        int selectedRow = tablaDeProductos.getSelectedRow();
        if (selectedRow != -1) {
            Producto producto = productoServicio.obtenerTodos().get(selectedRow);
            VistaModificarCantidad dialog = new VistaModificarCantidad(this, "Modificar Cantidad", producto);
            dialog.setVisible(true);
            escribirLog("Modificación de cantidad para producto: ID " + producto.getId() + ", Nombre: " + producto.getNombre());
            actualizarTabla();
            actualizarCSV();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para modificar su cantidad.");
        }
    }

    private List<Proveedor> obtenerProveedores() {
        return proveedorServicio.obtenerTodos();
    }
}
