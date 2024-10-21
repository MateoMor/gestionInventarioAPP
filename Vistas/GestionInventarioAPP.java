package Vistas;

import javax.swing.table.DefaultTableModel;

import Modelo.Producto;
import Modelo.ProductoPerecedero;

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

    public GestionInventarioAPP(){
        IProductoRepositorio repository = new ProductoRepositorio();
        productoServicio = new ProductoServicio(repository);
        
        modeloDeTabla = new DefaultTableModel(new String[]{"Nombre", "Precio", "Cantidad", "Tipo"}, 0);
        tablaDeProductos = new JTable(modeloDeTabla);
        
        // Configuración de la ventana
        setTitle("Gestión de Inventario");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 400);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnExportarCSV = new JButton("Exportar CSV");

        JPanel panelInventario = new JPanel();
        lblValorInventario = new JLabel("Valor de Inventario: $0.0");
        panelInventario.add(lblValorInventario);
        add(panelInventario, BorderLayout.NORTH);

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnExportarCSV);

        add(new JScrollPane(tablaDeProductos), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> agregarProducto());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnExportarCSV.addActionListener(e -> exportarCSV());


    }

    private void agregarProducto() {
        VistaProducto dialog = new VistaProducto(this, "Agregar Producto", null);
        dialog.setVisible(true);
        Producto producto = dialog.getProducto();
        if (producto != null) {
            productoServicio.agregarProducto(producto);
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
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.");
        }
    }

    private void exportarCSV() {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter("productos.csv"))) {
            writer.write("Nombre,Precio,Cantidad,Tipo\n");
            for (Producto p : productoServicio.obtenerTodos()) {
                writer.write(String.format("%s,%.2f,%d,%s\n", p.getNombre(), p.getPrecio(), p.getCantidad(), p instanceof ProductoPerecedero ? "Perecedero" : "No Perecedero"));
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
            modeloDeTabla.addRow(new Object[]{p.getNombre(), p.getPrecio(), p.getCantidad(), p instanceof ProductoPerecedero ? "Perecedero" : "No Perecedero"});
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

}
