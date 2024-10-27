package Repositorio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public void cargarProveedoresDesdeCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            boolean esPrimeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (esPrimeraLinea) {
                    esPrimeraLinea = false; // Omitir la primera línea
                    continue;
                }

                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    int id = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    String direccion = datos[2];
                    String telefono = datos[3];

                    Proveedor proveedor = new Proveedor();
                    proveedor.setId(id);
                    proveedor.setNombre(nombre);
                    proveedor.setDireccion(direccion);
                    proveedor.setTelefono(telefono);

                    proveedores.add(proveedor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Proveedor> obtenerTodos() {
        return new ArrayList<>(proveedores); // Devuelve una copia inmutable de la lista
    }
}
