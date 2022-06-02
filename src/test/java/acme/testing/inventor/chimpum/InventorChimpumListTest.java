package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumListTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/list.csv", encoding = "utf-8", numLinesToSkip = 1)	
	@Order(10)																																											
	public void positive(final int recordIndex, final String code, final String creationMoment, final String title, final String description, final String startDate, final String finishDate, final String budget, final String link) {
		//Money y Date se pasan como str (el toString() no coincide exactamente con el csv y falla)
		
		super.signIn("inventor2", "inventor2");
		
		super.clickOnMenu("Inventor", "Chimpums");
		super.checkListingExists();
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, creationMoment);
		super.checkColumnHasValue(recordIndex, 2, title);
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("link", link);
		
		super.signOut();
	}

}
