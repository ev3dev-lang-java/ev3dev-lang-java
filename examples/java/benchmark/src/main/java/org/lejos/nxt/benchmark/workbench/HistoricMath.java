package org.lejos.nxt.benchmark.workbench;

public class HistoricMath
{
	private static final double PI = 3.14159265358979323846264338328;
	private static final double PIhalf = PI * 0.5;
	private static final double PItwice = PI * 2.0;
	
	private static final double LN2 = 0.693147180559945;
	
	private static final double LOG_LOWER_BOUND = 0.9999999f;
	private static final double LOG_UPPER_BOUND = 1.0D;
	private static final double EXP_REL_BOUND = 0x1.0p-52;
		
	/**
	 * Square root
	 * @author Paulo Costa
	 */
	public static double sqrtSimple(double x)
	{
		double root = x, guess = 0;

		if (x < 0)
			return Double.NaN;

		// the accuracy test is percentual
		for (int i = 0; (i < 22) && ((guess > x * (1 + 5e-7f)) || (guess < x * (1 - 5e-7f))); i++)
		{
			root = (root + x / root) * 0.5f; // a multiplication is faster than a division
			guess = root * root; // cache the square to the test
		}
		return root;
	}

	// constants for approximating sqrt
	private static final int SQRT_APPROX_EVEN_MULT = 1756;
	private static final int SQRT_APPROX_EVEN_SHIFT = 12;
	private static final int SQRT_APPROX_ODD_MULT = 1170;
	private static final int SQRT_APPROX_ODD_SHIFT = 11;
	private static final long SQRT_APPROX_ODD_ADD = (1L << 52) - ((long)SQRT_APPROX_ODD_MULT << (52 - SQRT_APPROX_ODD_SHIFT));

	/**
	 * Linear approximation of sqrt.
	 * The maximum relative error is about 1/2^6.6.
	 */
	private static double sqrtLinearApprox(double x)
	{
		// @author Sven Köhler
		 
		//don't call this function for zero, infinity or NaN
		//assert (x > 0);
		//assert !Double.isNaN(x);
		//assert !Double.isInfinite(x);
		
		/**
		 * The following is done here:
		 * Imagine a floating point number f with 1 <= f < 2. This can be written as
		 * f = 1 + m. So basically, m is the mantissa of f. Since the sqrt(x) is almost
		 * linear in the interval x=0..1, the term sqrt(f) can be approximated linearly.
		 * This is done by chosing some c to that the function 1+m*c generates minimal
		 * relative errors for all m values in the interval [0,1]. c=1756/4096 seems to
		 * be optimal.
		 * Now imagine a floating point f with 2 <= f < 4. This can be written as
		 * f = 2 + 2*m. Again, m is the manitssa. Since sqrt(x) is almost linear
		 * in the interval x=2..4, the term sqrt(f) can be approximated linearly
		 * again. This time, I have chosen the function 1 + (m*c - c + 1) to
		 * approximate sqrt(f). For m=1 (that means f=4), this function will
		 * always return 2. Again, a value for c has been found, that produces
		 * minimal relative errors for all m in the interval [0,1]. The value
		 * c = 1170/2048 seems to be optimal.   
		 */
		
		/**
		 * In the above text, I describe the handling of numbers with the
		 * exponent 0 (1 <= f < 2) and number with with the exponent 1
		 * (that means 2 <= f < 4). The exponent can be handled separatly.
		 * It's simply deviced by two which is possible since this routine
		 * handles both the odd and even exponent case. 
		 */
		
		//extract exponent (sign bit is zero)
		long bits = Double.doubleToRawLongBits(x);
		int exp = (int)(bits >> 52);
		
		//zero indicates a denormalized value
		if (exp == 0)
		{
			//normalize the value again
			int zeros = Long.numberOfLeadingZeros(bits);
			//calculate normalized exponent
			exp = 12 - zeros;			
			//shift bits for correct mantissa extraction
			bits <<= zeros - 11;
		}
		
		//extract mantissa
		long man = bits & 0x000FFFFFFFFFFFFFL;			
		
		//calculate new exponent (biased by 1023)
		int newexp = ((exp + 1) >> 1) + 511;
		
		//check whether exponent iss odd (note the bias 1023)
		if ((exp & 1) == 0)
			//calculate new mantisssa for odd exponents
			man = ((man * SQRT_APPROX_ODD_MULT) >> SQRT_APPROX_ODD_SHIFT) + SQRT_APPROX_ODD_ADD;
		else
			//calculate new mantisssa for even exponents
			man = (man * SQRT_APPROX_EVEN_MULT) >> SQRT_APPROX_EVEN_SHIFT;
		
		//if this fails, sub 1 from SQRT_APPROX_ODD_ADD
		//assert man < (1L << 52);
		
		//return calculated number
		return Double.longBitsToDouble(((long)newexp << 52) | man);
	}
	
