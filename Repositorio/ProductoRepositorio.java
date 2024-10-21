package Repositorio;

import java.util.ArrayList;
import java.util.List;

import Modelo.Producto;

public class ProductoRepositorio implements IProductoRepositorio {
    private List<Producto> productos;

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
        if(index >= 0 && index < productos.size()) {
            productos.set(index, producto);
        }
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }
}