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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoxha
 */
public class AddStudentController implements Initializable {

    ObservableList<String> yearListBox = FXCollections.observableArrayList("1", "2", "3", "4");
    ObservableList<String> facultyListBox = FXCollections.observableArrayList("Economics", "Management", "Marketing", "Computer Science", "Politics", "Psichology");

    @FXML
    private Button backBtn;

    @FXML
    private TextField studentnameTxt;

    @FXML
    private TextField stsurnameTxt;

    @FXML
    private TextField stIDTxt;

    @FXML
    private TextField stdepartmentTxt;

    @FXML
    private TextField stemailTxt;

    @FXML
    private Button addStudentBtn;

    @FXML
    private Label msgLabel;

    @FXML
    private ComboBox facultyBox;

    @FXML
    private ComboBox yearBox;

    @FXML
    public void backto(ActionEvent event) throws IOException {
        Parent view3 = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene scene3 = new Scene(view3);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }

    @FXML
    public void addStudent(ActionEvent event) throws IOException {
        ResultSet rs;
        PreparedStatement ps;
        Connectivity connect = new Connectivity();
        try {

            Connection con = connect.ConnectDb();

            String name = studentnameTxt.getText().trim();
            String surname = stsurnameTxt.getText().trim();
            String id = stIDTxt.getText().trim();
            String year = yearBox.getValue().toString();
            String faculty = facultyBox.getValue().toString();
            String department = stdepartmentTxt.getText().trim();
            String email = stemailTxt.getText().trim();

            if (name.isEmpty() || surname.isEmpty() || id.isEmpty() || year.isEmpty() || department.isEmpty() || email.isEmpty() || faculty.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Library Error");
                alert.setHeaderText("Add Student Error!");
                alert.setContentText("Complete all the fields to insert a student to the system.");

                alert.showAndWait();
            } else {

                String sql2 = "insert into students (fname,sname,regno,year,faculty,department,email,noIssued) value (?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(sql2);

                ps.setString(1, studentnameTxt.getText());
                ps.setString(2, stsurnameTxt.getText());
                ps.setString(3, stIDTxt.getText());
                ps.setString(4, (String) yearBox.getValue());
                ps.setString(5, (String) facultyBox.getValue());
                ps.setString(6, stdepartmentTxt.getText());
                ps.setString(7, stemailTxt.getText());
                ps.setInt(8, 0);

                ps.execute();

                msgLabel.setText("Student added to the system");

            }

        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        yearBox.setValue("Year");
        yearBox.setItems(yearListBox);
        facultyBox.setValue("Choose faculty");
        facultyBox.setItems(facultyListBox);
    }

}
