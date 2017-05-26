package pharmacy.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pharmacy.model.Medication;

public class EditMedicationController {
	
	private static Logger logger = LoggerFactory.getLogger(EditMedicationController.class);
	@FXML
	private Button editMedToMainButton;
	@FXML
	private Button submitEditMedButton;
	@FXML
	private CheckBox editSupportedMedCheckBox;
	@FXML
	private TextArea editDescTextArea;
	@FXML
	private TextField editDoseTextField;
	@FXML
	private TextField editManufacturerTextField;
	@FXML
	private TextField editNameTextField;
	@FXML
	private TextField editQuantityTextField;
	@FXML
	private TextField editUnitpriceTextField;
	
	private Stage editDialogStage;
	private Medication medication;
	private boolean okClicked = false;
	
	@FXML
	private void initialize(){
		logger.info("Gyógyszer dialógus ablak megnyitása...");
		submitEditMedButton.setOnMouseClicked(e -> handleOk());
		editMedToMainButton.setOnMouseClicked(e -> handleCancel());
	}
	
	public void setDialogStage(Stage dialogStage){
		this.editDialogStage = dialogStage;
	}
	
	public void setMedication(Medication medication){
		this.medication = medication;
		
		editNameTextField.setText(medication.getName());
		editManufacturerTextField.setText(medication.getManufacturer());
		editDescTextArea.setText(medication.getDescription());
		editQuantityTextField.setText(medication.getQuantity().toString());
		editUnitpriceTextField.setText(Integer.toString(medication.getUnitprice()));
		editDoseTextField.setText(Integer.toString(medication.getDose()));
		if(medication.getSupportedMed() > 0){
			editSupportedMedCheckBox.setSelected(true);
		}
		else {
			editSupportedMedCheckBox.setSelected(false);
		}
		
	}
	
	public boolean isOkClicked(){
		return okClicked;
	}
	
	@FXML
	private void handleOk(){
		if(isInputValid()){
			medication.setName(editNameTextField.getText());
			medication.setManufacturer(editManufacturerTextField.getText());
			medication.setDescription(editDescTextArea.getText());
			medication.setQuantity(Integer.parseInt(editQuantityTextField.getText()));
			medication.setUnitprice(Integer.parseInt(editUnitpriceTextField.getText()));
			medication.setDose(Integer.parseInt(editDoseTextField.getText()));
			if(editSupportedMedCheckBox.isSelected()){
				medication.setSupportedMed(1);
			} else {
				medication.setSupportedMed(0);
			}
			
			okClicked = true;
			editDialogStage.close();
		}
	}
	
	@FXML
	private void handleCancel(){
		logger.info("Gyógyszer dialógus ablak bezására");
		editDialogStage.close();
	}
	
	private boolean isInputValid(){
		String errorMessage = "";
		
		if(editNameTextField.getText() == null || editNameTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő név! \n";
		}
		if(editManufacturerTextField.getText() == null || editManufacturerTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő gyártó! \n";
		}
		if(editDescTextArea.getText() == null || editDescTextArea.getText().length() == 0){
			errorMessage += "Nem megfelelő leírás! \n";
		}
		if(editQuantityTextField.getText() == null || editQuantityTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő mennyiség! \n";
		}
		if(editUnitpriceTextField.getText() == null || editUnitpriceTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő egységár! \n";
		}
		if(editDoseTextField.getText() == null || editDoseTextField.getText().length() == 0){
			errorMessage += "Nem megfelelő dózis! \n";
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
