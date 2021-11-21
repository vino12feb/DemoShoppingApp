package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.Keys;

import com.relevantcodes.extentreports.LogStatus;

import automation.SeleniumFunctions;
import data.Testdata;
import objects.Cart;
import objects.Header;
import objects.Home;
import objects.Wishlist;
import runner.RunDemoTest;
import utilities.ConfigFileReader;
import utilities.DataHandler;
import utilities.ReportHandler;

public class WishlistFunctions {
	
	SeleniumFunctions selOps = new SeleniumFunctions();
	ConfigFileReader config = new ConfigFileReader();
    ReportHandler report = new ReportHandler();
    DataHandler datahandler = new DataHandler();
    RunDemoTest run = new RunDemoTest();
    
    //Objects
    Header objHeader = new Header();
    Home objHome = new Home();
    Cart objCart = new Cart();
    Wishlist objWishlist = new Wishlist();

	
	public void launchApplication() {
		selOps.launchBrowser(config.getConfig("appUrl"));
		selOps.clickifavailable(objHome.acceptCookie(), "Accept Cookie");
		selOps.click(objHome.shop_Link(), "Shop Page");
		selOps.getScreenshot();
	}
	
	public void addRandomItemToWishlist(int itemNumbers) {		
		int totalAvailableItems = selOps.getElementSize(objHome.addToWishList_Button());
		Random random = new Random();
	    ArrayList<Integer> arrayList = new ArrayList<Integer>();

	    while (arrayList.size() < itemNumbers) {
	        int a = random.nextInt(totalAvailableItems-1)+1;
	        arrayList.add(a);
	        selOps.click(objHome.addToWishList_Button(a), "Added "+ (arrayList.size()) +" Item To Wishlist");
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        totalAvailableItems = selOps.getElementSize(objHome.addToWishList_Button());
	    }
		
	}
	
	public void viewWishlistPage() {
		selOps.click(objHeader.wishListPage_Link(), "Wishlist Page");
	}
	
	public void verifyListOfItems(int itemNumbers) {
		int itemsInWishlist = selOps.getElementSize(objWishlist.getItemList());
		if(itemsInWishlist == itemNumbers) {
			selOps.getScreenshot();
			report.logPass(itemsInWishlist + " items available in Wishlist");
		}else {
			selOps.getScreenshot();
			report.logFail(itemsInWishlist + " items available in Wishlist. Expected count is " + itemNumbers);
		}
	}
	
	public void searchLowestPriceItem() {
		Double lowestPrice = 0.00;
		int itemSize = selOps.getElementSize(objWishlist.getItemList());
		selOps.scrollToElement(objWishlist.getItemList());
		for(int i=1;i<=itemSize;i++) {
			String itemName = selOps.getElementByValue(objWishlist.getProductName(i));
			int priceCount = selOps.getElementSize(objWishlist.getProductPriceCount(itemName));
			String itemPrice = selOps.getElementByValue(objWishlist.getProductPrice(itemName, priceCount));
			report.logInfo("Product " + i + " : " + itemName + " available in cart with price " + itemPrice);
			itemPrice = itemPrice.replace("£", "");
			if(lowestPrice == 0.00) {
				lowestPrice = Double.parseDouble(itemPrice);
				datahandler.setRunTimeVariable(Testdata.LOWEST_PRICE_ITEM.toString(), itemPrice);
				datahandler.setRunTimeVariable(Testdata.SELECTED_PRODUCT_NAME.toString(), itemName);
			}			
			if(Double.parseDouble(itemPrice) < lowestPrice) {
				lowestPrice = Double.parseDouble(itemPrice);
				datahandler.setRunTimeVariable(Testdata.LOWEST_PRICE_ITEM.toString(), itemPrice);
				datahandler.setRunTimeVariable(Testdata.SELECTED_PRODUCT_NAME.toString(), itemName);
			}
		}
	}
	
	public void addLowestPriceItemToCart() {
		String lowestPriceItemName = datahandler.getRunTimeVariables(Testdata.SELECTED_PRODUCT_NAME.toString());
		selOps.click(objWishlist.addToCartByProductName_Button(lowestPriceItemName), "Add to cart");
		if(selOps.elementExists(objWishlist.verifyProductAddedMessage())) {
			selOps.scrollToElement(objWishlist.verifyProductAddedMessage());
			selOps.getScreenshot();
			report.logPass("Lowest price item " + lowestPriceItemName + " added to cart");
		}else {
			selOps.getScreenshot();
			report.logFail("Lowest price item " + lowestPriceItemName + " not added to cart");
		}
	}
	
	public void verifyLowestPriceItemInCart() {
		String lowestPriceItemName = datahandler.getRunTimeVariables(Testdata.SELECTED_PRODUCT_NAME.toString());
		selOps.click(objHeader.addToCartPage_Link(), "Cart Page");
		if(selOps.elementExists(objCart.getProductPriceByNameFromCart(lowestPriceItemName))){
			selOps.getScreenshot();
			report.logPass("Lowest price item " + lowestPriceItemName + " available in cart");
		}else {
			selOps.getScreenshot();
			report.logFail("Lowest price item " + lowestPriceItemName + " not available in cart");
		}
	}

}
