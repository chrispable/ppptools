package com.pkgingo.PPPUtils;
import java.util.Arrays;

import com.pkgingo.GenericUtils.DataUtils;
import com.pkgingo.GenericUtils.EndianUtils;

//Class that reads in / creates a PPP chart line 
public class PPPLine
{

	// raw data made or generated
	private byte[] rawData =
	{};

	// constructs in a line, in order of appearance, 12 bytes total
	private int msLineDelta = 0; // 2 bytes
	private PPPLineType lineType = PPPLineType.UNKNOWN; // 1 byte
	private int unknownA = 0;// 2 bytes
	private byte arrowColumn = -1;// 1 byte
	private int msEventLength = 0;// 2 bytes
	private int unknownB = 0;// 2 bytes
	private int unknownC = 0;// 2 bytes

	// endianness, the real game is always big endian... but reserved for future
	// use
	private PPPEndian endian = PPPEndian.BIG;

	// constructor, takes in a raw line, assumes big endian
	public PPPLine(byte[] chunk)
	{
		rawData = chunk;
		if (rawData.length > 12)
		{
			return;
		}
		parseRawData();
	}

	// constructor, takes in a raw line and an endian type endian
	public PPPLine(byte[] chunk, PPPEndian lineEndianType)
	{
		endian = lineEndianType;
		rawData = chunk;
		if (rawData.length > 12)
		{
			lineType = PPPLineType.HEADER;// the header should be a function of
											// the file construct, take it in
											// anyway but we cant do anything
											// with it
			return;
		}
		parseRawData();
	}

	// sets for the member functions, sets for multibyte parameters ASSUME
	// endian is properly set before sending data
	public void setRawDataBytes(byte[] rawdata)
	{
		rawData = rawdata;
	}

	public void setLineType(PPPLineType linetype)
	{
		lineType = linetype;
		buildRawData();
	}

	public void setArrowColumn(byte arrow)
	{
		arrowColumn = arrow;
		buildRawData();
	}

	public void setUnknownAInt(int unknowna)
	{
		unknownA = unknowna;
		buildRawData();
	}

	public void setUnknownABytes(byte[] unknowna)
	{
		int ua;
		if (endian == PPPEndian.BIG)
		{

			ua = EndianUtils.bigEndianByteArrayToInt(unknowna);
		}
		else
		{
			ua = EndianUtils.littleEndianByteArrayToInt(unknowna);
		}
		setUnknownAInt(ua);
	}

	public void setUnknownBInt(int unknownb)
	{
		unknownB = unknownb;
		buildRawData();
	}

	public void setUnknownBBytes(byte[] unknownb)
	{
		int ub;
		if (endian == PPPEndian.BIG)
		{

			ub = EndianUtils.bigEndianByteArrayToInt(unknownb);
		}
		else
		{
			ub = EndianUtils.littleEndianByteArrayToInt(unknownb);
		}
		setUnknownAInt(ub);
	}

	public void setUnknownCInt(int unknownc)
	{
		unknownC = unknownc;
		buildRawData();
	}

	public void setUnknownCBytes(byte[] unknownc)
	{
		int uc;
		if (endian == PPPEndian.BIG)
		{

			uc = EndianUtils.bigEndianByteArrayToInt(unknownc);
		}
		else
		{
			uc = EndianUtils.littleEndianByteArrayToInt(unknownc);
		}
		setUnknownCInt(uc);
	}

	public void setMsEventLengthInt(int mslength)
	{
		msEventLength = mslength;
		buildRawData();
	}

	public void setMsEventLengthBytes(byte[] mslength)
	{
		int ms;
		if (endian == PPPEndian.BIG)
		{

			ms = EndianUtils.bigEndianByteArrayToInt(mslength);
		}
		else
		{
			ms = EndianUtils.littleEndianByteArrayToInt(mslength);
		}
		setMsEventLengthInt(ms);
	}

	public void setMsLineDeltaInt(int mslinedelta)
	{
		msLineDelta = mslinedelta;
		buildRawData();
	}

	public void setMsLineDeltaBytes(byte[] mslinedelta)
	{
		int ms;
		if (endian == PPPEndian.BIG)
		{

			ms = EndianUtils.bigEndianByteArrayToInt(mslinedelta);
		}
		else
		{
			ms = EndianUtils.littleEndianByteArrayToInt(mslinedelta);
		}
		setMsLineDeltaInt(ms);
	}
	
