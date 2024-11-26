package pe.edu.upeu.sysalmacenfx.pruebas;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sysalmacenfx.servicio.UsuarioService;

@Component
public class UsuarioInicializador implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }

    /*private final UsuarioService usuarioService;

    public UsuarioInicializador(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioService.buscarUsuarioPorNombre("admin") == null) {
            usuarioService.guardarUsuario("admin", "12345", 2l); // Pasa el ID de perfil válido
            System.out.println("Usuario 'admin' creado con contraseña encriptada.");
        } else {
            System.out.println("El usuario 'admin' ya existe en la base de datos.");
        }
    }*/
}
