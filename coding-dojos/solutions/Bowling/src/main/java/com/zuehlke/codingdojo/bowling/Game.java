package com.zuehlke.codingdojo.bowling;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private List<Frame> frames = new ArrayList<>();

	public void addThrow(int points) {
		Frame f = getOrCreateFrame();
		
		if (f.getFirstThrow() == null) {
			f.setFirstThrow(points);
		} else {
			f.setSecondThrow(points);
		}
		
	}

	private Frame getOrCreateFrame() {
		if (frames.isEmpty()) {
			frames.add(new Frame());
		}
		Frame lastFrame = frames.get(frames.size() - 1);
		if (lastFrame.isComplete()) {
			frames.add(new Frame());	
		}
		return frames.get(frames.size() - 1);
	}

	public int totalPoints() {
		return frames.stream().mapToInt(f -> f.getTotalPoints()).sum();
	}

	
	
	
}
