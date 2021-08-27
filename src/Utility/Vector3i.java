package Utility;

public class Vector3i
{
	public static final Vector3i NULL_VECTOR = new Vector3i(0, 0, 0);
	
	public double x, y, z;
	
	public Vector3i()
	{
		
	}
	
	public Vector3i(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//ROTATION OPERATIONS :
	
	//Formula taken from https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula
	public Vector3i rotateAbout(Vector3i axis, double theta)
	{
		Vector3i k = axis.normalize();
		return this.multiply(Math.cos(theta)).add(k.cross(this).multiply(Math.sin(theta))).add(k.multiply(k.dot(this)).multiply(1 - Math.cos(theta)));
	}
	
	public Vector3i rotateX(double angle)
	{
		Vector3i result = new Vector3i();
		result.x = this.x;
		result.y = this.y * Math.cos(angle) - this.z * Math.sin(angle);
		result.z = this.y * Math.sin(angle) + this.z * Math.cos(angle);
		return result;
	}
	
	public Vector3i rotateY(double angle)
	{
		Vector3i result = new Vector3i();
		result.x = this.z * Math.sin(angle) + this.x * Math.cos(angle);
		result.y = this.y;
		result.z = this.z * Math.cos(angle) - this.x * Math.sin(angle);
		return result;
	}
	
	public Vector3i rotateZ(double angle)
	{
		Vector3i result = new Vector3i();
		result.x = this.x * Math.cos(angle) - this.y * Math.sin(angle);
		result.y = this.x * Math.sin(angle) - this.y * Math.cos(angle);
		result.z = this.z;
		return result;
	}
	
	//BASIC VECTOR OPERATIONS :
	
	public Vector3i normalize()
	{
		double mag = getMagnitude();
		if (mag == 0)
			return new Vector3i(0, 0, 0);
		return new Vector3i(this.x / mag, this.y / mag, this.z / mag);
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}
	
	public double dot(Vector3i a)
	{
		return (this.x * a.x + this.y * a.y + this.z * a.z);
	}
	
	public static double dot(Vector3i a, Vector3i b)
	{
		return (a.x * b.x + a.y * b.y + a.z * b.z);
	}
	
	public Vector3i multiply(double s)
	{
		return new Vector3i(this.x * s, this.y * s, this.z * s);
	}
	
	public static Vector3i multiply(Vector3i a, double s)
	{
		return new Vector3i(a.x * s, a.y * s, a.z * s);
	}
	
	public Vector3i divide(double s)
	{
		return new Vector3i(this.x / s, this.y / s, this.z / s);
	}
	
	public static Vector3i divide(Vector3i a, double s)
	{
		return new Vector3i(a.x / s, a.y / s, a.z / s);
	}
	
	public Vector3i cross(Vector3i a)
	{
		return new Vector3i(this.y * a.z - this.z * a.y, this.z * a.x - this.x * a.z, this.x * a.y - this.y * a.x);
	}
	
	public Vector3i cross(Vector2i a)
	{
		return cross(a.toVector3i());
	}
	
	public Vector3i add(Vector3i a)
	{
		return new Vector3i(this.x + a.x, this.y + a.y, this.z + a.z);
	}
	
	public Vector3i subtract(Vector3i a)
	{
		return new Vector3i(this.x - a.x, this.y - a.y, this.z - a.z);
	}
	
	public static Vector3i add(Vector3i a, Vector3i b)
	{
		return new Vector3i(a.x + b.x, a.y + b.y, a.z + b.z);
	}
	
	public static Vector3i subtract(Vector3i a, Vector3i b)
	{
		return new Vector3i(a.x - b.x, a.y - b.y, a.z - b.z);
	}
	
	//MISCELLANEOUS OPERATIONS :
	
	public double distanceFrom(Vector3i a)
	{
		return Math.sqrt((a.x - this.x) * (a.x - this.x) + (a.y - this.y) * (a.y - this.y) + (a.z - this.z) * (a.z - this.z));
	}
	
	public static double distanceBetween(Vector3i a, Vector3i b)
	{
		return Math.sqrt((b.x - a.x) * (b.x - a.x) + (b.y - a.y) * (b.y - a.y) + (b.z - a.z) * (b.z - a.z));
	}
	
	public static double angleBetween(Vector3i a, Vector3i b)
	{
		return Math.acos(a.dot(b) / (a.getMagnitude() * b.getMagnitude()));
	}
	
	public Vector2i toVector2i()
	{
		return new Vector2i(this.x, this.y);
	}
	
	//PROGRAMMING OPERATIONS :
	
	//hashCode() and equals() are going to be used in HashMaps and HashSets
	
	@Override
	public int hashCode()
	{
		Double x = Double.valueOf(this.x), y = Double.valueOf(this.y), z = Double.valueOf(this.z);
		return x.hashCode() + y.hashCode() + z.hashCode();
	}
	
	@Override
	public boolean equals(Object a)
	{
		Vector3i vec = (Vector3i) a;
		if (this.x == vec.x && this.y == vec.y && this.z == vec.z)
			return true;
		return false;
	}
	
	public Vector3i round()
	{
		return new Vector3i(Math.round(this.x), Math.round(this.y), Math.round(this.z));
	}
	
	public Vector3i clone()
	{
		return new Vector3i(this.x, this.y, this.z);
	}
	
	public void print()
	{
		System.out.print("x : " + this.x + ", y : " + this.y + ", z : " + this.z);
	}
	
	public void println()
	{
		System.out.println("x : " + this.x + ", y : " + this.y + ", z : " + this.z);
	}
	
	//Returns x, y, and z value if object is converted to a string (Useful in debugging window)
	@Override
	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
