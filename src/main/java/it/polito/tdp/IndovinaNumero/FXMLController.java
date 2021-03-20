/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.IndovinaNumero;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import it.polito.tdp.IndovinaNumero.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FXMLController {
	
	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private MenuButton btnLivello;

    @FXML
    private MenuItem btnFacile;

    @FXML
    private MenuItem btnMedio;

    @FXML
    private MenuItem btnDifficile;
    
    @FXML // fx:id="layoutTentativo"
    private HBox layoutTentativo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNuovaPartita"
    private Button btnNuovaPartita; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativi"
    private TextField txtTentativi; // Value injected by FXMLLoader

    @FXML // fx:id="txtTentativoUtente"
    private TextField txtTentativoUtente; // Value injected by FXMLLoader

    @FXML // fx:id="btnProva"
    private Button btnProva; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    private ProgressBar barProgress;
    
    @FXML
    void doNuovaPartita(ActionEvent event) {
    	//gestione dell'interfaccia
    	this.txtRisultato.clear();
    	doLivelloDifficolta(event);
    }

    @FXML
    void doLivelloDifficolta(ActionEvent event) {
    	btnLivello.setDisable(false);
    	txtRisultato.setText("Gioco indovina numero\t\tDifficoltà:\nFacile --> 7 tentativi per indovinare un numero da 1 a 100\n"
    			+"Medio --> 9 tentativi per indovinare un numero da 1 a 250\nDifficile --> 10 tentativi per indovinare un numero da 1 a 1000");
    }
    
    @FXML
    void doFacile(ActionEvent event) {
    	this.model.nuovaPartitaFacile();;
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    	this.barProgress.setProgress(0);
    }

    @FXML
    void doMedio(ActionEvent event) {
    	this.model.nuovaPartitaMedio();;
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    	this.barProgress.setProgress(0);
    }
    
    @FXML
    void doDifficile(ActionEvent event) {
    	this.model.nuovaPartitaDifficile();;
    	this.txtTentativi.setText(Integer.toString(this.model.getTMAX()));
    	this.layoutTentativo.setDisable(false);
    	this.barProgress.setProgress(0);
    }	
    
    @FXML
    void doTentativo(ActionEvent event) {
    	//lettura input dell'utente
    	String ts = txtTentativoUtente.getText();
    	
    	int tentativo;
    	try {
    		tentativo = Integer.parseInt(ts);
    	}catch(NumberFormatException e) {
    		txtRisultato.setText("Devi inserire un numero!");
    		return;
    	}

    	this.txtTentativoUtente.setText("");
    	
    	int result;
    	try {
    		result = this.model.tentativo(tentativo);
    		barProgress.setProgress((double)model.getTentativiFatti()/8);
    	}catch(IllegalStateException se) {
    		this.txtRisultato.setText(se.getMessage());
    		this.layoutTentativo.setDisable(true);
    		return;
    	}catch(InvalidParameterException pe) {
    		this.txtRisultato.setText(pe.getMessage());
    		return;
    	}
    	
    	this.txtTentativi.setText(Integer.toString(model.getTMAX()-model.getTentativiFatti()));
    
    	if(result==0) {
    		//HO INDOVINATO!
    		txtRisultato.setText("HAI VINTO CON " + this.model.getTentativiFatti() + " TENTATIVI");
    		this.layoutTentativo.setDisable(true);
    		return;
    	}else if(result<0) {
    		txtRisultato.setText("TENTATIVO TROPPO BASSO");
    	}else {
    		txtRisultato.setText("TENTATIVO TROPPO ALTO");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnNuovaPartita != null : "fx:id=\"btnNuovaPartita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnLivello != null : "fx:id=\"btnLivello\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnFacile != null : "fx:id=\"btnFacile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMedio != null : "fx:id=\"btnMedio\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDifficile != null : "fx:id=\"btnDifficile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativi != null : "fx:id=\"txtTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTentativoUtente != null : "fx:id=\"txtTentativoUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert barProgress != null : "fx:id=\"barProgress\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
