/// Java swing renderer for the game

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Renderer extends JPanel implements KeyListener {
	final int size = 20;
	final int delay = 100;
	final int fps = 165;
	final int mspf = 1000 / fps;

	Snake snake = new Snake();
	Timer physics = new Timer(delay, e -> tick());
	Timer render = new Timer(mspf, e -> repaint());

	public Renderer() {
		this.setPreferredSize(new Dimension(size * snake.size, size * snake.size));
		this.setBackground(Color.BLACK);

		addKeyListener(this);
		setFocusable(true);
		setDoubleBuffered(true);

		physics.setInitialDelay(1000);
		physics.start();
		render.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	void draw(Graphics g) {
		int[][] board = snake.render();

		for (int row = 0; row < board[0].length; row++) {
			for (int col = 0; col < board.length; col++) {
				switch (board[col][row]) {
					case 1 -> g.setColor(Color.LIGHT_GRAY);
					case 2 -> g.setColor(Color.WHITE);
					case 3 -> g.setColor(Color.RED);
					default -> g.setColor(Color.BLACK);
				}

				g.fillRect(col * size, row * size, size, size);
			}
		}
	}

	void tick() {
		snake.tick();
		
		if (snake.end()) {
			physics.stop();
			if (snake.success()) {
				JOptionPane.showMessageDialog(this, "You win!");
			} else {
				JOptionPane.showMessageDialog(this, "You lose!");
			}

			snake = new Snake();
			physics.restart();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP, KeyEvent.VK_W -> snake.addKeypress(0);
			case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> snake.addKeypress(1);
			case KeyEvent.VK_DOWN, KeyEvent.VK_S -> snake.addKeypress(2);
			case KeyEvent.VK_LEFT, KeyEvent.VK_A -> snake.addKeypress(3);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	public static void start() {
		JFrame frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Renderer());
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
}
