package pharmacy.DAO;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Medication;
import pharmacy.model.Purchase;

public class PurchaseServiceTest {
	private static PurchaseService purchaseService;
	private static Purchase purchase;
	private static Medication medication;
	private static ObservableList<Purchase> purchaseList;
	
	@BeforeClass
	public static void initialize(){

		purchaseList = FXCollections.observableArrayList();
		medication = new Medication();
		medication.setId(999);
		medication.setName("Teszt");
		medication.setDeleted(false);
		medication.setUnitprice(9000);
		
		purchase = new Purchase();
		purchase.setId(9999);
		purchase.getMedicationList().add(medication);
		purchase.setPrice(9000);
		purchaseList.add(purchase);
		
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
}
