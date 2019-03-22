/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zamowienie;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author przem
 */
public class Produkt {
    private SimpleStringProperty ProduktID, DostawcaID, Nazwa, CenaJednostkowa, Typ, Trwalosc, Rozmiar, Sklad, Material, StanMagazynowy;
    
    public Produkt(String ProduktID, String DostawcaID, String Nazwa, String CenaJednostkowa,
            String Typ, String Trwalosc, String Rozmiar, String Sklad, String Material, String StanMagazynowy) {
        this.ProduktID = new SimpleStringProperty(ProduktID);
        this.DostawcaID = new SimpleStringProperty(DostawcaID);
        this.Nazwa = new SimpleStringProperty(Nazwa);
        this.CenaJednostkowa = new SimpleStringProperty(CenaJednostkowa);
        this.Typ = new SimpleStringProperty(Typ);
        this.Trwalosc = new SimpleStringProperty(Trwalosc);
        this.Rozmiar = new SimpleStringProperty(Rozmiar);
        this.Sklad = new SimpleStringProperty(Sklad);
        this.Material = new SimpleStringProperty(Material);
        this.StanMagazynowy = new SimpleStringProperty(StanMagazynowy);
    }

    public String getProduktID() {
        return ProduktID.get();
    }

    public void setProduktID(SimpleStringProperty ProduktID) {
        this.ProduktID = ProduktID;
    }

    public String getDostawcaID() {
        return DostawcaID.get();
    }

    public void setDostawcaID(SimpleStringProperty DostawcaID) {
        this.DostawcaID = DostawcaID;
    }

    public String getNazwa() {
        return Nazwa.get();
    }

    public void setNazwa(SimpleStringProperty Nazwa) {
        this.Nazwa = Nazwa;
    }

    public String getCenaJednostkowa() {
        return CenaJednostkowa.get();
    }

    public void setCenaJednostkowa(SimpleStringProperty CenaJednostkowa) {
        this.CenaJednostkowa = CenaJednostkowa;
    }

    public String getTyp() {
        return Typ.get();
    }

    public void setTyp(SimpleStringProperty Typ) {
        this.Typ = Typ;
    }

    public String getTrwalosc() {
        return Trwalosc.get();
    }

    public void setTrwalosc(SimpleStringProperty Trwalosc) {
        this.Trwalosc = Trwalosc;
    }

    public String getRozmiar() {
        return Rozmiar.get();
    }

    public void setRozmiar(SimpleStringProperty Rozmiar) {
        this.Rozmiar = Rozmiar;
    }

    public String getSklad() {
        return Sklad.get();
    }

    public void setSklad(SimpleStringProperty Sklad) {
        this.Sklad = Sklad;
    }

    public String getMaterial() {
        return Material.get();
    }

    public void setMaterial(SimpleStringProperty Material) {
        this.Material = Material;
    }

    public String getStanMagazynowy() {
        return StanMagazynowy.get();
    }

    public void setStanMagazynowy(SimpleStringProperty StanMagazynowy) {
        this.StanMagazynowy = StanMagazynowy;
    }
    
}
