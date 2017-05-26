package pharmacy.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.NotEmpty;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;



/**
 * Betegeket reprezentáló osztály, mely a beteg objektumokat hozza létre.
 * @author Babély Norbert Alex
 *
 */
@Entity
@SQLDelete(sql = "UPDATE Patient SET isDeleted = 1 WHERE ID = ?",check = ResultCheckStyle.COUNT)
public class Patient {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@NotNull
	@NotEmpty
	private String tajszam;
	@NotEmpty
	private String name;
	@NotEmpty
	private String address;
	private LocalDate birthdate;
	private int patientRank;
	
	private boolean isDeleted = false;

	/**
	 * Üres konstruktor, mely létrehozza a beteg objektumot.
	 */
	public Patient(){
		
	}

	/**
	 * Konstruktor, mely létrehozza a beteg objektumot.
	 * @param tajszam a beteg TAJ-száma
	 * @param name a beteg neve
	 * @param address a beteg címe
	 * @param birthdate a beteg születési dátuma
	 * @param patientRank a beteg rangja 
	 */
	public Patient(String tajszam, String name,
			String address, LocalDate birthdate, int patientRank){
	
		this.tajszam = tajszam;
		this.name = name;
		this.address = address;
		this.birthdate = birthdate;
		this.patientRank = patientRank;
	}

	/**
	 * Visszaadja a beteg azonosítóját.
	 * @return idProperty a beteg azonosítója
	 */
	public IntegerProperty getIdProperty(){
		return new SimpleIntegerProperty(id);
	}
	
	/**Visszaadja a beteg TAJ-számát.
	 * @return tajszamProperty a beteg TAJ-száma
	 */
	public StringProperty getTajszamProperty() {
		return new SimpleStringProperty(tajszam);
	}
	
	/**Visszaadja a beteg nevét.
	 * @return nameProperty a beteg neve
	 */
	public StringProperty getNameProperty() {
		return new SimpleStringProperty(name);
	}
	
	/**Visszaadja a beteg címét.
	 * @return addressProperty a beteg címe
	 */
	public StringProperty getAddressProperty() {
		return new SimpleStringProperty(address);
	}
	
	/**Visszaadja a beteg születési dátumát.
	 * @return birthdateProperty a beteg születési dátuma
	 */
	public ObjectProperty<LocalDate> getBirthdateProperty() {
		return new SimpleObjectProperty<LocalDate>(birthdate);
	}
	
	/**Visszaadja a beteg rangját.
	 * @return patientRankProperty a beteg rangja
	 */
	public IntegerProperty getPatientRankProperty(){
		return new SimpleIntegerProperty(patientRank);
	}
	
	/**Visszaadja a beteg azonosítóját.
	 * @return id a beteg azonosítója.
	 */

	public Integer getId(){
		return id;
	}
	
	/**Visszaadja a beteg TAJ-számát.
	 * @return tajszam a beteg TAJ-száma
	 */
	public String getTajszam(){
		return tajszam;
	}
	
	/**Visszaadja a beteg nevét.
	 * @return name a beteg neve
	 */

	public String getName(){
		return name;
	}
	
	/**Visszaadja a beteg címét.
	 * @return address a beteg címe
	 */
	public String getAddress(){
		return address;
	}
	
	/**Visszaadja a beteg születési dátumát.
	 * @return birthdate a beteg születési dátuma
	 */
	public LocalDate getBirthdate(){
		return birthdate;
	}
	
	/**Visszaadja a beteg rangját.
	 * @return rank a beteg rangja
	 */
	public Integer getPatientRank(){
		return patientRank;
	}
	

	/**Visszaadja, hogy egy beteg logikailag törölve lett-e az adatbázisban.
	 * @return true ha a beteg állapota törölt
	 */
	public boolean isDeleted() {
		return isDeleted;
	}

	/**Beállítja a beteg azonosítóját.
	 * @param id a beteg azonosítója
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**Beállítja a beteg TAJ-számát.
	 * @param tajszam a beteg TAJ-száma
	 */
	public void setTajszam(String tajszam){
		this.tajszam = tajszam;
	}
	
	/**Beállítja a beteg nevét.
	 * @param name a beteg neve
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**Beállítja a beteg címét.
	 * @param address a beteg címe
	 */
	public void setAddress(String address){
		this.address = address;
	}
	
	/**Beállítja a beteg születési dátumát.
	 * @param birthdate a beteg születési dátuma
	 */
	public void setBirthDate(LocalDate birthdate){
		this.birthdate = birthdate;
	}
	
	/**Beállítja a beteg rangját.
	 * @param patientRank a beteg rangja
	 */
	public void setPatientRank(Integer patientRank){
		this.patientRank = patientRank;
	}

	/**Beállítja a beteg törlési állapotát.
	 * @param isDeleted - true ha törölt a beteg
	 */
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	
	/**Logikai törlés előtt beállítja a beteg törlési állapotát
	 * töröltre.
	 */
	@PreRemove
	public void deletePatient(){
		this.isDeleted = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + patientRank;
		result = prime * result + ((tajszam == null) ? 0 : tajszam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (id != other.id)
			return false;
		if (isDeleted != other.isDeleted)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (patientRank != other.patientRank)
			return false;
		if (tajszam == null) {
			if (other.tajszam != null)
				return false;
		} else if (!tajszam.equals(other.tajszam))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", tajszam=" + tajszam + ", name=" + name + ", address=" + address + ", birthdate="
				+ birthdate + ", patientRank=" + patientRank + ", isDeleted=" + isDeleted + "]";
	}	
	
	
}
