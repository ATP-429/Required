package Utility;

import java.util.ArrayList;

public class QuadTree
{
	public ArrayList<Vector2i> points = new ArrayList<Vector2i>();
	
	public QuadTree nw, ne, se, sw;
	public double width, height, x, y;
	public boolean divided;
	
	public QuadTree(double width, double height, double x, double y)
	{
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public void divide()
	{
		nw = new QuadTree(width / 2, height / 2, x, y);
		ne = new QuadTree(width / 2, height / 2, x + width / 2, y);
		se = new QuadTree(width / 2, height / 2, x, y + height / 2);
		sw = new QuadTree(width / 2, height / 2, x + height / 2, y + height / 2);
		divided = true;
	}
	
	public void insert(Vector2i point)
	{
		if (points.size() > 5)
			this.divide();
		if (!divided)
		{
			points.add(point);
		}
		else
		{
			if (point.x < width / 2)
			{
				if (point.y < height / 2)
				{
					nw.insert(point);
				}
				else
				{
					sw.insert(point);
				}
			}
			else
			{
				if (point.y < height / 2)
				{
					ne.insert(point);
				}
				else
				{
					se.insert(point);
				}
			}
		}
	}
	
	public boolean intersects(Vector2i center, double radius)
	{
		return !(Math.abs(center.x - (this.x + width)) > radius || Math.abs(center.y - (this.y + height)) > radius || Math.abs(center.x - (this.x)) > radius || Math.abs(center.y - (this.y)) > radius);
	}
	
	public ArrayList<Vector2i> search(Vector2i center, double radius)
	{
		ArrayList<Vector2i> result = new ArrayList<Vector2i>();
		get(result, center, radius);
		return result;
	}
	
	public void get(ArrayList<Vector2i> array, Vector2i center, double radius)
	{
		if (!divided)
		{
			if (this.intersects(center, radius))
			{
				for (int i = 0; i < points.size(); i++)
					array.add(points.get(i));
			}
		}
		else
		{
			nw.get(array, center, radius);
			ne.get(array, center, radius);
			se.get(array, center, radius);
			sw.get(array, center, radius);
		}
	}
}
