package com.example.coursework.controllers.programs;

import com.example.coursework.controllers.abstracts.BaseController;
import com.example.coursework.controllers.HomePageController;
import com.example.coursework.dao.program.ProgramDao;
import com.example.coursework.dao.program.ProgramDaoImpl;
import com.example.coursework.database.Const;
import com.example.coursework.dto.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TVProgramController extends BaseController implements Initializable {

    @FXML
    private ImageView addDataImage;
    @FXML
    private ImageView deleteDataImage;
    @FXML
    private ImageView editDataImage;
    @FXML
    private ImageView refreshTableImage;
    @FXML
    private TableColumn<Program, Integer> programLimitCol;
    @FXML
    private TableColumn<Program, Integer> programNumberCol;
    @FXML
    private TableColumn<Program, Double> programRateCol;
    @FXML
    private TableColumn<Program, Double> programRatingCol;
    @FXML
    private TableColumn<Program, String> programTitleCol;
    @FXML
    private TableView<Program> programsTable;
    private final ProgramDao programDao = new ProgramDaoImpl();

    @FXML
    void deleteData() {
        selectedProgram = programsTable.getSelectionModel().getSelectedItem();
        if (selectedProgram != null) {
            programDao.delete(selectedProgram);
            refreshTable();
        } else {
            alert.setHeaderText(null);
            alert.setContentText("Щоб видалити запис спочатку оберіть його!");
            alert.showAndWait();
        }
    }

    @FXML
    void editData() {
        selectedProgram = programsTable.getSelectionModel().getSelectedItem();
        if (selectedProgram != null) {
            openNewScene("Редагування телепередачі","/com/example/coursework/editTvProgram.fxml");
        } else {
            alert.setHeaderText(null);
            alert.setContentText("Щоб оновити запис спочатку оберіть його!");
            alert.showAndWait();
        }
    }

    @FXML
    void getAddView()  {
        openNewScene("Додавання телепередачі","/com/example/coursework/addTvProgram.fxml");
    }

    @FXML
    void refreshTable() {
        ObservableList<Program> observableAgentList = FXCollections.observableArrayList(programDao.findAll());
        programsTable.setItems(observableAgentList);
    }

    @FXML
    void showAddTip() {
        if(addDataImage.isVisible()){
            Tooltip tooltip = new Tooltip("Додати дані");
            Tooltip.install(addDataImage, tooltip);
        }
    }
    @FXML
    void showDeleteTip() {
        if(deleteDataImage.isVisible()){
            Tooltip tooltip = new Tooltip("Видалити дані");
            Tooltip.install(deleteDataImage, tooltip);
        }
    }
    @FXML
    void showEditTip() {
        if(editDataImage.isVisible()){
            Tooltip tooltip = new Tooltip("Редагувати дані");
            Tooltip.install(editDataImage, tooltip);}
    }
    @FXML
    void showRefreshTip() {
        if(refreshTableImage.isVisible()){
            Tooltip tooltip = new Tooltip("Оновити таблицю");
            Tooltip.install(refreshTableImage, tooltip);
        }
    }

    private void loadData() {
        List<Program> programList = programDao.findAll();
        // Convert the list to an ObservableList (required for TableView)
        ObservableList<Program> observableAgentList = FXCollections.observableArrayList(programList);

        // Set the cell value factories for each column
        programNumberCol.setCellValueFactory(new PropertyValueFactory<>(Const.PROGRAM_ID));
        programLimitCol.setCellValueFactory(new PropertyValueFactory<>(Const.PROGRAM_AD_LIMIT_PER_DAY));
        programRateCol.setCellValueFactory(new PropertyValueFactory<>(Const.PROGRAM_AD_RATE));
        programRatingCol.setCellValueFactory(new PropertyValueFactory<>(Const.PROGRAM_RATING));
        programTitleCol.setCellValueFactory(new PropertyValueFactory<>(Const.PROGRAM_NAME));

        // Populate the table with the data
        programsTable.setItems(observableAgentList);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        displayFunctional();
        if(HomePageController.authenticatedCustomer.isAdmin()){
            addDataImage.setVisible(true);
            editDataImage.setVisible(true);
            refreshTableImage.setVisible(true);
            deleteDataImage.setVisible(true);
        }
    }
}