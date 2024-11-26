package Modelo;

public class Pedido {
    private int productoId;
    private String proveedor;
    private int cantidadSolicitada;

    // Constructor, getters y setters
    public Pedido(int productoId, String proveedor, int cantidadSolicitada) {
        this.productoId = productoId;
        this.proveedor = proveedor;
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(int cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }
}

