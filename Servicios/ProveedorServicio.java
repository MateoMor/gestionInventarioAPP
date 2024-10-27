package Servicios;

import Modelo.Proveedor;
import Repositorio.IProveedorRepositorio;

import java.util.List;

public class ProveedorServicio implements IProveedorServicio {
    private IProveedorRepositorio repository;

    public ProveedorServicio(IProveedorRepositorio repository) {
        this.repository = repository;
    }

    public void agregarProveedor(Proveedor proveedor) {
        repository.agregarProveedor(proveedor);
    }

    public void eliminarProveedor(Proveedor proveedor) {
        repository.eliminarProveedor(proveedor);
    }

    public void eliminarProveedorPorId(int id) {
        List<Proveedor> proveedores = repository.obtenerTodos();
        for (Proveedor p : proveedores) {
            if (p.getId() == id) {
                repository.eliminarProveedor(p);
                break;
            }
        }
    }

    public void actualizarProveedor(int id, Proveedor proveedor) {
        List<Proveedor> proveedores = repository.obtenerTodos();
        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i).getId() == id) {
                repository.actualizarProveedor(i, proveedor);
                break;
            }
        }
    }

    public void cargarProveedoresDesdeCSV(String rutaArchivo) {
        repository.cargarProveedoresDesdeCSV(rutaArchivo);
    }

    public List<Proveedor> obtenerTodos() {
        return repository.obtenerTodos();
    }
}
