package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.sql.*;

public class LoginController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label statusField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;



    //Login status field
    public void loginButtonOnAction() {
        if (!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()) {
            statusField.setText("Validating...");
            validateLogin();
        } else {
            statusField.setText("Please enter credentials.");
        }

    }

    //Create account hyperlink
    public void accountHyperOnAction() throws SQLException, IOException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String query = "SELECT * FROM user_account WHERE account_id = '1'";
        Statement check = connectDB.createStatement();
        ResultSet queryResult = check.executeQuery(query);
        if (queryResult.next()) {
            Parent root = FXMLLoader.load(getClass().getResource("accExistsPopup.fxml"));
            Stage aeStage = new Stage();
            aeStage.initStyle(StageStyle.DECORATED);
            aeStage.setScene(new Scene(root, 300, 200));
            aeStage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("newAccount.fxml"));
            Stage aeStage = new Stage();
            aeStage.initStyle(StageStyle.DECORATED);
            aeStage.setScene(new Scene(root, 300, 200));
            aeStage.show();
        }

    }


    //Cancel/close button
    public void cancelButtonOnAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    //Login verification
    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + enterPasswordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    statusField.setText("Verified!");
                    accountTableView();
                    Stage stage = (Stage) cancelButton.getScene().getWindow();
                    stage.close();
                } else {
                    statusField.setText("Invalid credentials. Please try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    //Change scene once logged in
    public void accountTableView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("accountTable.fxml"));
            Stage tableStage = new Stage();
            tableStage.initStyle(StageStyle.UNDECORATED);
            tableStage.setScene(new Scene(root, 520, 400));
            WindowStyle.allowDrag(root, tableStage);
            tableStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }
    }
}
