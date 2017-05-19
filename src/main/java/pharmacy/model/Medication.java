package pharmacy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PreRemove;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**Gyógyszereket reprezentáló osztály, mely a gyógyszereket hozza létre.
 * @author Babély Norbert Alex
 *
 */

@Entity
@SQLDelete(sql = "UPDATE Medication SET isDeleted = 1 WHERE ID = ?",check = ResultCheckStyle.COUNT)
@Where(clause = "isDeleted = 'true'")
public class Medication {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String manufacturer;
	private int dose;
	private int quantity;
	private String description;
	private int unitprice;
	private int supportedMed;
	
	private boolean isDeleted = false;

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	@PreRemove
	public void deleteMedication(){
		this.isDeleted = true;
	}
	

	/**Paraméter nélküli konstruktor, mely létrehozza a gyógyszer objektumot.
	 * 
	 */
	public Medication(){

	}
	
	/**Kontruktor, mely létrehozza a gyógyszer objektumot.
	 * @param name a gyógyszer neve
	 * @param manufacturer a gyógyszer gyártójának neve
	 * @param dose a gyógyszer dózisa
	 * @param quantity a gyógyszer tablettáinak száma
	 * @param description a gyógyszer leírása
	 * @param unitprice a gyógyszer egységára
	 * @param supportedMed a gyógyszer TB-támogatottsága
	 */
	public Medication(String name, String manufacturer,
			int dose, int quantity, String description, 
			int unitprice, int supportedMed){
	
		this.name = name;
		this.manufacturer = manufacturer;
		this.dose = dose;
		this.quantity = quantity;
		this.description = description;
		this.unitprice = unitprice;
		this.supportedMed = supportedMed;
		
	}

	/**Visszaadja a gyógyszer azonosítóját.
	 * @return idProperty a gyógyszer azonosítója
	 */
	public IntegerProperty getIdProperty() {
		return new SimpleIntegerProperty(id);
	}
	
	/**Visszaadja a gyógyszer nevét.
	 * @return nameProperty a gyógyszer neve
	 */
	public StringProperty getNameProperty() {
		return new SimpleStringProperty(name);
	}
	
	/**Visszaadja a gyógyszer gyártójának nevét.
	 * @return manufacturerProperty a gyártó neve
	 */
	public StringProperty getManufacturerProperty() {
		return new SimpleStringProperty(manufacturer);
	}
	
	/**Visszaadja a gyógyszer dózisát.
	 * @return doseProperty a gyógyszer dózisa
	 */
	public IntegerProperty getDoseProperty() {
		return new SimpleIntegerProperty(dose);
	}
	
	/**Visszaadja a tabletták számát a gyógyszerben.
	 * @return quantityProperty a tabletták száma
	 */
	public IntegerProperty getQuantityProperty() {
		return new SimpleIntegerProperty(quantity);
	}
	
	/**Visszaadja a gyógyszer leírását.
	 * @return descriptionProperty a gyógyszer leírása
	 */
	public StringProperty getDescriptionProperty() {
		return new SimpleStringProperty(description);
	}
	
	/**Visszaadja a gyógyszer egységárát.
	 * @return unitpriceProperty a gyógyszer egységára
	 */
	public IntegerProperty getUnitpriceProperty() {
		return new SimpleIntegerProperty(unitprice);
	}
	/**Visszaadja, hogy a gyógyszer TB-támogatott-e.
	 * @return supportedMedProperty TB-támogatottság
	 */
	public IntegerProperty getSupportedMedProperty(){
		return new SimpleIntegerProperty(supportedMed);
	}
	
	/**Visszaadja a gyógyszer azonosítóját
	 * @return id a gyógyszer azonosítója
	 */

	public Integer getId(){
		return id;
	}
	
	/**Visszaadja a gyógyszer nevét.
	 * @return name a gyógyszer neve
	 */
	public String getName(){
		return name;
	}
	
	/**Visszaadja a gyógyszer gyártójának a nevét.
	 * @return manufacturer a gyógyszer gyártójának neve
	 */
	public String getManufacturer(){
		return manufacturer;
	}
	
	/**Visszaadja a gyógyszer dózisát.
	 * @return dose a gyógyszer dózisa
	 */
	public int getDose(){
		return dose;
	}
	
	/**Visszaadja a tabletták számát a gyógyszerben.
	 * @return quantity a tabletták száma
	 */
	public Integer getQuantity(){
		return quantity;
	}
	
	/**Visszaadja a gyógyszer leírását.
	 * @return description a gyógyszer leírása
	 */
	public String getDescription(){
		return description;
	}
	
	/**Visszaadja a gyógyszer egységárát.
	 * @return unitprice a gyógyszer egységára
	 */
	public int getUnitprice(){
		return unitprice;
	}
	
	/**Visszaadja, hogy a gyógyszer TB-támogatott-e.
	 * @return supportedMed a gyógyszer TB-támogatottsága
	 */
	public Integer getSupportedMed(){
		return supportedMed;
	}
	
	/**Beállítja a gyógyszer azonosítóját.
	 * @param id a gyógyszer azonosítója
	 */
	public void setId(Integer id){
		this.id = id;
	}
	
	/**Beállítja a gyógyszer nevét.
	 * @param name a gyógyszer neve
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**Beállítja a gyógyszer gyártójának a nevét.
	 * @param manufacturer a gyógyszer gyártójának neve
	 */
	public void setManufacturer(String manufacturer){
		this.manufacturer = manufacturer;
	}
	
	/**Beállítja a gyógyszer dózisát.
	 * @param dose a gyógyszer dózisa
	 */
	public void setDose(int dose){
		this.dose = dose;
	}
	
	/**Beállítja a gyógyszer tablettáinak a számát.
	 * @param quantity a tabletták száma
	 */
	public void setQuantity(Integer quantity){
		this.quantity = quantity;
	}
	
	/**Beállítja a gyógyszer leírását.
	 * @param description a gyógyszer leírása
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**Beállítja a gyógyszer egységárát.
	 * @param unitprice a gyógyszer egységára
	 */
	public void setUnitprice(int unitprice){
		this.unitprice = unitprice;
	}
	
	/**Beállítja a gyógyszer TB-támogatottságát.
	 * @param supportedMed a gyógyszer TB-támogatottsága
	 */
	public void setSupportedMed(int supportedMed){
		this.supportedMed = supportedMed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + dose;
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + quantity;
		result = prime * result + supportedMed;
		result = prime * result + unitprice;
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
		Medication other = (Medication) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dose != other.dose)
			return false;
		if (id != other.id)
			return false;
		if (isDeleted != other.isDeleted)
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (quantity != other.quantity)
			return false;
		if (supportedMed != other.supportedMed)
			return false;
		if (unitprice != other.unitprice)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Medication [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", dose=" + dose
				+ ", quantity=" + quantity + ", description=" + description + ", unitprice=" + unitprice
				+ ", supportedMed=" + supportedMed + ", isDeleted=" + isDeleted + "]";
	}
	
	
	
	
	
	
	
}