	public void setEndian(PPPEndian lineEndian)
	{
		endian=lineEndian;
	}

	// gets for the member functions, gets for multibyte member returns ASSUME
	// endian is properly set before requesting data
	public byte[] getRawDataBytes()
	{
		return rawData;
	}

	public PPPLineType getLineType()
	{
		return lineType;
	}

	public byte getArrowColumn()
	{
		return arrowColumn;
	}

	public int getUnknownAInt()
	{
		return unknownA;
	}

	public byte[] getUnknownABytes()
	{
		if (endian == PPPEndian.BIG)
		{
			return EndianUtils.numberToBigEndianByteArray(unknownA, 2);
		}
		else
		{
			return EndianUtils.numberToLittleEndianByteArray(unknownA, 2);
		}
	}

	public int getUnknownBInt()
	{
		return unknownB;
	}

	public byte[] getUnknownBBytes()
	{
		if (endian == PPPEndian.BIG)
		{
			return EndianUtils.numberToBigEndianByteArray(unknownB, 2);
		}
		else
		{
			return EndianUtils.numberToLittleEndianByteArray(unknownB, 2);
		}
	}

	public int getUnknownCInt()
	{
		return unknownC;
	}

	public byte[] getUnknownCBytes()
	{
		if (endian == PPPEndian.BIG)
		{
			return EndianUtils.numberToBigEndianByteArray(unknownC, 2);
		}
		else
		{
			return EndianUtils.numberToLittleEndianByteArray(unknownC, 2);
		}
	}

	public byte[] getMsEventLengthBytes()
	{
		if (endian == PPPEndian.BIG)
		{
			return EndianUtils.numberToBigEndianByteArray(msEventLength, 2);
		}
		else
		{
			return EndianUtils.numberToLittleEndianByteArray(msEventLength, 2);
		}
	}

	public int getMsEventLengthInt()
	{
		return msEventLength;
	}

	public int getMsLineDeltaInt()
	{
		return msLineDelta;
	}

	public byte[] getMsLineDeltaBytes()
	{
		if (endian == PPPEndian.BIG)
		{
			return EndianUtils.numberToBigEndianByteArray(msLineDelta, 2);
		}
		else
		{
			return EndianUtils.numberToLittleEndianByteArray(msLineDelta, 2);
		}
	}

	// creates a raw data array by parsing saved values in the class members
	public void buildRawData()
	{
		// setup temporary containers
		byte[] a;
		byte[] b;
		byte[] type =
		{ 0 };
		byte[] col =
		{ 0 };

		// get the right value for the line type
		switch (lineType)
		{
			case ARROW:
				type[0] = 0x00;
				break;
			case BPM:
				type[0] = 0x04;
				break;
			case SONG_END:
				type[0] = 0x06;
				break;
			case B_EVENT:
				type[0] = 0x0B;
				break;
			case MEASURE_IDENTIFIER:
				type[0] = 0x0C;
				break;
			case E_EVENT:
				type[0] = 0x0E;
				break;
			case BGA:
				type[0] = 0x0F;
				break;
			case EOF:
				type[0] = (byte) 0xFF;
				break;
			default:
				type[0] = 0x7F;
				break;
		}
		// copy arrow column to the right place
		col[0] = arrowColumn;

		// build arrays based on member values and merge to a new raw data array
		// obviously use the right endian functions to do this based on big or
		// little endian
		if (endian == PPPEndian.BIG)
		{
			a = EndianUtils.numberToBigEndianByteArray(msLineDelta, 2);
			a = EndianUtils.concatByteArrays(a, type);
			b = EndianUtils.numberToBigEndianByteArray(unknownA, 2);
			a = EndianUtils.concatByteArrays(a, b);
			a = EndianUtils.concatByteArrays(a, col);
			b = EndianUtils.numberToBigEndianByteArray(msEventLength, 2);
			a = EndianUtils.concatByteArrays(a, b);
			b = EndianUtils.numberToBigEndianByteArray(unknownB, 2);
			a = EndianUtils.concatByteArrays(a, b);
			b = EndianUtils.numberToBigEndianByteArray(unknownC, 2);
			a = EndianUtils.concatByteArrays(a, b);
		}
		else
		{
			a = EndianUtils.numberToLittleEndianByteArray(msLineDelta, 2);
			a = EndianUtils.concatByteArrays(a, type);
			b = EndianUtils.numberToLittleEndianByteArray(unknownA, 2);
			a = EndianUtils.concatByteArrays(a, b);
			a = EndianUtils.concatByteArrays(a, col);
			b = EndianUtils.numberToLittleEndianByteArray(msEventLength, 2);
			a = EndianUtils.concatByteArrays(a, b);
			b = EndianUtils.numberToLittleEndianByteArray(unknownB, 2);
			a = EndianUtils.concatByteArrays(a, b);
			b = EndianUtils.numberToLittleEndianByteArray(unknownC, 2);
			a = EndianUtils.concatByteArrays(a, b);
		}

		// update the raw data member
		rawData = a;
	}

