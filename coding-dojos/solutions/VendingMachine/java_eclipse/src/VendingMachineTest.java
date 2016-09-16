import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;


public class VendingMachineTest {

	private VendingMachine testee;;
	
	@Before
	public void setUp(){
		 testee = new VendingMachine();
	}

	@Test
	public void initialState(){
		assertEquals(0, testee.getValue());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}

	@Test
	public void acceptNickel(){
		assertTrue(testee.accept(new Coin(5, 21.21)));
		assertEquals(5, testee.getValue());
		assertEquals("$0.05", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}

	@Test
	public void acceptDime(){
		assertTrue(testee.accept(new Coin(2.268, 17.91)));
		assertEquals(10, testee.getValue());
		assertEquals("$0.10", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}

	@Test
	public void acceptQuarter(){
		assertTrue(testee.accept(new Coin(5.67, 24.26)));
		assertEquals(25, testee.getValue());
		assertEquals("$0.25", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}

	@Test
	public void rejectInvalidWeight(){
		Coin coin = new Coin(6, 21.21);
		assertFalse(testee.accept(coin));			
		assertEquals(0, testee.getValue());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(Collections.singletonList(coin), testee.getCoinReturn());
	}

	@Test
	public void rejectInvalidDiameter(){
		Coin coin = new Coin(5.67, 24.25);
		assertFalse(testee.accept(coin));
		assertEquals(0, testee.getValue());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(Collections.singletonList(coin), testee.getCoinReturn());
	}
	@Test
	public void getRejectedTwice(){
		Coin coin = new Coin(5.67, 24.25);
		assertFalse(testee.accept(coin));
		assertFalse(testee.accept(Coin.INVALID));
		assertEquals(Arrays.asList(coin, Coin.INVALID), testee.getCoinReturn());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}


	@Test
	public void acceptDimeAfterInvalid(){
		testee.accept(Coin.INVALID);
		testee.accept(Coin.DIME);
		assertEquals(10, testee.getValue());
		assertEquals("$0.10", testee.getDisplay());
		assertEquals(Collections.singletonList(Coin.INVALID), testee.getCoinReturn());
	}

	@Test
	public void invalidAfterAcceptDime(){
		testee.accept(Coin.DIME);
		testee.accept(Coin.INVALID);
		assertEquals(10, testee.getValue());
		assertEquals("$0.10", testee.getDisplay());
		assertEquals(Collections.singletonList(Coin.INVALID), testee.getCoinReturn());
	}

	@Test
	public void selectProductWithTooLittleMoney(){
		testee.accept(Coin.DIME);
		testee.selectProduct(Product.CANDY);
		assertEquals(10, testee.getValue());
		assertEquals("PRICE: $0.65", testee.getDisplay());
		assertEquals("$0.10", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
		assertEquals(Collections.emptyList(), testee.getProductOutput());
	}

	@Test
	public void selectProductWithNoMoney(){
		testee.selectProduct(Product.CANDY);
		assertEquals(0, testee.getValue());
		assertEquals("PRICE: $0.65", testee.getDisplay());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
		assertEquals(Collections.emptyList(), testee.getProductOutput());
	}


	@Test
	public void buyChipsWithQuarters(){
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.QUARTER);
		testee.selectProduct(Product.CHIPS);
		assertEquals("THANK YOU", testee.getDisplay());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(0, testee.getValue());
		assertEquals(Collections.singletonList(Product.CHIPS), testee.getProductOutput());
		assertEquals(Collections.emptyList(), testee.getProductOutput());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}

	@Test
	public void buyCandyWithDimes(){
		testee.accept(Coin.DIME);
		testee.accept(Coin.DIME);
		testee.accept(Coin.DIME);
		testee.accept(Coin.DIME);
		testee.accept(Coin.DIME);
		testee.accept(Coin.DIME);
		testee.accept(Coin.DIME);
		testee.selectProduct(Product.COLA);
		testee.selectProduct(Product.CANDY);
		assertEquals(0, testee.getValue());
		assertEquals("THANK YOU", testee.getDisplay());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(Collections.singletonList(Product.CANDY), testee.getProductOutput());
		assertEquals(Collections.emptyList(), testee.getProductOutput());
		assertEquals(Collections.singletonList(Coin.NICKEL), testee.getCoinReturn());
	}


	@Test
	public void returnCoins(){
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
		testee.accept(Coin.DIME);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.INVALID);
		testee.accept(Coin.DIME);
		testee.accept(Coin.NICKEL);
		testee.returnCoins();
		assertEquals(Arrays.asList(Coin.INVALID, Coin.DIME, Coin.QUARTER, Coin.DIME, Coin.NICKEL), testee.getCoinReturn());
		assertEquals(0, testee.getValue());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}

	@Test
	public void returnCoinsAfterBuy(){
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.INVALID);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.QUARTER);
		testee.selectProduct(Product.COLA);
		assertEquals(Collections.singletonList(Product.COLA), testee.getProductOutput());
		assertEquals("THANK YOU", testee.getDisplay());
		assertEquals(0, testee.getValue());
		testee.returnCoins();
		assertEquals(Arrays.asList(Coin.INVALID), testee.getCoinReturn());
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}

	@Test
	public void makeChange1(){
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.QUARTER);
		testee.selectProduct(Product.CANDY);
		assertEquals(Collections.singletonList(Product.CANDY), testee.getProductOutput());
		assertEquals(0, testee.getValue());
		assertEquals(Arrays.asList(Coin.DIME), testee.getCoinReturn());
	}

	@Test
	public void makeChange2(){
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
		testee.accept(Coin.NICKEL);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.DIME);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.QUARTER);
		testee.selectProduct(Product.CHIPS);
		assertEquals(Collections.singletonList(Product.CHIPS), testee.getProductOutput());
		assertEquals(0, testee.getValue());
		assertEquals(Arrays.asList(Coin.QUARTER, Coin.QUARTER, Coin.DIME, Coin.NICKEL), testee.getCoinReturn());
	}

	@Test
	public void makeChange3(){
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.INVALID);
		testee.accept(Coin.QUARTER);
		testee.accept(Coin.NICKEL);
		testee.selectProduct(Product.CHIPS);
		assertEquals(Collections.singletonList(Product.CHIPS), testee.getProductOutput());
		assertEquals("THANK YOU", testee.getDisplay());
		assertEquals(0, testee.getValue());
		assertEquals(Arrays.asList(Coin.INVALID, Coin.QUARTER, Coin.NICKEL), testee.getCoinReturn());
		testee.returnCoins();
		assertEquals("INSERT COIN", testee.getDisplay());
		assertEquals(Collections.emptyList(), testee.getCoinReturn());
	}
}
