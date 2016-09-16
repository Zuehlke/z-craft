
public class Coin {
	public final static Coin NICKEL = new Coin(5, 21.21);
	public final static Coin DIME = new Coin(2.268, 17.91);
	public final static Coin QUARTER = new Coin(5.67, 24.26);
	public final static Coin INVALID = new Coin(1, 1);
	
	final private double weight;
	final private double diameter;
	public Coin(double weight, double diameter) {
		super();
		this.weight = weight;
		this.diameter = diameter;
	}
	public double getWeight() {
		return weight;
	}
	public double getDiameter() {
		return diameter;
	}
}
