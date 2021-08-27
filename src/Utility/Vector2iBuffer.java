package Utility;

public class Vector2iBuffer
{
	public double x, y;
	
	public Vector2iBuffer()
	{
		
	}
	
	public Vector2iBuffer(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void rotate(double angle)
	{
		double x = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		double y = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		this.x = x;
		this.y = y;
	}
	
	public void normalize()
	{
		double mag = getMagnitude();
		this.x /= mag;
		this.y /= mag;
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public double multiply(Vector2iBuffer a)
	{
		return (this.x * a.x + this.y * a.y);
	}
	
	public static double multiply(Vector2iBuffer a, Vector2iBuffer b)
	{
		return (a.x * b.x + a.y * b.y);
	}
	
	public void multiply(double s)
	{
		this.x *= s;
		this.y *= s;
	}
	
	public static Vector2iBuffer multiply(Vector2iBuffer a, double s)
	{
		return new Vector2iBuffer(a.x * s, a.y * s);
	}
	
	public void add(Vector2iBuffer a)
	{
		this.x += a.x;
		this.y += a.y;
	}
	
	public void subtract(Vector2iBuffer a)
	{
		this.x -= a.x;
		this.y -= a.y;
	}
	
	public static Vector2iBuffer add(Vector2iBuffer a, Vector2iBuffer b)
	{
		return new Vector2iBuffer(a.x + b.x, a.y + b.y);
	}
	
	public static Vector2iBuffer subtract(Vector2iBuffer a, Vector2iBuffer b)
	{
		return new Vector2iBuffer(a.x - b.x, a.y - b.y);
	}
	
	public double distanceFrom(Vector2iBuffer a)
	{
		return Math.sqrt((a.x - this.x) * (a.x - this.x) + (a.y - this.y) * (a.y - this.y));
	}
	
	public static double distanceBetween(Vector2iBuffer a, Vector2iBuffer b)
	{
		return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
	}
	
	public Vector2iBuffer clone()
	{
		return new Vector2iBuffer(this.x, this.y);
	}
	
	public void print()
	{
		System.out.print("x : " + this.x + ", y : " + this.y);
	}
	
	public void println()
	{
		System.out.println("x : " + this.x + ", y : " + this.y);
	}
}
