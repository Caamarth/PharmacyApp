package pharmacy.DAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.DateUtil;
import pharmacy.model.Medication;
import pharmacy.model.Patient;
import pharmacy.model.Purchase;

public class PurchaseServiceTest {
	private static PurchaseService purchaseService;
	private static Purchase purchase;
	private static Medication medication;
	private static Patient patient;
	private static ObservableList<Purchase> purchaseList;
	private static List<Medication> medicationList;
	
	@BeforeClass
	public static void initialize(){

		purchaseList = FXCollections.observableArrayList();
		medication = new Medication();
		medication.setId(999);
		medication.setName("Teszt");
		medication.setDeleted(false);
		medication.setUnitprice(9000);
		
		patient = new Patient();
		
		medicationList = new ArrayList<>();
		medicationList.add(medication);
		
		purchase = new Purchase();
		purchase.setId(9999);
		purchase.setPatient(patient);
		purchase.setMedicationList(medicationList);
		purchase.setPrice(9000);
		purchaseList.add(purchase);
		purchase.setDate(DateUtil.parse("2017.01.01"));
		
		purchaseService = new PurchaseService(purchaseList);
		
	}
	
	@Test
	public void giveDiscountByRankFirstTest(){
		double discount = purchase.getPrice();
		discount = purchaseService.giveDiscount(0, discount);
		Assert.assertEquals(9000, discount, 0.00001);
	}
	
	@Test
	public void giveDiscountByRankSecondTest(){
		double discount = purchase.getPrice();
		discount = purchaseService.giveDiscount(1, discount);
		Assert.assertEquals(8550, discount, 0.00001);
	}
	
	@Test
	public void giveDiscountByRankThirdTest(){
		double discount = purchase.getPrice();
		discount = purchaseService.giveDiscount(2, discount);
		Assert.assertEquals(8100, discount, 0.00001);
	}
	
	@Test
	public void testGetterMethod(){
		
		Assert.assertEquals(9999, purchase.getId());
		Assert.assertEquals(patient, purchase.getPatient());
		Assert.assertEquals(medicationList, FXCollections.observableArrayList(purchase.getMedicationList()));
		Assert.assertEquals(9000, purchase.getPrice());
		Assert.assertEquals(DateUtil.parse("2017.01.01"), purchase.getDate());
		
		Assert.assertEquals(new SimpleIntegerProperty(9999).getValue(), purchase.getIdProperty().getValue());
		Assert.assertEquals(new SimpleIntegerProperty(9000).getValue(), purchase.getPriceProperty().getValue());
		Assert.assertEquals(new SimpleObjectProperty<LocalDate>(DateUtil.parse("2017.01.01")).getValue(), purchase.getDateProperty().getValue());
	}
}
