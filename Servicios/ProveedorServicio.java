package Servicios;

import java.util.List;
import Modelo.Proveedor;
import Repositorio.IProveedorRepositorio;

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

    public void actualizarProveedor(int index, Proveedor proveedor) {
        repository.actualizarProveedor(index, proveedor);
    }

    public List<Proveedor> obtenerTodos() {
        return repository.obtenerTodos();
    }
}
