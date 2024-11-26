package Repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Modelo.Producto;

public class ProductoRepositorio implements IProductoRepositorio {
    private List<Producto> productos;
    private static final String COMMA_DELIMITER = ","; // Define el delimitador aquí

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

    @Override
    public void leerProductosCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean isFirstLine = true; // Para saltar el encabezado

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Saltar la primera línea (encabezado)
                    continue;
                }

                String[] values = line.split(COMMA_DELIMITER); // Usamos el delimitador de coma para separar las
                                                               // columnas
                if (values.length >= 5) { // Asegurarse de que haya al menos 5 columnas (ID, Nombre, Fecha, Proveedor,
                                          // Categoría)
                    Producto producto = new Producto();

                    // Parsear ID (solo si es un número)
                    try {
                        producto.setId(Integer.parseInt(values[0].trim())); // Asignar el ID desde el CSV
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en la línea: " + line);
                        continue; // Si no puede parsear el ID, saltamos esta línea
                    }

                    // Establecer nombre
                    producto.setNombre(values[1].trim());

                    // Establecer fecha de vencimiento (si está presente, de lo contrario, nula)
                    if (!values[2].isEmpty()) {
                        producto.setFechaVencimiento(LocalDate.parse(values[2].trim(), DateTimeFormatter.ISO_DATE));
                    } else {
                        producto.setFechaVencimiento(null); // Fecha de vencimiento nula si no está presente
                    }

                    // Asignar proveedor (si está presente)
                    producto.setProveedor(values[3].trim());

                    // Asignar categoría (si está presente)
                    producto.setCategoria(values.length > 4 ? values[4].trim() : "");

                    // Añadir el producto a la lista
                    productos.add(producto);
                } else {
                    System.err.println("Error de formato en línea: " + line); // Si la línea no tiene suficientes
                                                                              // columnas
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores
        } catch (Exception e) {
            e.printStackTrace(); // Otros errores
        }
    }

    public List<Producto> obtenerProductosConBajoStock() {
        return productos.stream()
                .filter(Producto::estaPorDebajoDeMinima)
                .collect(Collectors.toList());
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }
}
