package Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.nio.IntBuffer;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class GLWindow implements Runnable, GLEventListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	private static final String CMD_FONT_PATH = "/cmdFont.png";
	
	public static JFrame frame;
	
	public int HEIGHT = 1000, WIDTH = 1000;
	public int fps = 0;
	public int msPerFrame = 1000 / 60;
	public GLCanvas glcanvas;
	
	public volatile int mouseX, mouseY;
	public volatile int prevMouseX, prevMouseY;
	public volatile int mouseScroll;
	public volatile GLDisplay main;
	
	public volatile boolean[] keys = new boolean[120];
	public volatile boolean[] previousKeys = new boolean[120];
	
	public boolean[] mousePressed = new boolean[120];
	public boolean[] prevMousePressed = new boolean[120];
	
	private boolean displayOnCall;
	
	IntBuffer textures = IntBuffer.allocate(100);
	
	Thread th;
	
	
	public void start(GLDisplay main, int width, int height)
	{
		this.main = main;
		GLProfile glprofile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(glprofile);
		glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(this);
		glcanvas.addKeyListener(this);
		glcanvas.addMouseListener(this);
		glcanvas.addMouseMotionListener(this);
		glcanvas.addMouseWheelListener(this);
		WIDTH = width;
		HEIGHT = height;
		glcanvas.setSize(this.WIDTH, this.HEIGHT);
		
		frame = new JFrame();
		frame.getContentPane().add(glcanvas);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//frame.setUndecorated(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		th = new Thread(this);
		th.start();
	}
	
	public void start(GLDisplay main, int width, int height, boolean displayOnCall)
	{
		this.displayOnCall = displayOnCall;
		start(main, width, height);
	}
	
	public void start(GLDisplay main)
	{
		start(main, 1000, 1000);
	}
	
	public void start(GLDisplay main, boolean displayOnCall)
	{
		this.displayOnCall = displayOnCall;
		start(main);
	}
	
	public void run()
	{
		if (displayOnCall)
		{
			glcanvas.display();
			return;
		}
		
		while (true)
			glcanvas.display();
	}
	
	public void display(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		main.display(drawable, WIDTH, HEIGHT);
	}
	
	public void initBasicDisplay(GL2 gl)
	{
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		Renderer.genInternalTextures(gl);
		try
		{
			Renderer.loadSimpleFont(CMD_FONT_PATH, 95, 8, 12, 0xFFFFFFFF, gl);
		}
		catch (Exception e)
		{
			System.err.println("WARNING : Font could not be loaded properly in GLWindow");
		}
	}
	
	public void initCustomDisplay(int bgCol, int fontCol, GL2 gl)
	{
		float[] cols = Renderer.getFloats(bgCol);
		gl.glClearColor(cols[1], cols[2], cols[3], cols[0]);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		try
		{
			Renderer.loadSimpleFont(CMD_FONT_PATH, 95, 8, 12, fontCol, gl);
		}
		catch (Exception e)
		{
			System.err.println("WARNING: Font could not be loaded properly in GLWindow");
		}
	}
	
	public void dispose(GLAutoDrawable drawable)
	{
		
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		
	}
	
	public void init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glGenTextures(100, textures);
		initBasicDisplay(gl);
	}
	
	public void repaint()
	{
		glcanvas.display();
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() < 120)
			keys[e.getKeyCode()] = true;
	}
	
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() < 120)
			keys[e.getKeyCode()] = false;
	}
	
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	public void mousePressed(MouseEvent e)
	{
		if (e.getButton() < 120)
			mousePressed[e.getButton()] = true;
	}
	
	public void mouseReleased(MouseEvent e)
	{
		if (e.getButton() < 120)
			mousePressed[e.getButton()] = false;
	}
	
	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		mouseScroll = e.getWheelRotation();
	}
}