	/**
	 * Square root.
	 */
	public static double sqrtLinear(double x) {
		// @author Sven Köhler
		
		if (x == 0)
			return 0;
		if (x < 0 || Double.isNaN(x))
			return Double.NaN;
		if (x == Double.POSITIVE_INFINITY)
			return Double.POSITIVE_INFINITY;
		
		double root = sqrtLinearApprox(x);
		double root2;
		
		/**
		 * 1 newton step:
		 * root = 0.5 * (root + x / root)
		 */

		/**
		 * 2 newton steps:
		 * root = root + x/root;
		 * root = 0.25 * root + x/root; 
		 */
		
		// 2 newton steps (short form, with test)
		root2 = x/root;
		if (root == root2)
			return root;
		root = root + root2;
		root = 0.25 * root + x/root;
				
		// 1 newton step (without check)
		root2 = x/root;
		root = 0.5 * (root + root2);
				
		return root;
	}

	/**
	 * Natural log function. Returns log(a) to base E Replaced with an algorithm
	 * that does not use exponents and so works with large arguments.
	 * 
	 * @see <a
	 *      href="http://www.geocities.com/zabrodskyvlada/aat/a_contents.html">here</a>
	 */
	public static double logSimple(double x)
	{
		if (x == 0)
			return Double.NaN;

		if (x < 1.0)
			return -logSimple(1.0 / x);

		double m = 0.0;
		double p = 1.0;
		while (p <= x)
		{
			m++;
			p = p * 2;
		}

		m = m - 1;
		double z = x / (p / 2);

		double zeta = (1.0 - z) / (1.0 + z);
		double n = zeta;
		double ln = zeta;
		double zetasup = zeta * zeta;

		for (int j = 1; true; j++)
		{
			n = n * zetasup;
			double newln = ln + n / (2 * j + 1);
			double term = ln / newln;
			if (ln == newln || (term >= LOG_LOWER_BOUND && term <= LOG_UPPER_BOUND))
				return m * LN2 - 2 * ln;
			ln = newln;
		}
	}
	
	/**
	 * Exponential function. Returns E^x (where E is the base of natural
	 * logarithms). author David Edwards
	 * 
	 */
	public static double expSimple(double a)
	{
		/**
		 * DEVELOPER NOTES: Martin E. Nielsen - modified code to handle large
		 * arguments. = sum a^n/n!, i.e. 1 + x + x^2/2! + x^3/3! Seems to work
		 * better for +ve numbers so force argument to be +ve.
		 */

		boolean neg = a < 0;
		if (neg)
			a = -a;
		
		double term = a;
		double sum = 1;

		for (int fac = 2; true; fac++)
		{
			if (term < sum * EXP_REL_BOUND)
				break;
			
			sum += term;
			term *= a / fac;
		}

		return neg ? 1.0 / sum : sum;
	}

	/**
	 * Sine function using a Chebyshev-Pade approximation. Author Paulo Costa.
	 */
	public static double sinChebyPade(double x)
	{
		int neg = 0;
		
		//reduce to interval [-2PI, +2PI]
		x = x % PItwice;
		
		//reduce to interval [0, 2PI]
		if (x < 0)
		{
			neg++;
			x = -x;
		}
		
		//reduce to interval [0, PI]
		if (x > PI)
		{
			neg++;
			x -= PI;
		}
		
		//reduce to interval [0, PI/2]
		if (x > PIhalf)
			x = PI - x;	
		
		// Using a Chebyshev-Pade approximation
		double x2 = x * x;
		double y = (0.9238318854 - 0.9595498071e-1 * x2) * x
				/ (0.9238400690 + (0.5797298195e-1 + 0.2031791179e-2 * x2) * x2);
		
		return ((neg & 1) == 0) ? y : -y;
	}

	/**
	 * Cosine function using a Chebyshev-Pade approximation. Author Paulo Costa.
	 */
	public static double cosChebyPade(double x)
	{
		int neg = 0;
		
		//reduce to interval [-2PI, +2PI]
		x = x % PItwice;
		
		//reduce to interval [0, 2PI]
		if (x < 0)
			x = -x;
		
		//reduce to interval [0, PI]
		if (x > PI)
		{
			neg++;
			x -= PI;
		}
		
		//reduce to interval [0, PI/2]
		if (x > PIhalf)
		{
			neg++;
			x = PI - x;
		}
		
		// Using a Chebyshev-Pade approximation
		double x2 = x * x;
		double y = (0.9457092528 + (-0.4305320537 + 0.1914993010e-1 * x2) * x2)
				/ (0.9457093212 + (0.4232119630e-1 + 0.9106317690e-3 * x2) * x2);
		
		return ((neg & 1) == 0) ? y : -y;
	}

