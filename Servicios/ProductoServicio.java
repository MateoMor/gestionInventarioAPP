package Servicios;

import java.util.List;

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

    public void leerProductosCSV(String path){
        repository.leerProductosCSV(path);
    }

    public List<Producto> obtenerTodos() {
        return repository.obtenerTodos();
    }
}