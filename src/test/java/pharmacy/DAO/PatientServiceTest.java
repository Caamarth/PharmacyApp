package pharmacy.DAO;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pharmacy.DateUtil;
import pharmacy.model.Patient;

public class PatientServiceTest {
	
	private static PatientService patientService;
	private static Patient patient;
	private static ObservableList<Patient> testPatientList;
	@BeforeClass
	public static void initialize(){
		
		patient = new Patient();
		patient.setId(999);
		patient.setTajszam("111111111");
		patient.setName("Tesztnév");
		patient.setAddress("Tesztcím");
		patient.setBirthDate(DateUtil.parse("1990.01.01"));
		
		testPatientList = FXCollections.observableArrayList();
		testPatientList.add(patient);
		
		patientService = new PatientService(testPatientList);
	}
	
	@Test
	public void patientRankDetailTestForVasarlo(){
		patient.setPatientRank(0);
		String test = patientService.patientRankDetail(patient);
		Assert.assertEquals("Vásárló", test);
	}
	
	@Test
	public void patientRankDetailTestForKiemelt(){
		patient.setPatientRank(1);
		String test = patientService.patientRankDetail(patient);
		Assert.assertEquals("Kiemelt Vásárló", test);
	}
	
	@Test
	public void patientRankDetailTestForTorzsvasarlo(){
		patient.setPatientRank(2);
		String test = patientService.patientRankDetail(patient);
		Assert.assertEquals("Törzsvásárló", test);
	}
	
	@Test
	public void getPatientByIdTest(){
		Assert.assertEquals(patient, patientService.getPatientById(999));
	}
	
	@Test
	public void getIdByTajTest(){
		Assert.assertEquals(999, patientService.getIdByTaj("111111111"));
	}

}
