/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfx;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoxha
 */
public class SignUpController implements Initializable {

    ResultSet rs;
    PreparedStatement ps;

    ObservableList<String> questionBoxList = FXCollections.observableArrayList("What is your favorite color?", "In what city were you born?", "What is your pets name?", "Which high school did you attend?", "What is your nickname?");

    @FXML
    private TextField nametxt;

    @FXML
    private TextField lasttxt;

    @FXML
    private TextField idtxt;

    @FXML
    private TextField usernametxt;

    @FXML
    private PasswordField passtxt;

    @FXML
    private ComboBox questiontxt;

    @FXML
    private PasswordField ansertxt;

    @FXML
    private Button signupBtn;

    @FXML
    private Label createLabel;

    @FXML
    private Button backBtn;

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        Connectivity connect = new Connectivity();

        try {

            Connection con = Connectivity.ConnectDb();

            String name = nametxt.getText().trim();
            String lastname = lasttxt.getText().trim();
            String id = idtxt.getText().trim();
            String username = usernametxt.getText().trim();
            String password = passtxt.getText().trim();
            String question = questiontxt.getValue().toString();
            String answer = ansertxt.getText().trim();

            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || lastname.isEmpty() || id.isEmpty() || question.isEmpty() || answer.isEmpty()) {
                createLabel.setText("Please complete all the fills");
            } else {
                if (password.length() < 8) {
                    createLabel.setText("Password is too weak, please choose atleast 8 characters");
                } else {

                    String sql = "select * from admins where username = ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, username);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        createLabel.setText("Username already taken, please try another username");
                    } else {

                        String sql2 = "insert into admins (firstname,secondname,regno,username,password,security,answer) values(?,?,?,?,?,?,?)";
                        ps = con.prepareStatement(sql2);

                        ps.setString(1, nametxt.getText().trim());
                        ps.setString(2, lasttxt.getText().trim());
                        ps.setString(3, idtxt.getText().trim());
                        ps.setString(4, usernametxt.getText().trim());
                        ps.setString(5, passtxt.getText().trim());
                        ps.setString(6, questiontxt.getValue().toString());
                        ps.setString(7, ansertxt.getText().trim());

                        ps.execute();

                        createLabel.setText("Account successfully registered");

                    }
                }
            }
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }

    @FXML
    public void backtosignin(ActionEvent event) throws IOException {
        Parent view3 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene scene3 = new Scene(view3);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questiontxt.setValue("Choose question");
        questiontxt.setItems(questionBoxList);
    }

}
