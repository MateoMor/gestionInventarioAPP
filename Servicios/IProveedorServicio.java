package Servicios;

import java.util.List;
import Modelo.Proveedor;

public interface IProveedorServicio {
    void agregarProveedor(Proveedor proveedor);
    void eliminarProveedor(Proveedor proveedor);
    void eliminarProveedorPorId(int id); // Nuevo método para eliminar por ID
    void actualizarProveedor(int id, Proveedor proveedor); // Actualización por ID
    List<Proveedor> obtenerTodos();
}
