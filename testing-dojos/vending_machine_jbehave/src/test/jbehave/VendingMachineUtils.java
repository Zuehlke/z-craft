package test.jbehave;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbehave.core.model.ExamplesTable;

import main.Coin;
import main.Product;
import main.VendingMachine;

public class VendingMachineUtils {
	
	public static Coin coinHelper(String coinName) {
		try {
			return (Coin) Coin.class.getDeclaredField(coinName).get(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			return Coin.INVALID;
		}
	}

	public static Product productHelper(String productName) {
		try {
			return (Product) Product.class.getDeclaredField(productName).get(null);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			return null;
		}
	}

	public static boolean assertedCoinReturn(VendingMachine machine, int asserted) {
		List<Coin> returnedCoins = machine.getCoinReturn();
		int value = 0;

		for (Iterator<Coin> iterator = returnedCoins.iterator(); iterator.hasNext();) {
			Coin coin = (Coin) iterator.next();
			value += coin.getValue();
		}

		return asserted == value;
	}

	public static List<Product> inventoryHelper(ExamplesTable table) {
		List<Product> products = new ArrayList<Product>();
		for (Map<String, String> row : table.getRows()) {
			int i = Integer.parseInt(row.get("amount"));

			for (int j = 0; j < i; j++) {
				Product product = productHelper(row.get("productName").toUpperCase());
				products.add(product);
			}
		}
		return products;
	}

	public static List<Coin> cashBoxHelper(ExamplesTable table) {
		List<Coin> coins = new ArrayList<Coin>();
		for (Map<String, String> row : table.getRows()) {
			int i = Integer.parseInt(row.get("amount"));

			for (int j = 0; j < i; j++) {
				Coin coin = coinHelper(row.get("coinName").toUpperCase());
				coins.add(coin);
			}
		}
		return coins;
	}

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
