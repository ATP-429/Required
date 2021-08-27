package Utility;

public class Array
{
	public static double[] toDoubleArray(int[] array)
	{
		double[] result = new double[array.length];
		for (int i = 0; i < array.length; i++)
		{
			result[i] = array[i];
		}
		return result;
	}
	
	public static void selectionSort(double[] array)
	{
		for (int i = 0; i < array.length - 1; i++)
		{
			int minIndex = i;
			for (int j = i + 1; j < array.length; j++)
			{
				if (array[minIndex] > array[j])
				{
					minIndex = j;
				}
			}
			swap(array, i, minIndex);
		}
	}
	
	public static void swap(double[] array, int i1, int i2)
	{
		double temp = array[i1];
		array[i1] = array[i2];
		array[i2] = temp;
	}
	
	public static void swap(Object[] array, int i1, int i2)
	{
		Object temp = array[i1];
		array[i1] = array[i2];
		array[i2] = temp;
	}
	
	public static void println(int[] array)
	{
		System.out.print("[");
		for(int i = 0; i < array.length-1; i++)
		{
			System.out.print(array[i] + ", ");
		}
		System.out.println(array[array.length-1] + "]");
	}
}
