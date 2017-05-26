package pharmacy.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pharmacy.MainApp;
import pharmacy.DAO.CartService;
import pharmacy.DAO.MedicationService;
import pharmacy.DAO.PatientService;
import pharmacy.DAO.PurchaseService;
import pharmacy.model.Medication;

public class NewPurchaseViewController {
	
	private static Logger logger = LoggerFactory.getLogger(NewPurchaseViewController.class);
	
	@FXML
	private Button newPurchaseToMainButton;
	@FXML
	private Button submitPurchaseButton;
	@FXML
	private Button addToCartButton;
	@FXML 
	private Button removeFromCartButton;
	@FXML
	private Label currentPriceLabel;
	@FXML
	private TableView<Medication> medicationTableView;
	@FXML
	private TableColumn<Medication, String> MedicationNameTableColumn;
	@FXML
	private TableColumn<Medication, Integer> MedicationUnitpriceTableColumn;
	@FXML
	private TableView<Medication> cartTableView;
	@FXML
	private TableColumn<Medication, String> cartNameTableColumn;
	@FXML
	private TableColumn<Medication, Integer> priceTableColumn;
	@FXML
	private TextField tajTextField;
	
	private MainApp mainApp;
	private BorderPane rootView;
	
	private MedicationService medicationService;
	private CartService cartService;
	private PatientService patientService;
	private PurchaseService purchaseService;
	
	private StringProperty sumValueProperty = new SimpleStringProperty();
	
	public NewPurchaseViewController(){
		
	}
	
	@FXML
	public void initialize(){
		getSumValue();
		currentPriceLabel.textProperty().bind(sumValueProperty);
		
		MedicationNameTableColumn.setCellValueFactory(cd -> cd.getValue().getNameProperty());
		MedicationUnitpriceTableColumn.setCellValueFactory(cd -> cd.getValue().getUnitpriceProperty().asObject());
		
		cartNameTableColumn.setCellValueFactory(cd -> cd.getValue().getNameProperty());
		priceTableColumn.setCellValueFactory(cd -> (cd.getValue().getUnitpriceProperty()).asObject());
		
		newPurchaseToMainButton.setOnMouseClicked(e -> loadMainView());
		addToCartButton.setOnMouseClicked(e -> addToCart(
				medicationTableView.getSelectionModel().getSelectedItem()));
		removeFromCartButton.setOnMouseClicked(e -> removeFromCart(
				cartTableView.getSelectionModel().getSelectedItem()));
		submitPurchaseButton.setOnMouseClicked(e -> submitPurchase());
	}
	
	public MainApp getMainApp(){
		return mainApp;
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	public MedicationService getMedicationService(){
		return this.medicationService;
	}
	
	public CartService getCartService(){
		return this.cartService;
	}
	
	public PatientService getPatientService(){
		return this.patientService;
	}
	
	public PurchaseService getPurchaseService(){
		return this.purchaseService;
	}
	
	public void setMedicationService(MedicationService medicationService){
		this.medicationService = medicationService;
		medicationTableView.setItems(medicationService.getAllMedications());
	}
	
	public void setCartService(CartService cartService){
		this.cartService = cartService;
		cartTableView.setItems(cartService.getCartMedications());
	}
	
	public void setPatientService(PatientService patientService){
		this.patientService = patientService;
	}
	
	public void setPurchaseService(PurchaseService purchaseService){
		this.purchaseService = purchaseService;
	}
	
	public void getSumValue(){
		int value = 0;
		if(cartService != null){
			value = (int) cartService.getCartSumValue();
		}
		
		if(!tajTextField.getText().isEmpty()){
			value = (int) purchaseService.giveDiscount(patientService.getPatientById(patientService.getIdByTaj(tajTextField.getText())).getPatientRank(),
					value);
		}
		sumValueProperty.set(Integer.toString(value));
	}
	
	
	
	private void loadRootView(){
		Stage primaryStage = (Stage) submitPurchaseButton.getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader();
		try{
			loader.setLocation(MainApp.class.getResource("/rootLayout.fxml"));
			rootView = (BorderPane) loader.load();
			Scene scene = new Scene(rootView);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e){
			//e.printStackTrace();
			logger.error("IOException történt!");
		}
	}
	
	private void loadMainView(){
		loadRootView();
		
		FXMLLoader loader = new FXMLLoader();
		try{
			loader.setLocation(MainApp.class.getResource("/MainScene.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			logger.info("Vissza a főoldalra a NewPurchaseView-ból.");
			rootView.setCenter(pane);
		}catch (IOException e){
			//e.printStackTrace();
			logger.error("IOException történt!");
		}
	}
	
	private void addToCart(Medication medication){
		//mainApp.getCartService().getCartMedications().add(medication);
		if(medication != null){
			cartService.addToCart(medication);
			getSumValue();
		}
	}
	
	private void removeFromCart(Medication medication){
		//int selectedIndex = cartTableView.getSelectionModel().getSelectedIndex();
		//Medication selectedMed = cartTableView.getSelectionModel().getSelectedItem();
		if(medication != null){
			//mainApp.getCartService().getCartMedications().remove(selectedIndex);
//			cartTableView.getItems().remove(selectedIndex);
			cartService.removeFromCart(medication);
			getSumValue();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("");
	        alert.setHeaderText("Nincs kiválasztott gyógyszer!");
	        alert.setContentText("Válasszon ki egy gyógyszert a törléshez!");

	        alert.showAndWait();
		}
	}
	
	private void submitPurchase(){
			String text = tajTextField.getText();
			int num = patientService.getIdByTaj(text);
		if(num != 0){
			patientService.setPatientRank(patientService.getIdByTaj(tajTextField.getText()), Integer.parseInt(sumValueProperty.get()));
			ObservableList<Medication> purchaseMeds = FXCollections.observableArrayList(mainApp.getCartService().getCartMedications());
			List<Medication> meds = medicationService.convertToList(purchaseMeds);
			purchaseService.addPurchase(
					patientService.getPatientById(patientService.getIdByTaj(tajTextField.getText())),
					Integer.parseInt(sumValueProperty.get()),
					LocalDate.now(),
					meds);
			logger.info("Új vásárlás történt!");
			loadMainView();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("");
	        alert.setHeaderText("A beteg nem szerepel az adatbázisban!");
	        alert.setContentText("Kérjük, regisztrálja a beteget a folytatáshoz!");

	        alert.showAndWait();
		}
	}
	
}
