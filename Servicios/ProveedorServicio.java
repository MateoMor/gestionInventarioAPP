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

    public void eliminarProveedorPorId(int id) {
        // Lógica para eliminar el proveedor por ID
        List<Proveedor> proveedores = repository.obtenerTodos();
        for (Proveedor p : proveedores) {
            if (p.getId() == id) {
                repository.eliminarProveedor(p);
                break; // Salir del bucle una vez encontrado
            }
        }
    }

    public void actualizarProveedor(int id, Proveedor proveedor) {
        // Lógica para actualizar el proveedor por ID
        List<Proveedor> proveedores = repository.obtenerTodos();
        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i).getId() == id) {
                repository.actualizarProveedor(i, proveedor); // Actualiza en el repositorio
                break; // Salir del bucle una vez encontrado
            }
        }
    }

    public List<Proveedor> obtenerTodos() {
        return repository.obtenerTodos();
    }
}
