package pharmacy.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Patient;

/**A betegeket kezelő osztály.
 * @author Babély Norbert Alex
 *
 */
public class PatientService {
	
	EntityManagerFactory entityManagerFactory;
	EntityManager entityManager;
	
	private PharmacyDAO pharmacyDAO;
	
	private ObservableList<Patient> patients;
	
	/**Paraméter nélküli kontstruktor,
	 * amely létrehozza a {@link pharmacy.DAO.PatientService}
	 * objektumot.
	 */
	public PatientService() {
		entityManagerFactory = Persistence.createEntityManagerFactory("MainApp");
		entityManager = entityManagerFactory.createEntityManager();
		pharmacyDAO = new PharmacyDAO(entityManager);
		patients = FXCollections.observableArrayList(pharmacyDAO.getPatientsList());
    }
	
	/**Paraméteres konstruktor az osztály JUnit teszteléséhez.
	 * @param patients a gyógyszerek listája
	 */
	public PatientService(ObservableList<Patient> patients){
		this.patients = patients;
	}
	
	
	protected void updateList(){
		ObservableList<Patient> patientList = FXCollections.observableArrayList(pharmacyDAO.getPatientsList());
		patients.removeAll(patients);
		for(Patient p : patientList){
			if(!patients.contains(p)){
				patients.add(p);
			}
		}
	}

	/**Visszaadja a betegeket tartalmazó listát.
	 * @return patients a betegeket tartalmazó lista
	 */
	public ObservableList<Patient> getAllPatients() {
		updateList();
		return patients;
    }

	
	/**A paraméterül kapott beteget mentésre küldi az adatbázisba.
	 * @param patient a menteni kívánt beteg
	 */
	public void addPatient(Patient patient){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.createPatient(patient.getTajszam(),
				patient.getName(),
				patient.getAddress(),
				patient.getBirthdate(),
				patient.getPatientRank());
		
		entityManager.getTransaction().commit();
		
		updateList();
	}
	
	/**A paraméterül kapott beteg módosításait menti az adatbázisba.
	 * @param patient a módosítandó beteg
	 */
	public void editPatient(Patient patient){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.editPatient(patient);
		
		entityManager.getTransaction().commit();
		
		updateList();
	}
	
	/**A paraméterül kapott beteget törlsre küldi az adatbázisba.
	 * @param patient a törölni kívánt beteg
	 */
	public void deletePatient(Patient patient){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.deletePatient(patient.getId());
		
		entityManager.getTransaction().commit();
		
		updateList();
	}

	/**Visszaadja a beteg azonosítóját
	 * a TAJ-száma alapján.
	 * @param tajszam a beteg TAJ-száma
	 * @return tempId a beteg azonosítója
	 */
	public int getIdByTaj(String tajszam) {
		int tempId = 0;
		for (Patient p : patients) {
			if (p.getTajszam().equals(tajszam)) {
				tempId = p.getId();
            }
        }
		return tempId;
    }

	/**Visszaadja a beteget az azonosítója alapján.
	 * @param id a beteg azonosítója
	 * @return result a beteg objektum
	 */
	public Patient getPatientById(int id) {
		Patient result = new Patient();
		for (Patient p : patients) {
			if (p.getId() == id) {
				result = p;
            }
        }
		return result;
    }

	/**Beállítja a beteg rangját a vásárolt összeg alapján.
	 * @param id a beteg azonosítója
	 * @param value a vásárlás végösszege
	 */
	public void setPatientRank(int id, int value) {
			if (value >= 20000 && getPatientById(id).getPatientRank() < 2) {
				getPatientById(id).setPatientRank(2);
				entityManager.getTransaction().begin();
				
				pharmacyDAO.editPatient(getPatientById(id));
				
				entityManager.getTransaction().commit();
            } else if (value >= 10000 && getPatientById(id).getPatientRank() < 1) {
				getPatientById(id).setPatientRank(1);
				entityManager.getTransaction().begin();
				
				pharmacyDAO.editPatient(getPatientById(id));
				
				entityManager.getTransaction().commit();
            }
    }

	/**Szöveggé alakítja a beteg rangját.
	 * @param patient a beteg objektum
	 * @return string a beteg rangja
	 */
	public String patientRankDetail(Patient patient) {
		if (patient.getPatientRank() == 2) {
			return "Törzsvásárló";
		}
		else if (patient.getPatientRank() == 1) {
			return "Kiemelt Vásárló";
		} else {
			return "Vásárló";
		}
	}
}
