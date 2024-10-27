package Repositorio;

import Modelo.Proveedor;
import java.util.List;

public interface IProveedorRepositorio {
    void agregarProveedor(Proveedor proveedor);
    void eliminarProveedor(Proveedor proveedor);
    void eliminarProveedorPorId(int id); // Nuevo método para eliminar por ID
    void actualizarProveedor(int id, Proveedor proveedor); // Actualización por ID
    List<Proveedor> obtenerTodos();
}