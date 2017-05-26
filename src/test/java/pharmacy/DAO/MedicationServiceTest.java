package pharmacy.DAO;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Medication;

public class MedicationServiceTest {
	
	private static MedicationService medicationService;
	private static Medication medication;
	private static ObservableList<Medication> medicationObsList;
	private static List<Medication> medicationList;
	
	@BeforeClass
	public static void inicialize(){

		
		medication = new Medication();
		medication.setId(999);
		medication.setName("Teszt");
		medication.setManufacturer("TesztGyarto");
		medication.setDose(200);
		medication.setQuantity(30);
		medication.setDescription("Tesztleírás");
		medication.setDeleted(false);
		medication.setUnitprice(9000);	
		
		medicationObsList = FXCollections.observableArrayList();
		medicationObsList.add(medication);
		medicationList = new ArrayList<Medication>();
		medicationList.add(medication);
		
		medicationService = new MedicationService(medicationObsList);
		
	}
	
	@Test
	public void testSupportedMedCalc(){
		medication.setSupportedMed(1);
		double value = MedicationService.supportedMedCalc(medication);
		Assert.assertEquals(4500, value, 0.00001);
	}
	
	@Test
	public void testNotSupportedMedCalc(){
		medication.setSupportedMed(0);
		double value = MedicationService.supportedMedCalc(medication);
		Assert.assertEquals(9000, value, 0.00001);
	}
	
	@Test
	public void testConvertToList(){
		Assert.assertEquals(medicationList, medicationService.convertToList(medicationObsList));
	}
	
	@Test
	public void testGetPatientById(){
		Assert.assertEquals(medication, medicationService.getMedicationById(999));
	}
	
	@Test
	public void testGetterMethods(){
		medication.setDeleted(false);
		
		Assert.assertEquals(999, medication.getId(), 0.000001);
		Assert.assertEquals("Teszt", medication.getName());
		Assert.assertEquals("TesztGyarto", medication.getManufacturer());
		Assert.assertEquals(200, medication.getDose(), 0.000001);
		Assert.assertEquals(30, medication.getQuantity(), 0.00001);
		Assert.assertEquals("Tesztleírás", medication.getDescription());
		Assert.assertEquals(false, medication.isDeleted());
		Assert.assertEquals(9000, medication.getUnitprice());
		
		Assert.assertEquals(new SimpleIntegerProperty(999).getValue(), medication.getIdProperty().getValue());
		Assert.assertEquals(new SimpleStringProperty("Teszt").getValue(), medication.getNameProperty().getValue());
		Assert.assertEquals(new SimpleStringProperty("TesztGyarto").getValue(), medication.getManufacturerProperty().getValue());
		Assert.assertEquals(new SimpleIntegerProperty(200).getValue(), medication.getDoseProperty().getValue());
		Assert.assertEquals(new SimpleIntegerProperty(30).getValue(), medication.getQuantityProperty().getValue());
		Assert.assertEquals(new SimpleStringProperty("Tesztleírás").getValue(), medication.getDescriptionProperty().getValue());
		Assert.assertEquals(new SimpleIntegerProperty(9000).getValue(), medication.getUnitpriceProperty().getValue());
	}
	
	@Test
	public void testToStringMethod(){
		String stringResult = "Medication [id=" + medication.getId() + 
				", name=" + medication.getName() + 
				", manufacturer=" + medication.getManufacturer() + 
				", dose=" + medication.getDose()
				+ ", quantity=" + medication.getQuantity() + 
				", description=" + medication.getDescription() + 
				", unitprice=" + medication.getUnitprice()
				+ ", supportedMed=" + medication.getSupportedMed() + 
				", isDeleted=" + medication.isDeleted() + "]";
		
		Assert.assertEquals(stringResult, medication.toString());
	}
	
	@Test
	public void testDeleteMedication(){
		medication.deleteMedication();
		Assert.assertEquals(true, medication.isDeleted());
	}
	
	
}
