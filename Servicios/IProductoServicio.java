package Servicios;

import java.util.List;
import Modelo.Producto;


public interface IProductoServicio {
  void agregarProducto(Producto producto);
  void eliminarProducto(Producto producto);
  void actualizarProducto(int index, Producto producto);
  void leerCSV(String path);
  List<Producto> obtenerTodos();
}