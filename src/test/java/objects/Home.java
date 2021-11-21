package objects;

import org.openqa.selenium.By;

public class Home {
	
	public By shop_Link() {
		return By.xpath("//a[@title='Shop']");
	}
	
	public By addToWishList_Button() {
		return By.xpath("//span[.='Add to wishlist']//ancestor::li//a[.='Add to cart' or .='Buy now!']/following-sibling::div//div[@class='product-wishlist']//span[.='Add to wishlist']");
	}
	
	public By addToWishList_Button(int itemNumber) {
		return By.xpath("(//span[.='Add to wishlist']//ancestor::li//a[.='Add to cart' or .='Buy now!']/following-sibling::div//div[@class='product-wishlist']//span[.='Add to wishlist'])["+itemNumber+"]");
	}
	
	public By acceptCookie() {
		return By.xpath("//a[.='Accept all']");
	}

}
