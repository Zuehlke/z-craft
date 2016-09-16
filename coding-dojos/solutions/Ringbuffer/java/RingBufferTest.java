import static org.junit.Assert.*;

import org.junit.Test;

public class RingBufferTest {
	
	Ringbuffer buffer = new Ringbuffer(4);

	@Test(expected=IllegalStateException.class)
	public void takeWhenEmpty() {
		buffer.take();
	}

	@Test(expected=IllegalStateException.class)
	public void takeWhenNewlyEmpty() {
		buffer.add(7);
		buffer.take();
		buffer.take();
	}

	@Test(expected=IllegalStateException.class)
	public void takeWhenEmptyAfterOverflow() {
		buffer.add(1);
		buffer.add(2);
		buffer.add(3);
		buffer.add(4);
		buffer.add(5);
		buffer.add(6);
		buffer.take();
		buffer.take();
		buffer.take();
		buffer.take();
		buffer.take();
	}


	@Test
	public void addSimple() {
		buffer.add(7);
		assertEquals(1, buffer.getSize());
	}

	@Test
	public void takeSimple() {
		buffer.add(5);
		assertEquals(5, buffer.take());
		assertEquals(0, buffer.getSize());
	}

	@Test
	public void takeDouble() {
		buffer.add(1);
		buffer.add(2);
		assertEquals(1, buffer.take());
		assertEquals(1, buffer.getSize());
		assertEquals(2, buffer.take());
		assertEquals(0, buffer.getSize());
	}

	@Test
	public void addToCapacity() {
		buffer.add(1);
		assertEquals(1, buffer.getSize());
		buffer.add(2);
		assertEquals(2, buffer.getSize());
		buffer.add(3);
		assertEquals(3, buffer.getSize());
		buffer.add(4);
		assertEquals(4, buffer.getSize());
		buffer.add(5);
		assertEquals(4, buffer.getSize());
	}

	@Test
	public void takeAfterOverflow() {
		buffer.add(1);
		buffer.add(2);
		buffer.add(3);
		buffer.add(4);
		buffer.add(5);
		assertEquals(2, buffer.take());
	}

}
