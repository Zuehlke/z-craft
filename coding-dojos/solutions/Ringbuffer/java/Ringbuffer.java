
public class Ringbuffer {
	
	int[] array;
	
	int addPos;
	int takePos;
	int size;

	public Ringbuffer(int capacity) {
		array = new int[capacity];
		addPos = 0;
		takePos = 0;
		size = 0;
	}
	
	public void add(int elem) {
		array[addPos] = elem;
		increaseAddPos();
		if (size == array.length) {
			increaseTakePos();
		}
		increaseSize();
	}

	public Object take() {
		validateSizeGreaterZero();
		int elem = array[takePos];
		decreaseSize();
		increaseTakePos();
		return elem;
	}

	public int getSize() {
		return size;
	}
	
	private void increaseTakePos() {
		takePos++;
		if (takePos == array.length) {
			takePos = 0;
		}
	}
	
	private void increaseAddPos() {
		addPos++;
		if (addPos >= array.length) {
			addPos = 0;
		} 
	}
	
	private void increaseSize() {
		if (size < array.length) {
			size++;
		}		
	}
	
	private void decreaseSize()  {
		if (size > 0) {
			size--;
		}
	}

	private void validateSizeGreaterZero() {
		if (size == 0 ) {
			throw new IllegalStateException();
		}
	}
}
