package game;

import java.awt.Rectangle;

/**
 * Class that manages all properties belonging to pipe obstacles.
 *
 */
public class Pipe {
	
	private static final int WIDTH = 100;
	private static final int PIPEGAP = 180;
	private static final int EDGE = 40;
	private static final int PIPESPEED = -3;
	
	private Rectangle topPipe;
	private Rectangle bottomPipe;
	
	/**
	 * Creates a new instance of the Pipe class that contains a top and bottom pipe.
	 * @param windowWidth the width of the play window
	 * @param windowHeight the height of the play window
	 */
	public Pipe(int windowWidth, int windowHeight) {
		// calculates a random value for the pipe opening between the minumum value which is the top EDGE
		// and the maximum value which is the windowHeight - the needed gap between the pipes - the bottom EDGE
		int randomPipeY = (int) (Math.random() * (windowHeight - PIPEGAP - EDGE - EDGE)) + 40;
		topPipe = new Rectangle(windowWidth, randomPipeY - windowHeight, WIDTH, windowHeight);	
		int heightBottom = randomPipeY + PIPEGAP;
		bottomPipe = new Rectangle(windowWidth, heightBottom, WIDTH, windowHeight);
	}
	
	/**
	 * Gets the top pipe object.
	 * @return top pipe as a Rectangle
	 */
	public Rectangle getTopPipe() {
		return topPipe;
	}
	
	/**
	 * Get the bottom pipe object.
	 * @return bottom pipe as a Rectangle.
	 */
	public Rectangle getBottomPipe() {
		return bottomPipe;
	}
	
	/**
	 * Moves the pipe objects to simulate movement on the screen.
	 */
	public void move() {
		topPipe.translate(PIPESPEED, 0);
		bottomPipe.translate(PIPESPEED, 0);
	}
	
	/**
	 * Checks if the Pipe has passed the whole play window and thus is not visible anymore.
	 * @return true if the whole pipe is off screen, false otherwise
	 */
	public boolean checkOutOfBounds() {
		return topPipe.getX() + WIDTH < 0;
	}

}
