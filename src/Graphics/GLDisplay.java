package Graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public abstract class GLDisplay
{
	public abstract void display(GLAutoDrawable drawable, int WIDTH, int HEIGHT);
	public abstract void init(GLAutoDrawable drawable, int WIDTH, int HEIGHT);
}
