package Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class GLWindowTest extends GLDisplay
{
	@Override
	public void display(GLAutoDrawable drawable, int WIDTH, int HEIGHT)
	{
		GL2 gl = drawable.getGL().getGL2();
		Renderer.fillCircle(0, 0, 50, 0xFFFF0000, gl);
	}
	
	public static void main(String[] args)
	{
		GLWindowTest main = new GLWindowTest();
		GLWindow window = new GLWindow();
		System.out.println("Starting window");
		window.start(main);
	}

	@Override
	public void init(GLAutoDrawable drawable, int WIDTH, int HEIGHT)
	{
		
	}
}
