package com.zuehlke.ioc_dojo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Test;

public class IoCTest {

	@Before
	public void setUp(){
		IoC.reset();
	}
	
	@Test(expected=UregisteredInterfaceException.class)
	public void reset() {
		Comparable<String> instance1 = "1";
		
		IoC.registerAsSingleton(Comparable.class, instance1);
		IoC.reset();
		IoC.resolve(Comparable.class);
	}
	@Test
	public void registerSingleton(){
		Runnable instance = new Runnable() {			
			@Override
			public void run() {
			}
		};
		IoC.registerAsSingleton(Runnable.class, instance);
		assertSame(instance, IoC.resolve(Runnable.class));
	}
	
	@Test(expected=UregisteredInterfaceException.class)
	public void unregisterInterfaceThrowsException() {
		IoC.registerAsSingleton(Map.class, new HashMap<Object, Object>());
		IoC.resolve(Runnable.class);
	}
	
	@Test
	public void registerAlreadyRegisteredType() {
		Comparable<String> instance1 = "1";
		Comparable<Integer> instance2 = 2;
		
		IoC.registerAsSingleton(Comparable.class, instance1);
		assertSame(instance1, IoC.resolve(Comparable.class));
		
		IoC.registerAsSingleton(Comparable.class, instance2);
		assertSame(instance2, IoC.resolve(Comparable.class));
	}
	
	@Test
	public void constructAndRegisterSingleton(){
		IoC.constructAndregisterSingleton(Map.class, HashMap.class);
		@SuppressWarnings("rawtypes")
		Map instance = IoC.resolve(Map.class);
		assertTrue(instance instanceof HashMap);
	}
	
	@Test
	public void constructAndRegisterSingletonResolvesToSingleton(){
		IoC.constructAndregisterSingleton(Map.class, HashMap.class);
		@SuppressWarnings("rawtypes")
		Map instance = IoC.resolve(Map.class);
		@SuppressWarnings("rawtypes")
		Map instance2 = IoC.resolve(Map.class);
		assertSame(instance, instance2);
	}
	
	@Test
	public void registerType(){
		IoC.registerType(Map.class, HashMap.class);
		@SuppressWarnings("rawtypes")
		Map instance = IoC.resolve(Map.class);
		assertTrue(instance instanceof HashMap);
	}
	
	@Test
	public void resolveTypeCreatesNew(){
		IoC.registerType(Map.class, HashMap.class);
		@SuppressWarnings("rawtypes")
		Map instance = IoC.resolve(Map.class);
		@SuppressWarnings("rawtypes")
		Map instance2 = IoC.resolve(Map.class);
		assertNotSame(instance, instance2);
	}
	
	@Test
	public void lazyConstructAndRegisterSingleton() {
		IoC.registerType(Comparable.class, String.class);
		IoC.registerType(Number.class, DefaultInteger.class);
		IoC.lazyConstructAndRegisterSingleton(Map.Entry.class, LazyConstruct.class);
		
		Map.Entry lazyInstance = IoC.resolve(Map.Entry.class);
		assertNotNull(lazyInstance);
		assertTrue(lazyInstance instanceof LazyConstruct);
		assertTrue(lazyInstance.getKey() instanceof String);
		assertTrue(lazyInstance.getValue() instanceof Number);
		
		Map.Entry lazyInstance2 = IoC.resolve(Map.Entry.class);
		assertSame(lazyInstance, lazyInstance2);
	}

	@Test
	public void lazyConstructAndRegisterSingletonUsingString() {
		IoC.lazyConstructAndRegisterSingleton(Comparable.class, String.class);
		Comparable lazyInstance = IoC.resolve(Comparable.class);
		assertNotNull(lazyInstance);
		assertTrue(lazyInstance instanceof String);
	}
	
	@Test
	public void lazyConstructAndRegisterSingletonWithMultipleConstructors() {
		IoC.registerType(Comparable.class, String.class);
		IoC.lazyConstructAndRegisterSingleton(Map.Entry.class, LazyConstruct.class);
		
		Map.Entry lazyInstance = IoC.resolve(Map.Entry.class);
		assertNotNull(lazyInstance);
		assertTrue(lazyInstance instanceof LazyConstruct);
		assertTrue(lazyInstance.getKey() instanceof String);
		assertNull(lazyInstance.getValue());
		
		Map.Entry lazyInstance2 = IoC.resolve(Map.Entry.class);
		assertSame(lazyInstance, lazyInstance2);
	}


	public static class LazyConstruct implements Map.Entry<Comparable<?>, Number>
	{
		private Number value;
		private Comparable<?> key;

		public LazyConstruct(Comparable<?> key, Number value) {
			this.key = key;
			this.value = value;
		}

		public LazyConstruct(Comparable<?> key) {
			this.key = key;
		}
		
		@Override
		public Comparable<?> getKey() {
			return key;
		}

		@Override
		public Number getValue() {
			return value;
		}

		@Override
		public Number setValue(Number value) {
			return null;
		}
	}
	
	public static class DefaultInteger extends Number{

		@Override
		public int intValue() {
			return 0;
		}

		@Override
		public long longValue() {
			return 0;
		}

		@Override
		public float floatValue() {
			return 0;
		}

		@Override
		public double doubleValue() {
			return 0;
		}
		
	}
}
