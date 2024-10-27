package Vistas;

import javax.swing.table.DefaultTableModel;
import Modelo.Proveedor;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import Repositorio.IProveedorRepositorio;
import Repositorio.ProveedorRepositorio;
import Servicios.IProveedorServicio;
import Servicios.ProveedorServicio;

public class VistaTablaDeProveedores extends JFrame {
    
    private JTable tablaDeProveedores;
    private DefaultTableModel modeloDeTabla;
    private IProveedorServicio proveedorServicio;

    public VistaTablaDeProveedores() {
        IProveedorRepositorio repository = new ProveedorRepositorio();
        proveedorServicio = new ProveedorServicio(repository);
        
        modeloDeTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Dirección", "Teléfono", "Correo"}, 0);
        tablaDeProveedores = new JTable(modeloDeTabla);
        
        // Habilitar el ordenamiento en la tabla
        tablaDeProveedores.setAutoCreateRowSorter(true);
        
        // Configuración de la ventana
        setTitle("Gestión de Proveedores");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setSize(750, 400);
    
        setLocationRelativeTo(null);
    
        // Panel de botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnExportarCSV = new JButton("Exportar CSV");
    
        JPanel panelInventario = new JPanel();
        add(panelInventario, BorderLayout.CENTER);
    
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnExportarCSV);
    
        add(new JScrollPane(tablaDeProveedores), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    
        btnAgregar.addActionListener(e -> agregarProveedor());
        btnEditar.addActionListener(e -> editarProveedor());
        btnEliminar.addActionListener(e -> eliminarProveedor());
        btnExportarCSV.addActionListener(e -> exportarCSV());
    
        // Cargar proveedores desde el archivo CSV
        cargarProveedoresDesdeCSV();
    }
    

    private void cargarProveedoresDesdeCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader("proveedores.csv"))) {
            String linea;
            reader.readLine(); // Leer la cabecera y descartarla
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(","); // Suponiendo que el CSV está separado por comas
                if (datos.length == 5) { // Asegurarse de que hay 5 columnas
                    Proveedor proveedor = new Proveedor();
                    proveedor.setId(Integer.parseInt(datos[0].trim()));
                    proveedor.setNombre(datos[1].trim());
                    proveedor.setDireccion(datos[2].trim());
                    proveedor.setTelefono(datos[3].trim());
                    proveedor.setCorreo(datos[4].trim());
                    proveedorServicio.agregarProveedor(proveedor); // Agregar al servicio
                    modeloDeTabla.addRow(new Object[]{proveedor.getId(), proveedor.getNombre(), proveedor.getDireccion(), proveedor.getTelefono(), proveedor.getCorreo()});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los proveedores desde el archivo CSV.");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error en el formato del ID en el archivo CSV.");
        }
    }

    private void agregarProveedor() {
        Proveedor proveedor = new Proveedor();
        VistaProveedor vistaProveedor = new VistaProveedor(this, "Agregar Proveedor", proveedor);
        vistaProveedor.setVisible(true);
        if (proveedor.getNombre() != null) {
            proveedorServicio.agregarProveedor(proveedor);
            modeloDeTabla.addRow(new Object[]{proveedor.getId(), proveedor.getNombre(), proveedor.getDireccion(), proveedor.getTelefono(), proveedor.getCorreo()});
        }
    }

    private void editarProveedor() {
        int filaSeleccionada = tablaDeProveedores.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = (String) modeloDeTabla.getValueAt(filaSeleccionada, 1);
            String direccion = (String) modeloDeTabla.getValueAt(filaSeleccionada, 2);
            String telefono = (String) modeloDeTabla.getValueAt(filaSeleccionada, 3);
            String correo = (String) modeloDeTabla.getValueAt(filaSeleccionada, 4);
            Proveedor proveedor = new Proveedor();
            proveedor.setId((Integer) modeloDeTabla.getValueAt(filaSeleccionada, 0)); // Obtener ID
            proveedor.setNombre(nombre);
            proveedor.setDireccion(direccion);
            proveedor.setTelefono(telefono);
            proveedor.setCorreo(correo);
            VistaProveedor vistaProveedor = new VistaProveedor(this, "Editar Proveedor", proveedor);
            vistaProveedor.setVisible(true);
            if (proveedor.getNombre() != null) {
                proveedorServicio.actualizarProveedor(filaSeleccionada, proveedor);
                modeloDeTabla.setValueAt(proveedor.getNombre(), filaSeleccionada, 1);
                modeloDeTabla.setValueAt(proveedor.getDireccion(), filaSeleccionada, 2);
                modeloDeTabla.setValueAt(proveedor.getTelefono(), filaSeleccionada, 3);
                modeloDeTabla.setValueAt(proveedor.getCorreo(), filaSeleccionada, 4);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para editar");
        }
    }

    private void eliminarProveedor() {
        int filaSeleccionada = tablaDeProveedores.getSelectedRow();
        if (filaSeleccionada != -1) {
            Proveedor proveedorAEliminar = proveedorServicio.obtenerTodos().get(filaSeleccionada);
            proveedorServicio.eliminarProveedor(proveedorAEliminar);
            modeloDeTabla.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor para eliminar");
        }
    }

    private void exportarCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("proveedores.csv"))) {
            writer.write("ID,Nombre,Dirección,Teléfono,Correo\n");
            for (Proveedor p : proveedorServicio.obtenerTodos()) {
                writer.write(p.getId() + "," + p.getNombre() + "," + p.getDireccion() + "," + p.getTelefono() + "," + p.getCorreo() + "\n");
            }
            JOptionPane.showMessageDialog(null, "Proveedores exportados a proveedores.csv");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al exportar el archivo.");
        }
    }
}
