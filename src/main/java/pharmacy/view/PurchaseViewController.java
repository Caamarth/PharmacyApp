package pharmacy.view;

import java.io.IOException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pharmacy.DateUtil;
import pharmacy.MainApp;
import pharmacy.DAO.PurchaseService;
import pharmacy.model.Medication;
import pharmacy.model.Purchase;

public class PurchaseViewController {
	
	private static Logger logger = LoggerFactory.getLogger(PurchaseViewController.class);
	
	@FXML 
	private Button purchaseToMainButton;
	@FXML
	private Label purchaseIdLabel;
	@FXML
	private Label purchaseDateLabel;
	@FXML
	private Label purchaseTajLabel;
	@FXML
	private Label purchaseSumLabel;
	@FXML
	private TableView<Medication> purchasedMedTableView;
	@FXML
	private TableColumn<Medication, String> medNameTableColumn;
	@FXML
	private TableView<Purchase> purchaseTableView;
	@FXML
	private TableColumn<Purchase, LocalDate> purchaseDateColumn;
	@FXML
	private TableColumn<Purchase, Integer> purchaseIdColumn;
	@FXML
	private TableColumn<Purchase, Integer> purchaseSumColumn;
	
	private MainApp mainApp;
	private PurchaseService purchaseService;
	
	private BorderPane rootView;
	
	public PurchaseViewController(){

	}
	
	@FXML
	public void initialize(){
		purchaseIdLabel.setText("");
		purchaseDateLabel.setText("");
		purchaseTajLabel.setText("");
		purchaseSumLabel.setText("");
		
		
		purchaseIdColumn.setCellValueFactory(cd -> cd.getValue().getIdProperty().asObject());
		purchaseDateColumn.setCellValueFactory(cd -> cd.getValue().getDateProperty());
		purchaseSumColumn.setCellValueFactory(cd -> cd.getValue().getPriceProperty().asObject());
		
		purchaseTableView.getSelectionModel().selectedItemProperty().addListener((o, old, _new) -> PurchaseDetails(_new));
		
		purchaseToMainButton.setOnMouseClicked(e -> loadMainView());
	}
	
	public void PurchaseDetails(Purchase purchase){
		if(purchase != null){
			ObservableList<Medication> medications = FXCollections.observableArrayList(purchase.getMedicationList());
			purchaseIdLabel.setText(Integer.toString(purchase.getId()));
			purchaseDateLabel.setText(DateUtil.format(purchase.getDate()));
			purchaseTajLabel.setText(purchase.getPatient().getTajszam());
			purchaseSumLabel.setText(Integer.toString(purchase.getPrice()));
			purchasedMedTableView.setItems(medications);
			medNameTableColumn.setCellValueFactory(cd -> cd.getValue().getNameProperty());
		}
		
	}
	
	public MainApp getMainApp(){
		return mainApp;
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	public PurchaseService getPurchaseService() {
		return purchaseService;
	}

	public void setPurchaseService(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
		purchaseTableView.setItems(purchaseService.getAllPurchases());
	}

	private void loadRootView(){
		Stage primaryStage = (Stage) purchaseToMainButton.getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader();
		try{
			loader.setLocation(MainApp.class.getResource("/rootLayout.fxml"));
			rootView = (BorderPane) loader.load();
			Scene scene = new Scene(rootView);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void loadMainView(){
		loadRootView();
		
		FXMLLoader loader = new FXMLLoader();
		try{
			loader.setLocation(MainApp.class.getResource("/MainScene.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			rootView.setCenter(pane);
			logger.info("Vissza a főoldalra a PurchaseView-ból.");
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
