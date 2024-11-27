package Servicios;

import java.io.IOException;
import java.util.List;
import Modelo.Producto;


public interface IProductoServicio {
  void agregarProducto(Producto producto);
  void eliminarProducto(Producto producto);
  void actualizarProducto(int index, Producto producto);
  void leerProductosCSV(String path) throws IOException; 
  void verificarYGenerarPedidosAutomaticos();
  //void recibirPedido(String nombreProducto, int cantidadRecibida);
  void procesarPedidosPendientes();
  List<Producto> obtenerPedidosPendientes();
  List<Producto> obtenerTodos();
}