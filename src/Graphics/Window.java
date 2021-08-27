package Graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import Graphics.Screen;

public class Window extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public int HEIGHT, WIDTH;
	
	public int[] pixels;
	public Screen screen;
	public Graphics dbg;
	
	private JFrame frame;
	private BufferedImage dbi;
	
	private boolean running;
	
	public Window()
	{
		
	}
	
	public Window(Screen screen, int width, int height)
	{
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[WIDTH * HEIGHT];
		Dimension size = new Dimension(WIDTH, HEIGHT);
		dbi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		dbg = dbi.getGraphics();
		pixels = ((DataBufferInt) (dbi.getRaster().getDataBuffer())).getData();
		frame = new JFrame();
		this.screen = screen;
		screen.fill(0xFFFFFFFF);
		//screen.pixels[0] = 0;
		
		setPreferredSize(size);
	}
	
	public void init()
	{
		running = true;
		Thread th = new Thread(this);
		th.start();
	}
	
	public void updateGame()
	{
		
	}
	
	public void render()
	{
		//screen.fill(0xFFFFFFFF);
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = screen.pixels[i];
		}
		repaint();
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(dbi, 0, 0, WIDTH, HEIGHT, null);
	}
	
	public void run()
	{
		/*double lastTime = System.nanoTime();
		double last = System.currentTimeMillis();
		double delta = 0;
		double nsPerTick = 1000000000.0 / 60;
		
		int frames = 0;
		int ups = 0;*/
		
		while (running)
		{
			render();
			/*double currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / nsPerTick;
			lastTime = currentTime;
			while (delta >= 1)
			{
				updateGame();
				delta--;
				ups++;
			}
			render();
			frames++;
			if (System.currentTimeMillis() - last >= 1000)
			{
				frame.setTitle("FPS : " + frames + ", UPS : " + ups);
				frames = 0;
				ups = 0;
				last += 1000;
			}*/
		}
	}
	
	public void start(Screen screen)
	{
		Window game = new Window(screen, 500, 500);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setResizable(false);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.init();
	}
	
	public void update(Graphics g)
	{
		paint(g);
	}
}