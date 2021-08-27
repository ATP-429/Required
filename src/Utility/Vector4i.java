package Utility;

public class Vector4i
{
	public double x, y, z, w;
	
	public Vector4i()
	{
		
	}
	
	public Vector4i(double x, double y, double z, double w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4i normalize()
	{
		double mag = this.getMagnitude();
		return new Vector4i(this.x / mag, this.y / mag, this.z / mag, this.w);
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	
	public double multiply(Vector4i a)
	{
		return (this.x * a.x + this.y * a.y + this.z * a.z);
	}
	
	public static double multiply(Vector4i a, Vector4i b)
	{
		return (a.x * b.x + a.y * b.y + a.z * b.z);
	}
	
	public double multiply(Vector3i a)
	{
		return (this.x * a.x + this.y * a.y + this.z * a.z);
	}
	
	public static double multiply(Vector3i a, Vector3i b)
	{
		return (a.x * b.x + a.y * b.y + a.z * b.z);
	}
	
	public Vector4i multiply(double s)
	{
		return new Vector4i(this.x * s, this.y * s, this.z * s, this.w);
	}
	
	public static Vector4i multiply(Vector4i a, double s)
	{
		return new Vector4i(a.x * s, a.y * s, a.z * s, a.w);
	}
	
	public Vector4i divide(double s)
	{
		return new Vector4i(this.x / s, this.y / s, this.z / s, this.w);
	}
	
	public static Vector4i divide(Vector4i a, double s)
	{
		return new Vector4i(a.x / s, a.y / s, a.z / s, a.w);
	}
	
	public Vector4i cross(Vector4i a)
	{
		return new Vector4i(this.y * a.z - this.z * a.y, this.z * a.x - this.x * a.z, this.x * a.y - this.y * a.x, this.w);
	}
	
	public Vector4i add(Vector4i a)
	{
		return new Vector4i(this.x + a.x, this.y + a.y, this.z + a.z, this.w+a.w);
	}
	
	public Vector4i subtract(Vector4i a)
	{
		return new Vector4i(this.x - a.x, this.y - a.y, this.z - a.z, this.w-a.w);
	}
	
	public static Vector4i add(Vector4i a, Vector4i b)
	{
		return new Vector4i(a.x + b.x, a.y + b.y, a.z + b.z, a.w+b.w);
	}
	
	public static Vector4i subtract(Vector4i a, Vector4i b)
	{
		return new Vector4i(a.x - b.x, a.y - b.y, a.z - b.z, a.w-b.w);
	}
	
	public double distanceFrom(Vector4i a)
	{
		return Math.sqrt((a.x - this.x) * (a.x - this.x) + (a.y - this.y) * (a.y - this.y) + (a.z - this.z) * (a.z - this.z));
	}
	
	public static double distanceBetween(Vector4i a, Vector4i b)
	{
		return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y) + (b.z - a.z) * (b.z - a.z));
	}
	
	public Vector4i clone()
	{
		return new Vector4i(this.x, this.y, this.z, this.w);
	}
	
	public void print()
	{
		System.out.print("x : " + this.x + ", y : " + this.y + ", z : " + this.z);
	}
	
	public void println()
	{
		System.out.println("x : " + this.x + ", y : " + this.y + ", z : " + this.z);
	}
}
