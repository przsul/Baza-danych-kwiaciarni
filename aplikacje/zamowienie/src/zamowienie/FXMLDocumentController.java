/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zamowienie;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author przem
 */
public class FXMLDocumentController implements Initializable {
    @FXML private TableColumn<Produkt, String> CenaJednostkowa_TableColumn;
    @FXML private TableColumn<Produkt, String> Typ_TableColumn;
    @FXML private PasswordField pass_TextField;
    @FXML private TableView<Produkt> produkty_TableView;
    @FXML private TableColumn<Produkt, String> Sklad_TableColumn;
    @FXML private TableColumn<Produkt, String> StanMagazynowy_TableColumn;
    @FXML private TableColumn<Produkt, String> Trwalosc_TableColumn;
    @FXML private TableColumn<Produkt, String> Rozmiar_TableColumn;
    @FXML private TableColumn<Produkt, String> Material_TableColumn;
    @FXML private Button loguj_Button;
    @FXML private TableColumn<Produkt, String> Nazwa_TableColumn;
    @FXML private TextField login_TextField;
    @FXML private TextField ilosc_TextField;
    @FXML private Button dodaj_Button;
    @FXML private Button zaplac_Button;
    @FXML private Label suma_Label;
    @FXML private Label ilosc_Label;
    
    Connection con;
    Statement st;
    ResultSet rs;
    double sumaLacznie = 0;
    Map<String, Double> produktMap = new HashMap<String, Double>();
    String klient;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if(event.getSource() == loguj_Button) {
            String login = login_TextField.getText();
            String pass = pass_TextField.getText();
            try {
                st = con.createStatement();
                rs = st.executeQuery("SELECT KlientID, Login, Haslo FROM klienci");
                while(rs.next()) {
                    if(rs.getString(2).equals(login) && rs.getString(3).equals(pass)) {
                        klient = rs.getString(1);
                        produkty_TableView.setDisable(false);
                        ilosc_Label.setDisable(false);
                        ilosc_TextField.setDisable(false);
                        dodaj_Button.setDisable(false);
                        zaplac_Button.setDisable(false);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("błąd SQL - " + ex);
            }
        }
        
        if(event.getSource() == dodaj_Button) {
            double ilosc = Integer.parseInt(ilosc_TextField.getText());
            Produkt produkt = produkty_TableView.getSelectionModel().getSelectedItem();
            
            sumaLacznie += (ilosc * Double.parseDouble(produkt.getCenaJednostkowa()));
            DecimalFormat df = new DecimalFormat("#.##");
            sumaLacznie = Double.parseDouble(df.format(sumaLacznie));
            
            produktMap.put(produkt.getProduktID(), ilosc);
            
            suma_Label.setText("Suma łącznie w koszyku: " + Double.toString(sumaLacznie) + " zł");
            suma_Label.setVisible(true);
        }
        
        if(event.getSource() == zaplac_Button) {
            produktMap.forEach((key, value) -> {
                try {
                    String query = "{call zamowienie(?, ?, ?, ?, ?, ?, ?)}"; 
                    CallableStatement statement = con.prepareCall(query);  
                    statement.setString(1, klient);
                    statement.setString(2, key);
                    statement.setString(3, "MiastoTest");
                    statement.setString(4, "UlicaTest");
                    statement.setInt(5, 1);
                    statement.setString(6, "00-000");
                    statement.setDouble(7, value);
                    statement.execute();
                    suma_Label.setText(suma_Label.getText() + " - Zamówione!");
                    produkty_TableView.setItems(getProducts()); 
                } catch(SQLException ex) {
                    System.out.println("błąd SQL - " + ex);
                }  
            });
        }
    }
    
    public ObservableList<Produkt> getProducts() {
        ObservableList<Produkt> produkty = FXCollections.observableArrayList();
      
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM produkty");
            while(rs.next())
                produkty.add(new Produkt(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
        } catch (SQLException ex) {
            System.out.println("błąd SQL - " + ex);
        }
        
        return produkty;
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/Kwiaciarnia", "root", "projektbazy");
            CenaJednostkowa_TableColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("CenaJednostkowa"));
            StanMagazynowy_TableColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("StanMagazynowy"));
            Typ_TableColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Typ"));
            Sklad_TableColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Sklad"));
            Trwalosc_TableColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Trwalosc"));
            Rozmiar_TableColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Rozmiar"));
            Material_TableColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Material"));
            Nazwa_TableColumn.setCellValueFactory(new PropertyValueFactory<Produkt, String>("Nazwa"));
            produkty_TableView.setItems(getProducts());            
        } catch (SQLException ex) {
            if(ex.getErrorCode() == 1045)
                System.out.println("błąd SQL - " + "Błąd logowania");
            else
                System.out.println("błąd SQL - " + ex);
        }
    }    
    
}
