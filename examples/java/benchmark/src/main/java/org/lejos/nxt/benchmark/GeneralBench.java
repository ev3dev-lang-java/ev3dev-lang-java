package org.lejos.nxt.benchmark;

import java.io.PrintStream;

/**
 * @author Bruce Boyes, based on code from Imsys (www.imsys.se)
 * 
 * @version 1.2
 * 
 *          <ul>
 *          <li>1.2 2009 Jul 27 skoehler: changes all over the place
 *          <li>1.1a 2003 Feb 19 bboyes adding byte math
 *          <li>1.0f 2003 Feb 18 bboyes making float and double more realistic,
 *          with actual float and double operands
 *          <li>1.0e 2003 Feb 18 bboyes total time and operations reporting
 *          <li>1.0d 2003 Feb 18 bboyes made int test better by using an int
 *          operand, added ops per second to report method.
 *          <li>1.0c 2003 Feb 17 bboyes attempting to improve math tests.
 *          Integer divide was just 3/3 then 1/1 forever, not very interesting.
 *          Using the iteration index in the test.
 *          <li>1.0 2003 Feb 08 bboyes Cleaning up this code a bit and fixing
 *          some problems such as a bug in arrayPerformance() which made byte
 *          and int array copies identical. Running on JStamp.
 *          <ul>
 *          <hr>
 *          This code is based on BenchMark.java from the Imsys SNAP examples.
 */
public class GeneralBench
{
	public GeneralBench()
	{
		// nothing
	}

	private void dummy1()
	{
		// dummy method
		return;
	}

	private static void dummy2()
	{
		// dummy method
		return;
	}

	private static final String VERSION = "1.2";
	private static final int[] PADVEC = { 8, 30, 6, 10 };

	/**
	 * Print out the time it took to complete a task Also calculate the rate per
	 * second = (count * 1000) / time <br>
	 * where time is in msec, hence the factor of 1000
	 * 
	 * @param count
	 *            How many iterations of the task
	 * @param task
	 *            String description of the task
	 * @param count2
	 *            How many items of unit
	 * @param unit
	 *            String description of the unit
	 * @param time
	 *            How many msec it took
	 */
	private static void report(long count, String task, long count2, String unit, long time)
	{
		BenchUtils.report(count, task, count2, unit, time, PADVEC);
	}
	
