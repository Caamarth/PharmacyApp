package pharmacy.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**Vásárlásokat reprecentáló osztály, amely létrehozza a vásárlások objektumait.
 * @author Babély Norbert Alex
 *
 */
@Entity
public class Purchase{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private Patient patient;
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Medication> medicationList = new ArrayList<Medication>();
	private int price;
	private LocalDate purchaseDate;
	
	/**Üres konstruktor, amely létrehoz egy vásárlás objektumot.
	 * 
	 */
	public Purchase(){

	}
	
	/**Konstruktor, amely létrehoz egy vásárlás objektumot.
	 * @param patient a vásárláshoz tartozó beteg
	 * @param price a vásárlás összege
	 * @param date a vásárlás dátuma
	 */
	public Purchase(Patient patient, int price, LocalDate date){
		super();
		this.patient = patient;
		this.price = price;
		this.purchaseDate = date;
	}

	/**Visszaadja a vásárlás azonosítóját.
	 * @return idProperty a vásárlás azonosítója
	 */
	public IntegerProperty getIdProperty() {
		return new SimpleIntegerProperty(id);
	}

	/**Visszaadja a vásárolt gyógyszerek listáját.
	 * @return mmedicationListProperty a gyógyszerek listája
	 */
	public List<Medication> getMedicationList() {
		return medicationList;
	}
	
	/**Visszaadja a vásárlás összegét.
	 * @return priceProperty a vásárlás összege
	 */
	public IntegerProperty getPriceProperty() {
		return new SimpleIntegerProperty(price);
	}
	
	/**Visszaadja a vásárlás dátumát.
	 * @return dateProperty a vásárlás dátuma
	 */
	public ObjectProperty<LocalDate> getDateProperty() {
		return new SimpleObjectProperty<LocalDate>(purchaseDate);
	}
	
	/**Visszaadja a vásárlás azonosítóját.
	 * @return id a vásárlás azonosítója
	 */
	public int getId(){
		return id;
	}	

	public Patient getPatient(){
		return patient;
	}
	
	/**Visszaadja a vásárlás összegét.
	 * @return price a vásárlás összege
	 */
	public int getPrice(){
		return price;
	}
	
	/**Visszaadja a vásárlás dátumát.
	 * @return date a váárlás dátuma
	 */
	public LocalDate getDate(){
		return purchaseDate;
	}

	/**Beállítja a vásárlás azonosítóját.
	 * @param id a vásárlás azonosítója
	 */
	public void setId(int id){
		this.id = id;
	}
	
	
	public void setPatient(Patient patient){
		this.patient = patient;
	}
	
	public void setMedicationList(List<Medication> medications){
		this.medicationList = medications;
	}
	
	/**Beállítja a vásárlás összegét.
	 * @param price a vásárlás összege
	 */
	public void setPrice(int price){
		this.price = price;
	}
	
	/**Beállítja a vásárlás dátumát.
	 * @param date a vásárlás dátuma.
	 */
	public void setDate(LocalDate date){
		this.purchaseDate = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((medicationList == null) ? 0 : medicationList.hashCode());
		result = prime * result + ((patient == null) ? 0 : patient.hashCode());
		result = prime * result + price;
		result = prime * result + ((purchaseDate == null) ? 0 : purchaseDate.hashCode());
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
		Purchase other = (Purchase) obj;
		if (id != other.id)
			return false;
		if (medicationList == null) {
			if (other.medicationList != null)
				return false;
		} else if (!medicationList.equals(other.medicationList))
			return false;
		if (patient == null) {
			if (other.patient != null)
				return false;
		} else if (!patient.equals(other.patient))
			return false;
		if (price != other.price)
			return false;
		if (purchaseDate == null) {
			if (other.purchaseDate != null)
				return false;
		} else if (!purchaseDate.equals(other.purchaseDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", patient=" + patient + ", medicationList=" + medicationList + ", price=" + price
				+ ", purchaseDate=" + purchaseDate + "]";
	}
	
	
	
}
