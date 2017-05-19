package pharmacy.DAO;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

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
	
	
}
