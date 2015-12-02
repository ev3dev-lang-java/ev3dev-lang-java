package org.lejos.nxt.benchmark.workbench;

public class HistoricStringUtils
{
	private static final double LN10 = 2.30258509299404568401799145468;
	
	private static final int STR_NAN_LEN = 3;
	private static final String STR_NAN = "NaN";
	private static final int STR_INFINITY_LEN = 8;
	private static final String STR_INFINITY = "Infinity";
		

	static final int MAX_FLOAT_CHARS = 32;
	
	static int getFloatChars(float number, char[] buf, int charPos)
	{
		return getDoubleChars(number, buf, charPos, 8);
	}

	static final int MAX_DOUBLE_CHARS = 32;
	
	static int getDoubleChars(double number, char[] buf, int charPos)
	{
		return getDoubleChars(number, buf, charPos, 17);
	}

    /**
     * Helper method for converting floats and doubles.
     *
     * @author Martin E. Nielsen
     * @author Sven Köhler
     **/
	private static int getDoubleChars(double number, char[] buf, int charPos, int significantDigits)
	{
		if (number != number)
		{
			STR_NAN.getChars(0, STR_NAN_LEN, buf, charPos);
			return charPos + STR_NAN_LEN;
		}
		
		//we need to detect -0.0 to be compatible with JDK
		boolean negative = (Double.doubleToRawLongBits(number) & 0x8000000000000000L) != 0;
		if (negative)
		{
			buf[ charPos++ ] = '-';
			number = -number;
		}
		
		if ( number == Double.POSITIVE_INFINITY)
		{
			STR_INFINITY.getChars(0, STR_INFINITY_LEN, buf, charPos);
			return charPos + STR_INFINITY_LEN;
		}
		
		if ( number == 0 ) {
			buf[ charPos++ ] = '0';
			buf[ charPos++ ] = '.';
			buf[ charPos++ ] = '0';
		} else {
			int exponent = 0;
			
			// calc. the power (base 10) for the given number:
			int pow = ( int )Math.floor( Math.log( number ) / LN10 );
	
			// use exponential formatting if number too big or too small
			if ( pow < -3 || pow > 6 ) {
				exponent = pow;
				number /= Math.exp( LN10 * exponent );
			} // if
	
			// Recalc. the pow if exponent removed and d has changed
			pow = ( int )Math.floor( Math.log( number ) / LN10 );
	
			// Decide how many insignificant zeros there will be in the
			// lead of the number.
			int insignificantDigits = -Math.min( 0, pow );
	
			// Force it to start with at least "0." if necessarry
			pow = Math.max( 0, pow );
				double divisor = Math.pow(10, pow);
	
			// Loop over the significant digits (17 for double, 8 for float)
			for ( int i = 0, end = significantDigits+insignificantDigits, div; i < end; i++  ) {
	
				// Add the '.' when passing from 10^0 to 10^-1
				if ( pow == -1 ) {
					buf[ charPos++ ] = '.';
				} // if
	
				// Find the divisor
				div = ( int ) ( number / divisor );
				// This might happen with 1e6: pow = 5 ( instead of 6 )
				if ( div == 10 ) {
					buf[ charPos++ ] = '1';
					buf[ charPos++ ] = '0';
				} // if
				else {
					buf[ charPos ] = (char)(div + '0');
					charPos++;
				} // else
	
				number -= div * divisor;
				divisor /= 10.0;
				pow--;
	
				// Break the loop if we have passed the '.'
				if ( number == 0 && divisor < 0.1 ) break;
			} // for
	
			// Remove trailing zeros
			while ( buf[ charPos-1 ] == '0' )
				charPos--;
	
			// Avoid "4." instead of "4.0"
			if ( buf[ charPos-1 ] == '.' )
				charPos++;
			if ( exponent != 0 ) {
				buf[ charPos++ ] = 'E';				
				if (exponent < 0)
				{
					buf[charPos++] = '-';
					exponent = -exponent;
				}			
				if (exponent >= 100)
					buf[charPos++] = (char)(exponent / 100 + '0');
				if (exponent >= 10)
					buf[charPos++] = (char)(exponent / 10 % 10 + '0');
				buf[charPos++] = (char)(exponent % 10 + '0');
			} // if
		}
		return charPos;
	}





	public static double parseDouble(String s) throws NumberFormatException {
		boolean negative = (s.charAt(0) == '-'); // Check if negative symbol.
		double result = 0.0D; // Starting value
		int index = s.indexOf('.');
		
		if(index > -1) {
		// Means the decimal place exists, add values to right of it
			int divisor = 1;
			for(int i=index+1;i<s.length();i++) {
				divisor *= 10;
				int curVal = (s.charAt(i)-48); // Convert char to int
				if(curVal > 9|curVal < 0)
					throw new NumberFormatException();
				result += ((double)curVal/divisor);
			}
		}
		else {
			index = s.length(); // If number string had no decimal
		}
			
		
		// Now add number characters to left of decimal
		int multiplier = 1;
		// TODO: Note: Sven removed a test here for no decimal place. Unsure if needed.
		// Check old version of SVN to see old line.
		int finish = negative ? 1 : 0; // Determine finishing position
		
		for(int i=index-1;i>= finish;i--) {
			int curVal = (s.charAt(i) - 48); // Convert char to int
			if(curVal > 9|curVal < 0)
				throw new NumberFormatException();
			result += (curVal * multiplier);
			multiplier *= 10;
		}	
		
		return negative ? -result : result;
	}
}
