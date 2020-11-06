package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;


public class TableController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private TextField usernameInput;
    @FXML
    private TextField websiteInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private TextField idInput;
    @FXML
    private Label statusField;

    @FXML
    private TableView<ModelTable> table;
    @FXML
    private TableColumn<ModelTable, String> col_username;
    @FXML
    private TableColumn<ModelTable, String> col_website;
    @FXML
    private TableColumn<ModelTable, String> col_password;
    @FXML
    private TableColumn<ModelTable, Integer> col_id;

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();


    //Without this the images don't load
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //load the table
        loadTable();

    }

    //Close button
    public void closeButtonOnAction() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    //Add button
    public void addButtonOnAction() {
        if (!usernameInput.getText().isBlank() && !passwordInput.getText().isBlank() && !websiteInput.getText().isBlank()) {
            statusField.setText("Adding to database");
            addDatabase();

        } else {
            statusField.setText("Please enter account information");
        }

    }

    //Delete button
    public void deleteButtonOnAction() {
        if (!idInput.getText().isBlank()) {
            statusField.setText("Deleting");
            deleteDatabase();

        } else {
            statusField.setText("Please enter account information");
        }

    }

    //Adding to database
    public void addDatabase() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String addToDatabase = "INSERT INTO password_manager (Website, Username, Password) VALUES ('" + websiteInput.getText() + "', '" + usernameInput.getText() + "', '" + passwordInput.getText() + "')";

        try {

            PreparedStatement statement = connectDB.prepareStatement(addToDatabase);

            statement.execute();
            statusField.setText("Account added!");
            loadTable();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Delete from database
    public void deleteDatabase() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String deleteFromDatabase = "DELETE FROM password_manager WHERE account_id = '" + idInput.getText() + "'";

        try {

            PreparedStatement statement = connectDB.prepareStatement(deleteFromDatabase);

            statement.execute();
            statusField.setText("Account removed!");
            loadTable();


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }


    //Load table
    public void loadTable() {
        table.getItems().clear();
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery("SELECT * FROM password_manager");

            while (queryResult.next()) {
                oblist.add(new ModelTable(queryResult.getInt("account_id"), queryResult.getString("Website"), queryResult.getString("Username"), queryResult.getString("Password")));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        //Load table results
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_website.setCellValueFactory(new PropertyValueFactory<>("website"));
        col_password.setCellValueFactory(new PropertyValueFactory<>("password"));

        table.setItems(oblist);

    }
}
