package pharmacy;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;



public class DateUtilTest {
	private static LocalDate localDateTest;
	private static String testString;
	
	@Test
	public void testFormatter(){
		testString = "1990.07.29";
		localDateTest = LocalDate.of(1990, 07, 29);
		Assert.assertEquals(testString, DateUtil.format(localDateTest));
	}
	
	@Test
	public void testParser(){
		testString = "1990.07.29";
		localDateTest = LocalDate.of(1990, 07, 29);
		Assert.assertEquals(localDateTest, DateUtil.parse(testString));
	}
	
	@Test
	public void testValidater(){
		testString = "1990.07.29";
		boolean testResult = true;
		Assert.assertEquals(testResult, DateUtil.validDate(testString));
	}
}
