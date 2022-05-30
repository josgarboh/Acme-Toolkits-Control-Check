package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumCreateTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positive(final int recordIndex, final String code, final String title, final String description, final String startDate, final String finishDate,final String budget, final String link) {

		super.signIn("inventor2", "inventor2");

		super.clickOnMenu("Inventor", "Chimpums");
		super.checkListingExists();

		super.clickOnButton("Create a Chimpum");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		
		super.clickOnSubmit("Create");
		super.checkNotErrorsExist();
		
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negative(final int recordIndex, final String code, final String title, final String description, final String startDate, final String finishDate,final String budget, final String link) {

		super.signIn("inventor2", "inventor2");

		super.clickOnMenu("Inventor", "Chimpums");
		super.checkListingExists();

		super.clickOnButton("Create a Chimpum");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		
		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		
		super.signOut();
	}
}
