package pharmacy.view;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pharmacy.MainApp;

public class MainSceneController {
	
	private static Logger logger = LoggerFactory.getLogger(MainSceneController.class);
	
	@FXML
	private Button newPurchaseButton;
	@FXML
	private Button patientsButton;
	@FXML
	private Button medicationsButton;
	@FXML
	private Button purchasesButton;
	@FXML
	private Button exitButton;
	
	private BorderPane rootView;
	
	private MainApp mainApp;

	public MainSceneController(){
		
	}
	
	@FXML
	private void initialize(){
		mainApp = new MainApp();	
		mainApp.setPatientService(mainApp.getPatientService());
		mainApp.setMedicationService(mainApp.getMedicationService());
		mainApp.setCartService(mainApp.getCartService());
		mainApp.setPurchaseService(mainApp.getPurchaseService());
		patientsButton.setOnMouseClicked(e -> LoadPatientsView());
		medicationsButton.setOnMouseClicked(e -> LoadMedicationsView());
		purchasesButton.setOnMouseClicked(e -> LoadPurchaseView());
		newPurchaseButton.setOnMouseClicked(e -> LoadNewPurchaseView());
		exitButton.setOnMouseClicked(e -> Platform.exit());
	}
	
	public MainApp getMainApp(){
		return mainApp;
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	private void loadRootView(){
		Stage primaryStage = (Stage) newPurchaseButton.getScene().getWindow();
		
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
	
	private void LoadPatientsView(){
		loadRootView();
		FXMLLoader loader = new FXMLLoader();
		try{
			loader.setLocation(MainApp.class.getResource("/PatientsView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			rootView.setCenter(pane);
			
			PatientsViewController controller = loader.getController();
			controller.setMainApp(this.mainApp);
			controller.setPatientService(mainApp.getPatientService());
			
			logger.info("Ugrás a főoldalról a PatientsView-ra.");
		} catch (IOException e){
			logger.error("IOException történt a PatientView betöltése közben! {}",e.getMessage());
		}
	}
	
	private void LoadMedicationsView(){
		loadRootView();
		FXMLLoader loader = new FXMLLoader();
		try{
			loader.setLocation(MainApp.class.getResource("/MedicationsView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			rootView.setCenter(pane);
			
			MedicationsViewController controller = loader.getController();
			controller.setMainApp(this.mainApp);
			controller.setMedicationService(mainApp.getMedicationService());
			
			logger.info("Ugrás a főoldalról a MedicationsView-ra.");
		}catch (IOException e){
			logger.error("IOException történt a MedicationsView betöltése közben! {}",e.getMessage());
		}
	}
	
	private void LoadPurchaseView(){
		loadRootView();
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(MainApp.class.getResource("/PurchaseView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			rootView.setCenter(pane);
			
			PurchaseViewController controller = loader.getController();
			controller.setMainApp(this.mainApp);
			controller.setPurchaseService(mainApp.getPurchaseService());
			
			logger.info("Ugrás a főoldalról a PurchaseView-ra.");
		} catch (IOException e){
			logger.error("IOException történt a PurchaseView betöltése közben. {}",e.getMessage());
		}
	}
	
	private void LoadNewPurchaseView(){
		loadRootView();
		FXMLLoader loader = new FXMLLoader();
		try {
			loader.setLocation(MainApp.class.getResource("/NewPurchaseView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			rootView.setCenter(pane);
			
			NewPurchaseViewController controller = loader.getController();
			controller.setMainApp(this.mainApp);
			controller.setMedicationService(mainApp.getMedicationService());
			controller.setCartService(mainApp.getCartService());
			controller.setPatientService(mainApp.getPatientService());
			controller.setPurchaseService(mainApp.getPurchaseService());
			
			logger.info("Ugrás a főoldalról a NewPurchaseView-ra.");
		} catch (IOException e){
			logger.error("IOException a NewPurchaseView betöltésekor! {}",e.getMessage());
		}
	}
	
	
	
}
