import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class VendingMachine {
	
	private static final String PRICE = "PRICE: ";
	private static final String INSERT = "INSERT COIN";
	private static final String THANKS = "THANK YOU";
	private static final Map<Coin, Integer> coinValues = new LinkedHashMap<>();
	static {
		coinValues.put(Coin.QUARTER, 25);
		coinValues.put(Coin.DIME, 10);
		coinValues.put(Coin.NICKEL, 5);
	}
	
	private List<Coin> coinReturn = new ArrayList<>();
	private List<Product> productOutput = new ArrayList<>();
	private int value = 0;
	private List<Coin> coinsReceived = new ArrayList<>();
	private Product selected;
	private State state = State.INITIAL;

	public boolean accept(Coin coin) {
		for(Coin candidate: Arrays.asList(Coin.NICKEL, Coin.DIME, Coin.QUARTER)){
			if(equalCoins(coin, candidate)){
				value += coinValues.get(candidate);
				coinsReceived.add(coin);
				state = State.MONEY;
				return true;
			}
		}
		coinReturn.add(coin);
		return false;
	}

	private String format(int amount) {
		return "$"+new BigDecimal(amount).setScale(2).divide(new BigDecimal(100));
	}

	boolean equalCoins(Coin coin, Coin candidate) {
		return Math.abs(candidate.getWeight() - coin.getWeight()) < 0.0001 
				&& Math.abs(candidate.getDiameter() - coin.getDiameter()) < 0.0001;
	}

	public int getValue() {
		return value;
	}

	public String getDisplay() {
		switch(this.state){
		case INITIAL:
			return INSERT;
		case MONEY:
			return format(value);
		case INSUFFICIENT:
			String result = PRICE+format(selected.getPrice());
			this.state = this.value == 0 ? State.INITIAL : State.MONEY;
			return result;
		case PURCHASE:
			this.state = State.INITIAL;
			return THANKS;
		default: 
			throw new IllegalStateException();
		}
	}

	public List<Coin> getCoinReturn() {
		List<Coin> result = coinReturn;
		this.coinReturn = new ArrayList<>();
		return result;
	}

	public void selectProduct(Product p) {
		if(p.getPrice() <= this.value){
			this.productOutput.add(p);
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
		for(Entry<Coin, Integer> entry : coinValues.entrySet()){
			while(change >= entry.getValue()){
				coinReturn.add(entry.getKey());
				change -= entry.getValue();
			}
		}
	}

	public List<Product> getProductOutput() {
		List<Product> result = productOutput;
		this.productOutput = new ArrayList<>();
		return result;
	}

	private static enum State{
		INITIAL, MONEY, PURCHASE, INSUFFICIENT;
	}

	public void returnCoins() {
		this.coinReturn.addAll(this.coinsReceived);
		this.value = 0;
		this.state = State.INITIAL;
		this.coinsReceived.clear();
	}
}
