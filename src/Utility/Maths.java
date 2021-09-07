package Utility;

import java.util.ArrayList;

public class Maths
{
	//PRIME Functions:
	
	//Generates prime numbers that are less than or equal to n
	public static ArrayList<Long> genPrimes(long n)
	{
		ArrayList<Long> result = new ArrayList<Long>();
		if (n >= 2)
			result.add(2L);
		else
			return result;
		for (long k = 3L; k <= n; k += 2)
		{
			if (isPrime(k))
			{
				result.add(k);
			}
		}
		return result;
	}
	
	public static ArrayList<Integer> genPrimes(int n)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		if (n >= 2)
			result.add(2);
		else
			return result;
		for (int k = 3; k <= n; k += 2)
		{
			if (isPrime(k))
			{
				result.add(k);
			}
		}
		return result;
	}
	
	//Eratosthenes sieve
	public static ArrayList<Integer> sieveGenPrimes(int n)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		boolean[] nums = new boolean[n + 1];
		for (int i = 2; i <= n; i++)
		{
			if (!nums[i])
				result.add(i);
			for (int j = i; j <= n; j += i)
				nums[j] = true;
		}
		return result;
	}
	
	//Generates n prime numbers. Example: if n = 5, we'll return [2,3,5,7,11] NOT [2,3,5]
	public static ArrayList<Long> genNPrimes(int n)
	{
		ArrayList<Long> result = new ArrayList<Long>();
		int count = 0;
		for (Long k = 2L;; k++)
		{
			if (isPrime(k))
			{
				result.add(k);
				count++;
			}
			if (count == n)
				break;
		}
		return result;
	}
	
	//This isPrime method is stolen from Euler007's overview
	public static boolean isPrime(long n)
	{
		if (n == 0)
			return false;
		else if (n == 1)
			return false;
		else if (n < 4)
			return true;
		else if (n % 2 == 0)
			return false;
		else if (n < 9)
			return true;
		else if (n % 3 == 0)
			return false;
		
		long r = (long) Math.sqrt(n);
		for (long i = 5; i <= r; i += 6)
		{
			if (n % i == 0)
				return false;
			if (n % (i + 2) == 0)
				return false;
		}
		return true;
	}
	
	public static long getNextPrime(long n)
	{
		while (true)
		{
			n++;
			if (Maths.isPrime(n))
				return n;
		}
	}
	
	//Returns all factors of n, not necessarily in ascending order
	public static ArrayList<Long> getFactors(long n)
	{
		ArrayList<Long> factors = new ArrayList<Long>();
		long sqrt = (long) Math.sqrt(n);
		for (long i = 1; i <= sqrt; i++)
		{
			if (n % i == 0)
			{
				if (i == n / i)
				{
					factors.add(i);
				}
				else
				{
					factors.add(i);
					factors.add(n / i);
				}
			}
		}
		
		return factors;
	}
	
	//Returns all factors of n, not necessarily in order, given the prime factorization of n
	public static ArrayList<Long> getFactors(ArrayList<Integer> pf)
	{
		ArrayList<Long> factors = new ArrayList<Long>();
		int[] pows = new int[pf.size() / 2]; //Stores the powers of the prime
		for (int i = 1; i < pf.size(); i += 2)
			pows[i / 2] = pf.get(i);
		Iterator itr = new Iterator(pf.size() / 2, pows, ITR_TYPE.NORMAL);
		
		do
		{
			long num = 1;
			
			//DONT CHANGE OUR pow function TO (long)(Math.pow(...)) because while trying to check if there was any errors in double rounding, by doing 100% integer calculations,
			//that is, calculating the values in long instead, I found out that calculating it in long is just faster than the Math function somehow.
			//We found this out while doing Euler 198.
			//If you use the second statement (now commented out) instead of the first one, it takes about 24s to get the answer in Euler 198.
			//However, if you use the first one, it only takes around 11s.
			for (int i = 0; i < itr.indices.length; i++)
			{
				num *= pow(pf.get(i * 2), itr.indices[i]);
				//num *= Math.pow(pf.get(i * 2), itr.indices[i]);
			}
			factors.add(num);
		}
		while (itr.next());
		
		return factors;
	}
	
	private static long pow(long n, int pow)
	{
		long result = 1;
		for (int i = 0; i < pow; i++)
			result *= n;
		return result;
	}
	
	//Returns prime factorization of a number given that you have produced an array of primes with appropriate limit
	public static ArrayList<Integer> getPrimeFactorization(int n, ArrayList<Integer> primes)
	{
		ArrayList<Integer> pf = new ArrayList<Integer>();
		int m = 0;
		while (n != 1)
		{
			int k = 0;
			if (m < primes.size() && n % primes.get(m) == 0)
			{
				pf.add(primes.get(m));
				while (n % primes.get(m) == 0)
				{
					k++;
					n /= primes.get(m);
				}
				pf.add(k);
			}
			m++;
			if (m >= primes.size() && n != 1)
			{
				pf.add(n);
				pf.add(1);
				break;
			}
		}
		return pf;
	}
	
	//Returns prime factorization of the product of the prime factorizations (Basically sums up the exponents of primes that are common and adds any uncommon primes)  
	public static ArrayList<Integer> multiplyPrimeFactorizations(ArrayList<Integer> pf1, ArrayList<Integer> pf2)
	{
		ArrayList<Integer> pf = new ArrayList<Integer>();
		int i = 0, j = 0;
		while (i < pf1.size() || j < pf2.size())
		{
			if (i < pf1.size() && j < pf2.size())
			{
				if (pf1.get(i) < pf2.get(j)) //This means prime at pf1 is less than prime at pf2, so we add that one to pf first
				{
					pf.add(pf1.get(i));
					pf.add(pf1.get(i + 1));
					i += 2;
				}
				else if (pf1.get(i) > pf2.get(j)) //This means prime at pf2 is less than prime at pf1, so we add that one to pf first
				{
					pf.add(pf2.get(j));
					pf.add(pf2.get(j + 1));
					j += 2;
				}
				else //Primes are equal, so we sum their values up
				{
					pf.add(pf1.get(i));
					pf.add(pf1.get(i + 1) + pf2.get(j + 1));
					i += 2;
					j += 2;
				}
			}
			else if (i < pf1.size()) //If only i is in bounds, add prime at pf1
			{
				pf.add(pf1.get(i));
				pf.add(pf1.get(i + 1));
				i += 2;
			}
			else //If only j is in bounds, add prime at pf2
			{
				pf.add(pf2.get(j));
				pf.add(pf2.get(j + 1));
				j += 2;
			}
		}
		
		return pf;
	}
	
	public static ArrayList<Integer> getPrimeFactorization(int n)
	{
		return getPrimeFactorization(n, Maths.sieveGenPrimes((int) Math.sqrt(n)));
	}
	
	public static int phi(int n)
	{
		ArrayList<Integer> pf = getPrimeFactorization(n);
		int result = 1;
		for (int i = 0; i < pf.size(); i += 2)
		{
			result *= Math.pow(pf.get(i), pf.get(i + 1) - 1);
			result *= pf.get(i) - 1;
		}
		
		return result;
	}
	
	//Returns the number of factors of n
	
	public static long countFactors(long n, ArrayList<Integer> primes)
	{
		long ans = 1;
		int m = 0;
		while (n != 1)
		{
			int k = 0;
			if (n % primes.get(m) == 0)
			{
				while (n % primes.get(m) == 0)
				{
					k++;
					n /= primes.get(m);
				}
				ans *= (k + 1);
			}
			m++;
			if (m >= primes.size() && n != 1)
			{
				ans *= 2; //If we get here, it means there's only one prime number remaining, n itself, so we will just multiply ans by 2 since pow of prime factor = 1
				break;
			}
		}
		
		return ans;
	}
	
	public static long countFactors(long n)
	{
		return countFactors(n, Maths.sieveGenPrimes((int) (Math.sqrt(n))));
	}
	
	/* This coprime checker uses the Euclidean algorithm. I learnt about this algorithm on 25/8/20 at 3:26 AM while solving Euler 127
	 * AND IT IS SO FUCKING GENIUS HOLY FUCK. OMFG IT IS ABSOLUTELY BRILLIANT. I CANNOT FUCKING USE ANY MORE WORDS TO EXPRESS MYSELF 
	 * BETTER THAN THIS IT IS JUST MOTHERFUCKING INSANE. FUCK! I LOVE MATHS
	 */
	public static boolean areCoPrime(long a, long b)
	{
		return (getGCD(a, b) == 1);
	}
	
	//This function was written after the above areCoPrime function.
	//I modified the function after I came across this question on codechef : https://www.codechef.com/problems/GCD2
	//Before, I had a while loop and I just kept subtracting the numbers from each other. Then after this question, before reading the algorithm Felman (made-up guy in the problem) implemented in that question,
	//I myself realized that if b was of the order 10^250, and a was 40000, we could simply find the b % a instead of having to keep subtracting a from b.
	//Then I looked at the algorithm Felman had implemented with recursion, and simply copied it in the code here.
	//Modified further after TSEC Maam Sunita Rathod told me to analyze her GCD function. Same logic but less code
	public static long getGCD(long a, long b)
	{
		a = Math.abs(a);
		b = Math.abs(b);
		if (b == 0)
			return a;
		else
			return getGCD(b, a % b);
		/*if (a == 0 || b == 0)
			return ((a == 0) ? b : a); //GCD(0,n) = n. If a is 0, return b. If b is 0, return a
		a = Math.abs(a);
		b = Math.abs(b);
		if (a > b)
			return getGCD(a % b, b);
		else
			return getGCD(a, b % a);*/
	}
	
	//GEOMETRICAL Functions:
	
	public static int getOrientation(Vector2i a, Vector2i b, Vector2i c)
	{
		double x1 = a.x, x2 = b.x, x3 = c.x;
		double y1 = a.y, y2 = b.y, y3 = c.y;
		/*double s1 = (y2-y1) / (x2-x1);
		double s2 = (y3-y2) / (x3-x2);*/
		double val = (y2 - y1) * (x3 - x2) - (y3 - y2) * (x2 - x1); //val = (s1-s2)*(x2-x1)(x3-x2);
		if (val == 0)
			return 0;
		else if (val > 0)
			return -1;
		else
			return 1;
		/*if (s1 > s2)
			return -1; //Clockwise
		else if (s1 < s2)
			return 1; //Anti-Clockwise
		else
			return 0; //Collinear	
		*/
	}
	
	public static boolean areIntersecting(Vector2i a, Vector2i b, Vector2i p, Vector2i q)
	{
		if (getOrientation(p, q, a) != getOrientation(p, q, b) && getOrientation(a, b, p) != getOrientation(a, b, q))
			return true;
		return false;
	}
	
	//ARRAY Functions:
	
	public static void sort(int[] nums)
	{
		for (int i = nums.length - 1; i >= 0; i--)
		{
			for (int j = 0; j < i; j++)
			{
				if (nums[j] > nums[j + 1])
				{
					int temp = nums[j];
					nums[j] = nums[j + 1];
					nums[j + 1] = temp;
				}
			}
		}
	}
	
	public static void sort(long[] nums)
	{
		for (int i = nums.length - 1; i >= 0; i--)
		{
			for (int j = 0; j < i; j++)
			{
				if (nums[j] > nums[j + 1])
				{
					long temp = nums[j];
					nums[j] = nums[j + 1];
					nums[j + 1] = temp;
				}
			}
		}
	}
	
	public static void sort(ArrayList<Integer> nums)
	{
		for (int i = nums.size() - 1; i >= 0; i--)
		{
			for (int j = 0; j < i; j++)
			{
				if (nums.get(j) > nums.get(j + 1))
				{
					int temp = nums.get(j);
					nums.set(j, nums.get(j + 1));
					nums.set(j + 1, temp);
				}
			}
		}
	}
	
	public static void sortLongs(ArrayList<Long> nums)
	{
		for (int i = nums.size() - 1; i >= 0; i--)
		{
			for (int j = 0; j < i; j++)
			{
				if (nums.get(j) > nums.get(j + 1))
				{
					long temp = nums.get(j);
					nums.set(j, nums.get(j + 1));
					nums.set(j + 1, temp);
				}
			}
		}
	}
	
	public static void sort(char[] array) //Sorts the characters by their ASCII values
	{
		for (int i = array.length - 1; i >= 0; i--)
		{
			for (int j = 0; j < i; j++)
			{
				if (array[j] > array[j + 1])
				{
					char temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
	}
	
	//Probability functions:
	public static boolean simProb(double prob)
	{
		if (prob < Math.random())
		{
			return true;
		}
		return false;
	}
	
	//Normal Variable functions:
	
	public static double lerp(double a, double b, double ratio)
	{
		return ((b - a) * ratio + a);
	}
	
	public static Vector2i lerp(Vector2i a, Vector2i b, double ratio)
	{
		Vector2i result = b.clone();
		result = result.subtract(a);
		result = result.multiply(ratio);
		result = result.add(a);
		return result;
	}
	
	public static Vector3i lerp(Vector3i a, Vector3i b, double ratio)
	{
		Vector3i result = b.clone();
		result = result.subtract(a);
		result = result.multiply(ratio);
		result = result.add(a);
		return result;
	}
	
	public static long factorial(long n)
	{
		long product = 1;
		for (long i = 2; i <= n; i++)
		{
			product *= i;
		}
		return product;
	}
	
	public static long choose(long n, long r)
	{
		/* nCr = n!/(r!(n-r)!)
		 * The way we will calculate this is by finding out the which one of r and n-r has the smallest factorial, because factorials 
		 * are really large and we want to avoid them. The numerator can then be calculated by finding product of remaining numbers
		 * This is just like how we would find the answer in real life. For example, to find 10C3, using our algorithm, we would see
		 * 3 is less than 10-3. So denominator would be 3!. For the numerator, we simply multiply all numbers from 8 to 10.
		 * So, 10C3 = 8*9*10/3!
		 */
		if (r > n) //There's no way to choose r objects from n if r is greater than n
			return 0;
		long denom = factorial(Math.min(n - r, r));
		long num = 1;
		long i = Math.max(n - r + 1, r + 1);
		while (i <= n)
		{
			num *= i;
			i++;
		}
		return num / denom;
	}
	
	public static double sigmoid(double val)
	{
		return 1.0 / (1 + Math.exp(-val));
	}
}
