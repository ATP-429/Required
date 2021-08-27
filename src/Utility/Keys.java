package Utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keys implements KeyListener
{
	public boolean[] keys = new boolean[120];
	
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() < 120)
			keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() < 120)
			keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
}
