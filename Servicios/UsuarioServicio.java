package Servicios;

import Modelo.Usuario;
import Repositorio.UsuarioRepositorio;

public class UsuarioServicio {
    public UsuarioServicio() {
        // Este es un usuario de prueba de administrador
        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setId(1);
        usuarioAdmin.setNombre("admin");
        usuarioAdmin.setApellido("admin");
        usuarioAdmin.setCorreo("admin@example.com");
        usuarioAdmin.setPassword("123");
        usuarioAdmin.setEstadoActivo(true);
        usuarioAdmin.setRol("Administrador");

        Usuario usuarioAuxiliar = new Usuario();
        usuarioAuxiliar.setId(2);
        usuarioAuxiliar.setNombre("auxiliar");
        usuarioAuxiliar.setApellido("auxiliar");
        usuarioAuxiliar.setCorreo("auxiliar@example.com");
        usuarioAuxiliar.setPassword("123");
        usuarioAuxiliar.setEstadoActivo(true);
        usuarioAuxiliar.setRol("Auxiliar");

        Usuario usuarioRapido = new Usuario();
        usuarioRapido.setId(3);
        usuarioRapido.setNombre("a");
        usuarioRapido.setApellido("a");
        usuarioRapido.setCorreo("a");
        usuarioRapido.setPassword("a");
        usuarioRapido.setEstadoActivo(true);
        usuarioRapido.setRol("Administrador");

        // Agregar los usuarios al repositorio
        UsuarioRepositorio.crearUsuario(usuarioAdmin);
        UsuarioRepositorio.crearUsuario(usuarioAuxiliar);
        UsuarioRepositorio.crearUsuario(usuarioRapido);
    }

    // Método para validar usuario
    public boolean validarUsuario(String correo, String password) {
        Usuario usuario = UsuarioRepositorio.obtenerUsuarioPorCorreo(correo);
        return usuario != null && usuario.getPassword().equals(password);
    }

    // Método para validar si un usuario tiene permisos según su rol
    public boolean usuarioTienePermiso(String correo, String accion) {
        return UsuarioRepositorio.tienePermiso(correo, accion);
    }

    // Método para realizar una acción, dependiendo de los permisos
    public void realizarAccion(String correo, String accion) {
        if (usuarioTienePermiso(correo, accion)) {
            System.out.println("El usuario con correo " + correo + " tiene permiso para realizar la acción: " + accion);
            // Aquí puedes incluir la lógica para realizar la acción
        } else {
            System.out.println("El usuario con correo " + correo + " no tiene permiso para realizar la acción: " + accion);
        }
    }
}
