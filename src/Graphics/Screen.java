package Graphics;

import java.util.ArrayList;
import Utility.Vector2i;
import Utility.Vector3i;

public class Screen
{
	public int width, height;
	public int[] pixels;
	
	public Screen(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear()
	{
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = 0xFFFFFFFF;
		}
	}
	
	public void fill(int col)
	{
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = col;
		}
	}
	
	public void fillCircle(double cx, double cy, double r, int col)
	{
		putPixel(cx, cy + 1, col);
		putPixel(cx, cy - 1, col);
		for (double i = r; i >= 0; i--)
		{
			drawCircle(cx, cy, i, col);
		}
	}
	
	public void drawSprite(Sprite sprite, double x, double y)
	{
		for (int yp = 0; yp < sprite.height; yp++)
		{
			for (int xp = 0; xp < sprite.width; xp++)
			{
				int xa = (int) (x + xp);
				int ya = (int) (y + yp);
				if (sprite.getPixel(xp, yp) != 0xFFFF00FF)
					putPixel(xa, ya, sprite.getPixel(xp, yp));
			}
		}
	}
	
	public void drawCircle(double cx, double cy, double r, double R, int col)
	{
		for (int i = 0; i < R; i++)
		{
			drawCircle(cx, cy, r + i, col);
		}
	}
	
	public void drawCircle(double cx, double cy, double r, int col)
	{
		double rsq = r * r;
		double x = r;
		double y = 0;
		double d = x * x + y * y - rsq;
		
		while (x > 0)
		{
			if (d > 0)
			{
				y--;
				d += 2 * y + 1;
			}
			else
			{
				x--;
				d += 2 * x + 1;
			}
			putPixel(cx + x, cy + y, col);
			putPixel(cx + x, cy - y, col);
			putPixel(cx - x, cy + y, col);
			putPixel(cx - x, cy - y, col);
		}
	}
	
	public void fillRect(double x1, double y1, double width, double height, int col)
	{
		for (int i = (int) width; i >= 0; i--)
		{
			drawRect(x1, y1, i, height, col);
		}
	}
	
	public void drawRect(double x1, double y1, double width, double height, int col)
	{
		for (int y = 0; y < height; y++)
		{
			putPixel(x1, y1 + y, col);
			putPixel(x1 + width, y1 + y, col);
		}
		for (int x = 0; x < width; x++)
		{
			putPixel(x1 + x, y1, col);
			putPixel(x1 + x, y1 + height, col);
		}
	}
	
	public void drawLine(double x1, double y1, double x2, double y2, int col)
	{
		if (!((x1 < 0 && x2 < 0) || (y1 < 0 && y2 < 0) || (x1 >= width && x2 >= width) || (y1 >= height && y2 >= height)))
		{
			if (x1 < 0)
			{
				y1 = (int) (y1 + (double) ((y2 - y1) * (-x1)) / (x2 - x1));
				x1 = 0;
			}
			else if (x1 >= width)
			{
				y1 = (int) (y1 + (double) ((y2 - y1) * (width - 1 - x1)) / (x2 - x1));
				x1 = width - 1;
			}
			
			if (y1 < 0)
			{
				x1 = (int) (x1 + (double) ((x2 - x1) * (-y1)) / (y2 - y1));
				y1 = 0;
			}
			else if (y1 >= height)
			{
				x1 = (int) (x1 + (double) ((x2 - x1) * (height - 1 - y1)) / (y2 - y1));
				y1 = height - 1;
			}
			double dx = x2 - x1;
			double dy = y2 - y1;
			double steps = (Math.abs(dx) > Math.abs(dy)) ? Math.abs(dx) : Math.abs(dy);
			double xi = dx / steps;
			double yi = dy / steps;
			double x = x1;
			double y = y1;
			
			for (int i = 0; i < steps; i++)
			{
				x += xi;
				y += yi;
				if (x >= 0 && x < width && y >= 0 && y < height)
					putPixel(x, y, col);
				else
					break;
			}
		}
	}
	
	public void drawTriangleN(Vector2i[] vertices, int col)
	{
		int yMax = (int) Math.max(Math.max(vertices[0].y, vertices[1].y), vertices[2].y);
		int yMin = (int) Math.min(Math.min(vertices[0].y, vertices[1].y), vertices[2].y);
		int imax = (vertices[0].y == yMax) ? 0 : ((vertices[1].y == yMax) ? 1 : 2);
		int imin = (vertices[0].y == yMin) ? 0 : ((vertices[1].y == yMin) ? 1 : 2);
		int imid = 3 - imax - imin;
		int yMid = (int) vertices[imid].y;
		
		EdgeN ledge = new EdgeN(imin, imax, vertices[imin].x, vertices[imin].y, vertices[imax].x, vertices[imax].y); //Longest edge
		EdgeN fedge = new EdgeN(imin, imid, vertices[imin].x, vertices[imin].y, vertices[imid].x, vertices[imid].y); //First edge
		EdgeN sedge = new EdgeN(imid, imax, vertices[imid].x, vertices[imid].y, vertices[imax].x, vertices[imax].y); //Second edge
		
		for (int y = yMin; y <= yMid; y++)
		{
			if (Double.isFinite(fedge.slope))
			{
				double xStart = (fedge.slope < ledge.slope) ? (fedge.x1) : (ledge.x1);
				double lim = (fedge.slope < ledge.slope) ? (ledge.x1 + ledge.sum) : (fedge.x1 + fedge.sum);
				for (double x = xStart; x <= lim; x++)
				{
					putPixel(x, y, col);
				}
				ledge.sum += ledge.slope;
				fedge.sum += fedge.slope;
			}
		}
		
		for (int y = yMid; y <= yMax; y++)
		{
			if (Double.isFinite(sedge.slope))
			{
				double xStart = (sedge.slope < ledge.slope) ? (sedge.x1) : (ledge.x1);
				double lim = (sedge.slope < ledge.slope) ? (ledge.x1 + ledge.sum) : (sedge.x1 + sedge.sum);
				for (double x = xStart; x <= lim; x++)
				{
					putPixel(x, y, col);
				}
				ledge.sum += ledge.slope;
				sedge.sum += sedge.slope;
			}
		}
	}
	
	public void drawTriangle(Vector2i[] vertices, int col)
	{
		EdgeBucket[] edgeTable = new EdgeBucket[3];
		edgeTable[0] = new EdgeBucket(vertices[0].x, vertices[0].y, vertices[2].x, vertices[2].y, 0);
		edgeTable[1] = new EdgeBucket(vertices[0].x, vertices[0].y, vertices[1].x, vertices[1].y, 1);
		edgeTable[2] = new EdgeBucket(vertices[1].x, vertices[1].y, vertices[2].x, vertices[2].y, 2);
		
		if (edgeTable[0].yMin > edgeTable[1].yMin)
		{
			EdgeBucket temp = edgeTable[0];
			edgeTable[0] = edgeTable[1];
			edgeTable[1] = temp;
		}
		if (edgeTable[1].yMin > edgeTable[2].yMin)
		{
			EdgeBucket temp = edgeTable[1];
			edgeTable[1] = edgeTable[2];
			edgeTable[2] = temp;
		}
		if (edgeTable[0].yMin > edgeTable[1].yMin)
		{
			EdgeBucket temp = edgeTable[0];
			edgeTable[0] = edgeTable[1];
			edgeTable[1] = temp;
		}
		
		double yMax = Math.max(Math.max(edgeTable[0].yMax, edgeTable[1].yMax), (edgeTable[2].yMax));
		
		EdgeBucket[] activeTable = new EdgeBucket[2];
		activeTable[0] = edgeTable[0];
		activeTable[1] = edgeTable[1];
		boolean switched = false;
		for (double y = activeTable[0].yMin; y <= yMax; y++)
		{
			if (!switched && edgeTable[2].yMin <= y && Double.isFinite(edgeTable[2].slope))
			{
				if (activeTable[0].yMax <= y)
					activeTable[0] = edgeTable[2];
				else
					activeTable[1] = edgeTable[2];
				switched = true;
			}
			
			double x1 = activeTable[0].xyMin + activeTable[0].sum;
			double x2 = activeTable[1].xyMin + activeTable[1].sum;
			double xMin = (x1 < x2) ? x1 : x2;
			double xMax = (x1 > x2) ? x1 : x2;
			
			for (double x = xMin; x < xMax; x++)
			{
				if (x >= 0 && x < width && y >= 0 && y < height)
				{
					pixels[(int) x + (int) y * width] = col;
				}
			}
			
			if (Double.isFinite(activeTable[0].slope))
				activeTable[0].sum += activeTable[0].slope;
			if (Double.isFinite(activeTable[1].slope))
				activeTable[1].sum += activeTable[1].slope;
		}
	}
	
	public void drawString(double x, double y, String str)
	{
		
	}
	
	public void putPixel(double x, double y, int col)
	{
		if (x >= 0 && x < width && y >= 0 && y < height)
			pixels[(int) x + (int) y * width] = col;
	}
	
	public void putPixelAtIndex(int index, int col)
	{
		if (index < pixels.length)
			pixels[index] = col;
	}	
	
	public void render(Sprite sprite, int xp, int yp)
	{
		int width = sprite.width;
		int height = sprite.height;
		
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				int xa = xp + x;
				int ya = yp + y;
				if (!(xa < 0 || xa >= this.width || ya < 0 || ya >= this.height) && sprite.pixels[x + y * width] != 0xFFFF00FF)
					pixels[xa + ya * this.width] = sprite.pixels[x + y * width];
			}
		}
	}
}

