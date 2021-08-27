package Utility;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener
{
	public Vector2i pos = new Vector2i();
	public boolean[] buttons = new boolean[4];
	public boolean[] last = new boolean[4];
	
	public void mouseDragged(MouseEvent e)
	{
		pos.x = e.getX();
		pos.y = e.getY();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		pos.x = e.getX();
		pos.y = e.getY();
	}
	
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	public void mousePressed(MouseEvent e)
	{
		last = buttons.clone();
		if (e.getButton() < 4)
			buttons[e.getButton()] = true;
	}
	
	public void mouseReleased(MouseEvent e)
	{
		last = buttons.clone();
		if (e.getButton() < 4)
			buttons[e.getButton()] = false;
	}
	
	public boolean pressed(int button)
	{
		if (buttons[button] && buttons[button] != last[button])
		{
			last = buttons.clone();
			return true;
		}
		return false;
	}
}
