package main;

public class Coin {
	public final static Coin NICKEL = new Coin(5, 21.21, 5);
	public final static Coin DIME = new Coin(2.268, 17.91, 10);
	public final static Coin QUARTER = new Coin(5.67, 24.26, 25);
	public final static Coin INVALID = new Coin(1, 1, 0);
	
	final private double weight;
	final private double diameter;
	final private int value;

	
	public Coin(double weight, double diameter,int value) {
		this.weight = weight;
		this.diameter = diameter;
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getDiameter() {
		return diameter;
	}
}
