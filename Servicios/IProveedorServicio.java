package Servicios;

import java.util.List;
import Modelo.Proveedor;

public interface IProveedorServicio {
    void agregarProveedor(Proveedor proveedor);
    void eliminarProveedor(Proveedor proveedor);
    void eliminarProveedorPorId(int id);
    void actualizarProveedor(int id, Proveedor proveedor);
    void cargarProveedoresDesdeCSV(String rutaArchivo);
    List<Proveedor> obtenerTodos();
}
