package Repositorio;

import java.util.ArrayList;
import java.util.List;

import Modelo.Proveedor;

public class ProveedorRepositorio implements IProveedorRepositorio {
    private List<Proveedor> proveedores;

    public ProveedorRepositorio() {
        proveedores = new ArrayList<>();
    }

    public void agregarProveedor(Proveedor proveedor) {
        proveedores.add(proveedor);
    }

    public void eliminarProveedor(Proveedor proveedor) {
        if (proveedor != null) {
            proveedores.remove(proveedor);
        }
    }

    public void eliminarProveedorPorId(int id) {
        proveedores.removeIf(proveedor -> proveedor.getId() == id);
    }

    public void actualizarProveedor(int id, Proveedor proveedor) {
        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i).getId() == id) {
                proveedores.set(i, proveedor);
                return;
            }
        }
    }

    public List<Proveedor> obtenerTodos() {
        return new ArrayList<>(proveedores); // Devuelve una copia inmutable de la lista
    }
}
