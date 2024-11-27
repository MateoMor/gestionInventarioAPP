package Vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Modelo.Producto;
import Servicios.IProductoServicio;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class VistaPedidosPendientes extends JDialog {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private IProductoServicio productoServicio;

    public VistaPedidosPendientes(IProductoServicio productoServicio) {
        this.productoServicio = productoServicio;

        setTitle("Pedidos Pendientes");
        setSize(600, 400);
        setLayout(new BorderLayout());
        setModal(true);

        // Configurar tabla para mostrar pedidos
        modeloTabla = new DefaultTableModel(new String[] { "ID", "Nombre", "Cantidad Solicitada", "Proveedor" }, 0);
        tablaPedidos = new JTable(modeloTabla);
        cargarPedidosPendientes();

        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAceptar = new JButton("Aceptar Pedidos");
        JButton btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        // Añadir acciones a los botones
        btnAceptar.addActionListener(_ -> {
            // Obtener los productos pendientes antes de procesarlos
            List<Producto> pedidosPendientes = productoServicio.obtenerPedidosPendientes();

            // Procesar los pedidos pendientes
            productoServicio.procesarPedidosPendientes();

            // Exportar los productos a CSV
            exportarProductosACSV(pedidosPendientes);

            // Mostrar mensaje de éxito y cerrar la ventana
            JOptionPane.showMessageDialog(this, "Pedidos procesados correctamente.");
            dispose(); // Cierra la ventana
        });

        btnCancelar.addActionListener(_ -> dispose()); // Cierra la ventana

        // Añadir componentes a la ventana
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cargarPedidosPendientes() {
        modeloTabla.setRowCount(0); // Limpiar tabla

        // Obtener lista de pedidos pendientes desde el servicio
        List<Producto> pedidosPendientes = productoServicio.obtenerPedidosPendientes();
        for (Producto pedido : pedidosPendientes) {
            int cantidadFaltante = 200 - pedido.getCantidad(); // La cantidad que falta para llegar al stock objetivo
            modeloTabla.addRow(new Object[] {
                    pedido.getId(),
                    pedido.getNombre(),
                    cantidadFaltante, // Cantidad que se solicitará
                    pedido.getProveedor() != null ? pedido.getProveedor() : "Desconocido" // Proveedor
            });
        }
    }

    private void exportarProductosACSV(List<Producto> productos) {
        String rutaArchivo = "productos.csv"; // Ruta del archivo CSV existente
        Map<Integer, Producto> productosExistentes = cargarProductosDesdeCSV(rutaArchivo);

        // Actualizar productos existentes con nuevas cantidades
        for (Producto producto : productos) {
            if (productosExistentes.containsKey(producto.getId())) {
                // Si el producto ya existe, aumentar la cantidad
                Producto existente = productosExistentes.get(producto.getId());
                existente.setCantidad(existente.getCantidad() + producto.getCantidad());
            } else {
                // Si el producto no existe, crear un nuevo objeto Producto usando setters
                Producto nuevoProducto = new Producto();
                nuevoProducto.setId(producto.getId());
                nuevoProducto.setNombre(producto.getNombre());
                nuevoProducto.setPrecio(producto.getPrecio());
                nuevoProducto.setCantidad(producto.getCantidad());
                nuevoProducto.setFechaVencimiento(producto.getFechaVencimiento());
                nuevoProducto.setProveedor(producto.getProveedor());
                nuevoProducto.setCategoria(producto.getCategoria());

                productosExistentes.put(producto.getId(), nuevoProducto);
            }
        }

        // Sobrescribir el archivo CSV con los productos actualizados
        escribirProductosACSV(rutaArchivo, productosExistentes);
    }

    private Map<Integer, Producto> cargarProductosDesdeCSV(String rutaArchivo) {
        Map<Integer, Producto> productos = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            boolean primeraLinea = true;

            while ((line = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false; // Omitir la primera línea (encabezado)
                    continue;
                }

                // Dividir la línea por comas
                String[] campos = line.split(",");
                if (campos.length == 7) { // Verificar que tiene 7 campos
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos[1].trim();
                    double precio = Double.parseDouble(campos[2].trim());
                    int cantidad = Integer.parseInt(campos[3].trim());
                    String fechaVencimiento = campos[4].trim();
                    String proveedor = campos[5].trim();
                    String categoria = campos[6].trim();

                    Producto producto = new Producto();
                    producto.setId(id);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    producto.setCantidad(cantidad);
                    producto.setFechaVencimiento(LocalDate.parse(fechaVencimiento));
                    producto.setProveedor(proveedor);
                    producto.setCategoria(categoria);

                    productos.put(id, producto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al leer el archivo CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return productos;
    }

    private void escribirProductosACSV(String rutaArchivo, Map<Integer, Producto> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            // Escribir encabezado
            writer.write("ID,Nombre,Precio,Cantidad,Fecha De Vencimiento,Proveedor,Categoría\n");

            // Escribir cada producto en el archivo CSV
            for (Producto producto : productos.values()) {
                writer.write(producto.getId() + "," +
                             producto.getNombre() + "," +
                             producto.getPrecio() + "," +
                             producto.getCantidad() + "," +
                             producto.getFechaVencimiento() + "," +
                             producto.getProveedor() + "," +
                             producto.getCategoria() + "\n");
            }

            // Mensaje de éxito
            JOptionPane.showMessageDialog(this, "Productos exportados a CSV correctamente.", "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar los productos a CSV.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
