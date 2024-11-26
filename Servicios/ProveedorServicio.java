package Servicios;

import Modelo.Proveedor;
import Repositorio.IProveedorRepositorio;

import java.util.List;

public class ProveedorServicio implements IProveedorServicio {
    private IProveedorRepositorio repository;

    public ProveedorServicio(IProveedorRepositorio repository) {
        this.repository = repository;
    }

    @Override
    public void agregarProveedor(Proveedor proveedor) {
        if (proveedor.getId() == 0) { // Si no tiene ID, asignarla desde el repositorio
            proveedor.setId(repository.obtenerSiguienteId());
        }
        repository.agregarProveedor(proveedor);
    }

    @Override
    public void eliminarProveedor(Proveedor proveedor) {
        repository.eliminarProveedor(proveedor);
    }

    @Override
    public void eliminarProveedorPorId(int id) {
        repository.eliminarProveedorPorId(id);
    }

    @Override
    public void actualizarProveedor(int id, Proveedor proveedor) {
        repository.actualizarProveedor(id, proveedor);
    }

    @Override
    public void cargarProveedoresDesdeCSV(String rutaArchivo) {
        repository.cargarProveedoresDesdeCSV(rutaArchivo);
    }

    @Override
    public List<Proveedor> obtenerTodos() {
        return repository.obtenerTodos();
    }
}
