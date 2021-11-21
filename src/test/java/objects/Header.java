package objects;

import org.openqa.selenium.By;

public class Header {
	
	public By wishListPage_Link() {
		return By.xpath("//div[@class='site-header container-fluid']//i[@class='lar la-heart']");
	}
	
	public By addToCartPage_Link() {
		return By.xpath("//div[@class='site-header container-fluid']//i[@class='la la-shopping-bag']");
	}


}
