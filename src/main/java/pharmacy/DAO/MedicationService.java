package pharmacy.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Medication;

/**A gyógyszereket kezelő osztály.
 * @author Babély Norbert Alex
 *
 */
public class MedicationService {
	
	private static Logger logger = LoggerFactory.getLogger(MedicationService.class);

	EntityManager entityManager;
	
	private PharmacyDAO pharmacyDAO;
	
	private ObservableList<Medication> medications;
        

	/**Paraméteres konstruktor, amely
	 * létrehozza a {@link pharmacy.DAO.MedicationService}
	 * objektumot.
	 *@param entityManager - {@link javax.persistence.EntityManager} perzisztencia objektum
	 */
	public MedicationService(EntityManager entityManager) {
		this.entityManager = entityManager;
		pharmacyDAO = new PharmacyDAO(this.entityManager);
		medications = FXCollections.observableArrayList(pharmacyDAO.getMedicationsList());
    }
	
	/**Paraméteres konstruktor az osztály JUnit teszteléséhez.
	 * @param medications a gyógyszerek listája
	 */
	public MedicationService(ObservableList<Medication> medications){
		this.medications = medications;
	}
	
	protected void updateList(){
		ObservableList<Medication> medicationList = FXCollections.observableArrayList(pharmacyDAO.getMedicationsList());
		medications.removeAll(medications);
		for(Medication m : medicationList){
			if(!medications.contains(m) && m.isDeleted() != true){
				medications.add(m);
			}
		}
		
		logger.info("Gyógyszer lista frissítése...");
	}

	/**Visszaadja a gyógyszereket tartalmazó listát.
	 * @return medications a gyógyszerek listája
	 */
	public ObservableList<Medication> getAllMedications() {
		logger.info("Gyógyszerek listájának lekérése.");
		updateList();
		List<Medication> filteredMedications = medications.stream().filter(e -> e.isDeleted() != true).collect(Collectors.toList());
		return FXCollections.observableArrayList(filteredMedications);
	}
	
	/**A paraméterül adott gyógyszert mentésre küldi az adatbázisba.
	 * @param medication a menteni kívánt gyogyszer
	 */
	public void addMedication(Medication medication){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.createMedication(medication.getName(),
				medication.getManufacturer(),
				medication.getDose(), medication.getQuantity(),
				medication.getDescription(),
				medication.getUnitprice(), 
				medication.getSupportedMed());
		
		entityManager.getTransaction().commit();
		
		logger.info("{} hozzáadása az adatbázishoz.",medication.getName());
		
		updateList();
	}
	
	/**A paraméterül adott gyógyszer módosításait menti az adatbázisba.
	 * @param medication a módosított gyógyszer
	 */
	public void editMedication(Medication medication){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.editMedication(medication);
		
		entityManager.getTransaction().commit();
		
		logger.info("{} módosításainak mentése az adatbázisban.",medication.getName());
		
		updateList();
	}
	
	/**A paraméterül adott gyógyszert törli az adatbázisból.
	 * @param medication a törölni kívánt gyógyszer
	 */
	public void deleteMedication(Medication medication){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.deleteMedication(medication.getId());
		
		entityManager.getTransaction().commit();
		
		logger.info("{} törlése az adatbázisból!",medication.getName());
		
		updateList();
	}

	/**Visszaad egy gyógyszer objektumot
	 * az azonosítója alapján.
	 * @param id a gyógyszer azonosítója
	 * @return result a gyógyszer objektum
	 */
	public Medication getMedicationById(int id) {
		Medication result = new Medication();
		for (Medication med : medications) {
			if (med.getId().equals(id)) {
				result = med;
            }
        }
        return result;
    }

	/**Visszaadja egy TB-támogatott gyógyszer
	 * árát.
	 * @param medication a gyóygszer objektum
	 * @return unitprice a módosított egységár
	 */
	public static double supportedMedCalc(Medication medication) {
		if (medication.getSupportedMed() == 1) {
			return medication.getUnitprice() * 0.5;
        }
		else return medication.getUnitprice();
    }
	
	/**{@link List} objektummá alakít egy paraméterül kapott {@link pharmacy.model.Medication}
	 * objektumokat tartalmazó {@link javafx.collections.ObservableList} objektumot.
	 * @param meds {@link javafx.collections.ObservableList} objektum
	 * @return {@link java.util.List} objektum
	 */
	public List<Medication> convertToList(ObservableList<Medication> meds){
		List<Medication> medications = new ArrayList<Medication>();
		for(Medication m : meds){
			medications.add(m);
		}
		
		return medications;
	}
}
