package Modelo;

public abstract class Producto {
    // Atributos privados
    private String nombre;
    private double precio;
    private int cantidad;
    private int proveedor;
    
    // Constructor
    public Producto() {
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getProveedor() {
        return proveedor;
    }

    public void setProveedor(int proveedor) {
        this.proveedor = proveedor;
    }
    
    public double calcularValorInventario() {
        return this.cantidad * this.precio ;
    }

    
}
