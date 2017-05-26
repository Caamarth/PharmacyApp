package pharmacy.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Medication;
import pharmacy.model.Patient;
import pharmacy.model.Purchase;

/**A vásárlások kezelését megvalósító osztály.
 * @author Babély Norbert Alex
 *
 */
public class PurchaseService {
	
	private static Logger logger = LoggerFactory.getLogger(PurchaseService.class);
	
	EntityManager entityManager;
	
	private PharmacyDAO pharmacyDAO;

	private ObservableList<Purchase> purchases;
	
	/**Paraméteres konstruktor, amely
	 *létrehoz egy {@link pharmacy.DAO.PurchaseService}
	 *objektumot.
	 *@param entityManager - {@link javax.persistence.EntityManager} perzisztencia objektum
	 */
	public PurchaseService(EntityManager entityManager) {	
		this.entityManager = entityManager;
		 pharmacyDAO = new PharmacyDAO(this.entityManager);
		 purchases = FXCollections.observableArrayList(pharmacyDAO.getPurchasesList());
    }
	
	/**Paraméteres konytruktor az osztály JUnit teszteléséhez.
	 * @param purchases a vásárlások listája
	 */
	public PurchaseService(ObservableList<Purchase> purchases){
		this.purchases = purchases;
	}
	
	protected void updateList(){
		ObservableList<Purchase> purchaseList = FXCollections.observableArrayList(pharmacyDAO.getPurchasesList());
		purchases.removeAll(purchases);
		for(Purchase p : purchaseList){
			if(!purchases.contains(p)){
				purchases.add(p);
			}
		}
		
		logger.info("Vásárlás lista frissítése...");
	}

	/**Visszaadja a vásárlásokat tartalmazó listát.
	 * @return purchases 
	 *a vásárlásokat tartalmazó lista
	 */
	public ObservableList<Purchase> getAllPurchases() {
		logger.info("Vásárlások listájának lekérése...");
		updateList();
		return purchases;
	}

	/**Hozzáad egy vásárlást a vásárlásokat
	 *tartalmazó listához.
	 * @param patient a vásárláshoz tartozó beteg
	 * @param price a vásárlás összege
	 * @param date a vásárlás dátuma
	 * @param medications a vásárolt gyógyszerek listája
	 */
	public void addPurchase(Patient patient, int price, LocalDate date, List<Medication> medications) {
		entityManager.getTransaction().begin();
		
		pharmacyDAO.createPurchase(patient, price, date, medications);
		
		entityManager.getTransaction().commit();
		
		logger.info("Új vásárlás mentése az adatbázisba.");
		
		updateList();
	}

	/**A beteg rangja alapján csökkenti a vásárlás
	 *végösszegének értékét, majd visszaadja azt.
	 * @param rank a beteg rangja
	 * @param value a vásárlás módosítás előtti értéke
	 * @return discount a vásárlás módosított értéke
	 */
	public double giveDiscount(int rank, double value) {
		double discount = value;
		if (rank == 2) {
			discount *= 0.9;
		} else if (rank == 1) {
			discount *= 0.95;
		}
		return discount;
	}
}
