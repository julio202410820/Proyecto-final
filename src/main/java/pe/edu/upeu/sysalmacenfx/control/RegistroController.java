package pe.edu.upeu.sysalmacenfx.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sysalmacenfx.servicio.UsuarioService;

import java.io.IOException;

@Component
public class RegistroController {

    @Autowired
    private ApplicationContext context;

    @FXML
    private TextField usuary;

    @FXML
    private PasswordField contra;

    private final UsuarioService usuarioService;

    @Autowired
    public RegistroController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @FXML
    public void salirr(ActionEvent event) throws IOException {
        try {
            // Cargar la vista de inicio de sesión
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
            loader.setControllerFactory(context::getBean); // Para usar Spring como controlador de dependencias
            Parent loginRoot = loader.load();

            // Configurar la nueva escena
            Scene loginScene = new Scene(loginRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(loginScene);
            stage.setTitle("Inicio de Sesión");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void nuevoUser (ActionEvent event) {
        // Obtener valores de TextField y PasswordField
        String username = usuary.getText();
        String password = contra.getText();

        // Verificar si el usuario ya existe
        if (usuarioService.buscarUsuarioPorNombre(username) == null) {
            // Guardar el usuario con la contraseña encriptada
            usuarioService.guardarUsuario(username, password, 2L); // Reemplaza "2L" con el ID de perfil adecuado

            System.out.println("Usuario '" + username + "' creado con contraseña encriptada.");
        } else {
            System.out.println("El usuario '" + username + "' ya existe en la base de datos.");
        }
    }
}