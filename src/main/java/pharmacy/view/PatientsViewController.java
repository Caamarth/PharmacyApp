package pharmacy.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pharmacy.DateUtil;
import pharmacy.MainApp;
import pharmacy.DAO.PatientService;
import pharmacy.model.Patient;

public class PatientsViewController {

	@FXML
	private Button newPatientButton;
	@FXML
	private Button editPatientButton;
	@FXML 
	private Button deletePatientButton;
	@FXML
	private Button patientToMainButton;
	@FXML
	private Label patientAddressLabel;
	@FXML
	private Label patientBirthdateLabel;
	@FXML
	private Label patientIdLabel;
	@FXML
	private Label patientNameLabel;
	@FXML
	private Label patientRankLabel;
	@FXML
	private Label patientTajLabel;
	@FXML
	private TableView<Patient> patientTableView;
	@FXML
	private TableColumn<Patient, String> patientTajColumn;
	@FXML
	private TableColumn<Patient, String> patientNameColumn;
	
	private MainApp mainApp;
	private PatientService patientService;
	private Stage primaryStage;
	
	private BorderPane rootView;
	
	public PatientsViewController(){
		
	}
	
	@FXML 
	public void initialize(){
		resetLabels();
		
		patientTajColumn.setCellValueFactory(cd -> cd.getValue().getTajszamProperty());
		patientNameColumn.setCellValueFactory(cd -> cd.getValue().getNameProperty());
		
		patientTableView.getSelectionModel().selectedItemProperty().addListener((o, old, _new) -> patientDetails(_new));
		
		
		patientToMainButton.setOnMouseClicked(e -> loadMainView());
		deletePatientButton.setOnAction(e -> handleDeletePatientButton());
		newPatientButton.setOnMouseClicked(e -> handleNewPatient());
		editPatientButton.setOnAction(e -> handleEditPatient());
	}
	
	public void patientDetails(Patient patient){
		if(patient != null){
			patientIdLabel.setText(patient.getId().toString());
			patientTajLabel.setText(patient.getTajszam());
			patientNameLabel.setText(patient.getName());
			patientAddressLabel.setText(patient.getAddress());
			patientBirthdateLabel.setText(DateUtil.format(patient.getBirthdate()));
			patientRankLabel.setText(patientService.patientRankDetail(patient));
			
		}
	}
	
	public MainApp getMainApp(){
		return mainApp;
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	public PatientService getPatientService(){
		return patientService;
	}
	
	public void setPatientService(PatientService patientService){
		this.patientService = patientService;
		setTableContent();
	}
	
	public void setTableContent(){
		patientTableView.setItems(patientService.getAllPatients());
	}
	
	private void loadRootView(){
		primaryStage = (Stage) patientToMainButton.getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader();
		try{
			loader.setLocation(MainApp.class.getResource("/rootLayout.fxml"));
			rootView = (BorderPane) loader.load();
			Scene scene = new Scene(rootView);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (IOException e){
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
			
			
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void resetLabels(){
		patientIdLabel.setText("");
		patientTajLabel.setText("");
		patientNameLabel.setText("");
		patientAddressLabel.setText("");
		patientBirthdateLabel.setText("");
		patientRankLabel.setText("");
	}
	
	@FXML
	private void handleDeletePatientButton(){
		Patient selectedIndex = patientTableView.getSelectionModel().getSelectedItem();
		if(selectedIndex != null){
			patientService.deletePatient(selectedIndex);
			resetLabels();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("");
	        alert.setHeaderText("Nincs kiválasztott beteg!");
	        alert.setContentText("Válasszon ki egy beteget a törléshez!");

	        alert.showAndWait();
		}
		
	}
	
	
	public boolean showPersonEditDialog(Patient patient){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/EditPatientView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			Stage editDialogStage = new Stage();
			editDialogStage.setTitle("");
			editDialogStage.initModality(Modality.WINDOW_MODAL);
			editDialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			editDialogStage.setScene(scene);
			
			EditPatientController controller = loader.getController();
			controller.setDialogStage(editDialogStage);
			controller.setPatient(patient);
			
			editDialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
	
	@FXML
	private void handleNewPatient(){
		Patient tempPatient = new Patient();
		boolean okClicked = showPersonEditDialog(tempPatient);
		if(okClicked){
			patientService.addPatient(tempPatient);
		}
	}
	
	@FXML
	private void handleEditPatient(){
		Patient selectedPatient = patientTableView.getSelectionModel().getSelectedItem();
		if(selectedPatient != null){
			boolean okClicked = showPersonEditDialog(selectedPatient);
			if(okClicked){
				patientService.editPatient(selectedPatient);
				patientDetails(selectedPatient);
				setTableContent();
			}
		}
		
		else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("");
	        alert.setHeaderText("Nincs beteg kiválasztva!");
	        alert.setContentText("Válasszon egy beteget a módosításhoz!");

	        alert.showAndWait();
		}
	}
}
