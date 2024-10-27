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

    // Getter y Setter para categoria
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double calcularValorInventario() {
        return this.cantidad * this.precio;
    }
}
