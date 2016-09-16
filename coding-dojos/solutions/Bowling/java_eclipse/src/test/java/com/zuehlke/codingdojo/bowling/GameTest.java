package com.zuehlke.codingdojo.bowling;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GameTest {

	@Test
	public void totalAfterFirstThrow() {
		Game g = new Game();
		int firstThrow = 5;
		g.addThrow(firstThrow);
		
		int totals = g.totalPoints();
		
		assertThat(totals, is(firstThrow));
	}
	
	@Test
	public void totalAfterSecondThrow() {
		Game g = new Game();
		g.addThrow(4);
		g.addThrow(1);
		int totals = g.totalPoints();		
		assertThat(totals, is(5));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void exceptionAfterSecondThrow() {
		Game g = new Game();
		g.addThrow(6);
		g.addThrow(6);
	}
	
	@Test
	public void totalAfterThirdThrow() {
		Game g = new Game();
		g.addThrow(4);
		g.addThrow(1);
		g.addThrow(3);
		int totals = g.totalPoints();		
		assertThat(totals, is(8));
	}
	
	@Test
	public void totalAfterStrikeAndSecondThrow() {
		Game g = new Game();
		g.addThrow(10);
		g.addThrow(1);
		int totals = g.totalPoints();		
		assertThat(totals, is(12));
	}
}
