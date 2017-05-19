package pharmacy.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pharmacy.MainApp;
import pharmacy.model.Medication;
import pharmacy.model.Patient;
import pharmacy.model.Purchase;

/**Az adatbázis műveleteket megvalósító osztály.
 * @author Babély Norbert Alex
 *
 */
@Transactional
public class PharmacyDAO {
	
	private static Logger logger = LoggerFactory.getLogger(MainApp.class);

    private EntityManager entityManager;
    
    public PharmacyDAO(EntityManager entityManager){
    	this.entityManager = entityManager;
    }

    public List<Patient> getPatientsList() {
    	TypedQuery<Patient> p = entityManager.createQuery("SELECT p from pharmacy.model.Patient p",Patient.class);
    	return p.getResultList();
    }
    
    public List<Medication> getMedicationsList(){
    	TypedQuery<Medication> m = entityManager.createQuery("SELECT m from pharmacy.model.Medication m",Medication.class);
    	return m.getResultList();
    }
    
    public List<Purchase> getPurchasesList(){
    	TypedQuery<Purchase> pch = entityManager.createQuery("SELECT pch from pharmacy.model.Purchase pch",Purchase.class);
    	return pch.getResultList();
    }
    
    public Patient createPatient(String tajszam, String name,
			String address, LocalDate birthdate, int patientRank) {
    	Patient patient = new Patient(tajszam, name, address, birthdate, patientRank);
    	
    	entityManager.persist(patient);
    	return patient;
    }
    
    public Medication createMedication(String name, String manufacturer,
			int dose, int quantity, String description, 
			int unitprice, int supportedMed){
    	Medication medication = new Medication(name, manufacturer, dose, quantity, description, unitprice, supportedMed);
    	entityManager.persist(medication);
    	return medication;
    }
    
    public Purchase createPurchase(Patient patient, int price, LocalDate date, List<Medication> medications) {
    	Purchase purchase = new Purchase(patient, price, date);
    	entityManager.persist(purchase); 	
    	for(Medication m : medications){
    		purchase.getMedicationList().add(m);
    	}
    	entityManager.merge(purchase);
    	logger.info("A {} azonosítójú vásárlás beszúrva az adatbázisba! ",purchase.getId());
    	return purchase;
    }
    
    public void editPatient(Patient patient){
    	entityManager.merge(patient);
    }
    
    public void editMedication(Medication medication){
    	entityManager.merge(medication);
    }
    
    public Patient findPatientById(int id) {
    	return entityManager.find(Patient.class, id);
    }
    
    public Medication findMedicationById(int id) {
    	return entityManager.find(Medication.class, id);
    }
    
    public Purchase findPurchaseById(int id) {
    	return entityManager.find(Purchase.class, id);
    }
    
    public void deletePatient(int id) {
    	Patient patient = findPatientById(id);
    	if (patient != null) {
    		entityManager.remove(patient);
    	} else {
    		throw new RuntimeException("Sikertelen törlés...");
    	}
    }
    
    public void deleteMedication(int id) {
    	Medication medication = findMedicationById(id);
    	if (medication != null) {
    		entityManager.remove(medication);
    	} else {
    		throw new RuntimeException("Sikertelen törlés...");
    	}
    	
    }

    
}
