package Modelo;

import java.time.LocalDate; 

public class ProductoPerecedero extends Producto {

    // Atributos adicionales
    private LocalDate fechaDeVencimiento;

    // Constructor vacío
    public ProductoPerecedero() {
        super(); // Importa los atributos de la clase padre
    }

    // Se inicializan los datos
    public ProductoPerecedero(String nombre, double precio, int cantidad, LocalDate fechaDeVencimiento) {
        super(); 
        setNombre(nombre);   
        setPrecio(precio);   
        setCantidad(cantidad); 
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    // Getters y Setters 
    public LocalDate getFechaDeVencimiento() { // Cambiado a "getFechaDeVencimiento"
        return fechaDeVencimiento;
    }

    public void setFechaDeVencimiento(LocalDate fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }
}
