package Servicios;

import Modelo.Pedido;
import java.util.List;

public interface IPedidoServicio {
    void agregarPedido(Pedido pedido);
    void eliminarPedido(Pedido pedido);
    List<Pedido> obtenerTodos();
    Pedido obtenerPedidoPorId(int id);
    void actualizarPedido(Pedido pedido);
    List<String> recibirTodosLosPedidos();
}
