package pharmacy.DAO;



import java.time.LocalDate;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
		patient.setDeleted(false);
		
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
	public void testGetPatientById(){
		Assert.assertEquals(patient, patientService.getPatientById(999));
	}
	
	@Test
	public void testGetIdByTaj(){
		Assert.assertEquals(999, patientService.getIdByTaj("111111111"));
	}
	
	@Test
	public void getPatientByIdTest(){
		Assert.assertEquals(patient, patientService.getPatientById(999));
	}
	
	@Test
	public void getIdByTajTest(){
		Assert.assertEquals(999, patientService.getIdByTaj("111111111"));
	}
	
	@Test
	public void testGetterMethods(){
		patient.setDeleted(false);
		
		Assert.assertEquals(999, patient.getId(), 0.000001);
		Assert.assertEquals("111111111", patient.getTajszam());
		Assert.assertEquals("Tesztnév", patient.getName());
		Assert.assertEquals("Tesztcím", patient.getAddress());
		Assert.assertEquals(DateUtil.parse("1990.01.01"), patient.getBirthdate());
		Assert.assertEquals(0, patient.getPatientRank(), 0.000001);
		Assert.assertEquals(false, patient.isDeleted());
		
		Assert.assertEquals(new SimpleIntegerProperty(999).getValue(), patient.getIdProperty().getValue());
		Assert.assertEquals(new SimpleStringProperty("111111111").getValue(), patient.getTajszamProperty().getValue());
		Assert.assertEquals(new SimpleStringProperty("Tesztnév").getValue(), patient.getNameProperty().getValue());
		Assert.assertEquals(new SimpleStringProperty("Tesztcím").getValue(), patient.getAddressProperty().getValue());
		Assert.assertEquals(new SimpleObjectProperty<LocalDate>(DateUtil.parse("1990.01.01")).getValue(), patient.getBirthdateProperty().getValue());
		Assert.assertEquals(new SimpleIntegerProperty(0).getValue(), patient.getPatientRankProperty().getValue());
	}
	
	@Test
	public void testToStringMethod(){
		String methodResult = "Patient [id=" + patient.getId() + 
				", tajszam=" + patient.getTajszam() + 
				", name=" + patient.getName() + 
				", address=" + patient.getAddress() + 
				", birthdate=" + patient.getBirthdate() + 
				", patientRank=" + patient.getPatientRank() + 
				", isDeleted=" + patient.isDeleted() + "]";
		Assert.assertEquals(methodResult, patient.toString());
	}
	
	@Test
	public void testPatientDelete(){
		patient.deletePatient();
		Assert.assertEquals(true, patient.isDeleted());
	}

}
