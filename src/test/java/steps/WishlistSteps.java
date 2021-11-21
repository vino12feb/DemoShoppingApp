package steps;

import application.WishlistFunctions;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utilities.ReportHandler;

public class WishlistSteps {
	
	WishlistFunctions wishListFunctions = new WishlistFunctions();
	ReportHandler report = new ReportHandler();
	
	@Before
    public void beforeHook(Scenario scenario) {
		report.updateScenarioDescription(scenario.getName());
    }
	
	@Given("I navigate to demo shopping application shop page")
    public void iNavigateToDemoShoppingApplicationShopPage() {
		wishListFunctions.launchApplication();
    }
	
	@And("I add {int} different products to my Wishlist")
    public void iAddDifferentProductsToMyWishlist(int itemNumbers) {
		wishListFunctions.addRandomItemToWishlist(itemNumbers);
    }
	
	@When("I view my wishlist table")
    public void iViewMyWishlistTable() {
		wishListFunctions.viewWishlistPage();
    }
	
	@Then("I find total {int} selected items in my Wishlist")
    public void iFindTotalSelectedItemsInMyWishlist(int itemNumbers) {
		wishListFunctions.verifyListOfItems(itemNumbers);
    }
	
	@When("I search for lowest price product")
    public void iSearchForLowestPriceProduct() {
		wishListFunctions.searchLowestPriceItem();
    }
	
	@And("I am able to add the lowest price item to my cart")
    public void iAmAbleToAddTheLowestPriceItemToMyCart() {
		wishListFunctions.addLowestPriceItemToCart();
    }
	
	@Then("I am able to verify the item in my cart")
    public void iAmAbleToVerifyTheItemInMyCart() {
		wishListFunctions.verifyLowestPriceItemInCart();
    }
	
	

}
