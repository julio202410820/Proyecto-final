package pe.edu.upeu.sysalmacenfx.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sysalmacenfx.modelo.Regreencauchee;
import pe.edu.upeu.sysalmacenfx.servicio.RegreencaucheService;

@Component
public class ReenController {

    @FXML
    private TextField medida;

    @FXML
    private TextField Tipbanda;

    @FXML
    private TextField Costo;

    @FXML
    private DatePicker fecha;

    @FXML
    private Label lbnMsg;

    @FXML
    private TableView<Regreencauchee> tableViewReen;

    @FXML
    private TableColumn<Regreencauchee, String> colmedida;
    @FXML
    private TableColumn<Regreencauchee, String> colbanda;
    @FXML
    private TableColumn<Regreencauchee, String> colfecha;
    @FXML
    private TableColumn<Regreencauchee, String> colCosto;
    @FXML
    private TableColumn<Regreencauchee, Void> colAccion;

    private final RegreencaucheService regreencaucheService;
    Long idProductoCE = 0L;

    @Autowired
    public ReenController(RegreencaucheService regreencaucheService) {
        this.regreencaucheService = regreencaucheService;
    }

    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        colmedida.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMedida())); // Cambié getDniruc() por getMedida()
        colbanda.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoBanda())); // Cambié getNombres() por getTipoBanda()
        colfecha.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFecha())); // Cambié getRepLegal() por getFecha()
        colCosto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCosto())); // Cambié getTipoDocumento() por getCosto()

        // Configurar la columna de acciones
        colAccion.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Regreencauchee, Void> call(TableColumn<Regreencauchee, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Editar");
                    private final Button deleteButton = new Button("Eliminar");
                    private final HBox actionButtons = new HBox(5, editButton, deleteButton);

                    {
                        editButton.setOnAction(event -> editarCliente(getTableView().getItems().get(getIndex())));
                        deleteButton.setOnAction(event -> eliminarRegreencauchee(getTableView().getItems().get(getIndex())));
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        });

        // Cargar datos de la base de datos
        cargarDatos();
    }

    private void cargarDatos() {
        tableViewReen.getItems().clear(); // Limpia los datos actuales en la tabla
        tableViewReen.getItems().addAll(regreencaucheService.list()); // Añade todos los registros obtenidos de la base de datos
        idProductoCE = 0L;
    }

    @FXML
    private void guardarSer(ActionEvent event) {
        try {
            // Crear un nuevo objeto Regreencauchee con los valores de los campos
            Regreencauchee nuevoRegreencauchee = new Regreencauchee();
            nuevoRegreencauchee.setMedida(medida.getText());           // Medida
            nuevoRegreencauchee.setTipoBanda(Tipbanda.getText());      // Tipo de Banda
            nuevoRegreencauchee.setFecha(fecha.getValue().toString()); // Fecha como String
            nuevoRegreencauchee.setCosto(Costo.getText());             // Costo

            // Llamar al servicio para guardar el nuevo registro
            regreencaucheService.save(nuevoRegreencauchee);  // Usar el servicio para guardar

            // Actualizar la tabla y mostrar mensaje de éxito
            tableViewReen.getItems().add(nuevoRegreencauchee);
            lbnMsg.setText("Registro guardado exitosamente.");

            // Limpiar los campos de entrada
            limpiarCampos();
        } catch (Exception e) {
            lbnMsg.setText("Error al guardar el registro.");
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelarSer(ActionEvent event) {
        limpiarCampos();
        lbnMsg.setText("Operación cancelada.");
    }

    private void limpiarCampos() {
        medida.clear();
        Tipbanda.clear();
        Costo.clear();
        fecha.setValue(null);
    }

    private void editarCliente(Regreencauchee regreencauchee) {
        // Llenar los campos con los datos del registro seleccionado
        medida.setText(regreencauchee.getMedida());
        Tipbanda.setText(regreencauchee.getTipoBanda());
        Costo.setText(regreencauchee.getCosto());
        fecha.setValue(java.time.LocalDate.parse(regreencauchee.getFecha()));
        idProductoCE = 0L;

        // Actualizar el mensaje
        lbnMsg.setText("Editando registro: " + regreencauchee.getMedida());

        // Puedes implementar una funcionalidad adicional para guardar los cambios
    }

    @FXML
    private void eliminarRegreencauchee(Regreencauchee regreencauchee) {
        try {
            // Usar getMedida() en vez de getId()
            regreencaucheService.delete(regreencauchee.getMedida());  // Elimina por el campo medida
            tableViewReen.getItems().remove(regreencauchee);  // Eliminar de la tabla
            lbnMsg.setText("Registro eliminado con éxito.");
        } catch (Exception e) {
            lbnMsg.setText("Error al eliminar el registro.");
            e.printStackTrace();
        }
    }

}
