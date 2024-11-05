package Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

import Modelo.Usuario;
import Repositorio.UsuarioRepositorio;
import Servicios.UsuarioServicio;

public class LoginFrame extends JFrame {
    private UsuarioServicio usuarioServicio;

    public LoginFrame() {
        usuarioServicio = new UsuarioServicio();

        setTitle("Login");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        JLabel labelCorreo = new JLabel("Correo:");
        JTextField campoCorreo = new JTextField(15);
        JLabel labelPassword = new JLabel("Contraseña:");
        JTextField campoPassword = new JTextField(15);
        JButton botonIngresar = new JButton("Ingresar");

        // Panel de diseño
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 3));

        
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); 

        panel.add(labelCorreo);
        panel.add(campoCorreo);
        panel.add(labelPassword);
        panel.add(campoPassword);
        panel.add(new JLabel()); 
        panel.add(botonIngresar);

        add(panel);

        // ActionListener para el botón "Ingresar"
        botonIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = campoCorreo.getText();
                String password = campoPassword.getText();

                if (usuarioServicio.validarUsuario(correo, password)) {  
                    Usuario usuario = UsuarioRepositorio.obtenerUsuarioPorCorreo(correo);
                    JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());

                    // Mostrar la interfaz principal
                    GestionInventarioAPP mainFrame = new GestionInventarioAPP();
                    mainFrame.setVisible(true);
                    dispose();  // Cerrar la ventana de login
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
