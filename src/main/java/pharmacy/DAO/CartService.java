package pharmacy.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Medication;

/**Vásárlás során a kosárba helyezett gyógyszereket
 * kezelő osztály.
 * @author Babély Norbert Alex
 *
 */
public class CartService {

    private ObservableList<Medication> purchaseCart;


	/**Paraméter nélküli konstruktor, amely
	 * inicializálja a {@link pharmacy.DAO.CartService}
	 * osztályt.
	 */
    public CartService() {
    	purchaseCart = FXCollections.observableArrayList();
	}
    
    public CartService(ObservableList<Medication> purchaseCart){
    	this.purchaseCart = purchaseCart;
    }

	/**Visszaadja a kosárban található
	 * gyógyszerek listáját.
	 * @return purchaseCart a gyógyszerek listája
	 */
    public final ObservableList<Medication> getCartMedications() {
		return purchaseCart;
	}

	/**Hozzáad egy gyógyszert a listához.
	 * @param medication egy gyógyszer objektum
	 */
    public final void addToCart(final Medication medication) {
		purchaseCart.add(medication);
	}

	/**Kiveszi a paraméterként kapott
	 * gyógyszert a listából.
	 * @param medication egy gyógyszer objektum
	 */
    public final void removeFromCart(final Medication medication) {
		purchaseCart.remove(medication);
	}

	/**Visszaadja a kosárban lévő gyógyszerek
	 * összértékét.
	 * @return {@link int} a gyógyszerek összértéke
	 */
    public final double getCartSumValue() {
		return purchaseCart.stream()
				.mapToDouble(i -> i.getUnitprice()*isSupported(i)).sum();
	}
    
    public double isSupported(Medication medication){
		if (medication.getSupportedMed() == 1) {
			return 0.5;
		} else {
			return 1.0;
		}
	}
}
