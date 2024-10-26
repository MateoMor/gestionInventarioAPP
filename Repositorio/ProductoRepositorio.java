package Repositorio;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

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

    public void leerCSV(String path) {
        List<List<Producto>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("book.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                // Aquí deberías convertir cada 'values' en una instancia de 'Producto'
                // records.add(Arrays.asList(values));  // Esto causará un error porque no es directamente compatible con Producto.
            }
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }
}
