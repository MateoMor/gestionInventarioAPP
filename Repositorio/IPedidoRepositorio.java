package Repositorio;

import Modelo.Pedido;
import java.util.List;

public interface IPedidoRepositorio {
    void agregarPedido(Pedido pedido);
    void eliminarPedido(Pedido pedido);
    List<Pedido> obtenerTodos();
    Pedido obtenerPedidoPorId(int id);
    void actualizarPedido(Pedido pedido);
}
