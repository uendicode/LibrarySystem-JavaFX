/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryfx;

import java.util.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hoxha
 */
public class IssueBookController implements Initializable {

    private static final int MAX_BOOK_TO_ISSUE = 3;

    @FXML
    private TextField btitleTxt;

    @FXML
    private TextField searchBookCode;

    @FXML
    private TextField bpriceTxt;

    @FXML
    private TextField bcategoryTxt;

    @FXML
    private TextField bpublisherTxt;

    @FXML
    private TextField beditionTxt;

    @FXML
    private Label msgLabel;

    @FXML
    private Button searchBookBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField searchStudentID;

    @FXML
    private Button searchStudentBtn;

    @FXML
    private TextField stNameTxt;

    @FXML
    private TextField stSurnameTxt;

    @FXML
    private TextField stYearTxt;

    @FXML
    private TextField stMajorTxt;

    @FXML
    private TextField stDepartmentTxt;

    @FXML
    private TextField stEmailTxt;

    @FXML
    private DatePicker issueDate;

    @FXML
    private Button issueBookBtn;

    public void clear() {
        searchBookCode.setDisable(false);
        searchStudentID.setDisable(false);
        searchBookCode.setText("");
        btitleTxt.setText("");
        bpriceTxt.setText("");
        bcategoryTxt.setText("");
        bpublisherTxt.setText("");
        beditionTxt.setText("");

        searchStudentID.setText("");
        stNameTxt.setText("");
        stSurnameTxt.setText("");
        stYearTxt.setText("");
        stMajorTxt.setText("");
        stDepartmentTxt.setText("");
        stEmailTxt.setText("");

        issueDate.setValue(null);
    }

    @FXML
    public void searchBook(ActionEvent event) throws IOException {
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connectivity connect = new Connectivity();
        String id = searchBookCode.getText();

        String sql = "select *from books where bookid = '" + id + "' ";
        try {
            Connection con = Connectivity.ConnectDb();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                msgLabel.setText("Book Available");
                searchBookCode.setDisable(false);
                btitleTxt.setText(rs.getString(2));
                bpriceTxt.setText(rs.getString(4));
                bcategoryTxt.setText(rs.getString(5));
                bpublisherTxt.setText(rs.getString(6));
                beditionTxt.setText(rs.getString(7));

                rs.close();
                pst.close();
            } else {
                msgLabel.setText("Book Not Available");
                //JOptionPane.showMessageDialog(null,"The book does not exist, check the book ID well");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    public void searchStudent(ActionEvent event) throws IOException {
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connectivity connect = new Connectivity();
        String stud = searchStudentID.getText();

        String sql = "select *from students where regno  = '" + stud + "'";
        try {
            Connection con = Connectivity.ConnectDb();
            pst = con.prepareStatement(sql);

            rs = pst.executeQuery();

            if (rs.next()) {
                stNameTxt.setText(rs.getString(1));
                stSurnameTxt.setText(rs.getString(2));
                stYearTxt.setText(rs.getString(4));
                stMajorTxt.setText(rs.getString(5));
                stDepartmentTxt.setText(rs.getString(6));
                stEmailTxt.setText(rs.getString(7));
                searchStudentID.setDisable(true);
                rs.close();
                pst.close();
            } else {
                JOptionPane.showMessageDialog(null, "The student does not exist in the database");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    void issueBook(ActionEvent event) throws IOException {

        conne();
        clear();

    }

    public void conne() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        Connectivity connect = new Connectivity();

        String reg = searchStudentID.getText();
        String sql = "select *from students where regno='" + reg + "'";
        String sql3 = "update students set noIssued = ? where regno='" + reg + "' ";
        try {
            Connection con = Connectivity.ConnectDb();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            int noIssued = rs.getInt(8);

            //int noIssued = Integer.parseInt(no);
            if (noIssued < MAX_BOOK_TO_ISSUE) {
                issue();
                PreparedStatement prepstm = con.prepareStatement(sql3);
                //String cnt = Integer.toString(counter);
                int counter = noIssued;
                counter++;
                prepstm.setInt(1, counter);
                prepstm.executeUpdate();
                pst.close();
                rs.close();
            } else {
                JOptionPane.showMessageDialog(null, "You cannot be issued with another book" + "\nYou already have 3 books");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void issue() {
        ResultSet rs = null, rs1, rs2, rs3 = null;
        PreparedStatement pst = null, pst1, pst2, pst3 = null;
        Connectivity connect = new Connectivity();

        String reg = searchStudentID.getText();
        String bId = searchBookCode.getText();

        String sql1 = "select *from students where regno = '" + reg + "'";
        String sql2 = "select *from books where bookid = '" + bId + "'";
        String sql4 = "select counter from students where regno = '" + reg + "'";
        String sql = "insert into issued (names,regno,yearOfStudy,faculty,department,email,dateIssued,bookId,booktitle,price,category,publishers,edition) value(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            Connection con = Connectivity.ConnectDb();
            pst1 = con.prepareStatement(sql1);
            rs1 = pst1.executeQuery();
            pst2 = con.prepareStatement(sql1);
            rs2 = pst2.executeQuery();
            if (rs1.next() && rs2.next()) {

                try {

                    //pst3 = conn.prepareStatement(sql4);
                    //rs3 = pst3.executeQuery();
                    //String numBook = rs3.getString(8);
                    //int numOfBook = Integer.parseInt(numBook);
                    pst = con.prepareStatement(sql);
                    {

                        pst.setString(1, (stNameTxt.getText() + " " + stSurnameTxt.getText()));
                        pst.setString(2, searchStudentID.getText());
                        pst.setString(3, stYearTxt.getText());
                        pst.setString(4, stMajorTxt.getText());
                        pst.setString(5, stDepartmentTxt.getText());
                        pst.setString(6, stEmailTxt.getText());

                        Date dNow = new Date();
                        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                        String date = ft.format(dNow);

//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String date = sdf.format(issueDate.getValue());
                        pst.setString(7, date);
                        pst.setString(8, searchBookCode.getText());
                        pst.setString(9, btitleTxt.getText());
                        pst.setString(10, bpriceTxt.getText());
                        pst.setString(11, bcategoryTxt.getText());
                        pst.setString(12, bpublisherTxt.getText());
                        pst.setString(13, beditionTxt.getText());

                        pst.execute();
                        pst.close();
                        JOptionPane.showMessageDialog(null, "Book issued Successfully");

                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Either the book ID or the Student Registration number is incorrect");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "sasa");
        }
        try {
            Connection con = Connectivity.ConnectDb();
        String sql3 = "DELETE FROM books where bookid = '"+bId+"'";
        pst3 = con.prepareStatement(sql3);
        pst3.executeUpdate(sql3);
        pst3.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, "The book is removed from the shelf");
            }
        }
    }

     @FXML
    public void backto(ActionEvent event) throws IOException {
        Parent view3 = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene scene3 = new Scene(view3);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
