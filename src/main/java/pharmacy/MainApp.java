package pharmacy;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pharmacy.DAO.CartService;
import pharmacy.DAO.MedicationService;
import pharmacy.DAO.PatientService;
import pharmacy.DAO.PurchaseService;
import pharmacy.view.MainSceneController;

/**A program kezdőosztálya.
 * @author Babély Norbert Alex
 *
 */
public class MainApp extends Application {
	
	private static Logger logger = LoggerFactory.getLogger(MainApp.class);

	private Stage primaryStage;
	private BorderPane rootLayout;
	private PatientService patientService;
	private MedicationService medicationService;
	private PurchaseService purchaseService;
	private CartService cartService;
	
	public PatientService getPatientService(){
		return this.patientService;
	}
	
	public MedicationService getMedicationService(){
		return this.medicationService;
	}
	
	public PurchaseService getPurchaseService(){
		return this.purchaseService;
	}
	
	public CartService getCartService(){
		return this.cartService;
	}
	
	public void setPatientService(PatientService patientService){
		this.patientService = patientService;
	}
	
	public void setMedicationService(MedicationService medicationService){
		this.medicationService = medicationService;
	}
	
	public void setPurchaseService(PurchaseService purchaseService){
		this.purchaseService = purchaseService;
	}
	
	public void setCartService(CartService cartService){
		this.cartService = cartService;
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("PharmacyApp");
		this.medicationService = new MedicationService();
		this.patientService = new PatientService();
		this.cartService = new CartService();
		this.purchaseService = new PurchaseService();
		initRootLayout();
		
		showMainScene();
	}

	public void initRootLayout(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/rootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch (IOException e){
			logger.error("IOException történt!",e.getMessage());
		}
	}

	public void showMainScene(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/MainScene.fxml"));
			AnchorPane mainScene = (AnchorPane) loader.load();
			rootLayout.setCenter(mainScene);
			
			MainSceneController controller = loader.getController();
			controller.setMainApp(this);
		}catch (IOException e){
			logger.error("IOException történt!",e.getMessage());
			
		}
	}

	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
