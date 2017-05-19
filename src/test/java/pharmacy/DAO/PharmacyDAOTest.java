package pharmacy.DAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import pharmacy.DateUtil;
import pharmacy.model.Medication;
import pharmacy.model.Patient;
import pharmacy.model.Purchase;

public class PharmacyDAOTest {
	private static PharmacyDAO pharmacyDAO;
	private static EntityManager entityManager;
	private static List<Patient> patientList;
	private static List<Medication> medicationList;
	private static Patient patient;
	private static Medication medication;
	private static List<Purchase> purchaseList;
	private static Purchase purchase;
	
	@SuppressWarnings("unchecked")
	private static EntityManager initiate(){
		EntityManager mockedEntityManager = Mockito.mock(EntityManager.class);
		TypedQuery<Patient> mockedQuery = Mockito.mock(TypedQuery.class);
		TypedQuery<Medication> mockedQuery2 = Mockito.mock(TypedQuery.class);
		TypedQuery<Purchase> mockedQuery3 = Mockito.mock(TypedQuery.class);
		Mockito.when(mockedQuery.getResultList()).thenReturn(patientList);
		Mockito.when(mockedEntityManager.createQuery("SELECT p from pharmacy.model.Patient p",Patient.class)).thenReturn(mockedQuery);
		Mockito.when(mockedEntityManager.find(Patient.class, 999)).thenReturn(patient);
		Mockito.when(mockedEntityManager.find(Patient.class, 9999)).thenReturn(null);
		
		Mockito.when(mockedQuery2.getResultList()).thenReturn(medicationList);		
		Mockito.when(mockedEntityManager.createQuery("SELECT m from pharmacy.model.Medication m",Medication.class)).thenReturn(mockedQuery2);
		Mockito.when(mockedEntityManager.find(Medication.class, 999)).thenReturn(medication);
		Mockito.when(mockedEntityManager.find(Medication.class, 9999)).thenReturn(null);
		
		Mockito.when(mockedQuery3.getResultList()).thenReturn(purchaseList);		
		Mockito.when(mockedEntityManager.createQuery("SELECT pch from pharmacy.model.Purchase pch",Purchase.class)).thenReturn(mockedQuery3);
		Mockito.when(mockedEntityManager.find(Purchase.class, 999)).thenReturn(purchase);
		Mockito.when(mockedEntityManager.find(Purchase.class, 9999)).thenReturn(null);
		return mockedEntityManager;
	}
	
	@BeforeClass
	public static void setup(){
		
		patient = new Patient();
		patient.setId(999);
		patient.setTajszam("111111111");
		patient.setName("Tesztnév");
		patient.setAddress("Tesztcím");
		patient.setBirthDate(DateUtil.parse("1990.01.01"));
		
		medication = new Medication();
		medication.setId(999);
		medication.setName("Teszt");
		medication.setManufacturer("TesztGyarto");
		medication.setDose(200);
		medication.setQuantity(30);
		medication.setDescription("Tesztleírás");
		medication.setDeleted(false);
		medication.setUnitprice(9000);
		
		patientList = new ArrayList<>();
		patientList.add(patient);
		
		medicationList = new ArrayList<>();
		medicationList.add(medication);
		
		purchase = new Purchase();
		purchase.setId(999);
		purchase.getMedicationList().add(medication);
		purchase.setPrice(9000);
		
		purchaseList = new ArrayList<>();
		purchaseList.add(purchase);
		
		entityManager = initiate();
		pharmacyDAO  = new PharmacyDAO(entityManager);
	}
	
	@Test
	public void getAllPatientsTest(){
		Assert.assertEquals(1, pharmacyDAO.getPatientsList().size());
	}
	
	@Test
	public void getPatientByIdSuccessTest(){
		Assert.assertEquals(patient, pharmacyDAO.findPatientById(999));
	}
	
	@Test
	public void getPatientByIdNullTest(){
		Assert.assertEquals(null, pharmacyDAO.findPatientById(9999));
	}
	
	@Test
	public void getAllMedicationsTest(){
		Assert.assertEquals(1, pharmacyDAO.getMedicationsList().size());
	}
	
	@Test
	public void getMedicationByIdSuccessTest(){
		Assert.assertEquals(medication, pharmacyDAO.findMedicationById(999));
	}
	
	@Test
	public void getMedicationByIdNullTest(){
		Assert.assertEquals(null, pharmacyDAO.findMedicationById(9999));
	}
	
	@Test
	public void getAllPurchasesTest(){
		Assert.assertEquals(1, pharmacyDAO.getPurchasesList().size());
	}
	
	@Test
	public void getPurchaseByIdSuccessTest(){
		Assert.assertEquals(purchase, pharmacyDAO.findPurchaseById(999));
	}
	
	@Test 
	public void getPurchaseByIdNullTest(){
		Assert.assertEquals(null, pharmacyDAO.findPurchaseById(9999));
	}
	
	@Test
	public void createPatientTest(){
		Patient patientTest = new Patient();
		patientTest.setTajszam("111111111");
		patientTest.setName("Tesztnév");
		patientTest.setAddress("Tesztcím");
		patientTest.setBirthDate(DateUtil.parse("1990.01.01"));
		patientTest.setPatientRank(0);
		
		Assert.assertEquals(patientTest, pharmacyDAO.createPatient("111111111", "Tesztnév", "Tesztcím", DateUtil.parse("1990.01.01"), 0));
		Assert.assertEquals(patientTest.hashCode(), pharmacyDAO.createPatient("111111111", "Tesztnév", "Tesztcím", DateUtil.parse("1990.01.01"), 0).hashCode());
	}
	
	@Test
	public void createMedicationTest(){
		Medication medicationTest = new Medication();
		medicationTest.setName("Teszt");
		medicationTest.setManufacturer("Teszt");
		medicationTest.setDose(300);
		medicationTest.setDescription("Teszt");
		medicationTest.setQuantity(30);
		medicationTest.setUnitprice(3000);
		medicationTest.setSupportedMed(0);
		
		Assert.assertEquals(medicationTest, pharmacyDAO.createMedication("Teszt", "Teszt", 300, 30, "Teszt", 3000, 0));
		Assert.assertEquals(medicationTest.hashCode(), pharmacyDAO.createMedication("Teszt", "Teszt", 300, 30, "Teszt", 3000, 0).hashCode());
	}
	
	@Test
	public void createPurchaseTest(){
		Purchase purchaseTest = new Purchase();
		purchaseTest.getMedicationList().add(medication);
		purchaseTest.setDate(DateUtil.parse("2010.01.01"));
		purchaseTest.setPatient(patient);
		purchaseTest.setPrice(9000);
		
		Assert.assertEquals(purchaseTest, pharmacyDAO.createPurchase(patient, 9000, DateUtil.parse("2010.01.01"), medicationList));
		Assert.assertEquals(purchaseTest.hashCode(), pharmacyDAO.createPurchase(patient, 9000, DateUtil.parse("2010.01.01"), medicationList).hashCode());
	}
}
