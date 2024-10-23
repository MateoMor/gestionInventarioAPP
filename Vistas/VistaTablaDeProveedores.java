package Vistas;

import javax.swing.table.DefaultTableModel;
import Modelo.Proveedor;

import javax.swing.*;
import java.awt.*;

import Repositorio.IProveedorRepositorio;
import Repositorio.ProveedorRepositorio;
import Servicios.IProveedorServicio;
import Servicios.ProveedorServicio;

public class VistaTablaDeProveedores extends JFrame {
    
    private JTable tablaDeProveedores;
    private DefaultTableModel modeloDeTabla;
    private IProveedorServicio proveedorServicio;
    private JLabel lblValorInventario;

    public VistaTablaDeProveedores(){
        IProveedorRepositorio repository = new ProveedorRepositorio();
        proveedorServicio = new ProveedorServicio(repository);
        
        modeloDeTabla = new DefaultTableModel(new String[]{"Nombre", "Dirección", "Teléfono", "Correo"}, 0);
        tablaDeProveedores = new JTable(modeloDeTabla);
        
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
        lblValorInventario = new JLabel("Valor de Inventario: $0.0");
        panelInventario.add(lblValorInventario);
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
    }

    private void agregarProveedor() {
        Proveedor proveedor = new Proveedor();
        VistaProveedor vistaProveedor = new VistaProveedor(this, "Agregar Proveedor", proveedor);
        vistaProveedor.setVisible(true);
        if (proveedor.getNombre() != null) {
            proveedorServicio.agregarProveedor(proveedor);
            modeloDeTabla.addRow(new Object[]{proveedor.getNombre(), proveedor.getDireccion(), proveedor.getTelefono(), proveedor.getCorreo()});
        }
    }

    private void editarProveedor() {
        int filaSeleccionada = tablaDeProveedores.getSelectedRow();
        if (filaSeleccionada != -1) {
            String nombre = (String) modeloDeTabla.getValueAt(filaSeleccionada, 0);
            String direccion = (String) modeloDeTabla.getValueAt(filaSeleccionada, 1);
            String telefono = (String) modeloDeTabla.getValueAt(filaSeleccionada, 2);
            String correo = (String) modeloDeTabla.getValueAt(filaSeleccionada, 3);
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(nombre);
            proveedor.setDireccion(direccion);
            proveedor.setTelefono(telefono);
            proveedor.setCorreo(correo);
            VistaProveedor vistaProveedor = new VistaProveedor(this, "Editar Proveedor", proveedor);
            vistaProveedor.setVisible(true);
            if (proveedor.getNombre() != null) {
                proveedorServicio.actualizarProveedor(filaSeleccionada, proveedor);
                modeloDeTabla.setValueAt(proveedor.getNombre(), filaSeleccionada, 0);
                modeloDeTabla.setValueAt(proveedor.getDireccion(), filaSeleccionada, 1);
                modeloDeTabla.setValueAt(proveedor.getTelefono(), filaSeleccionada, 2);
                modeloDeTabla.setValueAt(proveedor.getCorreo(), filaSeleccionada, 3);
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
    
}