	/**
	 * Tangent function.
	 */
	public static double tanSimple(double a)
	{
		return sinChebyPade(a) / cosChebyPade(a);
	}

	/**
	 * Exponential function.
	 * Returns E^x (where E is the base of natural logarithms).
	 */
	public static double expTaylor(double x)
	{
		// also catches NaN
		if (!(x > -750))
			return (x < 0) ? 0 : Double.NaN;
		if (x > 710)
			return Double.POSITIVE_INFINITY;

		int k = (int)(x / LN2);
		if (x < 0)
			k--;
		x -= k * LN2;
		
		//known ranges:
		//	0 <= $x < LN2
		//ergo:
		//  $xpow will converge quickly towards 0

		double sum = 1;
		double xpow = x;
		int fac = 2;

		while (true)
		{
			if (xpow < 0x1p-52)
				break;
			
			sum += xpow;
			xpow = xpow * x / fac++;
		}
		
		double f1;
		if (k > 1000)
		{
			k -= 1000;
			f1 = 0x1p+1000; 
		}
		else if (k < -1000)
		{
			k += 1000;
			f1 = 0x1p-1000; 
		}
		else
			f1 = 1.0;
		
		double f2 = Double.longBitsToDouble((long)(k+1023) << 52);
		return sum * f2 * f1;
	}

	/**
	 * Natural log function. Returns log(x) to base E.
	 */
	public static double logZeta(double x)
	{
		// also catches NaN
		if (!(x > 0))
			return (x == 0) ? Double.NEGATIVE_INFINITY : Double.NaN;
		if (x == Double.POSITIVE_INFINITY)
			return Double.POSITIVE_INFINITY;
	
		// Algorithm has been derived from the one given at
		// http://www.geocities.com/zabrodskyvlada/aat/a_contents.html 

		int m;
		if (x >= Double.MIN_NORMAL)
			m = -1023;
		else
		{
			m = -1023-64;
			x *= 0x1p64;
		}
	
		//extract mantissa and reset exponent
		long bits = Double.doubleToRawLongBits(x);
		m += (int)(bits >>> 52);
		bits = (bits & 0x000FFFFFFFFFFFFFL) + 0x3FF0000000000000L;
		x = Double.longBitsToDouble(bits);
		
		double zeta = (x - 1.0) / (x + 1.0);
		double zeta2 = zeta * zeta;		
		
		//known ranges:
		//	1 <= $x < 2
		//  0 <= $zeta < 1/3
		//  0 <= $zeta2 < 1/9
		//ergo:
		//  $zetapow will converge quickly towards 0
		
		double zetapow = zeta2;
		double r = 1;
		int i = 3;
	
		while(true)
		{
			double tmp = zetapow / i;
			if (tmp < 0x1p-52)
				break;
			
			i += 2;
			r += tmp;
			zetapow *= zeta2;
		}
		
		return m * LN2 + 2 * zeta * r;
	}

	
	// Private because it only works when -1 < x < 1 but it is used by atan2
	private static double arctanChebyPade(double x)
	{
		// Using a Chebyshev-Pade approximation
		double x2 = x * x;
		return (0.7162721433f + 0.2996857769f * x2) * x
				/ (0.7163164576f + (0.5377299313f + 0.3951620469e-1f * x2) * x2);
	}
	
	/**
	 * Arc tangent function.
	 */
	public static double atanChebyPade(double x)
	{
		return atan2ChebyPade(x, 1);
	}

	/**
	 * Arc tangent function valid to the four quadrants y and x can have any
	 * value without sigificant precision loss atan2(0,0) returns 0. author
	 * Paulo Costa
	 */
	public static double atan2ChebyPade(double y, double x)
	{
		float ax = (float) Math.abs(x);
		float ay = (float) Math.abs(y);

		if ((ax < 1e-7) && (ay < 1e-7))
			return 0f;

		if (ax > ay)
		{
			if (x < 0)
			{
				if (y >= 0)
					return arctanChebyPade(y / x) + PI;
				else
					return arctanChebyPade(y / x) - PI;
			}
			else
				return arctanChebyPade(y / x);
		}
		else
		{
			if (y < 0)
				return arctanChebyPade(-x / y) - PI / 2;
			else
				return arctanChebyPade(-x / y) + PI / 2;
		}
	}

}