	// TODO: delete this?, this is a debugging thing
	public void printDetails()
	{
		DataUtils.DebugPrintln(DataUtils.bytesToHex(rawData));
		DataUtils.DebugPrintln(msLineDelta);
		DataUtils.DebugPrintln(lineType);
		DataUtils.DebugPrintln(unknownA);
		DataUtils.DebugPrintln(arrowColumn);
		DataUtils.DebugPrintln(msEventLength);
		DataUtils.DebugPrintln(unknownB);
		DataUtils.DebugPrintln(unknownC);
		DataUtils.DebugPrintln(endian);
	}

	// macro to parse all data in the raw data array
	private void parseRawData()
	{
		parseMs();
		parseChartType();
		parseUnknownA();
		parseColumn();
		parseEventLength();
		parseUnknownB();
		parseUnknownC();
	}

	// data parsers, they parse the appropriate place in the raw data array and
	// set the interpretted data
	private void parseMs()
	{
		if (endian == PPPEndian.BIG)
		{
			msLineDelta = EndianUtils.bigEndianByteArrayToInt(Arrays
					.copyOfRange(rawData, 0, 2));
		}
		else
		{
			msLineDelta = EndianUtils.littleEndianByteArrayToInt(Arrays
					.copyOfRange(rawData, 0, 2));
		}
	}

	private void parseColumn()
	{
		arrowColumn = rawData[5];
	}

	private void parseEventLength()
	{
		if (endian == PPPEndian.BIG)
		{
			msEventLength = EndianUtils.bigEndianByteArrayToInt(Arrays
					.copyOfRange(rawData, 6, 8));
		}
		else
		{
			msEventLength = EndianUtils.littleEndianByteArrayToInt(Arrays
					.copyOfRange(rawData, 6, 8));
		}
	}

	private void parseUnknownA()
	{
		if (endian == PPPEndian.BIG)
		{
			unknownA = EndianUtils.bigEndianByteArrayToInt(Arrays.copyOfRange(
					rawData, 3, 5));
		}
		else
		{
			unknownA = EndianUtils.littleEndianByteArrayToInt(Arrays
					.copyOfRange(rawData, 3, 5));
		}
	}

	private void parseUnknownB()
	{
		if (endian == PPPEndian.BIG)
		{
			unknownB = EndianUtils.bigEndianByteArrayToInt(Arrays.copyOfRange(
					rawData, 8, 10));
		}
		else
		{
			unknownB = EndianUtils.littleEndianByteArrayToInt(Arrays
					.copyOfRange(rawData, 8, 10));
		}
	}

	private void parseUnknownC()
	{
		if (endian == PPPEndian.BIG)
		{
			unknownC = EndianUtils.bigEndianByteArrayToInt(Arrays.copyOfRange(
					rawData, 10, 12));
		}
		else
		{
			unknownC = EndianUtils.littleEndianByteArrayToInt(Arrays
					.copyOfRange(rawData, 10, 12));
		}
	}

	private void parseChartType()
	{
		switch (rawData[2])
		{
			case 0x00:
				lineType = PPPLineType.ARROW;
				break;
			case 0x04:
				lineType = PPPLineType.BPM;
				break;
			case 0x06:
				lineType = PPPLineType.SONG_END;
				break;
			case 0x0B:
				lineType = PPPLineType.B_EVENT;
				break;
			case 0x0C:
				lineType = PPPLineType.MEASURE_IDENTIFIER;
				break;
			case 0x0E:
				lineType = PPPLineType.E_EVENT;
				break;
			case 0x0F:
				lineType = PPPLineType.BGA;
				break;
			case (byte) 0xFF:
				lineType = PPPLineType.EOF;
				break;
			default:
				lineType = PPPLineType.UNKNOWN;
				break;
		}
	}

}
