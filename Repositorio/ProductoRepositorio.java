package Repositorio;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Modelo.Producto;

public class ProductoRepositorio implements IProductoRepositorio {
    private List<Producto> productos;
    private static final String COMMA_DELIMITER = ",";  // Define el delimitador aquí

    public ProductoRepositorio() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public void actualizarProducto(int index, Producto producto) {
        if (index >= 0 && index < productos.size()) {
            productos.set(index, producto);
        }
    }

    public void leerProductosCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true; // Variable para detectar la primera línea
    
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Marca que la primera línea ha sido leída
                    continue; // Salta a la siguiente iteración (es decir, omite esta línea)
                }
    
                String[] values = line.split(COMMA_DELIMITER);
                if (values.length >= 6) { // Asegúrate de que hay suficientes columnas
                    Producto producto = new Producto();
    
                    // Parsear ID como Integer
                    producto.setId(Integer.parseInt(values[0])); // Asignar el ID desde el CSV
    
                    // Establecer nombre
                    producto.setNombre(values[1]);
    
                    // Parsear precio como Double
                    producto.setPrecio(Double.parseDouble(values[2]));
    
                    // Parsear cantidad como Integer
                    producto.setCantidad(Integer.parseInt(values[3]));
    
                    // Si hay una fecha de vencimiento en la cuarta columna
                    if (!values[4].isEmpty()) {
                        LocalDate fechaVencimiento = LocalDate.parse(values[4], DateTimeFormatter.ISO_DATE);
                        producto.setFechaVencimiento(fechaVencimiento);
                    } else {
                        producto.setFechaVencimiento(null); // Sin fecha de vencimiento
                    }
    
                    // Asignar proveedor (puede estar vacío)
                    if (!values[5].isEmpty()) {
                        producto.setProveedor(values[5]); // Asignar el nombre del proveedor desde el CSV
                    } 
    
                    // Si hay una categoría en la séptima columna
                    if (values.length > 6) {
                        producto.setCategoria(values[6]); // Asumiendo que hay un método setCategoria
                    }
    
                    productos.add(producto); // Agregar el producto a la lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Manejo de errores por formato de número
        }
    }
    
    

    public List<Producto> obtenerTodos() {
        return productos;
    }
}
