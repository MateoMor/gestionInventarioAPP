package Servicios;

import java.io.IOException;
import java.util.List;
import Modelo.Producto;


public interface IProductoServicio {
  void agregarProducto(Producto producto);
  void eliminarProducto(Producto producto);
  void actualizarProducto(int index, Producto producto);
  void leerProductosCSV(String path) throws IOException; // Se espera una excepción de E/S
  List<Producto> obtenerTodos();
}