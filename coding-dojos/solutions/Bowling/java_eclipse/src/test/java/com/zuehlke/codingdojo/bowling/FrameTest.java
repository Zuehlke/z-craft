package com.zuehlke.codingdojo.bowling;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;


public class FrameTest {
	
	private Frame f;
	
	@Before
	public void setup() {
		f = new Frame();
	}
	

	@Test(expected=IllegalStateException.class)
	public void exceptionWhenNoThrows() {
		f.getTotalPoints();
	}
	
	@Test
	public void totalAfterFirstThrow(){
		f.setFirstThrow(3);
		assertThat(3, is(f.getTotalPoints()));
		assertThat(3, is(f.getFirstThrow()));
	}
	
	@Test
	public void totalAfterSecondThrowNoSpare() {
		f.setFirstThrow(3);
		f.setSecondThrow(5);
		assertThat(8, is(f.getTotalPoints()));
		assertThat(3, is(f.getFirstThrow()));
		assertThat(5, is(f.getSecondThrow()));
	}

	@Test
	public void totalWithNoPoints(){
		f.setFirstThrow(0);
		assertThat(0, is(f.getTotalPoints()));
		assertThat(0, is(f.getFirstThrow()));
		f.setSecondThrow(0);
		assertThat(0, is(f.getTotalPoints()));
		assertThat(0, is(f.getSecondThrow()));
	}

	@Test(expected=IllegalArgumentException.class)
	public void exceptionWhenFirstThrowNegative(){
		f.setFirstThrow(-1);
	}
	@Test(expected=IllegalArgumentException.class)
	public void exceptionWhenFirstThrowTooHigh(){
		f.setFirstThrow(11);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void exceptionWhenSecondThrowNegative(){
		f.setFirstThrow(5);
		f.setSecondThrow(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void exceptionWhenSecondThrowTooHigh(){
		f.setFirstThrow(0);
		f.setSecondThrow(11);
	}
	
	@Test(expected=IllegalStateException.class)
	public void exceptionWhenSecondThrowWithoutFirst() {
		f.setSecondThrow(4);
	}
	
	@Test(expected=IllegalStateException.class)
	public void exceptionWhenSecondThrowAfterStrike() {
		f.setFirstThrow(10);
		f.setSecondThrow(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void exceptionWhenFirstAndSecondThrowGreaterTen(){
		f.setFirstThrow(5);
		f.setSecondThrow(6);
	}
	
	@Test(expected=IllegalStateException.class)
	public void exceptionWhenFirstThrowSetTwice() {
		f.setFirstThrow(1);
		f.setFirstThrow(1);
	}
	
	@Test(expected=IllegalStateException.class)
	public void exceptionWhenSecondThrowSetTwice() {
		f.setFirstThrow(1);
		f.setSecondThrow(1);
		f.setSecondThrow(1);
	}
	
	@Test
	public void isComplete() {
		f.setFirstThrow(1);
		assertThat(f.isComplete(), is(false));
		f.setSecondThrow(1);
		assertThat(f.isComplete(), is(true));
	}
	
}
