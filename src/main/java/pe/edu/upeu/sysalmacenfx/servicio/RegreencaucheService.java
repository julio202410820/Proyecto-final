package pe.edu.upeu.sysalmacenfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysalmacenfx.modelo.Regreencauchee;
import pe.edu.upeu.sysalmacenfx.repositorio.RegreencaucheRepository;

import java.util.List;

@Service
public class RegreencaucheService {

    @Autowired
    private RegreencaucheRepository repo; // Repositorio para acceder a la base de datos

    // Guardar un nuevo registro de Regreencauchee
    public Regreencauchee save(Regreencauchee regreencauchee) {
        return repo.save(regreencauchee);  // Guarda el objeto Regreencauchee en la base de datos
    }

    // Listar todos los registros de Regreencauchee
    public List<Regreencauchee> list() {
        return repo.findAll();  // Retorna todos los registros de la tabla reg_reencauche
    }

    // Actualizar un registro existente de Regreencauchee
    public Regreencauchee update(Regreencauchee regreencauchee, String medida) {
        try {
            Regreencauchee existingRegreencauchee = repo.findById(medida).orElse(null);  // Buscar por la medida
            if (existingRegreencauchee != null) {
                existingRegreencauchee.setTipoBanda(regreencauchee.getTipoBanda());  // Actualizar el tipo de banda
                existingRegreencauchee.setCosto(regreencauchee.getCosto());  // Actualizar costo
                existingRegreencauchee.setFecha(regreencauchee.getFecha());  // Actualizar fecha
                return repo.save(existingRegreencauchee);  // Guardar los cambios
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;  // Si no se encuentra el registro, retornar null
    }

    // Eliminar un registro por su medida (clave primaria)
    public void delete(String medida) {
        repo.deleteById(medida);  // Eliminar el registro usando la medida como identificador
    }

    // Buscar un registro por su medida
    public Regreencauchee searchById(String medida) {
        return repo.findById(medida).orElse(null);  // Buscar y retornar el registro por medida
    }
}
