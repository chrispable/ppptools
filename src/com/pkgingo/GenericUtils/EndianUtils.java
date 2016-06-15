package com.pkgingo.GenericUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;


public class EndianUtils {
	public static byte[] numberToBigEndianByteArray(int value)
	{
		int wordSize=numberOfBytesForANumber(value);
		byte[] b= numberToBigEndianByteArray(value, wordSize);
		return b;
	}
	
	public static byte[] numberToLittleEndianByteArray(int value)
	{
		int wordSize=numberOfBytesForANumber(value);
		byte[] b= numberToLittleEndianByteArray(value, wordSize);
		return b;
	}
	
	public static byte[] numberToBigEndianByteArray(int value, int wordSizeInBytes)
	{
		byte[] barray=null;
		ByteBuffer b = null;
		
		switch (wordSizeInBytes)
		{
		case 1:
			b = ByteBuffer.allocate(1);
			b.put((byte) (value & 0xFF));
			//DataUtils.DebugPrintln("Put byte " + (byte) (value & 0xFF));
			break;
		case 2:
			b = ByteBuffer.allocate(2);
			b.putShort((short) (value & 0xFFFF));
			//DataUtils.DebugPrintln("Put short " + (short) (value & 0xFFFF));
			break;
		case 3:
		case 4:
			b = ByteBuffer.allocate(4);
			b.putInt((int) (value & 0xFFFFFFFF));
			//DataUtils.DebugPrintln("Put int " + (int) (value & 0xFFFFFFFF));
			break;
		default:
			return null;
		}
		
		barray=b.array();

		return barray;
	}
	
	public static byte[] numberToLittleEndianByteArray(int value, int wordSizeInBytes)
	{
		byte[] barray=null;
		ByteBuffer b = null;
		
		switch (wordSizeInBytes)
		{
		case 1:
			b = ByteBuffer.allocate(1);
			b.put((byte) (value & 0xFF));
			//DataUtils.DebugPrintln("Put byte " + (byte) (value & 0xFF));
			break;
		case 2:
			b = ByteBuffer.allocate(2);
			b.putShort((short) (value & 0xFFFF));
			//DataUtils.DebugPrintln("Put short " + (short) (value & 0xFFFF));
			break;
		case 3:
		case 4:
			b = ByteBuffer.allocate(4);
			b.putInt((int) (value & 0xFFFFFFFF));
			//DataUtils.DebugPrintln("Put int " + (int) (value & 0xFFFFFFFF));
			break;
		default:
			return null;
		}

		
		b.order(ByteOrder.LITTLE_ENDIAN);
		barray=reverseByteArray(b.array());
		barray=Arrays.copyOfRange(barray, 0, wordSizeInBytes);
		return barray;
		
		
	}
	
	public static int bigEndianByteArrayToInt(byte[] data)
	{	
		data=reverseByteArray(data);
		return littleEndianByteArrayToInt(data);
	}
	
	public static int littleEndianByteArrayToInt(byte[] data)
	{
		ByteBuffer b = ByteBuffer.allocate(4);
		b.order(ByteOrder.LITTLE_ENDIAN);
		b.put(data);
		b.position(0);
		return b.getInt();
	}
	
	public static byte[] concatByteArrays(byte[] a, byte[] b) {
		   int aLen = a.length;
		   int bLen = b.length;
		   byte[] c= new byte[aLen+bLen];
		   System.arraycopy(a, 0, c, 0, aLen);
		   System.arraycopy(b, 0, c, aLen, bLen);
		   return c;
		}
	
	
	public static int numberOfBytesForANumber(int number)
	{
		
		if (number<0)
		{
			number=-1*number;
		}
		
		double bits=(Math.ceil(Math.log(number) / Math.log(2)));
		//DataUtils.DebugPrintln("bit size is: " + bits);
		int bytes=(int) Math.ceil(bits / 8.0);
		if (bytes == 3) bytes = 4;
		//DataUtils.DebugPrintln("byte size is: " + bytes);
		return bytes;
	}
	
	public static byte[] reverseByteArray(byte[] origData)
	{
		byte[] data=Arrays.copyOf(origData, origData.length);
	    for (int left = 0, right = data.length - 1; left < right; left++, right--)
	    {
	        // swap the values at the left and right indices
	        byte temp = data[left];
	        data[left]  = data[right];
	        data[right] = temp;
	    }
	    return data;
	}
}
