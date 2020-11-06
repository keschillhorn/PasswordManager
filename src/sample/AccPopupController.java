package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Button;

public class AccPopupController {

    @FXML
    private Button deleteButton;
    //Delete button
    public void deleteButtonOnAction(ActionEvent event) {
        try {
            deleteData();
            deleteButton.setText("DATA DELETED (CLOSE WINDOW)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            deleteButton.setText("FAILED TO DELETE DATA");
        }
    }

    //Delete data and create new account
    public void deleteData() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String deleteAc = "DELETE FROM user_account";
        String deletePa = "DELETE FROM password_manager";
        PreparedStatement clearAc = connectDB.prepareStatement(deleteAc);
        PreparedStatement clearPa = connectDB.prepareStatement(deletePa);
        clearAc.execute();
        clearPa.execute();

    }
}
