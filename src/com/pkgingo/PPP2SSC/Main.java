package com.pkgingo.PPP2SSC;

import com.pkgingo.GenericUtils.DataUtils;
import com.pkgingo.PPPUtils.PPPFile;
import com.pkgingo.SSCUtils.SSCFileCreator;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(DataUtils.programName());
		/*
		byte[] ex = {01,02};
		DataUtils.DebugPrintln("Original array " + bytesToHex(ex));
		
		int e = PPPChunk.bigEndianByteArrayToInt(ex);
		DataUtils.DebugPrintln("Original array read as BE int " + e);
		
		
		e = PPPChunk.littleEndianByteArrayToInt(ex);
		DataUtils.DebugPrintln("Original array read as LE int " + e);
		
		
		DataUtils.DebugPrintln("--------------");
		int t= -47;
		DataUtils.DebugPrintln("Bytes for "+t+": " + PPPChunk.numberOfBytesForANumber(t));
		ex=PPPChunk.numberToBigEndianByteArray(t,2);
		DataUtils.DebugPrintln("BE derived array " +bytesToHex(ex));
		ex=PPPChunk.numberToLittleEndianByteArray(t,2);
		DataUtils.DebugPrintln("LE derived array " + bytesToHex(ex));
		*/
		
		/*byte[] test = {0x05, (byte) 0xF8, 0x0C, 00, 00, 00, 00, 00, 00, 00, 00, 00};
		PPPLine a = new PPPLine(test);
		a.printDetails();
		a.buildRawData();
		a.printDetails();*/
		PPPFile a = new PPPFile("C:\\ppp\\SD\\01_C.BIN");
		
		/*
		int[] l = a.getMeasureMsLengths();
		DataUtils.DebugPrintln(Arrays.toString(l));
		
		double[] bpms = a.getMeasureBPMs();
		DataUtils.DebugPrintln(Arrays.toString(bpms));
		
		DataUtils.DebugPrintln(a.createBPMSegment());
		
		DataUtils.DebugPrintln("Measures - XXXXXXXXXXXXXXXX");
		a.quantizeToMeasurePosition(0);
		a.quantizeToMeasurePosition(1528);
		a.quantizeToMeasurePosition(3056);
		a.quantizeToMeasurePosition(4584);
		a.quantizeToMeasurePosition(6111);
		DataUtils.DebugPrintln("Arrows - XXXXXXXXXXXXXXXX");
		a.quantizeToMeasurePosition(6111);
		a.quantizeToMeasurePosition(6302);
		a.quantizeToMeasurePosition(6493);
		a.quantizeToMeasurePosition(6875);
		a.quantizeToMeasurePosition(7257);
		*/
		
		SSCFileCreator ssc= new SSCFileCreator();
		ssc.addParaParaChart(a);
		System.out.println(ssc.generateSSCFile());
		
	}
	
	
	
	

}
