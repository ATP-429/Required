package Utility;

public class Vector3iBuffer
{
	public double x, y, z;

	public Vector3iBuffer()
	{

	}

	public Vector3iBuffer(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void normalize()
	{
		double mag = getMagnitude();
		this.x /= mag;
		this.y /= mag;
		this.z /= mag;
	}

	public double getMagnitude()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	public double multiply(Vector3iBuffer a)
	{
		return (this.x * a.x + this.y * a.y + this.z * a.z);
	}

	public static double multiply(Vector3iBuffer a, Vector3iBuffer b)
	{
		return (a.x * b.x + a.y * b.y + a.z * b.z);
	}

	public void multiply(double s)
	{
		this.x *= s;
		this.y *= s;
		this.z *= s;
	}

	public static Vector3iBuffer multiply(Vector3iBuffer a, double s)
	{
		return new Vector3iBuffer(a.x * s, a.y * s, a.z*s);
	}

	public void add(Vector3iBuffer a)
	{
		this.x += a.x;
		this.y += a.y;
		this.z += a.z;
	}

	public void subtract(Vector3iBuffer a)
	{
		this.x -= a.x;
		this.y -= a.y;
		this.z -= a.z;
	}

	public static Vector3iBuffer add(Vector3iBuffer a, Vector3iBuffer b)
	{
		return new Vector3iBuffer(a.x + b.x, a.y + b.y, a.z + b.z);
	}

	public static Vector3iBuffer subtract(Vector3iBuffer a, Vector3iBuffer b)
	{
		return new Vector3iBuffer(a.x - b.x, a.y - b.y, a.z-b.z);
	}

	public double distanceFrom(Vector3iBuffer a)
	{
		return Math.sqrt((a.x - this.x) * (a.x - this.x) + (a.y - this.y) * (a.y - this.y) + (a.z - this.z) * (a.z - this.z));
	}

	public static double distanceBetween(Vector3iBuffer a, Vector3iBuffer b)
	{
		return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y) + (b.z - a.z) * (b.z - a.z));
	}

	public Vector3iBuffer clone()
	{
		return new Vector3iBuffer(this.x, this.y, this.z);
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
