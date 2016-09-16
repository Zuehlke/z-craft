package com.zuehlke.codingdojo.bowling;

public class Frame {

	private Integer firstThrow;
	private Integer secondThrow;
	
	public void setFirstThrow(int i) {
		checkUnset(firstThrow);
		checkBounds(i);
		firstThrow = i;
	}

	private void checkUnset(Integer i) {
		if(i != null)
			throw new IllegalStateException("Cannot set throw twice");
	}

	private void checkBounds(int i) {
		if(i < 0){
			throw new IllegalArgumentException("Throw cannot be negative");
		}
		if(i > 10){
			throw new IllegalArgumentException("Throw cannot be greater than 10");
		}
	}
	

	public Integer getTotalPoints() {
		if (firstThrow == null)
			throw new IllegalStateException("Empty frame");
		
		if (secondThrow == null)
			return firstThrow;
		
		return firstThrow + secondThrow;
	}

	public Integer getFirstThrow() {
		return firstThrow;
	}

	public void setSecondThrow(int i) {
		checkUnset(secondThrow);
		checkFirstThrow();
		checkBounds(i);
		checkTotal(i);
		secondThrow = i;
	}

	private void checkTotal(int i) {
		if(firstThrow + i > 10)
			throw new IllegalArgumentException("Sum of first and second throw cannot be greater than 10");
	}

	private void checkFirstThrow() {
		if(firstThrow == null)
			throw new IllegalStateException("first throw is missing");
		
		if(firstThrow == 10) {
			throw new IllegalStateException("first throw was strike");
		}
	}

	public Integer getSecondThrow() {
		return secondThrow;
	}

	public boolean isComplete() {
		return secondThrow != null;
	}
	
}
