package main;

public enum Product {
	CHIPS(50), COLA(100), CANDY(65);
	
	private final int price;

	private Product(int price) {
		this.price = price;
	}

	public int getPrice() {
		return price;
	}
}
