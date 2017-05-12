package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> boxPartenza;

    @FXML
    private ComboBox<Fermata> boxArrivo;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	Fermata partenza= boxPartenza.getValue();
    	Fermata arrivo= boxArrivo.getValue();
    	
    	String percorso=model.stampaPercorso(partenza, arrivo);
    	
    	txtResult.setText(percorso);
    	
    	txtResult.appendText("\nTempo di percorrenza stimato: "+model.getTempo());
    }
    
    public void setModel(Model model){
    	this.model=model;
    	
    	model.getGrafo();
    	
    	boxPartenza.getItems().addAll(model.getFermate());
    	boxArrivo.getItems().addAll(model.getFermate());
 
    }

    @FXML
    void initialize() {
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert boxArrivo != null : "fx:id=\"boxArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }
}

