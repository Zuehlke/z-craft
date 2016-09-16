package com.zuehlke.zcraft.hashtable;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;

@RunWith(Parameterized.class)
public class Benchmark {
	private Hashtable testee;
	private int values;
	private int hashtableSize;
	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();
	 
	public Benchmark(int hashtableSize, int values){
		testee = new Hashtable(hashtableSize);
		this.values = values;
		this.hashtableSize = hashtableSize;
	}
	
	  @Parameters
	  public static List<Object[]> data() {
	    return Arrays.asList(new Object[][] { { 10, 5000 }, { 10, 10000 }, { 10, 15000 }, { 10, 20000 }, { 10, 30000 }, { 10, 40000 }});
	  }
	  
	  
	@Test
	@Ignore
	public void collisionBig(){
		Random rnd = new Random(42);
		for(int i=0; i<values; i++){
			testee.add(rnd.nextLong());
		}
		rnd = new Random(42);
		for(int i=0; i<values; i++){
			assertTrue(testee.contains(rnd.nextLong()));
		}
	}
	
	@Test
	public void collisionProduced(){
		for(int i=0; i<values; i++){
			long v = i * hashtableSize + 57;
			testee.add(v);
		}
		for(int i=0; i<values; i++){
			long v = i * hashtableSize + 57;
			assertTrue(testee.contains(v));
		}
	}
}
