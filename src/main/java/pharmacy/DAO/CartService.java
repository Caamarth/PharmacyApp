package pharmacy.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Medication;

/**Vásárlás során a kosárba helyezett gyógyszereket
 * kezelő osztály.
 * Egy {@link ObservableList}-ben tárolja azokat a gyógyszereket,
 * melyet a vásárlás során a kosárba helyeznek.
 * Ebben az osztályban kerül kiszámolásra a vásárlás aktuális összege.
 * @author Babély Norbert Alex
 *
 */
public class CartService {
	
	private static Logger logger = LoggerFactory.getLogger(CartService.class);

    private ObservableList<Medication> purchaseCart;

	/**Paraméter nélküli konstruktor, amely
	 * inicializálja a {@link pharmacy.DAO.CartService}
	 * osztályt.
	 */
    public CartService() {
    	purchaseCart = FXCollections.observableArrayList();
	}
    
    /**Paraméteres konstruktor a JUnit egységtesztelésekhez.
     * @param purchaseCart gyógyszerek listája
     */
    public CartService(ObservableList<Medication> purchaseCart){
    	this.purchaseCart = purchaseCart;
    }

	/**Visszaadja a kosárban található
	 * gyógyszerek {@link ObservableList} listáját.
	 * @return purchaseCart a gyógyszerek listája
	 */
    public final ObservableList<Medication> getCartMedications() {
    	logger.info("Kosár lista lekérése...");
		return purchaseCart;
	}

	/**Hozzáad egy gyógyszert a listához.
	 * A hozzáadást a {@link java.util.List} osztály {@code add()} metódusával
	 * végzi el.
	 * @param medication egy gyógyszer objektum
	 */
    public final void addToCart(final Medication medication) {
		purchaseCart.add(medication);
		logger.info("{} elhelyezése a kosárba.",medication.getName());
	}

	/**Kiveszi a paraméterként kapott
	 * gyógyszert a listából.
	 * Az eltávolítást a {@link java.util.List} osztály
	 * {@code remove()} metódusával végzi el.
	 * @param medication egy gyógyszer objektum
	 */
    public final void removeFromCart(final Medication medication) {
		purchaseCart.remove(medication);
		logger.info("{} kivétele a kosárból.",medication.getName());
	}

	/**Visszaadja a kosárban lévő gyógyszerek
	 * összértékét.
	 * Ha bármelyik gyógyszer TB-támogatott, akkor annak a  gyógyszernek az értékét
	 * a felére csökkenti.
	 * @return {@link java.lang.Double} a gyógyszerek összértéke
	 */
    public final double getCartSumValue() {
    	logger.info("Vásárlás végösszegének lekérése...");
		return purchaseCart.stream()
				.mapToDouble(i -> i.getUnitprice()*isSupported(i)).sum();
	}
    
    /**Visszaad egy szorzó értéket a paraméterül kapott gyógyszer
     * TB-támogatottságától függően. 
     * Ha a gyógyszer TB-támogatott, a szorzó értéke 0.5, különben 1.
     * @param medication a vizsgált gyógyszer
     * @return {@link java.lang.Double} szorzó értéke 
     */
    public double isSupported(Medication medication){
		if (medication.getSupportedMed() == 1) {
			return 0.5;
		} else {
			return 1.0;
		}
	}
}
