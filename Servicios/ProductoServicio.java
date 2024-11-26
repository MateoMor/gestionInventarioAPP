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
            if (campos.length == 7) {  // Ahora esperamos 7 campos
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


    public List<Producto> obtenerTodos() {
        return repository.obtenerTodos();
    }
}