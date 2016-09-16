package com.zuehlke.ioc_dojo;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class IoC {
	private static Map<Class<?>, Supplier<?>> singletonMap = new HashMap<>();

	public static void reset(){
		singletonMap.clear();
	}
	
	public static <T, U extends T> void registerAsSingleton(Class<T> type, U instance) {
		singletonMap.put(type, () -> instance);
	}

	@SuppressWarnings("unchecked")
	public static <T> T resolve(Class<T> type) {
		Supplier<?> singleton = singletonMap.get(type);
		if(singleton == null) {
			throw new UregisteredInterfaceException();
		}
		return (T) singleton.get();
	}

	public static <T, U extends T> void constructAndregisterSingleton(Class<T> type,
		Class<U> implementation) {
		try {
			U singleton = implementation.getConstructor(new Class[0]).newInstance(new Object[0]);
			singletonMap.put(type, () -> singleton);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T, U extends T> void registerType(Class<T> type, Class<U> implementation) {
		singletonMap.put(type, () -> {
			try {
				return implementation.getConstructor(new Class[0]).newInstance(new Object[0]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});		
	}

	public static <T, U extends T> void lazyConstructAndRegisterSingleton(Class<T> type, Class<U> implementation) {
		singletonMap.put(type, () -> {
			try {
				Constructor<U> constructor = findSatisfiableConstructor(implementation);
				U result = constructor.newInstance(mapParams(constructor).toArray());
				
				// replace this supplier with one that always returns the instance we just lazily generated 
				registerAsSingleton(type, result); 
				return result;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});		
	}

	static List<Object> mapParams(Constructor constructor) {
		return Arrays.asList(constructor.getParameters()).stream().map( param -> {
			Supplier<?> supplier = singletonMap.get(param.getType());
			return supplier == null ? null : supplier.get();
		}).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	static <U> Constructor<U> findSatisfiableConstructor(Class<U> implementation) {
		return (Constructor<U>) Arrays.asList(implementation.getConstructors()).stream()
				.sorted((one, two) -> two.getParameterCount() - one.getParameterCount())
				.filter(constructor -> {
					return !mapParams(constructor).contains(null);
				}).findFirst().get();
	}
}
