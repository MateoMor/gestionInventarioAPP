package Repositorio;

import Modelo.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepositorio implements IPedidoRepositorio {
    private List<Pedido> pedidos;

    public PedidoRepositorio() {
        this.pedidos = new ArrayList<>();
    }

    @Override
    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    @Override
    public void eliminarPedido(Pedido pedido) {
        pedidos.remove(pedido);
    }

    @Override
    public List<Pedido> obtenerTodos() {
        return new ArrayList<>(pedidos); // Devuelve una copia de la lista
    }

    @Override
    public Pedido obtenerPedidoPorId(int id) {
        return pedidos.stream()
                      .filter(p -> p.getProductoId() == id)
                      .findFirst()
                      .orElse(null);
    }

    @Override
    public void actualizarPedido(Pedido pedido) {
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getProductoId() == pedido.getProductoId()) {
                pedidos.set(i, pedido);
                break;
            }
        }
    }
}
