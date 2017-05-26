package pharmacy.DAO;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pharmacy.model.Medication;
import pharmacy.model.Patient;
import pharmacy.model.Purchase;

/**Az adatbázis műveleteket megvalósító osztály. Az {@link EntityManager} objektumot
 * a {@link pharmacy.MainApp} osztályból kapjuk.
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
     * A {@code createQuery()} metódus közvetlenül kezéri az adatbázisból a paraméterként
     * adott SQL utasítást, pl.: {@code SELECT p from pharmacy.model.Patient p}, amely egy 
     * {@link javax.persistence.TypedQuery} objektumot ad vissza eredényül.
     * Az eredményt listaként a {@code getResultList()} metódussal kapjuk meg.
     * @return összes beteg listája
     */
    public List<Patient> getPatientsList() {
    	TypedQuery<Patient> p = entityManager.createQuery("SELECT p from pharmacy.model.Patient p",Patient.class);
    	return p.getResultList();
    }
    
    /**Lekéri az adatbázisból az összes gyógyszert, majd listaként visszaadja.
     * A {@code createQuery()} metódus közvetlenül kezéri az adatbázisból a paraméterként
     * adott SQL utasítást, pl.: {@code SELECT m from pharmacy.model.Medication m}, amely egy 
     * {@link javax.persistence.TypedQuery} objektumot ad vissza eredényül.
     * Az eredményt listaként a {@code getResultList()} metódussal kapjuk meg.
     * @return az összes gyógyszer listája
     */
    public List<Medication> getMedicationsList(){
    	TypedQuery<Medication> m = entityManager.createQuery("SELECT m from pharmacy.model.Medication m",Medication.class);
    	return m.getResultList();
    }
    
    /**Lekéri az összes vásárlást az adatbázisból, majd listaként visszaadja.
     * A {@code createQuery()} metódus közvetlenül kezéri az adatbázisból a paraméterként
     * adott SQL utasítást, pl.: {@code SELECT pch from pharmacy.model.Purchase pch}, amely egy 
     * {@link javax.persistence.TypedQuery} objektumot ad vissza eredényül.
     * Az eredményt listaként a {@code getResultList()} metódussal kapjuk meg.
     * @return az összes vásárlás listája
     */
    public List<Purchase> getPurchasesList(){
    	TypedQuery<Purchase> pch = entityManager.createQuery("SELECT pch from pharmacy.model.Purchase pch",Purchase.class);
    	return pch.getResultList();
    }
    
    /**Létrehozza az új beteg objektumot, majd elmenti az adatbázisba.
     * A mentést a Persistence segítségével a {@code persist()} metódus végzi.
     * @param tajszam a beteg tajszáma
     * @param name a beteg neve
     * @param address a beteg címe
     * @param birthdate a beteg születési dátuma
     * @param patientRank a beteg rangja
     * @return az adatbázisba elmentett beteg objektum
     * @exception IllegalArgumentException ha az entitás már leválasztott a kontextusból
     * @exception EntityExistsException ha az entitás már létezik az adatbázisban
     */
    public Patient createPatient(String tajszam, String name,
			String address, LocalDate birthdate, int patientRank) {
    	try {
	    	Patient patient = new Patient(tajszam, name, address, birthdate, patientRank);
	    	entityManager.persist(patient);
	    	return patient;
    	} catch (IllegalArgumentException | EntityExistsException e){
    		logger.error("Beteg létrehozása sikertelen...{}", e.getMessage());
    		return null;
    	}
    }
    
    /**Létrehoz egy új gyógyszer objektumot, majd elmenti az adatbázisba.
     * A mentést a Persistence segítségével a {@code persist()} metódus végzi.
     * @param name a gyógyszer neve
     * @param manufacturer a gyógyszer gyártója
     * @param dose a gyógyszer dózisa
     * @param quantity a tabletták mennyisége
     * @param description a gyógyszer leírása
     * @param unitprice a gyógyszer egységára
     * @param supportedMed a gyógyszer TB-támogatottsága
     * @return az adatbázisba elmentett gyógyszer objektum
     * @exception IllegalArgumentException ha az entitás már leválasztott a kontextusból
     * @exception EntityExistsException ha az entitás már létezik az adatbázisban
     */
    public Medication createMedication(String name, String manufacturer,
			int dose, int quantity, String description, 
			int unitprice, int supportedMed){
    	try {
	    	Medication medication = new Medication(name, manufacturer, dose, quantity, description, unitprice, supportedMed);
	    	entityManager.persist(medication);
	    	return medication;
    	} catch (IllegalArgumentException | EntityExistsException e) {
    		logger.error("Gyógyszer létrehozása sikertelen... {}", e.getMessage());
    		return null;
    	}
    }
    
    /**Létrehoz egy új vásárlás objektumot, majd elmenti az adatbázisba.
     * A mentést a Persistence segítségével a {@code persist()} metódus végzi.
     * @param patient a vásárló beteg 
     * @param price a vasárlás végösszege
     * @param date a vásárlás dátuma
     * @param medications a vásárolt gyógyszerek listája
     * @return az adatbázisba elmentett vásárlás objektum
     * @exception IllegalArgumentException ha az entitás már leválasztott a kontextusból
     * @exception EntityExistsException ha az entitás már létezik az adatbázisban
     */
    public Purchase createPurchase(Patient patient, int price, LocalDate date, List<Medication> medications) {
    	try {
	    	Purchase purchase = new Purchase(patient, price, date);
	    	entityManager.persist(purchase); 	
	    	for(Medication m : medications){
	    		purchase.getMedicationList().add(m);
	    	}
	    	entityManager.merge(purchase);
	    	return purchase;
    	} catch (IllegalArgumentException | EntityExistsException e) {
    		logger.error("Vásárlás mentése sikertelen... {n}", e.getMessage());
    		return null;
    	}
    }
    
    /**A paraméterül kapott beteg módosításait elmenti az adatbázisba.
     * A mentést a Persistence segítségével a {@code merge()} metódus végzi.
     * @param patient a módosított beteg
     * @exception IllegalArgumentException ha az entitás már leválasztott a kontextusból
     * vagy nem létező entitás
     */
    public void editPatient(Patient patient){
    	try {
    		entityManager.merge(patient);
    	} catch (IllegalArgumentException e) {
    		logger.error("Módosítások mentése sikertelen... {}",e.getMessage());
    	}
    }
    
    /**A paraméterül kapott gyógyszer módosításait elmenti az adatbázisba.
     * A mentést a Persistence segítségével a {@code merge()} metódus végzi.
     * @param medication a módosított gyógyszer
     * @exception IllegalArgumentException ha az entitás már leválasztott a kontextusból
     * vagy nem létező entitás
     */
    public void editMedication(Medication medication){
    	try {
    	entityManager.merge(medication);
    	} catch (IllegalArgumentException e) {
    		logger.error("Módosítások mentése sikertelen... {}",e.getMessage());
    	}
    }
    
    /**A paraméterül kapott azonosító alapján megkeresi a 
     * beteget az adatbázisban. 
     * @param id a beteg azonosítója
     * @return a megtalált beteg objektum
     * @exception IllegalArgumentException ha az első paraméter nem létező entitásosztály,
     * vagy a második paraméter nem megfelelő típusú elsődleges kulcs.
     */
    public Patient findPatientById(int id) {
    	try {
    		return entityManager.find(Patient.class, id);
    	} catch (IllegalArgumentException e) {
    		logger.error("Beteg keresése sikertelen...{}",e.getMessage());
    		return null;
    	}
    }
    
    /**A paraméterül kapott azonosító alapján megkeresi a 
     * gyógyszert az adatbázisban.
     * @param id a gyógyszer azonosítója
     * @return a megtalált gyógyszer objektum
     * @exception IllegalArgumentException ha az első paraméter nem létező entitásosztály,
     * vagy a második paraméter nem megfelelő típusú elsődleges kulcs.
     */
    public Medication findMedicationById(int id) {
    	try {
    		return entityManager.find(Medication.class, id);
    	} catch (IllegalArgumentException e) {
    		logger.error("Gyógyszer keresése sikertelen... {}",e.getMessage());
    		return null;
    	}
    }
    
    /**A paraméterül kapott azonosító alapján megleresi a
     * vásárlást az adatbázisban.
     * @param id a vásárlás azonosítója
     * @return a megtalált vásárlás objektum
     * @exception IllegalArgumentException ha az első paraméter nem létező entitásosztály,
     * vagy a második paraméter nem megfelelő típusú elsődleges kulcs.
     */
    public Purchase findPurchaseById(int id) {
    	try {
    		return entityManager.find(Purchase.class, id);
    	} catch (IllegalArgumentException e){
    		logger.error("Vásárlás keresése sikertelen... {}", e.getMessage());
    		return null;
    	}
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
