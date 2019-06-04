package graphics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.*;

public class FlappyPanel extends JPanel implements KeyListener, Runnable {
	
	private static final long serialVersionUID = 1910951651951425753L;
	private static final int windowWidth = 440;
	private static final int windowHeight = 600;
	private static final String filename = "sprites/sprites.png";
	
	private Bird bird;
	private boolean gameEnd;
	private LinkedList<Pipe> pipes;
	private BufferedImage spriteSheet;
	private Thread game;
	
	public FlappyPanel() {
		super();
		initialize();
	}
	
	/**
	 * Initializes the panel. Sets the size, loads the sprites and adds the listeners.
	 */
	public void initialize() {
		setPreferredSize(new Dimension(windowWidth, windowHeight));
		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(this);
		try {
            spriteSheet = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
		startGame();
	}
	
	/**
	 * Starts a new FlappyBird game.
	 */
	private void startGame() {
		bird = new Bird();
		pipes = new LinkedList<Pipe>();
		pipes.add(new Pipe(windowWidth, windowHeight));
		pipes.add(new Pipe(windowWidth + 400, windowHeight));
		gameEnd = false;
		game = new Thread(this);
		game.start();	
	}
	
	/**
	 * Draws all the different objects used in the game to the screen.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int x1, y1, x2, y2;
		//background
		g2.drawImage(spriteSheet, 0, 0, windowWidth, 500, 0, 0, 140, 250, null);
		g2.drawImage(spriteSheet, 0, 500, windowWidth, windowHeight, 295, 5, 460, 55, null);
		//draw bird
		Ellipse2D.Double flappy = bird.getBird();
		x1 = (int)flappy.getX();
		y1 = (int)flappy.getY();
		x2 = (int)(flappy.getX() + flappy.getWidth());
		y2 = (int)(flappy.getY() + flappy.getHeight());
		g2.drawImage(spriteSheet, x1, y1, x2, y2, 2, 490, 19, 503, null);
		// draws hit detection object for bird
		// g2.draw(bird.getBird());
		Rectangle p;
		// draw pipes
		for (Pipe pipe: pipes) {
			// draws hit detection object for top pipe
			//g2.draw(pipe.getTopPipe());
			p = pipe.getTopPipe();
			x1 = (int)p.getX() - 12;
			y1 = (int)p.getY();
			x2 = (int)(p.getX() + p.getWidth());
			y2 =  (int)(p.getY() + p.getHeight());
			g2.drawImage(spriteSheet, x1 , y1, x2, y2 + 2, 54, 323, 82, 483, null);
			// draws hit detection object for bottom pipe
			//g2.draw(pipe.getBottomPipe());
			p = pipe.getBottomPipe();
			x1 = (int)p.getX() - 4;
			y1 = (int)p.getY();
			x2 = (int)(p.getX() + p.getWidth());
			y2 =  (int)(p.getY() + p.getHeight());
			g2.drawImage(spriteSheet, x1, y1, x2, y2, 84, 323, 110, 483, null);
		}
	}

	/**
	 * Interface inclusion. Empty method.
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

	/**
	 * 
	 */
	@Override 
	public void keyPressed(KeyEvent e) {
		if (gameEnd && e.getKeyCode() == KeyEvent.VK_ENTER) {
			startGame();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			bird.takeFlight();
		}
	}

	/**
	 * Interface inclusion. Empty method.
	 */
	@Override
	public void keyReleased(KeyEvent e) {}
	
	/**
	 * All the actions that happen every frame.
	 */
	private void frame() {
		// Checks if the first pipe in the list is out of bounds if so removes it and generates a new pipe.
		if (pipes.getFirst().checkOutOfBounds()) {
			pipes.removeFirst();
			pipes.add(new Pipe(windowWidth + 200, windowHeight));	
		}
		// Movement effecting the bird.
		bird.gravity();
		if (bird.flying()) {
			bird.up();
		}
		// Movement of the pipes.
		for (Pipe pipe: pipes) {
			pipe.move();
		}
	}
	
	/**
	 * Checks if there are any collisions between the bird and the pipes and checks if the bird is still on screen.
	 * @return true if the bird is off screen or collided with a pipe, false otherwise
	 */
	private boolean collisions() {		
		if (bird.dead(windowHeight)) {
			return true;
		}
		Shape flappy = bird.getBird();
		for (Pipe pipe: pipes) {
			if (flappy.intersects(pipe.getTopPipe()) || flappy.intersects(pipe.getBottomPipe())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Run method that makes the game move along.
	 */
    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        int delay = 15;
        gameEnd = false;
        beforeTime = System.currentTimeMillis();
        while (!gameEnd) {
        	frame();
            repaint();
            gameEnd = collisions();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = delay - timeDiff;      
            if (sleep < 0) {
                sleep = 2;
            }           
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {            
                e.printStackTrace();             
            }
            beforeTime = System.currentTimeMillis();
        }
    }


}
