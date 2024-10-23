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
        proveedores.remove(proveedor);
    }

    public void actualizarProveedor(int index, Proveedor proveedor) {
        if(index >= 0 && index < proveedores.size()) {
            proveedores.set(index, proveedor);
        }
    }

    public List<Proveedor> obtenerTodos() {
        return proveedores;
    }
}