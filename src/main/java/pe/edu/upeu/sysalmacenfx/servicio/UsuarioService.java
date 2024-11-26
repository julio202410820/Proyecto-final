package pe.edu.upeu.sysalmacenfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysalmacenfx.modelo.Cliente;
import pe.edu.upeu.sysalmacenfx.modelo.Perfil;
import pe.edu.upeu.sysalmacenfx.modelo.Usuario;
import pe.edu.upeu.sysalmacenfx.repositorio.ClienteRepository;
import pe.edu.upeu.sysalmacenfx.repositorio.PerfilRepository;
import pe.edu.upeu.sysalmacenfx.repositorio.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    public Usuario save(Usuario to) {
        return repo.save(to);
    }

    public List<Usuario> list() {
        return repo.findAll();
    }

    public Usuario update(Usuario to, Long id) {
        try {
            Usuario toe = repo.findById(id).orElse(null);
            if (toe != null) {
                toe.setClave(to.getClave());
                return repo.save(toe);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;

    }

    public Usuario guardarUsuario(String nombreUsuario, String clave, Long idPerfil) {
        // Busca el perfil por su ID
        Perfil perfil = perfilRepository.findById(idPerfil)
                .orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));

        // Encripta la contraseña antes de guardarla
        String claveEncriptada = passwordEncoder.encode(clave);

        // Crea el usuario con el perfil asignado
        Usuario usuario = Usuario.builder()
                .user(nombreUsuario)
                .clave(claveEncriptada)
                .estado("activo")
                .idPerfil(perfil) // Asigna el perfil al usuario
                .build();

        return usuarioRepository.save(usuario);
    }
    public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
        return usuarioRepository.findByUser(nombreUsuario); // Usa el método de UsuarioRepository
    }
    public Usuario searchById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Usuario loginUsuario(String username, String password) {
        // Busca el usuario en la base de datos por el nombre de usuario
        Usuario usuario = usuarioRepository.findByUser (username);

        // Verifica si el usuario existe y si la contraseña es correcta
        if (usuario != null && passwordEncoder.matches(password, usuario.getClave())) {
            return usuario; // Retorna el usuario si la autenticación es exitosa
        }
        return null;
    }
}
