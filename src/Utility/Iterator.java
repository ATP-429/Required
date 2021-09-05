package Utility;

public class Iterator
{
	public final ITR_TYPE TYPE;
	
	public int[] indices;
	public int[] lim; //Tells us the limit at every index of the iterator
	
	public Iterator(int size, int lim, ITR_TYPE type)
	{
		this(size, arr(lim, size), type);
	}
	
	public Iterator(int size, int[] lim, ITR_TYPE TYPE)
	{
		indices = new int[size];
		
		if (TYPE == ITR_TYPE.ASCENDING)
			for (int i = 0; i < indices.length; i++)
				indices[i] = i;
		this.lim = lim.clone();
		this.TYPE = TYPE;
	}
	
	private static int[] arr(int num, int size)
	{
		int[] arr = new int[size];
		for (int i = 0; i < size; i++)
			arr[i] = num;
		return arr;
	}
	
	public void reset()
	{
		indices = new int[indices.length];
		if (TYPE == ITR_TYPE.ASCENDING)
			for (int i = 0; i < indices.length; i++)
				indices[i] = i;
	}
	
	public boolean next() //returns false if iteration is over
	{
		if (indices.length == 0)
			return false;
		indices[indices.length - 1]++;
		int j = indices.length - 1;
		
		if (TYPE == ITR_TYPE.NORMAL)
		{
			while (indices[j] > lim[j] && j > 0)
			{
				indices[j] = 0;
				indices[j - 1]++;
				j--;
			}
			if (indices[0] > lim[0])
				return false;
		}
		else if (TYPE == ITR_TYPE.ASCENDING)
		{
			while (indices[j] > lim[j] - (indices.length - j - 1) && j > 0)
			{
				indices[j - 1]++;
				j--;
			}
			if (indices[0] > lim[0] - (indices.length - 1))
				return false;
			while (j < indices.length - 1)
			{
				indices[j + 1] = indices[j] + 1;
				j++;
			}
		}
		
		return true;
	}
	
	public void print()
	{
		for (int i = 0; i < indices.length; i++)
		{
			System.out.print(indices[i] + " ");
		}
		System.out.println();
	}
	
	public int get(int index)
	{
		return indices[index];
	}
	
	@Override
	public String toString()
	{
		String str = "[";
		for (int i = 0; i < indices.length - 1; i++)
		{
			str += indices[i] + ", ";
		}
		if (indices.length > 0)
			str += indices[indices.length - 1];
		str += "]";
		return str;
	}
}
