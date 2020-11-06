package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewAccountController {

    @FXML
    private Button createButton;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label statusField;

    public void createButtonOnAction(ActionEvent event){
        if (!usernameField.getText().isBlank() && !passwordField.getText().isBlank()) {
            try {
                createAccount();
                statusField.setText("Creating account");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        else {
            statusField.setText("Please enter account details");
        }
    }

    public void createAccount() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String createData = "INSERT INTO user_account (account_id, username, password) VALUES ('1', '" + usernameField.getText() + "' , '" + passwordField.getText() + "');";
        PreparedStatement preparedStatement = connectDB.prepareStatement(createData);
        preparedStatement.execute();

    }
}
