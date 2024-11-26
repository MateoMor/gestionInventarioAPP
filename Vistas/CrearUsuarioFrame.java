package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

import Modelo.Usuario;
import Repositorio.UsuarioRepositorio;

public class CrearUsuarioFrame extends JFrame {

    public CrearUsuarioFrame() {
        setTitle("Crear Usuario");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal con layout
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Componentes del formulario
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField();
        JLabel lblApellido = new JLabel("Apellido:");
        JTextField txtApellido = new JTextField();
        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField();
        JLabel lblPassword = new JLabel("Contraseña:");
        JPasswordField txtPassword = new JPasswordField();
        JLabel lblRol = new JLabel("Rol:");
        JComboBox<String> comboRol = new JComboBox<>(new String[]{"Administrador", "Auxiliar"});
        JLabel lblEstado = new JLabel("Estado:");
        JCheckBox chkActivo = new JCheckBox("Activo");

        // Botones
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        // Añadir componentes al panel
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblApellido);
        panel.add(txtApellido);
        panel.add(lblCorreo);
        panel.add(txtCorreo);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(lblRol);
        panel.add(comboRol);
        panel.add(lblEstado);
        panel.add(chkActivo);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ActionListener para el botón "Guardar"
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String correo = txtCorreo.getText();
                String password = new String(txtPassword.getPassword());
                String rol = comboRol.getSelectedItem().toString();
                boolean estadoActivo = chkActivo.isSelected();

                // Validar campos obligatorios
                if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(CrearUsuarioFrame.this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear el objeto Usuario
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setId(UsuarioRepositorio.obtenerListadoUsuarios().size() + 1); // Generar ID basado en el tamaño de la lista
                nuevoUsuario.setNombre(nombre);
                nuevoUsuario.setApellido(apellido);
                nuevoUsuario.setCorreo(correo);
                nuevoUsuario.setPassword(password);
                nuevoUsuario.setRol(rol);
                nuevoUsuario.setEstadoActivo(estadoActivo);

                // Guardar en el repositorio
                UsuarioRepositorio.crearUsuario(nuevoUsuario);
                JOptionPane.showMessageDialog(CrearUsuarioFrame.this, "Usuario creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        // ActionListener para el botón "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el frame
            }
        });
    }

    public static void main(String[] args) {
        // Para probar el frame
        SwingUtilities.invokeLater(() -> {
            CrearUsuarioFrame frame = new CrearUsuarioFrame();
            frame.setVisible(true);
        });
    }
}
