package com.zuehlke.zcraft.hashtable;

import java.util.Random;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;

import static org.junit.Assert.*;

public class HashtableTest {
	private Hashtable testee = new Hashtable(100);
	
	@Test
	public void add(){
		testee.add(17);		
		testee.add(-37);		
	}
	
	@Test
	public void containsTrue(){
		testee.add(17);		
		testee.add(-37);
		assertTrue(testee.contains(17));
		assertTrue(testee.contains(-37));
	}
	
	@Test
	public void containsFalse(){
		assertFalse(testee.contains(17));
		assertFalse(testee.contains(-37));
		testee.add(17);		
		testee.add(-37);
		assertFalse(testee.contains(0));
		assertFalse(testee.contains(37));
	}
	
	@Test
	public void collision(){
		testee = new Hashtable(2);
		testee.add(17);		
		testee.add(-37);
		testee.add(0);
		assertTrue(testee.contains(17));
		assertTrue(testee.contains(-37));
		assertTrue(testee.contains(0));
	}
	
	@Test
	public void collisionBig(){
		testee = new Hashtable(10);
		Random rnd = new Random(42);
		for(int i=0; i<300; i++){
			testee.add(rnd.nextLong());
		}
		rnd = new Random(42);
		for(int i=0; i<300; i++){
			assertTrue(testee.contains(rnd.nextLong()));
		}
	}
	
	@Test
	public void remove(){
		testee.add(17);		
		testee.add(-37);
		assertTrue(testee.remove(17));		
		assertFalse(testee.contains(17));
		assertTrue(testee.contains(-37));
		assertTrue(testee.remove(-37));
		assertFalse(testee.contains(17));
		assertFalse(testee.contains(-37));
	}

	@Test
	public void removeNotPresent(){
		assertFalse(testee.remove(17));		
		testee.add(17);		
		testee.add(-37);
		assertFalse(testee.remove(0));		
	}
	
	@Test
	public void removeBig(){
		testee = new Hashtable(10);
		Random rnd = new Random(25);
		for(int i=0; i<300; i++){
			testee.add(rnd.nextLong());
		}
		rnd = new Random(25);
		for(int i=0; i<300; i++){
			assertTrue(testee.remove(rnd.nextLong()));
		}
	}
	
	@Test
	public void elementCount() {
		testee = new Hashtable(10);
		assertEquals(0, testee.getElementCount());
		Random rnd = new Random(25);
		for(int i=0; i<300; i++){
			testee.add(rnd.nextLong());
			assertEquals(i+1, testee.getElementCount());
		}
		rnd = new Random(25);
		for(int i=0; i<300; i++){
			assertTrue(testee.remove(rnd.nextLong()));
			assertEquals(299-i, testee.getElementCount());
		}
	}
}
