package Modelo;

import java.time.LocalDate;

public class Producto {
    // Atributos privados
    private int id;
    private String nombre;
    private double precio;
    private int cantidad;
    private String proveedor;
    private LocalDate fechaVencimiento;
    private int proveedorId;
    private String categoria;
    private int cantidadMaxima; // Nueva cantidad máxima
    private int cantidadMinima; // Nueva cantidad mínima

    // Constructor
    public Producto() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Establece la fecha de vencimiento. 
     * Si el producto no tiene vencimiento, asigna null.
     */
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Verifica si el producto tiene fecha de vencimiento.
     * @return true si tiene fecha de vencimiento, false si no.
     */
    public boolean tieneFechaDeVencimiento() {
        return fechaVencimiento != null;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double calcularValorInventario() {
        return this.cantidad * this.precio;
    }

    // Getters y Setters para cantidad máxima y mínima
    public int getCantidadMaxima() {
        return cantidadMaxima;
    }

    public void setCantidadMaxima(int cantidadMaxima) {
        this.cantidadMaxima = cantidadMaxima;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    /**
     * Verifica si la cantidad actual está por debajo de la cantidad mínima.
     * @return true si está por debajo, false si no.
     */
    public boolean estaPorDebajoDeMinima() {
        return cantidad < cantidadMinima;
    }

    /**
     * Verifica si la cantidad actual excede la cantidad máxima.
     * @return true si excede, false si no.
     */
    public boolean excedeMaxima() {
        return cantidad > cantidadMaxima;
    }
}
