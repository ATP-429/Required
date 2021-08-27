package Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GL2;

public class Renderer
{
	public static int fontWidth, fontHeight;
	
	private static final int SIDES = 100; //Number of sides of a circle that'll be drawn by default whenever a circle related function is called
	
	private static IntBuffer textures = IntBuffer.allocate(1000);
	private static int fontStartID; //fontStartID is the texID of the first character of the font (!)
	private static int nt = 0; //Number of textures
	
	//IMPORTANT NOTE : Call this function before you use any texture function that uses the IntBuffer 'textures' from this class
	public static void genInternalTextures(GL2 gl)
	{
		gl.glGenTextures(1000, textures);
	}
	
	public static void drawString(String text, double x, double y, double scale, GL2 gl)
	{
		for (int i = 0; i < text.length(); i++)
		{
			int c = text.charAt(i);
			int texID = 0;
			if (c == 32) //This means character is SPACE or ' ', so we just leave that space empty
			{
				continue;
			}
			else if (c >= '!' && c <= '~') //Character that exists in our loaded font
			{
				texID = c - 33 + fontStartID;
			}
			else //Missing character font
			{
				texID = 94 + fontStartID;
			}
			Renderer.useColor(0xFFFFFFFF, gl);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.get(texID));
			gl.glEnable(GL2.GL_TEXTURE_2D);
			gl.glEnable(GL2.GL_BLEND);
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
			gl.glBegin(GL2.GL_POLYGON);
			gl.glTexCoord2f(0.0f, 1.0f);
			gl.glVertex2d(x + i * fontWidth * scale, y);
			gl.glTexCoord2f(1.0f, 1.0f);
			gl.glVertex2d(x + (i + 1) * fontWidth * scale, y);
			gl.glTexCoord2f(1.0f, 0.0f);
			gl.glVertex2d(x + (i + 1) * fontWidth * scale, y + fontHeight * scale);
			gl.glTexCoord2f(0.0f, 0.0f);
			gl.glVertex2d(x + i * fontWidth * scale, y + fontHeight * scale);
			gl.glEnd();
			gl.glDisable(GL2.GL_BLEND);
			gl.glDisable(GL2.GL_TEXTURE_2D);
		}
	}
	
	public static void loadSimpleFont(String path, int charNum, int width, int height, int color, GL2 gl) throws IOException
	{
		BufferedImage img = ImageIO.read(new File(path));
		
		fontStartID = nt;
		fontWidth = width;
		fontHeight = height;
		for (int i = 0; i < charNum; i++)
		{
			ByteBuffer imgBuffer = ByteBuffer.allocate(4 * width * height);
			for (int y = 0; y < img.getHeight(); y++)
			{
				for (int x = i * width; x < (i + 1) * width; x++)
				{
					int pixel = img.getRGB(x, y); //Color will be stored as such (in binary) : RRRRRRRR GGGGGGGG BBBBBBBB (8 bits for each color, 256 shades of color)
					if (pixel == 0xFFFFFFFF) //If pixel is WHITE, that means there it's part of the font, so we have to read and put it in the image
					{
						imgBuffer.put((byte) ((color >> 16) & 0xFF)); //Red color //We get RRRRRRRR after pixel >> 16
						imgBuffer.put((byte) ((color >> 8) & 0xFF)); //Green color //We would get RRRRRRRR GGGGGGGG after pixel >> 8. If we want only GGGGGGGG, we need to AND the result with 00000000 11111111 (Or just 0xFF)
						imgBuffer.put((byte) (color & 0xFF)); //Blue color //Similary, here we would get RRRRRRRR GGGGGGGG BBBBBBBB. If we only want BBBBBBBB, we need to AND the result with 00000000 00000000 11111111 (0xFF, again)
						imgBuffer.put((byte) ((color >> 24) & 0xFF)); //Alpha value
					}
					else //This means that there is no pixel at this position, so we can put whatever we want in RGB but we need to make sure alpha is 0
					{
						imgBuffer.put((byte) 0); //Red
						imgBuffer.put((byte) 0); //Green
						imgBuffer.put((byte) 0xFF); //Blue
						imgBuffer.put((byte) 0); //Setting the alpha value to 0
					}
				}
			}
			imgBuffer.flip(); //Sets the position to 0. Basically, now we can start reading from this buffer because the pointer is set to 0. Otherwise, we'd be at the end of the buffer and not be reading anything
			
			gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.get(nt));
			gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, width, height, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, imgBuffer);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
			gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
			nt++; //Increment the number of textures loaded in Menu
		}
	}
	
	public static void drawTexture(double[][] vertices, double[][] uvs, int texID, IntBuffer textures, GL2 gl)
	{
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.get(texID));
		gl.glBegin(GL2.GL_POLYGON);
		if (vertices.length != uvs.length)
		{
			System.err.println("ERROR: Mismatch between number of vertices and UVs for texID " + texID);
			return;
		}
		for (int i = 0; i < vertices.length; i++)
		{
			gl.glTexCoord2d(uvs[i][0], uvs[i][1]);
			gl.glVertex2d(vertices[i][0], vertices[i][1]);
		}
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}
	
	public static void loadTexture(String path, int texID, IntBuffer textures, GL2 gl)
	{
		loadTexture(path, 0, 0, -1, -1, texID, textures, gl); //Put -1 as width and height because we haven't loaded the image yet
	}
	
	public static void loadTexture(String path, int x, int y, int width, int height, int texID, IntBuffer textures, GL2 gl) //(x,y) refer to the starting position of the pixel we want to start reading from, and width and height refer to the size of the selection that we want to read
	{
		BufferedImage img = loadImage(path);
		loadTexture(img, x, y, width, height, texID, textures, gl);
	}
	
	//This function can be used if you want to load multiple crops from the same image. Just load the image once, then call this function multiple times instead of using the above function and loading the image multiple times
	public static void loadTexture(BufferedImage img, int x, int y, int width, int height, int texID, IntBuffer textures, GL2 gl)
	{
		if (x + width > img.getWidth() || y + height > img.getHeight())
		{
			System.err.println("ERROR : Crop specified does not lie entirely in the source image for texID " + texID);
			return;
		}
		
		//If height or width are -1, it means user just wants to load the whole image and doesn't know the height or width of image
		if (height < 0)
			height = img.getHeight();
		if (width < 0)
			width = img.getWidth();
		
		//Loading the image into a ByteBuffer
		ByteBuffer imgBuffer = ByteBuffer.allocate(4 * width * height);
		for (int ay = y; ay < y + height; ay++)
		{
			for (int ax = x; ax < x + width; ax++)
			{
				int pixel = img.getRGB(ax, ay); //Color will be stored as such (in binary) : RRRRRRRR GGGGGGGG BBBBBBBB (8 bits for each color, 256 shades of color)
				imgBuffer.put((byte) ((pixel >> 16) & 0xFF)); //Red color //We get RRRRRRRR after pixel >> 16
				imgBuffer.put((byte) ((pixel >> 8) & 0xFF)); //Green color //We would get RRRRRRRR GGGGGGGG after pixel >> 8. If we want only GGGGGGGG, we need to AND the result with 00000000 11111111 (Or just 0xFF)
				imgBuffer.put((byte) (pixel & 0xFF)); //Blue color //Similary, here we would get RRRRRRRR GGGGGGGG BBBBBBBB. If we only want BBBBBBBB, we need to AND the result with 00000000 00000000 11111111 (0xFF, again)
				imgBuffer.put((byte) ((pixel >> 24) & 0xFF)); //Alpha value 
			}
		}
		imgBuffer.flip(); //Sets the position to 0. Basically, now we can start reading from this buffer because the pointer is set to 0. Otherwise, we'd be at the end of the buffer and not be reading anything
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.get(texID));
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, width, height, 0, GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, imgBuffer);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
		gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
	}
	
	public static void fillCircle(double x, double y, double radius, int col, GL2 gl)
	{
		useColor(col, gl);
		gl.glEnable(GL2.GL_POLYGON_SMOOTH);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL2.GL_POLYGON);
		pathCircle(x, y, radius, SIDES, gl);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_POLYGON_SMOOTH);
	}
	
	public static void drawCircle(double x, double y, double radius, int col, GL2 gl)
	{
		useColor(col, gl);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL2.GL_LINE_STRIP);
		pathCircle(x, y, radius, SIDES, gl);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
	}
	
	//(x, y) refers to the bottom left of the rectangle
	public static void fillRect(double x, double y, double width, double height, int col, GL2 gl)
	{
		useColor(col, gl);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL2.GL_POLYGON);
		pathRect(x, y, width, height, gl);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
	}
	
	public static void drawRect(double x, double y, double width, double height, int col, GL2 gl)
	{
		useColor(col, gl);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL2.GL_LINE_STRIP);
		pathRect(x, y, width, height, gl);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
	}
	
	public static void drawTexturedRect(double x, double y, double width, double height, int texID, IntBuffer textures, int col, GL2 gl)
	{
		useColor(col, gl);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures.get(texID));
		gl.glBegin(GL2.GL_POLYGON);
		gl.glTexCoord2d(0, 1);
		gl.glVertex2d(x, y);
		gl.glTexCoord2d(1, 1);
		gl.glVertex2d(x + width, y);
		gl.glTexCoord2d(1, 0);
		gl.glVertex2d(x + width, y + height);
		gl.glTexCoord2d(0, 0);
		gl.glVertex2d(x, y + height);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}
	
	public static void drawLine(double x1, double y1, double x2, double y2, int col, GL2 gl)
	{
		useColor(col, gl);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex2d(x1, y1);
		gl.glVertex2d(x2, y2);
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
	}
	
	public static void pathCircle(double x, double y, double radius, int sides, GL2 gl)
	{
		double step = 2 * Math.PI / sides;
		for (double angle = 0; angle < 2 * Math.PI; angle += step)
		{
			gl.glVertex2d(x + Math.cos(angle) * radius, y + Math.sin(angle) * radius);
		}
		gl.glVertex2d(x + radius, y);
	}
	
	public static void pathRect(double x, double y, double width, double height, GL2 gl)
	{
		gl.glVertex2d(x, y);
		gl.glVertex2d(x + width, y);
		gl.glVertex2d(x + width, y + height);
		gl.glVertex2d(x, y + height);
	}
	
	public static void useColor(int col, GL2 gl)
	{
		float[] floats = getFloats(col);
		gl.glColor4f(floats[1], floats[2], floats[3], floats[0]);
	}
	
	public static float[] getFloats(int col) //Returns individual color values as floats between 0 and 1 as an array
	{
		return new float[] {(float) ((byte) (col >> 24) & 0xFF) / 255, (float) ((byte) (col >> 16) & 0xFF) / 255, (float) ((byte) (col >> 8) & 0xFF) / 255, (float) ((byte) col & 0xFF) / 255};
	}
	
	public static BufferedImage loadImage(String path)
	{
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(new File(path));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return img;
	}
}
