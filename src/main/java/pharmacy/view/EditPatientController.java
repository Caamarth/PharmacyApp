package pharmacy.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pharmacy.DateUtil;
import pharmacy.model.Patient;

public class EditPatientController {
	
	private static Logger logger = LoggerFactory.getLogger(EditPatientController.class);
	@FXML
	private Button editPatientToMainButton;
	@FXML
	private Button submitEditPatientButton;
	@FXML
	private TextField editAddressTextField;
	@FXML
	private TextField editTajTextField;
	@FXML
	private TextField editNameTextField;
	@FXML
	private TextField editBirthdateTextField;
	
	private Stage editDialogStage;
	private Patient patient;
	private boolean okClicked = false;
	
	@FXML
	private void initialize(){
		logger.info("Beteg dialógus ablak megnyitása...");
		submitEditPatientButton.setOnMouseClicked(e -> handleOk());
		editPatientToMainButton.setOnMouseClicked(e -> handleCancel());
	}
	
	public void setDialogStage(Stage dialogStage){
		this.editDialogStage = dialogStage;
	}
	
	public void setPatient(Patient patient){
		this.patient = patient;
		
		editTajTextField.setText(patient.getTajszam());
		editNameTextField.setText(patient.getName());
		editBirthdateTextField.setText(DateUtil.format(patient.getBirthdate()));
		editBirthdateTextField.setPromptText("yyyy.mm.dd");
		editAddressTextField.setText(patient.getAddress());
	}
	
	public boolean isOkClicked(){
		return okClicked;
	}
	
	@FXML
	private void handleOk(){
		if(isInputValid()){
			patient.setTajszam(editTajTextField.getText());
			patient.setName(editNameTextField.getText());
			patient.setAddress(editAddressTextField.getText());
			patient.setBirthDate(DateUtil.parse(editBirthdateTextField.getText()));
		
			okClicked = true;
			editDialogStage.close();
		}
	}
	
	@FXML 
	private void handleCancel(){
		logger.info("Beteg dialógus ablak bezására...");
		editDialogStage.close();
	}
	
	private boolean isInputValid(){
		String errorMessage = "";
		
		if(editTajTextField.getText() == null || editTajTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő TAJ-szám! \n";
		}
		if(editNameTextField.getText() == null || editTajTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő név! \n";
		}
		if(editAddressTextField.getText() == null || editTajTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő cím! \n";
		}
		if(editBirthdateTextField.getText() == null || editTajTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő születési dátum! \n";
		} else {
			if(!DateUtil.validDate(editBirthdateTextField.getText())){
				errorMessage += "Nem megfelelő formátum! Formátum: yyyy.mm.dd \n";
			}
		}
		
		if(errorMessage.length() == 0){
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(editDialogStage);
			alert.setTitle("Hibás módosítás");
			alert.setHeaderText("Javítsa a hibás mezőket!");
			alert.setContentText(errorMessage);
			
			alert.showAndWait();
			
			return false;
		}
		
	}
}
