package pe.edu.upeu.sysalmacenfx.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysalmacenfx.modelo.Usuario;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Agrega el método para buscar por nombre de usuario
    Usuario findByUser(String user);
}
