package Repositorio;

import java.util.List;
import Modelo.Producto;

public interface IProductoRepositorio {
  void agregarProducto(Producto producto);
  void eliminarProducto(Producto producto);
  void actualizarProducto(int index, Producto producto);
  List<Producto> obtenerTodos();
}