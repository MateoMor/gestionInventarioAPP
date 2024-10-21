package Modelo;

import java.time.LocalDate; 

// Clase ProductoDuradero que hereda de Producto
public class ProductoDuradero extends Producto {

    // Atributos adicionales
    private int duracionEnMeses;
    private LocalDate fechaDeFabricacion;

    // Constructor vacío
    public ProductoDuradero() {
        super(); // Importa los atributos de la clase padre
    }

    // Se inicializacn los datos
    public ProductoDuradero(String nombre, double precio, int cantidad, int duracionEnMeses, LocalDate fechaDeFabricacion) {
        super(); 
        setNombre(nombre);   
        setPrecio(precio);   
        setCantidad(cantidad); 
        this.duracionEnMeses = duracionEnMeses;
        this.fechaDeFabricacion = fechaDeFabricacion;
    }

    // Getters y Setters 
    public int getDuracionEnMeses() {
        return duracionEnMeses;
    }

    public void setDuracionEnMeses(int duracionEnMeses) {
        this.duracionEnMeses = duracionEnMeses;
    }

    public LocalDate getFechaDeFabricacion() {
        return fechaDeFabricacion;
    }

    public void setFechaDeFabricacion(LocalDate fechaDeFabricacion) {
        this.fechaDeFabricacion = fechaDeFabricacion;
    }
}
