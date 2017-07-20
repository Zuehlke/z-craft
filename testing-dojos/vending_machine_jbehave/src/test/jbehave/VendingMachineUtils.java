package test.jbehave;

import java.util.ArrayList;
import java.util.List;
import main.Coin;
import main.Product;

public class VendingMachineUtils {

	public static List<Coin> defaultCashBox() {
		List<Coin> coins = new ArrayList<Coin>();

		for (int i = 0; i < 10; i++) {
			coins.add(Coin.NICKEL);
			coins.add(Coin.DIME);
			coins.add(Coin.QUARTER);
		}

		return coins;
	}

	public static List<Product> defaultInventory() {
		List<Product> products = new ArrayList<Product>();

		for (int i = 0; i < 10; i++) {
			products.add(Product.COLA);
			products.add(Product.CANDY);
			products.add(Product.CHIPS);
		}

		return products;
	}

}