class EdgeN
{
	public int label1, label2;
	public double x1, x2, y1, y2;
	public double slope, sum;
	
	EdgeN(int l1, int l2, double x1, double y1, double x2, double y2)
	{
		label1 = l1;
		label2 = l2;
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		slope = (x2 - x1) / (y2 - y1);
	}
}

class EdgeBucket
{
	double yMin, yMax, xyMin, xyMax, slope, sum, xMin, x1, x2, y1, y2;
	Vector2i pos;
	int label;
	
	public EdgeBucket()
	{
		
	}
	
	public EdgeBucket(double x1, double y1, double x2, double y2, int label)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		if (y2 < y1)
		{
			this.yMin = y2;
			this.yMax = y1;
			this.xyMin = x2;
			this.xyMax = x1;
		}
		else
		{
			this.yMin = y1;
			this.yMax = y2;
			this.xyMin = x1;
			this.xyMax = x2;
		}
		/*this.yMin = (y1 <= y2) ? y1 : y2;
		this.yMax = (y1 > y2) ? y1 : y2;
		this.xMin = (x1 <= x2) ? x1 : x2;
		this.xyMin = (y1 <= y2) ? x1 : x2;
		this.xyMax = (y1 > y2) ? x1 : x2;*/
		this.slope = (double) (x2 - x1) / (y2 - y1);
		this.label = label;
		this.pos = new Vector2i(xyMin, yMin);
	}
	
	public EdgeBucket(double x1, double y1, double x2, double y2, int label, double zInv1, double zInv2)
	{
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		if (y2 < y1)
		{
			this.yMin = y2;
			this.yMax = y1;
			this.xyMin = x2;
			this.xyMax = x1;
		}
		else
		{
			this.yMin = y1;
			this.yMax = y2;
			this.xyMin = x1;
			this.xyMax = x2;
		}
		/*this.yMin = (y1 <= y2) ? y1 : y2;
		this.yMax = (y1 > y2) ? y1 : y2;
		this.xMin = (x1 <= x2) ? x1 : x2;
		this.xyMin = (y1 <= y2) ? x1 : x2;
		this.xyMax = (y1 > y2) ? x1 : x2;*/
		this.slope = (double) (x2 - x1) / (y2 - y1);
		this.label = label;
		this.pos = new Vector2i(xyMin, yMin);
	}
	
	public EdgeBucket clone()
	{
		EdgeBucket result = new EdgeBucket();
		result.yMin = this.yMin;
		result.yMax = this.yMax;
		result.xyMin = this.xyMin;
		result.slope = this.slope;
		result.sum = this.sum;
		result.label = this.label;
		if (this.pos != null)
			result.pos = this.pos.clone();
		result.x1 = x1;
		result.x2 = x2;
		result.y1 = y1;
		result.y2 = y2;
		
		return result;
	}
}


