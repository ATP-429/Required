package Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite
{
	public int width, height;
	int[] pixels;
	
	public Sprite(double width, double height)
	{
		this.width = (int) width;
		this.height = (int) height;
		this.pixels = new int[this.width * this.height];
	}
	
	public Sprite(String path)
	{
		try
		{
			BufferedImage sprite = ImageIO.read(new File(path));
			this.width = sprite.getWidth();
			this.height = sprite.getHeight();
			this.pixels = sprite.getRGB(0, 0, width, height, null, 0, width);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Sprite rotate(double angle)
	{
		Sprite result = new Sprite(this.width + this.width * 0.015, this.height + this.height * 0.015);
		double cos = Math.cos(-angle);
		double sin = Math.sin(-angle);
		
		for (int i = 0; i < result.pixels.length; i++)
		{
			int x = i % width;
			int y = i / width;
			double xRot = (x - width / 2) * cos - (y - height / 2) * sin;
			double yRot = (x - width / 2) * sin + (y - height / 2) * cos;
			double finXRot = xRot + width / 2;
			double finYRot = yRot + height / 2;
			if (finXRot >= 0 && finXRot < width && finYRot >= 0 && finYRot < height)
				result.setPixel(x, y, getPixel(finXRot, finYRot));
			else
				result.setPixel(x, y, 0xFFFF00FF);
		}
		return result;
	}
	
	public int getPixel(double x, double y)
	{
		return pixels[(int) x + (int) y * width];
	}
	
	public void setPixel(double x, double y, int col)
	{
		if (x >= 0 && x < width && y >= 0 && y < height)
			pixels[(int) x + (int) y * width] = col;
	}
}
