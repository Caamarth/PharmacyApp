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
import pharmacy.MainApp;
import pharmacy.DAO.MedicationService;
import pharmacy.model.Medication;

public class MedicationsViewController {

	@FXML
	private Button newMedicationButton;
	@FXML
	private Button editMedicationButton;
	@FXML
	private Button deleteMedicationButton;
	@FXML 
	private Button medicationToMainButton;
	@FXML
	private TableView<Medication> medicationTableView;
	@FXML
	private TableColumn<Medication, String> medicationNameColumn;
	@FXML
	private TableColumn<Medication, String> manufacturerColumn;
	@FXML
	private Label medicineIdLabel;
	@FXML
	private Label medicineDescriptionLabel;
	@FXML
	private Label medicineDoseLabel;
	@FXML
	private Label medicineNameLabel;
	@FXML
	private Label medicineManufacturerLabel;
	@FXML
	private Label medicineUnitpriceLabel;
	@FXML
	private Label medicineQuantityLabel;
	@FXML
	private Label supportedMedLabel;
	
	private MainApp mainApp;
	
	private Stage primaryStage;
	
	private BorderPane rootView;
	
	private MedicationService medicationService;
	
	public MedicationsViewController(){
		
	}
	
	@FXML
	private void initialize(){
		resetLabels();

		medicationNameColumn.setCellValueFactory(cd -> cd.getValue().getNameProperty());
		manufacturerColumn.setCellValueFactory(cd -> cd.getValue().getManufacturerProperty());
		
		medicationTableView.getSelectionModel().selectedItemProperty().addListener((o, old, _new) -> medicationDetails(_new));
		
		medicationToMainButton.setOnMouseClicked(e -> loadMainView());
		deleteMedicationButton.setOnAction(e -> handleDeleteMedicationButton());
		newMedicationButton.setOnAction(e -> handleNewMedication());
		editMedicationButton.setOnAction(e -> handleEditMedication());
	}
	
	private void medicationDetails(Medication medication){
		
		if (medication != null) {
			medicineIdLabel.setText(medication.getId().toString());
			medicineNameLabel.setText(medication.getName());
			medicineManufacturerLabel.setText(medication.getManufacturer());
			medicineDoseLabel.setText(Integer.toString(medication.getDose()));
			medicineQuantityLabel.setText(medication.getQuantity().toString());
			medicineDescriptionLabel.setText(medication.getDescription());
			medicineUnitpriceLabel.setText(Integer.toString(medication.getUnitprice()));
			if (medication.getSupportedMed() == 0) {
				supportedMedLabel.setText("Nem TB-támogatott");
			} else {
				supportedMedLabel.setText("TB-támogatott");
			} 
		}
		
	}
	
	public MainApp getMainApp(){
		return mainApp;
	}
	
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}
	
	public MedicationService getMedicationService(){
		return medicationService;
	}
	
	public void setMedicationService(MedicationService medicationService){
		this.medicationService = medicationService;
		setTableContent();
	}
	
	public void setTableContent(){
		medicationTableView.setItems(medicationService.getAllMedications());
	}
	
	private void loadRootView(){
		primaryStage = (Stage) newMedicationButton.getScene().getWindow();
		
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
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void resetLabels(){
		medicineIdLabel.setText("");
		medicineNameLabel.setText("");
		medicineManufacturerLabel.setText("");
		medicineDoseLabel.setText("");
		medicineQuantityLabel.setText("");
		medicineDescriptionLabel.setText("");
		medicineUnitpriceLabel.setText("");
		supportedMedLabel.setText("");
	}
	
	@FXML
	private void handleDeleteMedicationButton(){
		Medication selectedMed = medicationTableView.getSelectionModel().getSelectedItem();
		if(selectedMed != null){
			medicationService.deleteMedication(selectedMed);
			resetLabels();
			
		} else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("");
	        alert.setHeaderText("Nincs kiválasztott gyógyszer!");
	        alert.setContentText("Válasszon ki egy gyógyszert a törléshez!");

	        alert.showAndWait();
		}
		
	}
	
	public boolean showMedicationEditDialog(Medication medication){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/EditMedicationView.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			Stage editDialogStage = new Stage();
			editDialogStage.setTitle("");
			editDialogStage.initModality(Modality.WINDOW_MODAL);
			editDialogStage.initOwner(primaryStage);
			Scene scene = new Scene(pane);
			editDialogStage.setScene(scene);
			
			EditMedicationController controller = loader.getController();
			controller.setDialogStage(editDialogStage);
			controller.setMedication(medication);
			
			editDialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}
	
	@FXML
	private void handleNewMedication(){
		Medication tempMedication = new Medication();
		boolean okClicked = showMedicationEditDialog(tempMedication);
		if(okClicked){
			medicationService.addMedication(tempMedication);
		}
	}
	
	@FXML
	private void handleEditMedication(){
		Medication selectedMedication = medicationTableView.getSelectionModel().getSelectedItem();
		if(selectedMedication != null){
			boolean okClicked = showMedicationEditDialog(selectedMedication);
			if(okClicked){
				medicationService.editMedication(selectedMedication);
				medicationDetails(selectedMedication);
				setTableContent();
			}
		}
		
		else {
			Alert alert = new Alert(AlertType.WARNING);
	        alert.initOwner(mainApp.getPrimaryStage());
	        alert.setTitle("");
	        alert.setHeaderText("Nincs gyógyszer kiválasztva!");
	        alert.setContentText("Válasszon egy gyógyszert a módosításhoz!");

	        alert.showAndWait();
		}
	}
	
}
