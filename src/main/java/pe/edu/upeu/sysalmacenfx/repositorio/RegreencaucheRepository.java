package pe.edu.upeu.sysalmacenfx.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upeu.sysalmacenfx.modelo.Regreencauchee;

public interface RegreencaucheRepository extends JpaRepository<Regreencauchee, String> {
    // Métodos adicionales si es necesario
}
