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

public class Main implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener
{
	public static final int HEIGHT = 1000, WIDTH = 1000;
	public static int fps = 0, ups = 0;
	
	public static volatile int mouseX, mouseY;
	
	IntBuffer textures = IntBuffer.allocate(100);
	
	public static void main(String[] args)
	{
		GLProfile glprofile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(glprofile);
		GLCanvas glcanvas = new GLCanvas(capabilities);
		Main main = new Main();
		glcanvas.addGLEventListener(main);
		glcanvas.addKeyListener(main);
		glcanvas.addMouseListener(main);
		glcanvas.addMouseMotionListener(main);
		glcanvas.addMouseWheelListener(main);
		glcanvas.setSize(WIDTH, HEIGHT);
		
		JFrame frame = new JFrame();
		frame.getContentPane().add(glcanvas);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//frame.setUndecorated(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		while (true)
		{
			glcanvas.display();
		}
	}
	
	public void display(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glScaled(2.0 / (WIDTH), 2.0 / (HEIGHT), 1.0f);
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
	}
	
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		
	}
	
	public void keyReleased(KeyEvent e)
	{
		
	}
	
	public void mouseClicked(MouseEvent e)
	{
	}
	
	public void mousePressed(MouseEvent e)
	{
		
	}
	
	public void mouseReleased(MouseEvent e)
	{
		
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
		
	}
}
