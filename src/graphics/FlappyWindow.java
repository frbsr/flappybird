package graphics;

import javax.swing.JFrame;


public class FlappyWindow extends JFrame {
	
	private static final long serialVersionUID = -8286582304291391488L;
	private FlappyPanel fPanel = null;

	public FlappyWindow() {
		super();
		initialize();
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void initialize() {
		setTitle("FlappyBird");
		fPanel = new FlappyPanel();
		setContentPane(fPanel);
		setResizable(false);
		pack();
	}
	
	public static void main(String[] args) {
		new FlappyWindow();
	}

}
