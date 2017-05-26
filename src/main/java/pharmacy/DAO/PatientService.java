package pharmacy.DAO;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.model.Patient;

/**A betegeket kezelő osztály.
 * A betegeket a {@link pharmacy.DAO.PharmacyDAO} osztályon keresztül lekérdezi az
 * adatbázisból, majd egy {@link java.util.List}-ben kerülnek tárolásra.
 * Az osztály kapcsolatot tart a felhasználói felület kontrollerrel és az adatbázikezelő
 * osztállyal. 
 * A vásárlás összegétől függően beállíthatja a beteg rangját, amit az adatbázisban rögzít.
 * @author Babély Norbert Alex
 *
 */
public class PatientService {
	
	private static Logger logger = LoggerFactory.getLogger(PatientService.class);
	
	EntityManager entityManager;
	
	private PharmacyDAO pharmacyDAO;
	
	private ObservableList<Patient> patients;
	
	/**Paraméteres kontstruktor,
	 * amely létrehozza a {@link pharmacy.DAO.PatientService}
	 * objektumot.
	 * @param entityManager {@link javax.persistence.EntityManager} perzisztencia objektum
	 */
	public PatientService(EntityManager entityManager) {
		this.entityManager = entityManager;
		pharmacyDAO = new PharmacyDAO(this.entityManager);
		patients = FXCollections.observableArrayList(pharmacyDAO.getPatientsList());
    }
	
	/**Paraméteres konstruktor az osztály JUnit teszteléséhez.
	 * @param patients a gyógyszerek listája
	 */
	public PatientService(ObservableList<Patient> patients){
		this.patients = patients;
	}
	
	
	/**Frissíti a betegek listáját az adatbázisból. 
	 * A listába csak azok a betegek kerülnek be, akik logikailag nem töröltek,
	 * vagyis a {@link pharmacy.model.Patient} objektum {@code isDeleted} értéke
	 * false.
	 */
	protected void updateList(){
		ObservableList<Patient> patientList = FXCollections.observableArrayList(pharmacyDAO.getPatientsList());
		patients.removeAll(patients);
		for(Patient p : patientList){
			if(!patients.contains(p) && p.isDeleted() != true){
				patients.add(p);
			}
		}
		logger.info("Beteg lista frissítése...");
	}

	/**Visszaadja a betegeket tartalmazó listát.
	 * A listába csak azok a {@link pharmacy.model.Patient} objektumok
	 * kerülnek be, melyek logikailag nem töröltek, vagyis a
	 * {@code isDeleted} értéke false.
	 * @return patients a betegeket tartalmazó lista
	 */
	public ObservableList<Patient> getAllPatients() {
		logger.info("Betegek listájának lekérése.");
		updateList();
		List<Patient> filteredPatients = patients.stream().filter(e -> e.isDeleted() != true).collect(Collectors.toList());
		return FXCollections.observableArrayList(filteredPatients);
    }

	
	/**A paraméterül kapott beteget mentésre küldi az adatbázisban.
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
		
		logger.info("{} hozzáadása az adatbázishoz.",patient.getName());
		
		updateList();
	}
	
	/**A paraméterül kapott beteg módosításait menti az adatbázisban.
	 * @param patient a módosítandó beteg
	 */
	public void editPatient(Patient patient){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.editPatient(patient);
		
		entityManager.getTransaction().commit();
		
		logger.info("{} módosításainak mentése az adatbázisban.",patient.getName());
		
		updateList();
	}
	
	/**A paraméterül kapott beteget törlésre küldi az adatbázisban.
	 * @param patient a törölni kívánt beteg
	 */
	public void deletePatient(Patient patient){
		entityManager.getTransaction().begin();
		
		pharmacyDAO.deletePatient(patient.getId());
		
		entityManager.getTransaction().commit();
		
		logger.info("{} törlése az adatbázisból.",patient.getName());
		
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
	 * Ha a beteg 20000 Ft-nál nagyobb összegben vásárolt egyösszegben,
	 * a {@code patientRank} értékét 2-re, 10000 Ft-nál nagyobb összeg esetén 1-re
	 * állítja.
	 * @param id a beteg azonosítója
	 * @param value a vásárlás végösszege
	 */
	public void setPatientRank(int id, int value) {
			if (value >= 20000 && getPatientById(id).getPatientRank() < 2) {
				getPatientById(id).setPatientRank(2);
				entityManager.getTransaction().begin();
				
				pharmacyDAO.editPatient(getPatientById(id));
				
				entityManager.getTransaction().commit();
				logger.info("{} azonosítójú beteg rangjának változtatása: 2",id);
            } else if (value >= 10000 && getPatientById(id).getPatientRank() < 1) {
				getPatientById(id).setPatientRank(1);
				entityManager.getTransaction().begin();
				
				pharmacyDAO.editPatient(getPatientById(id));
				
				entityManager.getTransaction().commit();
				logger.info("{} azonosítójú beteg rangjának változtatása: 1",id);
            }
    }

	/**Szöveggé alakítja a beteg rangját.
	 * Ha a {@link pharmacy.model.Patient} objektum {@code patientRank}
	 * értéke 2, akkor Törzsvásárló, ha értéke 1, akkor Kiemelt Vásárló,
	 * ha 0, akkor Vásárló szöveggel tér vissza.
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
