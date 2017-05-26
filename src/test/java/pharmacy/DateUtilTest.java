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
	public void testFormatterFail(){
		localDateTest = null;
		Assert.assertEquals(null, DateUtil.format(localDateTest));
		
	}
	
	@Test
	public void testParser(){
		testString = "1990.07.29";
		localDateTest = LocalDate.of(1990, 07, 29);
		Assert.assertEquals(localDateTest, DateUtil.parse(testString));
	}
	
	@Test
	public void testParserFail(){
		testString = "1876-11-07";
		Assert.assertEquals(null, DateUtil.parse(testString));
	}
	
	@Test
	public void testValidater(){
		testString = "1990.07.29";
		Assert.assertEquals(true, DateUtil.validDate(testString));
	}
	
	@Test
	public void testValidaterFail(){
		testString = "2010-08-12";
		Assert.assertEquals(false, DateUtil.validDate(testString));
	}
}
