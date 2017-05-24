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

/**A program belépési pontja, amely az indítás után létrehozza a kezelőosztályokat,
 * és a kezdőfelületet.
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
	
	/**Visszaadja a {@link pharmacy.DAO.PatientService} betegkezelő-osztály objektumot.
	 * @return {@link pharmacy.DAO.PatientService}
	 */
	public PatientService getPatientService(){
		return this.patientService;
	}
	
	/**Visszaadja a {@link pharmacy.DAO.MedicationService} 
	 * gyógyszerkezelő-osztály objektumot.
	 * @return {@link pharmacy.DAO.MedicationService}
	 */
	public MedicationService getMedicationService(){
		return this.medicationService;
	}
	
	/**Visszaadja a {@link pharmacy.DAO.PurchaseService}
	 * vásárláskezelő-osztály objektumot.
	 * @return {@link pharmacy.DAO.PurchaseService}
	 */
	public PurchaseService getPurchaseService(){
		return this.purchaseService;
	}
	
	/**Visszaadja a {@link pharmacy.DAO.CartService}
	 * kosárkezelő-osztály objektumot.
	 * @return {@link pharmacy.DAO.CartService}
	 */
	public CartService getCartService(){
		return this.cartService;
	}
	
	/**Beállítja a paraméterül kapott {@link pharmacy.DAO.PatientService}
	 * objektumot a jelenlegi {@link pharmacy.MainApp} objektumhoz.
	 * @param patientService betegkezelő-osztály objektum
	 */
	public void setPatientService(PatientService patientService){
		this.patientService = patientService;
	}
	
	/**Beállítja a paraméterül kapott {@link pharmacy.DAO.MedicationService}
	 * objektumot a jelenlegi {@link pharmacy.MainApp} objektumhoz.
	 * @param medicationService a gyógyszerkezelő-osztály objektum
	 */
	public void setMedicationService(MedicationService medicationService){
		this.medicationService = medicationService;
	}
	
	/**Beállítja a paraméterül kapott {@link pharmacy.DAO.PurchaseService}
	 * objektumot a jelenlegi {@link pharmacy.MainApp} objektumhoz.
	 * @param purchaseService a vásárláskezelő-osztály objektum
	 */
	public void setPurchaseService(PurchaseService purchaseService){
		this.purchaseService = purchaseService;
	}
	
	/**Beállítja a paraméterül kapott {@link pharmacy.DAO.CartService}
	 * objektumot a jelenlegi {@link pharmacy.MainApp} objektumhoz.
	 * @param cartService a kosárkezelő-osztály objektum
	 */
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

	/**Inicializálja a program elsődleges {@link javafx.stage.Stage}-ét, és hozzárendeli
	 * a kontrollert.
	 * 
	 */
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

	/**Inicializálja a program első jelenetét.
	 * 
	 */
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

	/**Visszaadja a program {@link javafx.stage.Stage} objektumát.
	 * @return a program {@link javafx.stage.Stage}-e. 
	 */
	public Stage getPrimaryStage(){
		return primaryStage;
	}
	
	/**A program main metódusa, ide lép be a program indításakor.
	 * @param args indítási argumentumok
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
