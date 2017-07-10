package test.jbehave;

import static test.jbehave.VendingMachineUtils.defaultCashBox;
import static test.jbehave.VendingMachineUtils.defaultInventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.junit.Assert;

import main.Coin;
import main.Product;
import main.VendingMachine;

public class VendingMachineStep {
	private VendingMachine machine;
	
	private boolean assertedCoinReturn(VendingMachine machine, int asserted) {
		List<Coin> returnedCoins = machine.getCoinReturn();
		int value = 0;

		for (Iterator<Coin> iterator = returnedCoins.iterator(); iterator.hasNext();) {
			Coin coin = (Coin) iterator.next();
			value += coin.getValue();
		}

		return asserted == value;
	}
	
	private List<Product> inventory(ExamplesTable table) {
		List<Product> products = new ArrayList<Product>();
		for (Map<String, String> row : table.getRows()) {
			int i = Integer.parseInt(row.get("amount"));

			for (int j = 0; j < i; j++) {
				Product product = Product.valueOf(row.get("productName").toUpperCase());
				products.add(product);
			}
		}
		return products;
	}
	
	private List<Coin> cashBox(ExamplesTable table) {
		List<Coin> coins = new ArrayList<Coin>();
		for (Map<String, String> row : table.getRows()) {
			int i = Integer.parseInt(row.get("amount"));

			for (int j = 0; j < i; j++) {
				Coin coin = Coin.valueOf(row.get("coinName").toUpperCase());
				coins.add(coin);
			}
		}
		return coins;
	}

	@Given("a vending machine")
	public void initMachine() {
		machine = new VendingMachine();
		machine.setInventory(defaultInventory());
		machine.setCashBox(defaultCashBox());
	}

	@When("the given inventory is $productList")
	public void setInventory(ExamplesTable productList) {
		machine.setInventory(inventory(productList));
	}

	@When("the given cashbox is $coinList")
	public void setCashBox(ExamplesTable coinList) {
		machine.setCashBox(cashBox(coinList));
	}

	@When("the customer selects $productName")
	public void selectProduct(String productName) {
		Product product = Product.valueOf(productName.toUpperCase());
		machine.selectProduct(product);
	}

	@When("the customer press return coins")
	public void returnCoins() {
		machine.returnCoins();
	}

	@When("the customer inserts a $coinName")
	public void insertCoin(String coinName) {
		Coin coin = Coin.valueOf(coinName.toUpperCase());
		machine.accept(coin);
	}

	@Then("the display shows $text")
	public void display(String text) {
		Assert.assertEquals(text, machine.getDisplay());
	}

	@Then("the return tray contains $coinList")
	public void coinReturn(List<String> coinNameList) {
		List<Coin> coinReturn = machine.getCoinReturn();

		for (String coinName : coinNameList) {
			Coin coin = Coin.valueOf(coinName.toUpperCase());
			Assert.assertTrue(coinReturn.remove(coin));
		}
	}

	@Then("the machine dispends $productName")
	public void dispendProduct(String productName) {
		Product product = Product.valueOf(productName.toUpperCase());
		List<Product> productOutput = machine.getProductOutput();

		Assert.assertNotNull(productOutput.contains(product));
	}

	@Then("the chashbox value is $value")
	public void cashBoxValue(int value) {
		Assert.assertEquals(value, machine.cashBoxValue());
	}

	@Then("nothing should be dispensed")
	public void dispendNothing() {
		Assert.assertTrue(machine.getProductOutput().isEmpty());
	}

	@Then("the machine should return $c cents")
	public void returnChange(int c) {
		Assert.assertTrue(assertedCoinReturn(machine, c));
	}
	
	@Then("the return tray is empty")
	public void emptyReturnTray(){
		Assert.assertEquals(0, machine.getProductOutput().size());
	}
	

}
