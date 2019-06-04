package game;

import java.awt.geom.Ellipse2D;

/**
 * Class that manages all the properties belonging to the Bird.
 *
 */
public class Bird {
	
	private static final int X = 120;
	private static final int Y = 200;
	private static final int WIDTH = 51;
	private static final int HEIGHT = 36;
	private static final int GRAVITY = 1;
	private static final int FLAPS = 12;
	private static final int UPDISTANCE = 10;
	
	private Ellipse2D.Double bird;
	private int flight;
	private int gravityAcceleration;
	
	/**
	 * Creates a new instance of the Bird class.
	 */
	public Bird() {
		bird = new Ellipse2D.Double(X, Y, WIDTH, HEIGHT);
		flight = 0;
		gravityAcceleration = 6;
	}
	
	/**
	 * Get function to return the Shape that represents the bird
	 * @return the bird as an Ellipse2d.Double object
	 */
	public Ellipse2D.Double getBird() {
		return bird;
	}
	
	/**
	 * Calculates the gravity the bird is experiencing.
	 */
	public void gravity() {
		bird.setFrame(X, bird.getY() + (GRAVITY * gravityAcceleration) , WIDTH, HEIGHT);
	}
	
	/**
	 * Add to the birds flying status.
	 */
	public void takeFlight() {
			flight += FLAPS;
			gravityAcceleration = 1;
	}
	
	/**
	 * Moves the bird upwards.
	 */
	public void up() {
		bird.setFrame(X, bird.getY() - UPDISTANCE, WIDTH, HEIGHT);
		flight--;
		switch (flight) {
		case 8:
			gravityAcceleration += 1;
			break;
		case 4:
			gravityAcceleration += 1;
			break;
		case 0:
			gravityAcceleration += 3;
			break;
		}
	}
	
	/**
	 * Checks if the bird currently if flying as in it has upwards momentum.
	 * @return true if the bird is going up, false if it is falling down
	 */
	public boolean flying() {
		return flight > 0;
	}
	
	/**
	 * Checks if the bird goes above or below the visible screen
	 * @param screenHeight the height of the screen as int
	 * @return true if the bird is out of bounds, false otherwise
	 */
	public boolean dead(int screenHeight) {
		return bird.getY() > screenHeight || bird.getY() + HEIGHT < 0;
	}

}
