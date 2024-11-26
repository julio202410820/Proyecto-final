package pe.edu.upeu.sysalmacenfx.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sysalmacenfx.componente.StageManager;
import pe.edu.upeu.sysalmacenfx.componente.Toast;
import pe.edu.upeu.sysalmacenfx.dto.SessionManager;
import pe.edu.upeu.sysalmacenfx.modelo.Usuario;
import pe.edu.upeu.sysalmacenfx.servicio.UsuarioService;

import java.io.IOException;

@Component
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ApplicationContext context;

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtClave;
    @FXML
    private Button btnIngresar;

    @FXML
    public void login(ActionEvent event) throws IOException {
        try {

            // Autentica el usuario con el servicio
            Usuario usuario = usuarioService.loginUsuario(txtUsuario.getText(), txtClave.getText());

            if (usuario != null) {
                // Manejo de sesión si la autenticación es exitosa
                SessionManager.getInstance().setUserId(usuario.getIdUsuario());
                SessionManager.getInstance().setUserName(usuario.getUser());
                SessionManager.getInstance().setNombrePerfil(usuario.getIdPerfil().getNombre());

                // Cargar la pantalla principal si el login es exitoso
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/guimainfx.fxml"));
                loader.setControllerFactory(context::getBean);
                Parent mainRoot = loader.load();
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getBounds();
                Scene mainScene = new Scene(mainRoot, bounds.getWidth(), bounds.getHeight() - 30);
                mainScene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.getIcons().add(new Image(getClass().getResource("/img/store.png").toExternalForm()));
                stage.setScene(mainScene);
                stage.setTitle("Pantalla Principal");
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setResizable(true);
                StageManager.setPrimaryStage(stage);
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
                stage.show();
            } else {
                // Muestra un mensaje de error si la autenticación falla
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                double width = stage.getWidth() * 2;
                double height = stage.getHeight() / 2;
                Toast.showToast(stage, "Credenciales inválidas, intente nuevamente", 2000, width, height);
            }
        } catch (Exception e) {
            System.out.println("Error en la autenticación: " + e.getMessage());
        }
    }
    @FXML
    public void registrarUsuario(ActionEvent event) throws IOException {
        try {
            // Cargar la vista de registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_registro.fxml"));
            loader.setControllerFactory(context::getBean); // Para usar Spring como controlador de dependencias
            Parent registroRoot = loader.load();

            // Configurar la nueva escena
            Scene registroScene = new Scene(registroRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(registroScene);
            stage.setTitle("Registro de Usuario");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
