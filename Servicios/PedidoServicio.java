package Servicios;

import Modelo.Pedido;
import Repositorio.IPedidoRepositorio;

import java.util.List;

public class PedidoServicio implements IPedidoServicio {
    private IPedidoRepositorio pedidoRepositorio;

    public PedidoServicio(IPedidoRepositorio pedidoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
    }

    @Override
    public void agregarPedido(Pedido pedido) {
        pedidoRepositorio.agregarPedido(pedido);
    }

    @Override
    public void eliminarPedido(Pedido pedido) {
        pedidoRepositorio.eliminarPedido(pedido);
    }

    @Override
    public List<Pedido> obtenerTodos() {
        return pedidoRepositorio.obtenerTodos();
    }

    @Override
    public Pedido obtenerPedidoPorId(int id) {
        return pedidoRepositorio.obtenerPedidoPorId(id);
    }

    @Override
    public void actualizarPedido(Pedido pedido) {
        pedidoRepositorio.actualizarPedido(pedido);
    }

    @Override
    public void recibirPedido(Pedido pedido) {
        // Lógica para procesar la recepción de un pedido
        Pedido pedidoExistente = pedidoRepositorio.obtenerPedidoPorId(pedido.getProductoId());
        if (pedidoExistente != null) {
            eliminarPedido(pedidoExistente); // Eliminar el pedido recibido
            System.out.println("Pedido recibido para el producto ID: " + pedido.getProductoId());
        }
    }
}
