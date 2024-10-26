package Vistas;

import javax.swing.table.DefaultTableModel;

import Modelo.Producto;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Repositorio.IProductoRepositorio;
import Repositorio.ProductoRepositorio;
import Servicios.IProductoServicio;
import Servicios.ProductoServicio;

public class GestionInventarioAPP extends JFrame {

    private JTable tablaDeProductos;
    private DefaultTableModel modeloDeTabla;
    private IProductoServicio productoServicio;
    private JLabel lblValorInventario;

    public GestionInventarioAPP() {
        IProductoRepositorio repository = new ProductoRepositorio();
        productoServicio = new ProductoServicio(repository);
        
        modeloDeTabla = new DefaultTableModel(new String[]{"Nombre", "Precio", "Cantidad", "Fecha de vencimiento", "Proveedor"}, 0);
        tablaDeProductos = new JTable(modeloDeTabla);
    
        // Configuración de la ventana
        setTitle("Gestión de Inventario");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 400);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnproveedores = new JButton("Proveedores");
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnExportarCSV = new JButton("Exportar CSV");
        JButton btnLog = new JButton("Log");

        JPanel panelInventario = new JPanel();
        lblValorInventario = new JLabel("Valor de Inventario: $0.0");
        panelInventario.add(lblValorInventario);
        add(panelInventario, BorderLayout.NORTH);

        // Cargar datos desde el archivo CSV
        leerCSV();  // Llama al método para leer el archivo CSV y cargar datos
        actualizarValor();  // Llama al método para calcular el valor del inventario

        panelBotones.add(btnproveedores);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnExportarCSV);
        panelBotones.add(btnLog);

        add(new JScrollPane(tablaDeProductos), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnproveedores.addActionListener(e -> mostrarProveedores());
        btnAgregar.addActionListener(e -> agregarProducto());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnExportarCSV.addActionListener(e -> exportarCSV());
        btnLog.addActionListener(e -> mostrarLog());
    }

    private void mostrarProveedores() {
        VistaTablaDeProveedores vistaProveedores = new VistaTablaDeProveedores();
        vistaProveedores.setVisible(true);
    }

    private void agregarProducto() {
        VistaProducto dialog = new VistaProducto(this, "Agregar Producto", null);
        dialog.setVisible(true);
        Producto producto = dialog.getProducto();
        if (producto != null) {
            productoServicio.agregarProducto(producto);
            escribirLog("Producto agregado: " + producto.getNombre());
            actualizarTabla();
        }
    }

    private void editarProducto() {
        int selectedRow = tablaDeProductos.getSelectedRow();
        if (selectedRow != -1) {
            Producto producto = productoServicio.obtenerTodos().get(selectedRow);
            VistaProducto dialog = new VistaProducto(this, "Editar Producto", producto);
            dialog.setVisible(true);
            if (dialog.getProducto() != null) {
                productoServicio.actualizarProducto(selectedRow, dialog.getProducto());
                escribirLog("Producto editado: " + dialog.getProducto().getNombre());
                actualizarTabla();
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
            escribirLog("Producto eliminado: " + producto.getNombre());
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
        }
    }

    private void exportarCSV() {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.csv"))) {
            writer.write("Nombre,Precio,Cantidad,Fecha De Vencimiento\n");
            for (Producto p : productoServicio.obtenerTodos()) {
                writer.write(String.format("%s,%.2f,%d,%s\n", p.getNombre(), p.getPrecio(), p.getCantidad(), p.getFechaVencimiento()));
            }
            JOptionPane.showMessageDialog(null, "Productos exportados a productos.csv");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al exportar el archivo.");
        }
    }

    private void actualizarTabla() {
        modeloDeTabla.setRowCount(0);
        List<Producto> productos = productoServicio.obtenerTodos();
        for (Producto p : productos) {
            modeloDeTabla.addRow(new Object[]{p.getNombre(), p.getPrecio(), p.getCantidad(), p.getFechaVencimiento(), p.getProveedor()});
        }

        actualizarValor();
    }

    private void actualizarValor() {
        var valorInv = 0.0;
        List<Producto> productos = productoServicio.obtenerTodos();
        for (Producto p : productos) {
            valorInv += p.calcularValorInventario();
        }
        
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
    

}