	public static void main(String args[])
	{
		// USB.usbEnable(2);
		System.out.println("GeneralBench " + VERSION);

		BenchUtils.cleanUp("At start");
		int chunkSize = 0x4000; // Must be power of 2

		int iterate = 200000;
		int tests = iterate / 10;

		int countAll = 0;
		long startAll = System.currentTimeMillis();

		countAll += benchArrayMCopyByte(iterate / chunkSize / 2, chunkSize);
		BenchUtils.cleanUp(null);

		countAll += benchArraySCopyByte(iterate * 100 / chunkSize, chunkSize);
		BenchUtils.cleanUp(null);

		countAll += benchArrayMCopyInt(iterate / chunkSize * 2, chunkSize / 4);
		BenchUtils.cleanUp(null);

		countAll += benchArraySCopyInt(iterate * 100 / chunkSize, chunkSize / 4);
		BenchUtils.cleanUp(null);

		countAll += benchArithByte(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithShort(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithChar(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithInt(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithLong(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithFloat(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchArithDouble(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchMethod(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchMethodStatic(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchMethodStaticNative(iterate);
		BenchUtils.cleanUp(null);

		countAll += benchStringConcat(tests / 10);
		BenchUtils.cleanUp(null);

		countAll += benchStringCompareEasy(tests);
		BenchUtils.cleanUp(null);

		countAll += benchStringCompareHard(tests / 20);
		BenchUtils.cleanUp(null);

		countAll += benchNewOp(tests);
		BenchUtils.cleanUp(null);

		long endAll = System.currentTimeMillis();
		report(countAll, "Total Loop Executions", countAll, "loops", endAll - startAll);
		System.out.println("Note: each Loop Execution includes multiple Java operations");
		System.out.flush();
	}

	/**
	 * 
	 * @param count
	 * @param chunkSize
	 */
	private static int benchArrayMCopyByte(int count, int chunkSize)
	{
		byte b1[] = new byte[chunkSize];
		byte b2[] = new byte[chunkSize];

		long nullTime = BenchUtils.getIterationTime(chunkSize);

		// byte array copy
		long start = System.currentTimeMillis();		
		for (int i = 0; i < count; i++)
			for (int j = 0; j < chunkSize; j++)
				b1[j] = b2[j];
		long end = System.currentTimeMillis();

		report(count, "byte["+chunkSize+"] manual copies", count * chunkSize, "bytes", end - start - nullTime);
		
		return count * chunkSize;
	}

	/**
	 * 
	 * @param count
	 * @param chunkSize
	 */
	private static int benchArrayMCopyInt(int count, int chunkSize)
	{
		int i1[] = new int[chunkSize];
		int i2[] = new int[chunkSize];

		long nullTime = BenchUtils.getIterationTime(chunkSize);

		// int array access/copy
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			for (int j = 0; j < chunkSize; j++)
				i1[j] = i2[j];
		long end = System.currentTimeMillis();

		report(count, "int["+chunkSize+"] manual copies", count * chunkSize * 4, "bytes", end - start - nullTime);
		
		return count * chunkSize;
	}

	/**
	 * We want to "touch" the same number of array elements as in the array
	 * access test, but using System.arraycopy
	 * 
	 * @param count
	 * @param chunkSize
	 */
	private static int benchArraySCopyByte(int count, int chunkSize)
	{
		byte b1[] = new byte[chunkSize];
		byte b2[] = new byte[chunkSize];
		
		long nullTime = BenchUtils.getIterationTime(count);

		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			System.arraycopy(b1, 0, b2, 0, chunkSize);
		long end = System.currentTimeMillis();

		report(count, "byte["+chunkSize+"] arraycopies", count * chunkSize, "bytes", end - start - nullTime);
		return count;
	}

	private static int benchArraySCopyInt(int count, int chunkSize)
	{
		int i1[] = new int[chunkSize];
		int i2[] = new int[chunkSize];

		long nullTime = BenchUtils.getIterationTime(count);

		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			System.arraycopy(i1, 0, i2, 0, chunkSize);
		long end = System.currentTimeMillis();

		report(count, "int["+chunkSize+"] arraycopies", count * chunkSize * 4, "bytes", end - start - nullTime);
		return count;
	}

	private static int benchStringConcat(int count) 
	{
		String s1 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		String s2 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		String s;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			s = s1 + s2;
		end = System.currentTimeMillis();

		report(count, "string concats", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchStringCompareEasy(int count) 
	{
		String s1 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		String s2 = "Abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		boolean b;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			b = s1.equals(s2);
		end = System.currentTimeMillis();

		report(count, "string compares (easy)", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchStringCompareHard(int count) 
	{
		String s1 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		String s2 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz";
		boolean b;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			b = s1.equals(s2);
		end = System.currentTimeMillis();

		report(count, "string compares (hard)", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchNewOp(int count)
	{
		long nullTime = BenchUtils.getIterationTime(count);
		long start = 0, end = 0;

		// Object creations
		try
		{
			start = System.currentTimeMillis();
			for (int i = 0; i < count; i++)
				new GeneralBench();
			end = System.currentTimeMillis();
		}
		catch (Exception e)
		{
			BenchUtils.cleanUp(e.toString());
		}

		report(count, "object creations", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchMethod(int count)
	{
		long nullTime = BenchUtils.getIterationTime(count);
		GeneralBench b = new GeneralBench();

		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			b.dummy1();
		long end = System.currentTimeMillis();

		report(count, "method calls", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchMethodStatic(int count)
	{
		long nullTime = BenchUtils.getIterationTime(count);

		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			dummy2();
		long end = System.currentTimeMillis();

		report(count, "static method calls", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchMethodStaticNative(int count)
	{
		long nullTime = BenchUtils.getIterationTime(count);

		// Function calls
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			System.out.print("");
			//NXT.getFirmwareRevision();
		long end = System.currentTimeMillis();

		report(count, "native static method calls", count, "ops", end - start - nullTime);
		return count;
	}

	private static int benchArithDouble(int count)
	{
		double a, b, c;
		
		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Double Add
		a = 0x7777777777777777p0;
		b = 0x1111111111111111p0;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a + b;
		end = System.currentTimeMillis();
		report(count, "double add", count, "ops", end - start - nullTime);

		// Double sub
		a = 0x8888888888888888p0;
		b = 0x1111111111111111p0;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a - b;
		end = System.currentTimeMillis();
		report(count, "double sub", count, "ops", end - start - nullTime);

		// Double Mul
		a = 0x0F0F0F0F0F0F0F0Fp0;
		b = 0x1111111111111111p0;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a * b;
		end = System.currentTimeMillis();
		report(count, "double mul", count, "ops", end - start - nullTime);

		// Double Div
		a = 0xFEFEFEFEFEFEFEFEp0;
		b = 0x0E0E0E0E0E0E0E0Ep0;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a / b;
		end = System.currentTimeMillis();
		report(count, "double div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	private static int benchArithFloat(int count)
	{
		float a, b, c;
		
		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Float Add
		a = 0x77777777p0f;
		b = 0x11111111p0f;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a + b;
		end = System.currentTimeMillis();
		report(count, "float add", count, "ops", end - start - nullTime);

		// Float sub
		a = 0x88888888p0f;
		b = 0x11111111p0f;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a - b;
		end = System.currentTimeMillis();
		report(count, "float sub", count, "ops", end - start - nullTime);

		// Float Mul
		a = 0x0F0F0F0Fp0f;
		b = 0x11111111p0f;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a * b;
		end = System.currentTimeMillis();
		report(count, "float mul", count, "ops", end - start - nullTime);

		// Float Div
		a = 0xFEFEFEFEp0f;
		b = 0x0E0E0E0Ep0f;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a / b;
		end = System.currentTimeMillis();
		report(count, "float div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * int primitive performance: add, sub, mul, div. I recoded this to use the
	 * index in the calculation and an odd 32-bit constant as the other operand.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithInt(int count)
	{
		int a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Integer Add
		a = 0x77777777;
		b = 0x11111111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a + b;
		end = System.currentTimeMillis();
		report(count, "int add", count, "ops", end - start - nullTime);

		// Integer sub
		a = 0x88888888;
		b = 0x11111111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a - b;
		end = System.currentTimeMillis();
		report(count, "int sub", count, "ops", end - start - nullTime);

		// Integer Mul
		a = 0x0F0F0F0F;
		b = 0x11111111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a * b;
		end = System.currentTimeMillis();
		report(count, "int mul", count, "ops", end - start - nullTime);

		// Integer Div
		a = 0xFEFEFEFE;
		b = 0x0E0E0E0E;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a / b;
		end = System.currentTimeMillis();
		report(count, "int div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * int primitive performance: add, sub, mul, div. I recoded this to use the
	 * index in the calculation and an odd 32-bit constant as the other operand.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithLong(int count)
	{
		long a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Integer Add
		a = 0x7777777777777777L;
		b = 0x1111111111111111L;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			// j = j + j;
			c = a + b;
		end = System.currentTimeMillis();
		report(count, "long add", count, "ops", end - start - nullTime);

		// Integer sub
		a = 0x8888888888888888L;
		b = 0x1111111111111111L;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			// j = j - i;
			c = a - b;
		end = System.currentTimeMillis();
		report(count, "long sub", count, "ops", end - start - nullTime);

		// Integer Mul
		a = 0x0F0F0F0F0F0F0F0FL;
		b = 0x1111111111111111L;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = a * b;
		end = System.currentTimeMillis();
		report(count, "long mul", count, "ops", end - start - nullTime);

		// Integer Div
		a = 0xFEFEFEFEFEFEFEFEL;
		b = 0x0E0E0E0E0E0E0E0EL;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			// Imsys original code: j = j / j;
			c = a / b;
		end = System.currentTimeMillis();
		report(count, "long div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * char primitive performance: add, sub, mul, div.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithChar(int count)
	{
		char a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Add
		a = (char)0x7777;
		b = (char)0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (char) (a + b);
		end = System.currentTimeMillis();
		report(count, "char add", count, "ops", end - start - nullTime);

		// sub
		a = (char) 0x8888;
		b = (char) 0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (char) (a - b);
		end = System.currentTimeMillis();
		report(count, "char sub", count, "ops", end - start - nullTime);

		// Mul
		a = (char) 0x0F0F;
		b = (char) 0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (char) (a * b);
		end = System.currentTimeMillis();
		report(count, "char mul", count, "ops", end - start - nullTime);

		// Div
		a = (char) 0xFEFE;
		b = (char) 0x0E0E;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (char) (a / b);
		end = System.currentTimeMillis();
		report(count, "char div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * short primitive performance: add, sub, mul, div.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithShort(int count)
	{
		short a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Add
		a = (short)0x7777;
		b = (short)0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (short) (a + b);
		end = System.currentTimeMillis();
		report(count, "short add", count, "ops", end - start - nullTime);

		// sub
		a = (short) 0x8888;
		b = (short) 0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (short) (a - b);
		end = System.currentTimeMillis();
		report(count, "short sub", count, "ops", end - start - nullTime);

		// Mul
		a = (short) 0x0F0F;
		b = (short) 0x1111;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (short) (a * b);
		end = System.currentTimeMillis();
		report(count, "short mul", count, "ops", end - start - nullTime);

		// Div
		a = (short) 0xFEFE;
		b = (short) 0x0E0E;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (short) (a / b);
		end = System.currentTimeMillis();
		report(count, "short div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}

	/**
	 * byte primitive performance: add, sub, mul, div.
	 * 
	 * @param count
	 *            the number of iterations of each operation
	 */
	private static int benchArithByte(int count)
	{
		byte a, b, c;

		long nullTime = BenchUtils.getIterationTime(count);
		long start, end;

		// Add
		a = (byte)0x77;
		b = (byte)0x11;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (byte) (a + b);
		end = System.currentTimeMillis();
		report(count, "byte add", count, "ops", end - start - nullTime);

		// sub
		a = (byte) 0x88;
		b = (byte) 0x11;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (byte) (a - b);
		end = System.currentTimeMillis();
		report(count, "byte sub", count, "ops", end - start - nullTime);

		// Mul
		a = (byte) 0x0F;
		b = (byte) 0x11;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (byte) (a * b);
		end = System.currentTimeMillis();
		report(count, "byte mul", count, "ops", end - start - nullTime);

		// Div
		a = (byte) 0xFE;
		b = (byte) 0x0E;
		start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			c = (byte) (a / b);
		end = System.currentTimeMillis();
		report(count, "byte div", count, "ops", end - start - nullTime);
		
		return count * 4;
	}
}
