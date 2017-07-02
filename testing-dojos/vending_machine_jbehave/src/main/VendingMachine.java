package main;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class VendingMachine {

	private static final String PRICE = "PRICE: ";
	private static final String INSERT = "INSERT COINS";
	private static final String EXACT_CHANGE = "EXACT CHANGE ONLY";
	private static final String THANKS = "THANK YOU";
	private static final String SOLD_OUT = "SOLD OUT";
	private static final String ERROR = "ERROR";

	private List<Coin> coinReturn = new ArrayList<>();
	private List<Product> inventory = new ArrayList<Product>();
	private List<Coin> cashBox = new ArrayList<Coin>();
	private List<Product> productOutput = new ArrayList<>();
	private int value = 0;
	private List<Coin> coinsReceived = new ArrayList<>();
	private Product selected;
	private State state = State.INITIAL;

	public void setInventory(List<Product> inventory) {
		this.inventory = inventory;
	}

	public List<Product> getInventory() {
		return inventory;
	}

	public void setCashBox(List<Coin> cashBox) {
		this.cashBox = cashBox;
	}

	private boolean exactChange() {
		int nickles = Collections.frequency(cashBox, Coin.NICKEL);
		int dimes = Collections.frequency(cashBox, Coin.DIME);

		// change only needed to return $0.05 and $0.10
		return (!((nickles >= 2) || (nickles >= 1 && dimes > 0)));
	}

	public boolean accept(Coin coin) {
		if (coin.getValue() >= 5) {
			value += coin.getValue();
			coinsReceived.add(coin);
			state = State.MONEY;
			return true;
		}
		coinReturn.add(coin);
		return false;
	}

	private String format(int amount) {
		return "$" + new BigDecimal(amount).setScale(2).divide(new BigDecimal(100));
	}

	boolean equalCoins(Coin coin, Coin candidate) {
		return coin.getValue() == candidate.getValue();
	}

	public int getValue() {
		return value;
	}

	public String getDisplay() {
		switch (this.state) {
		case INITIAL:
			return exactChange() ? EXACT_CHANGE : INSERT;
		case EXACT_CHANGE:
			return EXACT_CHANGE;
		case MONEY:
			return format(value);
		case SOLD_OUT:
			return SOLD_OUT;
		case INSUFFICIENT:
			String result = PRICE + format(selected.getPrice());
			this.state = this.value == 0 ? State.INITIAL : State.MONEY;
			return result;
		case PURCHASE:
			this.state = State.INITIAL;
			return THANKS;
		default:
			return ERROR;
		}
	}

	public List<Coin> getCoinReturn() {
		List<Coin> result = coinReturn;
		this.coinReturn = new ArrayList<>();
		return result;
	}

	public void selectProduct(Product p) {
		if (!inventory.contains(p)) {
			this.selected = null;
			this.state = State.SOLD_OUT;
			return;
		}

		if (p.getPrice() <= this.value) {
			this.productOutput.add(p);
			this.cashBox.addAll(coinsReceived);
			this.coinsReceived.clear();
			this.state = State.PURCHASE;
			returnChange(this.value - p.getPrice());
			this.value = 0;
		} else {
			this.selected = p;
			this.state = State.INSUFFICIENT;
		}
	}

	private void returnChange(int change) {
		List<Coin> changeCoins = new ArrayList<Coin>();

		for (Coin coin : cashBox) {
			if (change >= coin.getValue()) {
				changeCoins.add(coin);
				change -= coin.getValue();
				
				if (change == 0) 
					break;
			}
		}

		cashBox.removeAll(changeCoins);
		coinReturn.addAll(changeCoins);

	}

	public List<Product> getProductOutput() {
		List<Product> result = productOutput;
		this.productOutput = new ArrayList<>();
		return result;
	}

	private static enum State {
		INITIAL, MONEY, PURCHASE, SOLD_OUT, INSUFFICIENT, EXACT_CHANGE;
	}

	public void returnCoins() {
		this.coinReturn.addAll(this.coinsReceived);
		this.value = 0;
		this.state = State.INITIAL;
		this.coinsReceived.clear();
	}

	public List<Coin> getCashBox() {
		return cashBox;
	}
}
