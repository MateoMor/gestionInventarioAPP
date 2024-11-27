package Servicios;

import Modelo.Pedido;
import Modelo.Producto;
import Repositorio.IPedidoRepositorio;
import Repositorio.IProductoRepositorio;

import java.util.ArrayList;
import java.util.List;

public class PedidoServicio implements IPedidoServicio {
    private IPedidoRepositorio pedidoRepositorio;
    private IProductoRepositorio productoRepositorio;

    public PedidoServicio(IPedidoRepositorio pedidoRepositorio, IProductoRepositorio productoRepositorio) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.productoRepositorio = productoRepositorio;
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

    /**
     * Procesa todos los pedidos pendientes, actualiza el stock de los productos y devuelve los detalles.
     * @return Lista de mensajes con los productos recibidos y las cantidades añadidas.
     */
    public List<String> recibirTodosLosPedidos() {
        List<Pedido> pedidosPendientes = pedidoRepositorio.obtenerTodos(); // Obtener todos los pedidos
        List<String> detallesRecepcion = new ArrayList<>(); // Lista para almacenar los detalles

        for (Pedido pedido : pedidosPendientes) {
            Producto producto = productoRepositorio.obtenerProductoPorId(pedido.getProductoId()); // Buscar el producto

            if (producto != null) {
                // Actualizar el stock del producto
                producto.setCantidad(producto.getCantidad() + pedido.getCantidadSolicitada());
                productoRepositorio.actualizarProducto(producto.getId(), producto);

                // Agregar el detalle del pedido recibido
                detallesRecepcion.add(
                    "Producto: " + producto.getNombre() +
                    ", Cantidad recibida: " + pedido.getCantidadSolicitada()
                );

                // Eliminar el pedido procesado
                eliminarPedido(pedido);
            }
        }

        return detallesRecepcion; // Retornar la lista con los detalles
    }
}
