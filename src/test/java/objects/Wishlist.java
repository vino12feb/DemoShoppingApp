package objects;

import org.openqa.selenium.By;

public class Wishlist {
	
	public By addToCartByPrice_Button(int amount) {
		return By.xpath("//table//td[@class='product-price']/ins//bdi[contains(.,'"+amount+"')]//ancestor::td//following-sibling::td[@class='product-add-to-cart']/a");
	}
	
	public By addToCartByProductName_Button(String itemName) {
		return By.xpath("//table//td[@class='product-name']/a[contains(.,'"+itemName+"')]//ancestor::td//following-sibling::td[@class='product-add-to-cart']/a");
	}
	
	public By getProductNameByPrice(int amount) {
		return By.xpath("//table//td[@class='product-price']/ins//bdi[contains(.,'"+amount+"')]//ancestor::td//preceding-sibling::td[@class='product-name']/a");
	}
	
	public By getItemList() {
		return By.xpath("//table//tbody[@class='wishlist-items-wrapper']/tr");
	}
	
	public By getProductName(int index) {
		return By.xpath("(//table//td[@class='product-name']/a)["+index+"]");
	}
	
	public By getProductPriceCount(String productName) {
		return By.xpath("//table//td[@class='product-name']/a[contains(.,'"+productName+"')]/parent::td/following-sibling::td[@class='product-price']//bdi");
	}
	
	public By getProductPrice(String productName, int priceIndex) {
		return By.xpath("(//table//td[@class='product-name']/a[contains(.,'"+productName+"')]/parent::td/following-sibling::td[@class='product-price']//bdi)["+priceIndex+"]");
	}
	
	public By verifyProductAddedMessage() {
		return By.xpath("//div[@class='woocommerce-message' and contains(.,'Product added to cart successfully')]");
	}

}
