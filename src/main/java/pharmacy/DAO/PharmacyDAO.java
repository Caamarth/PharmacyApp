package pharmacy.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pharmacy.model.Medication;
import pharmacy.model.Patient;
import pharmacy.model.Purchase;

/**Az adatbázis műveleteket megvalósító osztály.
 * @author Babély Norbert Alex
 *
 */
@Transactional
public class PharmacyDAO {
	
	private static Logger logger = LoggerFactory.getLogger(PharmacyDAO.class);

    private EntityManager entityManager;
    
    /**Paraméteres konstruktor, amely egy {@link pharmacy.DAO.PharmacyDAO} objektumot hoz létre.
     * @param entityManager egy {@link javax.persistence.EntityManager} objektum
     */
    public PharmacyDAO(EntityManager entityManager){
    	this.entityManager = entityManager;
    }

    /**Lekéri az adatbázisból az összes beteget, majd listaként visszaadja.
     * @return összes beteg listája
     */
    public List<Patient> getPatientsList() {
    	TypedQuery<Patient> p = entityManager.createQuery("SELECT p from pharmacy.model.Patient p",Patient.class);
    	return p.getResultList();
    }
    
    /**Lekéri az adatbázisból az összes gyógyszert, majd listaként visszaadja.
     * @return az összes gyógyszer listája
     */
    public List<Medication> getMedicationsList(){
    	TypedQuery<Medication> m = entityManager.createQuery("SELECT m from pharmacy.model.Medication m",Medication.class);
    	return m.getResultList();
    }
    
    /**Lekéri az összes vásárlást az adatbázisból, majd listaként visszaadja.
     * @return az összes vásárlás listája
     */
    public List<Purchase> getPurchasesList(){
    	TypedQuery<Purchase> pch = entityManager.createQuery("SELECT pch from pharmacy.model.Purchase pch",Purchase.class);
    	return pch.getResultList();
    }
    
    /**Létrehozza az új beteg objektumot, majd elmenti az adatbázisba.
     * @param tajszam a beteg tajszáma
     * @param name a beteg neve
     * @param address a beteg címe
     * @param birthdate a beteg születési dátuma
     * @param patientRank a beteg rangja
     * @return az adatbázisba elmentett beteg objektum
     */
    public Patient createPatient(String tajszam, String name,
			String address, LocalDate birthdate, int patientRank) {
    	Patient patient = new Patient(tajszam, name, address, birthdate, patientRank);
    	entityManager.persist(patient);
    	return patient;
    }
    
    /**Létrehoz egy új gyógyszer objektumot, majd elmenti az adatbázisba.
     * @param name a gyógyszer neve
     * @param manufacturer a gyógyszer gyártója
     * @param dose a gyógyszer dózisa
     * @param quantity a tabletták mennyisége
     * @param description a gyógyszer leírása
     * @param unitprice a gyógyszer egységára
     * @param supportedMed a gyógyszer TB-támogatottsága
     * @return az adatbázisba elmentett gyógyszer objektum
     */
    public Medication createMedication(String name, String manufacturer,
			int dose, int quantity, String description, 
			int unitprice, int supportedMed){
    	Medication medication = new Medication(name, manufacturer, dose, quantity, description, unitprice, supportedMed);
    	entityManager.persist(medication);
    	return medication;
    }
    
    /**Létrehoz egy új vásárlás objektumot, majd elmenti az adatbázisba.
     * @param patient a vásárló beteg 
     * @param price a vasárlás végösszege
     * @param date a vásárlás dátuma
     * @param medications a vásárolt gyógyszerek listája
     * @return az adatbázisba elmentett vásárlás objektum
     */
    public Purchase createPurchase(Patient patient, int price, LocalDate date, List<Medication> medications) {
    	Purchase purchase = new Purchase(patient, price, date);
    	entityManager.persist(purchase); 	
    	for(Medication m : medications){
    		purchase.getMedicationList().add(m);
    	}
    	entityManager.merge(purchase);
    	return purchase;
    }
    
    /**A paraméterül kapott beteg módosításait elmenti az adatbázisba.
     * @param patient a módosított beteg
     */
    public void editPatient(Patient patient){
    	entityManager.merge(patient);
    }
    
    /**A paraméterül kapott gyógyszer módosításait elmenti az adatbázisba.
     * @param medication a módosított gyógyszer
     */
    public void editMedication(Medication medication){
    	entityManager.merge(medication);
    }
    
    /**A paraméterül kapott azonosító alapján megkeresi a 
     * beteget az adatbázisban. 
     * @param id a beteg azonosítója
     * @return a megtalált beteg objektum
     */
    public Patient findPatientById(int id) {
    	return entityManager.find(Patient.class, id);
    }
    
    /**A paraméterül kapott azonosító alapján megkeresi a 
     * gyógyszert az adatbázisban.
     * @param id a gyógyszer azonosítója
     * @return a megtalált gyógyszer objektum
     */
    public Medication findMedicationById(int id) {
    	return entityManager.find(Medication.class, id);
    }
    
    /**A paraméterül kapott azonosító alapján megleresi a
     * vásárlást az adatbázisban.
     * @param id a vásárlás azonosítója
     * @return a megtalált vásárlás objektum
     */
    public Purchase findPurchaseById(int id) {
    	return entityManager.find(Purchase.class, id);
    }
    
    /**A paraméterül kapott azonosító alapján törli a
     * hozzá tartozó beteget az adatbázisból.
     * @param id a beteg azonosítója
     */
    public void deletePatient(int id) {
    	Patient patient = findPatientById(id);
    	if (patient != null) {
    		entityManager.remove(patient);
    	} else {
    		logger.info("A {} azonosítójú beteg sikertelen törlése...",id);
    	}
    }
    
    /**A paraméterül kapott azonosító alapján törli a 
     * hozzá tartozó gyógyszert az adatbázisból.
     * @param id a gyógyszer azonosítója
     */
    public void deleteMedication(int id) {
    	Medication medication = findMedicationById(id);
    	if (medication != null) {
    		entityManager.remove(medication);
    	} else {
    		logger.info("A {} azonosítójú gyógyszer sikertelen törlése...",id);
    	}
    	
    }

    
}
