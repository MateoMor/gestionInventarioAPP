package Repositorio;

import Modelo.Proveedor;
import java.util.List;

public interface IProveedorRepositorio {
    void agregarProveedor(Proveedor proveedor);
    void eliminarProveedor(Proveedor proveedor);
    void eliminarProveedorPorId(int id); 
    void actualizarProveedor(int id, Proveedor proveedor); 
    void cargarProveedoresDesdeCSV(String rutaArchivo);
    List<Proveedor> obtenerTodos();
}