//This method doesn't work properly yet [IF YOU USE THIS AND ANY PROBLEM OCCURS, LOOK HERE FIRST]
/*public void drawPolygon(ArrayList<Vector2i> points, int col)
{
	ArrayList<EdgeBucket> edgeTable = new ArrayList<EdgeBucket>();
	Vector2i start = points.get(0);
	Vector2i end = points.get(points.size() - 1);
	edgeTable.add(new EdgeBucket((int) end.x, (int) end.y, (int) start.x, (int) start.y));
	for (int i = 0; i < points.size() - 1; i++)
	{
		Vector2i current = points.get(i);
		Vector2i next = points.get(i + 1);
		edgeTable.add(new EdgeBucket((int) current.x, (int) current.y, (int) next.x, (int) next.y));
	}
	int yMin = edgeTable.get(0).yMin;
	for (int i = 1; i < edgeTable.size(); i++) //GET MINIMUM Y
	{
		int curr = edgeTable.get(i).yMin;
		if (curr < yMin)
			yMin = curr;
	}
	ArrayList<EdgeBucket> activeTable = new ArrayList<EdgeBucket>();
	for (int y = yMin; edgeTable.size() > 0; y++)
	{
		for (int i = 0; i < edgeTable.size(); i++)
		{
			if (edgeTable.get(i).yMin == y)
			{
				activeTable.add(edgeTable.get(i));
			}
			if (edgeTable.get(i).yMax == y)
			{
				edgeTable.remove(i);
				i--;
			}
		}
		for (int i = 0; i < activeTable.size(); i++)
		{
			if (y == activeTable.get(i).yMax)
			{
				activeTable.remove(i);
			}
		}
		for (int i = activeTable.size() - 1; i >= 0; i--)
		{
			for (int j = 0; j < i; j++)
			{
				EdgeBucket curr = activeTable.get(j);
				EdgeBucket next = activeTable.get(j + 1);
				if ((curr.xyMin + curr.sum) > (next.xyMin + next.sum))
				{
					EdgeBucket temp = curr.clone();
					activeTable.set(j, next);
					activeTable.set(j + 1, temp);
				}
			}
		}
		for (int i = 0; i < activeTable.size() - 1; i += 2)
		{
			EdgeBucket curr = activeTable.get(i);
			EdgeBucket next = activeTable.get(i + 1);

			for (int x = (int) (curr.xyMin + curr.sum); x < (int) (next.xyMin + next.sum); x++)
			{
				if (x >= 0 && x < width && y >= 0 && y < height)
					pixels[x + y * width] = col;
			}
			if (Double.isFinite(curr.slope))
			{
				curr.sum += curr.slope;
			}
			if (Double.isFinite(next.slope))
			{
				next.sum += next.slope;
			}
		}

	}
}*/
