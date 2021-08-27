package Utility;

public class Matrix
{
	public static Matrix UNIT_MATRIX_TWO = new Matrix(new double[] {1, 0, 0, 1}, 2, 2);
	
	public double[] data;
	public int rows, cols;
	
	public Matrix()
	{
		
	}
	
	public Matrix(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.data = new double[rows * cols];
	}
	
	public Matrix(double[] array, int rows)
	{
		data = array.clone();
		this.rows = rows;
		this.cols = data.length / rows;
	}
	
	public Matrix(double[] array, int rows, int cols)
	{
		data = array.clone();
		this.rows = rows;
		this.cols = cols;
	}
	
	public Matrix multiplyElementWise(Matrix m)
	{
		Matrix result = new Matrix(this.rows, this.cols);
		for (int i = 0; i < m.rows * m.cols; i++)
		{
			result.setElementAtIndex(i, getElementAtIndex(i) * m.getElementAtIndex(i));
		}
		return result;
	}
	
	public Matrix multiply(Matrix m)
	{
		Matrix result = new Matrix(this.rows, m.cols);
		for (int c = 0; c < result.cols; c++)
		{
			for (int r = 0; r < result.rows; r++)
			{
				for (int c2 = 0; c2 < cols; c2++)
				{
					result.setElement(r, c, result.getElement(r, c) + getElement(r, c2) * m.getElement(c2, c));
				}
			}
		}
		return result;
	}
	
	public Matrix multiply(double s)
	{
		Matrix result = new Matrix(rows, cols);
		for (int i = 0; i < data.length; i++)
		{
			result.data[i] = data[i] * s;
		}
		return result;
	}
	
	public Matrix add(Matrix m)
	{
		Matrix result = new Matrix(rows, cols);
		for (int i = 0; i < m.cols * m.rows; i++)
		{
			result.setElementAtIndex(i, getElementAtIndex(i) + m.getElementAtIndex(i));
		}
		return result;
	}
	
	public Matrix subtract(Matrix m)
	{
		Matrix result = new Matrix(rows, cols);
		for (int i = 0; i < m.cols * m.rows; i++)
		{
			result.setElementAtIndex(i, this.getElementAtIndex(i) - m.getElementAtIndex(i));
		}
		return result;
	}
	
	public Matrix transpose()
	{
		Matrix result = new Matrix(this.cols, this.rows);
		for (int c = 0; c < cols; c++)
		{
			for (int r = 0; r < rows; r++)
			{
				result.setElement(c, r, getElement(r, c));
			}
		}
		return result;
	}
	
	public double getElement(int r, int c)
	{
		return data[c + r * cols];
	}
	
	public double getElementAtIndex(int i)
	{
		return data[i];
	}
	
	public void setElement(int r, int c, double val)
	{
		data[c + r * cols] = val;
	}
	
	public void setElementAtIndex(int i, double val)
	{
		data[i] = val;
	}
	
	public Matrix clone()
	{
		Matrix result = new Matrix(this.rows, this.cols);
		result.data = this.data.clone();
		return result;
	}
}
