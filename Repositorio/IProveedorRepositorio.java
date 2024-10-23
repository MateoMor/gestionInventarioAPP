package Repositorio;

import java.util.List;
import Modelo.Proveedor;

public interface IProveedorRepositorio {
    void agregarProveedor(Proveedor proveedor);
    void eliminarProveedor(Proveedor proveedor);
    void actualizarProveedor(int index, Proveedor proveedor);
    List<Proveedor> obtenerTodos();

}
