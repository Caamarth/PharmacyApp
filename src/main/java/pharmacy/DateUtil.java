package pharmacy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**Dátumformázó osztály, amely egy {@link java.time.LocalDate} objektumot formáz meg a
 * {@link java.time.format.DateTimeFormatter} segítségével.
 * @author Babély Norbert Alex
 *
 */
public class DateUtil {

	private static final String DATE_PATTERN = "yyyy.MM.dd";

	private static final DateTimeFormatter DATE_FORMATTER = 
			DateTimeFormatter.ofPattern(DATE_PATTERN);

	/**Átalakít egy {@link java.time.LocalDate}
	 *objektumot {@link java.lang.String} objektummá.
	 * @param date az átalakítandó LocalDate
	 *objektum
	 * @return string a átalakított objektum
	 */
	public static String format(LocalDate date) {
		if (date == null) {
			return null;
		}
		return DATE_FORMATTER.format(date);
	}

	/**Átalakít egy {@link java.lang.String} objektumot
	 *{@link java.time.LocalDate} objektummá, amennyiben
	 *annak formátuma megfelelő.
	 * @param dateString az átalakítandó string objektum 
	 * @return LocalDate az átalakított objektum
	 */
	public static LocalDate parse(String dateString) {
		try {
			return DATE_FORMATTER.parse(dateString, LocalDate::from);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	/**Ellenőrzi, hogy a paraméterül kapott
	 *{@link java.lang.String} objektum megfelel-e a formátumnak.
	 * @param dateString az ellenőrizendő
	 *string objektum
	 * @return boolean
	 */
	public static boolean validDate(String dateString) {
		return DateUtil.parse(dateString) != null;
	}

}
