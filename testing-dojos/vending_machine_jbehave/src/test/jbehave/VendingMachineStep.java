package test.jbehave;

import static test.jbehave.VendingMachineUtils.assertedCoinReturn;
import static test.jbehave.VendingMachineUtils.cashBoxHelper;
import static test.jbehave.VendingMachineUtils.coinHelper;
import static test.jbehave.VendingMachineUtils.defaultCashBox;
import static test.jbehave.VendingMachineUtils.defaultInventory;
import static test.jbehave.VendingMachineUtils.inventoryHelper;
import static test.jbehave.VendingMachineUtils.productHelper;

import java.util.List;

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

	@Given("a vending machine")
	public void initMachine() {
		machine = new VendingMachine();
		machine.setInventory(defaultInventory());
		machine.setCashBox(defaultCashBox());
		machine.getCashBox().size();
	}

	@When("the given inventory is $productList")
	public void setInventory(ExamplesTable productList) {
		machine.setInventory(inventoryHelper(productList));
	}

	@When("the given cashbox is $coinList")
	public void setCashBox(ExamplesTable coinList) {
		machine.setCashBox(cashBoxHelper(coinList));
	}

	@When("the customer selects $productName")
	public void selectProduct(String productName) {
		Product product = productHelper(productName.toUpperCase());
		machine.selectProduct(product);
	}

	@When("the customer press return coins")
	public void returnCoins() {
		machine.returnCoins();
	}

	@When("the customer inserts a $coinName")
	public void insertCoin(String coinName) {
		Coin coin = coinHelper(coinName.toUpperCase());
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
			Coin coin = coinHelper(coinName.toUpperCase());
			Assert.assertTrue(coinReturn.remove(coin));
		}
	}

	@Then("the machine dispends $productName")
	public void dispendProduct(String productName) {
		Product product = productHelper(productName.toUpperCase());
		List<Product> productOutput = machine.getProductOutput();

		Assert.assertNotNull(productOutput.contains(product));
	}

	@Then("the chashbox value is $value")
	public void cashBoxValue(int value) {
		int cashBoxValue = machine.getCashBox().stream().mapToInt(Coin::getValue).sum();

		Assert.assertEquals(value, cashBoxValue);
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
