package pharmacy.DAO;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Medication;

public class CartServiceTest {
	private static CartService cartService;
	private static ObservableList<Medication> cartList;
	private static ObservableList<Medication> testList;
	private static Medication medication;
	
	@BeforeClass
	public static void initialize(){

		
		cartList = FXCollections.observableArrayList();
		testList = FXCollections.observableArrayList();
		
		medication = new Medication();
		medication.setId(999);
		medication.setName("Teszt");
		medication.setManufacturer("Teszt");
		medication.setDose(1000);
		medication.setQuantity(30);
		medication.setDescription("Teszt");
		medication.setUnitprice(2000);
		medication.setSupportedMed(0);
		
		cartService = new CartService(cartList);
		
	}
	
	@Test
	public void addToCartTest(){
		cartService.getCartMedications().clear();
		cartService.addToCart(medication);
		Assert.assertEquals(1, cartService.getCartMedications().size());
	}
	
	@Test
	public void removeFromCartTest(){
		cartService = new CartService(FXCollections.observableArrayList());
		cartService.addToCart(medication);
		cartService.removeFromCart(medication);
		Assert.assertEquals(0, cartService.getCartMedications().size());
	}
	
	@Test
	public void getCartMedicationsTest(){
		testList.add(medication);
		cartService.addToCart(medication);
		Assert.assertEquals(testList, cartService.getCartMedications());
	}
	
	@Test
	public void getCartSumValueTest(){
		cartService = new CartService(FXCollections.observableArrayList());
		cartService.addToCart(medication);
		Assert.assertEquals(2000, cartService.getCartSumValue(), 0.00001);
	}
	
	@Test
	public void isSupportedTest(){
		medication.setSupportedMed(0);
		Assert.assertEquals(1.0, cartService.isSupported(medication),0.00001);
	}
	
	@Test
	public void isSupportedNotTest(){
		medication.setSupportedMed(1);
		Assert.assertEquals(0.5, cartService.isSupported(medication), 0.00001);
	}
	
	
}
