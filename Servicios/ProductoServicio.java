package Servicios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import Modelo.Producto;
import Repositorio.IProductoRepositorio;

public class ProductoServicio implements IProductoServicio {
    private IProductoRepositorio repository;

    public ProductoServicio(IProductoRepositorio repository) {
        this.repository = repository;
    }

    public void agregarProducto(Producto producto) {
        repository.agregarProducto(producto);
    }

    public void eliminarProducto(Producto producto) {
        repository.eliminarProducto(producto);
    }

    public void actualizarProducto(int index, Producto producto) {
        repository.actualizarProducto(index, producto);
    }

    public void leerProductosCSV(String rutaArchivo) {
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
                if (campos.length == 7) { // Ahora esperamos 7 campos
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos[1].trim();
                    double precio = Double.parseDouble(campos[2].trim());
                    int cantidad = Integer.parseInt(campos[3].trim());
                    LocalDate fechaVencimiento = LocalDate.parse(campos[4].trim());
                    String proveedor = campos[5].trim();
                    String categoria = campos[6].trim();

                    Producto producto = new Producto();
                    producto.setId(id);
                    producto.setNombre(nombre);
                    producto.setPrecio(precio);
                    producto.setCantidad(cantidad);
                    producto.setFechaVencimiento(fechaVencimiento);
                    producto.setProveedor(proveedor);
                    producto.setCategoria(categoria);

                    // Agregar el producto al repositorio
                    agregarProducto(producto);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al leer el archivo CSV.");
        }
    }

    public void verificarYGenerarPedidosAutomaticos() {
        List<Producto> productos = obtenerTodos(); // Obtiene todos los productos del repositorio

        for (Producto producto : productos) {
            if (producto.getCantidad() < 50) { // Verifica si el stock es menor a 50
                int cantidadFaltante = 200 - producto.getCantidad();

                // Simula la generación de un pedido automático
                System.out.println("Pedido generado para: " + producto.getNombre() +
                        " | Cantidad solicitada: " + cantidadFaltante);

                // Mostrar alerta visual
                JOptionPane.showMessageDialog(
                        null,
                        "El producto \"" + producto.getNombre() + "\" tiene stock bajo (" + producto.getCantidad()
                                + ").\n" +
                                "Se ha generado un pedido de " + cantidadFaltante + " unidades.",
                        "Alerta de Stock Bajo",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void recibirPedido(String nombreProducto, int cantidadRecibida) {
        List<Producto> productos = obtenerTodos(); // Obtén todos los productos

        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombreProducto)) { // Busca el producto por nombre
                producto.setCantidad(producto.getCantidad() + cantidadRecibida); // Actualiza la cantidad
                actualizarProducto(producto.getId(), producto); // Guarda los cambios en el repositorio

                // Confirmación visual
                JOptionPane.showMessageDialog(
                        null,
                        "Se ha recibido el pedido del producto \"" + producto.getNombre() + "\".\n" +
                                "Cantidad añadida: " + cantidadRecibida + ".\nNuevo stock: " + producto.getCantidad()
                                + ".",
                        "Pedido Recibido",
                        JOptionPane.INFORMATION_MESSAGE);
                return; // Salir del método después de encontrar y actualizar el producto
            }
        }

        // Mensaje en caso de que el producto no se encuentre
        JOptionPane.showMessageDialog(
                null,
                "El producto \"" + nombreProducto + "\" no fue encontrado.",
                "Error al recibir pedido",
                JOptionPane.ERROR_MESSAGE);
    }

    public List<Producto> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public void procesarPedidosPendientes() {
        List<Producto> productos = obtenerTodos(); // Obtiene todos los productos del repositorio
    
        for (Producto producto : productos) {
            if (producto.getCantidad() < 50) { // Verifica si el stock es menor al mínimo permitido
                int cantidadFaltante = 200 - producto.getCantidad(); // Calcula la cantidad necesaria para reponer
    
                // Actualiza el inventario
                producto.setCantidad(producto.getCantidad() + cantidadFaltante);
                actualizarProducto(producto.getId(), producto); // Guarda los cambios en el repositorio
    
                // Mensaje de confirmación en consola
                System.out.println("Pedido procesado para: " + producto.getNombre() +
                        " | Cantidad recibida: " + cantidadFaltante);
    
                // Mensaje visual para el usuario
                JOptionPane.showMessageDialog(
                        null,
                        "El pedido del producto \"" + producto.getNombre() + "\" ha sido procesado.\n" +
                                "Cantidad añadida: " + cantidadFaltante + ".\nNuevo stock: " + producto.getCantidad() + ".",
                        "Pedido Procesado",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
}