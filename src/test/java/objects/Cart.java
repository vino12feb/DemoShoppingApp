package objects;

import org.openqa.selenium.By;

public class Cart {
	
	public By getProductNameFromCart() {
		return By.xpath("//form[@class='woocommerce-cart-form']//tbody/tr/td[@class='product-name']/a");
	}
	
	public By getProductPriceByNameFromCart(String productName) {
		return By.xpath("//form[@class='woocommerce-cart-form']//tbody/tr/td[@class='product-name']/a[contains(.,'"+productName+"')]/parent::td/following-sibling::td[@class='product-price']//bdi");
	}

}
