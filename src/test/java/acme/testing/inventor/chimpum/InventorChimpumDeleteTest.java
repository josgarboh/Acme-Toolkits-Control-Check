package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumDeleteTest extends TestHarness {
	
	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/delete.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex) {
		super.signIn("inventor2", "inventor2");

		super.clickOnMenu("Inventor", "Chimpums");
		super.checkListingExists();

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		
		super.signOut();
	}
}