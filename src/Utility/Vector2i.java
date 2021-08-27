package Utility;

public class Vector2i
{
	public static final Vector2i NULL_VECTOR = new Vector2i(0, 0);
	
	public double x, y;
	
	public Vector2i()
	{
		
	}
	
	public Vector2i(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2i rotate(double angle)
	{
		Vector2i result = new Vector2i();
		result.x = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		result.y = this.x * Math.sin(angle) + this.y * Math.cos(angle);
		return result;
	}
	
	public Vector2i normalize()
	{
		double mag = getMagnitude();
		if (mag == 0)
		{
			return new Vector2i(this.x, this.y);
		}
		return new Vector2i(this.x / mag, this.y / mag);
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public double multiply(Vector2i a)
	{
		return (this.x * a.x + this.y * a.y);
	}
	
	public Vector2i projectAlong(Vector2i a)
	{
		return (a.normalize().multiply(this.multiply(a.normalize())));
	}
	
	public static double multiply(Vector2i a, Vector2i b)
	{
		return (a.x * b.x + a.y * b.y);
	}
	
	public Vector2i multiply(double s)
	{
		return new Vector2i(this.x * s, this.y * s);
	}
	
	public static Vector2i multiply(Vector2i a, double s)
	{
		return new Vector2i(a.x * s, a.y * s);
	}
	
	public Vector3i cross(Vector3i a)
	{
		return (new Vector3i(this.x, this.y, 0).cross(a));
	}
	
	public Vector3i cross(Vector2i a)
	{
		return (new Vector3i(this.x, this.y, 0).cross(a.toVector3i()));
	}
	
	public Vector2i divide(double s)
	{
		return new Vector2i(this.x / s, this.y / s);
	}
	
	public static Vector2i divide(Vector2i a, double s)
	{
		return new Vector2i(a.x / s, a.y / s);
	}
	
	public Vector2i add(Vector2i a)
	{
		return new Vector2i(this.x + a.x, this.y + a.y);
	}
	
	public Vector2i subtract(Vector2i a)
	{
		return new Vector2i(this.x - a.x, this.y - a.y);
	}
	
	public static Vector2i add(Vector2i a, Vector2i b)
	{
		return new Vector2i(a.x + b.x, a.y + b.y);
	}
	
	public static Vector2i subtract(Vector2i a, Vector2i b)
	{
		return new Vector2i(a.x - b.x, a.y - b.y);
	}
	
	public double distanceFrom(Vector2i a)
	{
		return Math.sqrt((a.x - this.x) * (a.x - this.x) + (a.y - this.y) * (a.y - this.y));
	}
	
	public static double distanceBetween(Vector2i a, Vector2i b)
	{
		return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y));
	}
	
	public static double angleBetween(Vector2i a, Vector2i b)
	{
		return Math.acos(a.multiply(b) / (a.getMagnitude() * b.getMagnitude()));
	}
	
	public Vector3i toVector3i()
	{
		return new Vector3i(this.x, this.y, 0);
	}
	
	//hashCode() and equals() are going to be used in HashMaps and HashSets
	
	@Override
	public int hashCode()
	{
		Double x = Double.valueOf(this.x), y = Double.valueOf(this.y);
		return x.hashCode() + y.hashCode();
	}
	
	@Override
	public boolean equals(Object a)
	{
		Vector2i vec = (Vector2i) a;
		if (this.x == vec.x && this.y == vec.y)
			return true;
		return false;
	}
	
	public Vector2i clone()
	{
		return new Vector2i(this.x, this.y);
	}
	
	public void print()
	{
		System.out.print("x : " + this.x + ", y : " + this.y);
	}
	
	public void println()
	{
		System.out.println("x : " + this.x + ", y : " + this.y);
	}
	
	//Returns x and y value if object is converted to a string (Useful in debugging window)
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
}
