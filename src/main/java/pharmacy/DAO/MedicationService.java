package pharmacy.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Medication;

/**A gyógyszereket kezelő osztály.
 * @author Babély Norbert Alex
 *
 */
public class MedicationService {
	
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("MainApp");
	EntityManager entityManager = entityManagerFactory.createEntityManager();
	
	private PharmacyDAO pharmacyDAO = new PharmacyDAO(entityManager);
	
	public ObservableList<Medication> medications
        = FXCollections.observableArrayList(pharmacyDAO.getMedicationsList());

	/**Paraméter nélküli konstruktor, amely
	 * létrehozza a {@link pharmacy.DAO.MedicationService}
	 * objektumot.
	 *
	 */
	public MedicationService() {
		
    }
	
	public MedicationService(ObservableList<Medication> medications){
		this.medications = medications;
	}
	
	public void updateList(){
		ObservableList<Medication> medicationList = FXCollections.observableArrayList(pharmacyDAO.getMedicationsList());
		medications.removeAll(medications);
		for(Medication m : medicationList){
			if(!medications.contains(m)){
				medications.add(m);
			}
		}
	}

	/**Visszaadja a gyógyszereket tartalmazó listát.
	 * @return medications a gyógyszerek listája
	 */
	public ObservableList<Medication> getAllMedications() {
		updateList();
		return medications;
	}
	
	public void addMedication(Medication medication){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.createMedication(medication.getName(),
				medication.getManufacturer(),
				medication.getDose(), medication.getQuantity(),
				medication.getDescription(),
				medication.getUnitprice(), 
				medication.getSupportedMed());
		
		entityManager.getTransaction().commit();
		
		updateList();
	}
	
	public void editMedication(Medication medication){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.editMedication(medication);
		
		entityManager.getTransaction().commit();
		
		updateList();
	}
	
	public void deleteMedication(Medication medication){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.deleteMedication(medication.getId());
		
		entityManager.getTransaction().commit();
		
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
	
	public List<Medication> convertToList(ObservableList<Medication> meds){
		List<Medication> medications = new ArrayList<Medication>();
		for(Medication m : meds){
			medications.add(m);
		}
		
		return medications;
	}
}
